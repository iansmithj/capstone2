package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.model.Transfers;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private TransfersDAO transfersDAO;

    public TransferController(TransfersDAO transfersDAO) {
        this.transfersDAO = transfersDAO;
    }
    @RequestMapping(path = , method = RequestMethod.GET)
    public List<Transfers> list() {
        return transfersDAO.();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfers get(@PathVariable int transfer_id) {
        Transfers transfers =transfersDAO.getTransferById(transfer_id);
        if (transfers== null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found");
        } else {
            return transfers;
        }
    }
}
