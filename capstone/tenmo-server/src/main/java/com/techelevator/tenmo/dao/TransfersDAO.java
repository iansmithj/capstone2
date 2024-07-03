package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfers;

import java.util.List;

public interface TransfersDAO {

    List<Transfers> AllOfUsersTransfers(int id);

    Transfers createTransfer(Transfers transfers);

    Transfers getTransferById(int id);
}
