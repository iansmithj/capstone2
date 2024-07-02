package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Accounts {
    private int accountId;

    private int user_id;

    private BigDecimal balance;


    //public Accounts(){ }
    public Accounts(int accountId, int user_id, BigDecimal balance){
        this.accountId = accountId;
        this.user_id = user_id;
        this.balance = balance;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getUser_id() {
        return user_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }


}
