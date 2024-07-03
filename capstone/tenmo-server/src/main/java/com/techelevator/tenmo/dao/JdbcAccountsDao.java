package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;



@Component
public class JdbcAccountsDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    private final String SELECT_ACCOUNT = "SELECT a.account_id, a.user_id, a.balance FROM accounts a";

    public JdbcAccountsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Account getAccountById(int user_id) {
        Account account = null;
        String sql = SELECT_ACCOUNT + "WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
            if (results.next()) {
                account = mapRowToAccounts(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return account;
    }


        @Override                           // how to generate a transaction in transfer table
    public Account updatedbalance(Account account) {
        Account updatedAccount = null;
        String sql = "START TRANSACTION; UPDATE account SET balance = balance - ? WHERE account_id  IN (SELECT withdraw_id FROM transfer WHERE withdraw_id =?);" +
                "UPDATE account SET balance = balance + ? WHERE account_id IN (SELECT deposit_id FROM transfer WHERE deposit_id =?);COMMIT;";
        try {
            int numberOfRowsAffected = jdbcTemplate.update(sql,updatedAccount.getBalance());
            if (numberOfRowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedAccount = getAccountById(account.getUser_id());
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return updatedAccount;
    }


    private Account mapRowToAccounts(SqlRowSet results) {
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUser_id(results.getInt("user_id"));
        account.setBalance(results.getDouble("balance"));
        return account;

    }


}
