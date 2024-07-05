package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;

public interface AccountDao {

    Account getAccountByUserId(int user_id);
    Account moveMoney(double amountToTransfer, int withdraw_id, int deposit_id);
}
