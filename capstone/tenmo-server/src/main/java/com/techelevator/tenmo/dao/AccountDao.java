package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;

public interface AccountDao {

    Account getAccountById(int user_id);
    Account updatedbalance(Account account);
}
