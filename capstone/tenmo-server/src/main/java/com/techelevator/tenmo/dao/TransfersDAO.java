package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

import java.util.List;

public interface TransfersDAO {

    List<Transfers> allOfUsersTransfers(int id);

    Transfers createTransfer(Transfers transfers);

    Transfers getTransferById(int id);
    List <Transfers> getTransferByStatusPending();
    Transfers updateTransferStatusToApproved(Transfers transfers);


}



