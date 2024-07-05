package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private int accountId;

    private int user_id;
    private BigDecimal balance;

public Account (){}

    public Account(int accountId, BigDecimal balance,int user_id){
        this.accountId = accountId;
        this.balance = balance;
        this.user_id = user_id;
    }
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}






