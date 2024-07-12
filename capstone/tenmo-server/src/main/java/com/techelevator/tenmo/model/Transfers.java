package com.techelevator.tenmo.model;



import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Transfers {
    private int transfer_id;
    private int deposit_account_id;
    private int withdraw_account_id;
    private int type_id;
    private LocalDateTime transfer_date_time;

    private BigDecimal amount_transferred;

    private int status_id;
     public Transfers(){}

    public Transfers(int transfer_id, int deposit_account_id, int withdraw_account_id, int type_id, LocalDateTime transfer_date_time, BigDecimal amount_transferred, int status_id) {
        this.transfer_id = transfer_id;
        this.deposit_account_id = deposit_account_id;
        this.withdraw_account_id = withdraw_account_id;
        this.type_id = type_id;
        this.transfer_date_time = transfer_date_time;
        this.amount_transferred = amount_transferred;
        this.status_id = status_id;
    }




    public int getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(int transfer_id) {
        this.transfer_id = transfer_id;
    }

    public int getDeposit_account_id() {
        return deposit_account_id;
    }

    public void setDeposit_account_id(int deposit_account_id) {
        this.deposit_account_id = deposit_account_id;
    }

    public int getWithdraw_account_id() {
        return withdraw_account_id;
    }

    public void setWithdraw_account_id(int withdraw_account_id) {
        this.withdraw_account_id = withdraw_account_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public LocalDateTime getTransfer_date_time() {
        return transfer_date_time;
    }

    public void setTransfer_date_time(LocalDateTime transfer_date_time) {
        this.transfer_date_time = transfer_date_time;
    }

    public BigDecimal getAmount_transferred() {
        return amount_transferred;
    }

    public void setAmount_transferred(BigDecimal amount_transferred) {
        this.amount_transferred = amount_transferred;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }
}
