package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private int account_Id;

    private int user_id;
    private BigDecimal balance;

public Account (){}

    public Account(int account_Id, BigDecimal balance,int user_id){
        this.account_Id = account_Id;
        this.balance = balance;
        this.user_id = user_id;
    }
    public int getAccountId() {
        return account_Id;
    }

    public void setAccountId(int accountId) {
        this.account_Id = accountId;
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






