package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TransferController {

    private TransfersDAO transfersDAO;

    public TransferController(TransfersDAO transfersDAO) {
        this.transfersDAO = transfersDAO;
    }
    @ResponseStatus (HttpStatus.CREATED)
    @PostMapping("/user/transfer")
    public Transfers add(@Valid @RequestBody Transfers transfers) {
        return transfersDAO.createTransfer(transfers);

    }

    @GetMapping("/user/transfer/{id}")
    public Transfers transfersById (@PathVariable int id) {
        Transfers transfers = transfersDAO.getTransferById(id);
        if (transfers == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
        return transfers;

    }
    @PutMapping("/user/{id}/transfer")
    public List<Transfers> getAllTransfersById (@PathVariable int id) {
        return transfersDAO.allOfUsersTransfers(id);

    }
    @GetMapping("/user/transfer? status = Pending")
    public List<Transfers> getTransferByStatusPending(){
        return transfersDAO.getTransferByStatusPending();
    }



    @PutMapping("user/transfer? status = Pending")
    public void updateTransferStatusToApproved(@PathVariable int status_id,@RequestBody Transfers transfers){
        transfers.setStatus_id(transfers.getStatus_id());
        try{
            this.transfersDAO.updateTransferStatusToApproved(transfers);
        }catch(DaoException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Unsuccesful Transfer");
        }
    }





}
