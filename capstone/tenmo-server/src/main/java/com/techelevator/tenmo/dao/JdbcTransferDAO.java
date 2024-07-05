package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransfersDAO {
    private final JdbcTemplate jdbcTemplate;
    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfers> allOfUsersTransfers(int id) {
        List<Transfers> allTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, type_id, status_id, deposit_id, withdraw_id, transfer_date_time, amount_transferred" +
        "FROM public.transfer WHERE withdraw_id = ? OR deposit_id = ?;";
        try{

            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
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
    public Transfers createTransfer(Transfers transfers) {
        Transfers createdTransfer = null;
        final String sql = "INSERT INTO transfer(  type_id, status_id, deposit_id, withdraw_id, transfer_date_time, amount_transferred)"+
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING transfer_id;";
        try{
            final int newTransfer_id = jdbcTemplate.queryForObject(sql, int.class,
                    transfers.getType_id(),
                    transfers.getStatus_id(),
                    transfers.getDeposit_account_id(),
                    transfers.getWithdraw_account_id(),
                    transfers.getTransfer_date_time(),
                    transfers.getAmount_transfered());
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
            String sql = "SELECT transfer_id, type_id, status_id, deposit_id, withdraw_id, transfer_date_time, amount_transferred"+
                         "FROM transfer WHERE transfer_id = ?;";
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
        public List<Transfers> getTransferByStatusPending(){////rgrggregrgergegregregegr
        List<Transfers> transfers = new ArrayList<>();
        String sql = "SELECT * FROM transfer WHERE status_id IN (SELECT status_id FROM transfer_status = Pending;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            transfers.add(mapRowToTransfer(results));
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
        transfers.setAmount_transfered(results.getBigDecimal("amount_transferred"));

        return transfers;
    }
}
