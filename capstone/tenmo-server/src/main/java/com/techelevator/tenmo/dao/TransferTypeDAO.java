package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;

import java.util.List;

public interface TransferTypeDAO {
    List <TransferType> listTransfersBySend(String type);

    List <TransferType> listTransfersByRequest(String type);

    TransferType getTransferBySend(String type);

    TransferType getTransferByRequest(String type);



}
