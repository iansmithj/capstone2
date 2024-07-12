package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransfersDAO {
    private final JdbcTemplate jdbcTemplate;
    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final String SELECT_TRANSFER = " SELECT t.transfer_id, t.type_id, t.status_id, t.deposit_id, t.withdraw_id, t.transfer_date_time, t.amount_transferred FROM transfer t ";

    @Override   // 5. As an authenticated user of the system, I need to be able to see transfers I have sent or received.
    public List<Transfers> getTransfersByUserId(int id) {
        List<Transfers> allTransfers = new ArrayList<>();
        String sql = SELECT_TRANSFER +
        "WHERE deposit_id IN (SELECT account_id FROM account WHERE user_id = ?) " +
        "OR withdraw_id IN (SELECT account_id FROM account WHERE user_id = ?); ";
        try{

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, id);
            while (results.next()) {
                Transfers transfersResult = mapRowToTransfer(results);
                allTransfers.add(transfersResult);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return allTransfers;
    }


//    @Override
//    public Transfers getUsersPendingTransfers(int id) { //askjdhcjgehdwkaudhukdhuekdhcuewdhiuwhewudhew
//        List<Transfers> allTransfers = new ArrayList<>();
//        String sql = "SELECT transfer_id, type_id, status_id, deposit_id, withdraw_id, transfer_date_time, amount_transferred" +
//                "FROM transfer WHERE deposit_id = ?;";
//        try{
//
//            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
//            while (results.next()) {
//                Transfers transfersResult = mapRowToTransfer(results);
//                allTransfers.add(transfersResult);
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        }
//
//        return null;
//    }
    @Override
    public Transfers createTransfer(TransferDTO transferDTO, int status, int type) {
        Transfers createdTransfer = null;
        final String sql = "INSERT INTO transfer(  type_id, status_id, deposit_id, withdraw_id, transfer_date_time, amount_transferred) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING transfer_id;";
        try{
             int newTransfer_id = jdbcTemplate.queryForObject(sql, int.class,
                    type,
                    status,
                    transferDTO.getUserTo(),
                    transferDTO.getUserFrom(),
                    LocalDateTime.now(),
                     transferDTO.getAmount());
            createdTransfer = this.getTransferById(newTransfer_id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return createdTransfer;

    }



    @Override
    public Transfers getTransferById(int id) {
            Transfers transfers = null;
            String sql = SELECT_TRANSFER +
                         "WHERE transfer_id = ?;";
        try{

                SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
                if (results.next()) {
                    transfers = mapRowToTransfer(results);
                }
            } catch (CannotGetJdbcConnectionException e) {
                throw new DaoException("Unable to connect to server or database", e);
            }

            return transfers;
        }

        @Override
        public List<Transfers> getUsersPendingtransfers(int id){
        List<Transfers> transfers = new ArrayList<>();
        String sql = SELECT_TRANSFER +
            "JOIN transfer_status ON t.status_id = transfer_status.status_id " +
            "WHERE transfer_status.status = 'Pending' " +
            "AND ( deposit_id IN (SELECT account_id FROM account WHERE user_id = ?) " +
                "OR withdraw_id IN (SELECT account_id FROM account WHERE user_id = ?)); ";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, id);
            while (results.next()) {
                Transfers transfersResult = mapRowToTransfer(results);
                transfers.add(transfersResult);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw  new DaoException("Unable to connect to server");
        }
        return transfers;
        }

    @Override
    public Transfers updateTransferStatusToApproved(Transfers transfers) {
        Transfers updatedStatustoApprove = null;
        String sql = "UPDATE transfer SET status_id = ? WHERE status_id IN (SELECT status_id FROM transfer WHERE status = Pending);";
        try {
            int numberOfRowsAffected = jdbcTemplate.update(sql,transfers.getStatus_id());
            if (numberOfRowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedStatustoApprove = getTransferById(transfers.getStatus_id());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return updatedStatustoApprove;
    }


    private Transfers mapRowToTransfer(SqlRowSet results){
        Transfers transfers = new Transfers();
        transfers.setTransfer_id(results.getInt("transfer_id"));
        transfers.setType_id(results.getInt("type_id"));
        transfers.setStatus_id(results.getInt("status_id"));
        transfers.setDeposit_account_id(results.getInt("deposit_id"));
        transfers.setWithdraw_account_id(results.getInt("withdraw_id"));
        transfers.setTransfer_date_time(results.getTimestamp("transfer_date_time").toLocalDateTime());
        transfers.setAmount_transferred(results.getBigDecimal("amount_transferred"));

        return transfers;
    }
    //    @Override
//    public Transfers moveMoney(BigDecimal amountToTransfer, int withdraw_id, int deposit_id) {
//        Transfers updatedAccount = null;
//        String sql = " UPDATE account SET balance = balance - ? WHERE account_id  IN (SELECT withdraw_id FROM transfer WHERE withdraw_id =?);" +
//                "UPDATE account SET balance = balance + ? WHERE account_id IN (SELECT deposit_id FROM transfer WHERE deposit_id =?);";
//        try {
//            int numberOfRowsAffected = jdbcTemplate.update(sql,amountToTransfer, withdraw_id,amountToTransfer,deposit_id);
//            if (numberOfRowsAffected == 0) {
//                throw new DaoException("Zero rows affected, expected at least one");
//            } else {
//                updatedAccount = getTransfersByUserId());
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            throw new DaoException("Unable to connect to server or database", e);
//        } catch (DataIntegrityViolationException e) {
//            throw new DaoException("Data integrity violation", e);
//        }
//
//        return updatedAccount;
//    }
}
