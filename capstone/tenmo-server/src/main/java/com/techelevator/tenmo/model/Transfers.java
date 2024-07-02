package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transfers {
    private int transfer_id;
    private int deposit_account_id;
    private int withdraw_account_id;

    private LocalTime transfer_time;
    private LocalDate transfer_date;

    private BigDecimal amount_transfered;

    private String status;

    public Transfers(int transfer_id, int deposit_account_id, int withdraw_account_id, LocalTime transfer_time, LocalDate transfer_date, BigDecimal amount_transfered, String status) {
        this.transfer_id = transfer_id;
        this.deposit_account_id = deposit_account_id;
        this.withdraw_account_id = withdraw_account_id;
        this.transfer_time = transfer_time;
        this.transfer_date = transfer_date;
        this.amount_transfered = amount_transfered;
        this.status = status;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public void setDeposit_account_id(int deposit_account_id) {
        this.deposit_account_id = deposit_account_id;
    }

    public void setWithdraw_account_id(int withdraw_account_id) {
        this.withdraw_account_id = withdraw_account_id;
    }

    public void setTransfer_time(LocalTime transfer_time) {
        this.transfer_time = transfer_time;
    }

    public void setTransfer_date(LocalDate transfer_date) {
        this.transfer_date = transfer_date;
    }

    public void setAmount_transfered(BigDecimal amount_transfered) {
        this.amount_transfered = amount_transfered;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTransfer_id() {
        return transfer_id;
    }

    public int getDeposit_account_id() {
        return deposit_account_id;
    }

    public int getWithdraw_account_id() {
        return withdraw_account_id;
    }

    public LocalTime getTransfer_time() {
        return transfer_time;
    }

    public LocalDate getTransfer_date() {
        return transfer_date;
    }

    public BigDecimal getAmount_transfered() {
        return amount_transfered;
    }

    public String getStatus() {
        return status;
    }


}
