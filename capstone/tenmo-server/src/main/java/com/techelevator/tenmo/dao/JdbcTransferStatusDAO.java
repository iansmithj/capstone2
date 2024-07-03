package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferStatusDAO implements TransferStatusDAO {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferStatusDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public List<TransferStatus> listTransfersByPending(String status) {
        List<TransferStatus> transferStatuses = new ArrayList<>();
        String sql "";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                TransferStatus transferStatus = mapRowToTransferStatus(results);
                transferStatuses.add(transferStatus);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
return transferStatuses;
    }

    private TransferStatus mapRowToTransferStatus(SqlRowSet results) {
        return null;
    }

}
