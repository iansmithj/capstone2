package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;


@Component
public class JdbcAccountsDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    private final String SELECT_ACCOUNT = "SELECT a.account_id, a.user_id, a.balance FROM account a";

    public JdbcAccountsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Account getAccountByUserId(int id) {
        Account account = null;
        String sql = SELECT_ACCOUNT + " WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                account = mapRowToAccounts(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return account;
    }


        @Override
        public Account moveMoney(BigDecimal amountToTransfer, int withdraw_id, int deposit_id) {
        Account updatedAccount = null;
        String sql = " UPDATE account SET balance = balance - ? WHERE account_id  IN (SELECT withdraw_id FROM transfer WHERE withdraw_id =?);" +
                "UPDATE account SET balance = balance + ? WHERE account_id IN (SELECT deposit_id FROM transfer WHERE deposit_id =?);";
        try {
            int numberOfRowsAffected = jdbcTemplate.update(sql,amountToTransfer, withdraw_id,amountToTransfer,deposit_id);
            if (numberOfRowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            } else {
                updatedAccount = getAccountByUserId(updatedAccount.getAccountId());
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
        account.setBalance(results.getBigDecimal("balance"));
        return account;

    }


}
