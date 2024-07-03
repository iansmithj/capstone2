package com.techelevator.tenmo.model;

public class Account {
    private int accountId;

    private int user_id;
    private  double balance;

public Account (){}

    public Account(int accountId, double balance,int user_id){
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }
}






