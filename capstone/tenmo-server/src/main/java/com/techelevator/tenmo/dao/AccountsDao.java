package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Accounts;

import java.math.BigDecimal;

public interface AccountsDao {

    Accounts getAccountById(int id);

    Accounts getAccountsByUserId(int id);

    Accounts getBalance(BigDecimal balance);
}
