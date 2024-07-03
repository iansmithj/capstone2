package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDAO implements TransfersDAO {
    private final JdbcTemplate jdbcTemplate;
    public JdbcTransferDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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

    @Override
    public Transfers getUsersPendingTransfers(int id) {
        List<Transfers> allTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, type_id, status_id, deposit_id, withdraw_id, transfer_date_time, amount_transferred" +
                "FROM transfer WHERE deposit_id = ?;";
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

    @Override // ask questions about this
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

    private Transfers mapRowToTransfer(SqlRowSet results){
        Transfers transfers = new Transfers();
        transfers.setTransfer_id(results.getInt("transfer_id"));
        transfers.setType_id(results.getInt("type_id"));
        transfers.setStatus_id(results.getInt("status_id"));
        transfers.setDeposit_account_id(results.getInt("deposit_id"));
        transfers.setWithdraw_account_id(results.getInt("deposit_id"));
        transfers.setTransfer_date_time(results.getTimestamp("transfer_date_time").toLocalDateTime());
        transfers.setAmount_transfered(results.getDouble("amount_transferred"));

        return transfers;
    }
}
