package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account getAccountByUserId(int user_id);

    Account moveMoney(BigDecimal amountToTransfer, int withdraw_id, int deposit_id);
}
