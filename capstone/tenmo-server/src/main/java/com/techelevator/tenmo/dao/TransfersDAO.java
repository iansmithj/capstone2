package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.Transfers;

import java.math.BigDecimal;
import java.util.List;

public interface TransfersDAO {

    List<Transfers> getTransfersByUserId(int id);

    Transfers createTransfer(TransferDTO transferDTO, int status,int type);

    Transfers getTransferById(int id);
    List <Transfers> getUsersPendingtransfers(int id);
    Transfers updateTransferStatusToApproved(Transfers transfers);
//    Transfers moveMoney(BigDecimal amountToTransfer, int withdraw_id, int deposit_id);


}



