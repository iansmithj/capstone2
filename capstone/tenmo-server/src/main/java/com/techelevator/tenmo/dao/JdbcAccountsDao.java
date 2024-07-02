package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Accounts;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountsDao implements AccountsDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Accounts getAccountById(int id) {
        Accounts accounts = null;
        String sql = "SELECT account_id, user_id, balance " +
                "FROM accounts WHERE account_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                accounts = mapRowToAccounts(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return accounts;
    }

    @Override
    public Accounts getAccountsByUserId(int id) {

        Accounts accounts = null;
        String sql = "SELECT account_id, user_id, balance " +
                "FROM accounts WHERE user_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                accounts = mapRowToAccounts(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return accounts;
    }

    @Override
    public Accounts getBalance(BigDecimal balance) {
return null;
    }

        //        Accounts accounts = null;
//        String sql = "SELECT account_id, user_id, balance " +
//                "FROM accounts WHERE account_id";
//
//    }

    private Accounts mapRowToAccounts(SqlRowSet results) {
        Accounts accounts = new Accounts();
        accounts.setAccountId(results.getInt("account_id"));
        accounts.setUser_id(Integer.parseInt(results.getString("user_id")));
        accounts.setBalance(results.getBigDecimal("balance"));
        return accounts;

    }


}
