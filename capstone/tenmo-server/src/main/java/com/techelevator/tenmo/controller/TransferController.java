package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransfersDAO transfersDAO;

    public TransferController(TransfersDAO transfersDAO) {
        this.transfersDAO = transfersDAO;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
   public List<Transfers> getAllTransfersById (@PathVariable int id) {
         return transfersDAO.allOfUsersTransfers(id);
    }

    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
        public Transfers add(@Valid @RequestBody Transfers transfers) {
        return transfersDAO.createTransfer(transfers);

    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfers transfersById (@PathVariable int id) {
        return transfersDAO.getTransferById(id);
    }


}
