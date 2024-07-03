package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;

import java.util.List;

public interface TransferStatusDAO {

    List <TransferStatus> listTransfersByPending(String status);

    TransferStatus getTransferByApproved(String status);
    TransferStatus getTransferByRejected(String status);
    TransferStatus getTransferByPending(String status);
}
