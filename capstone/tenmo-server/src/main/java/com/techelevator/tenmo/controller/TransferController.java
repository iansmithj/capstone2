package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransfersDAO;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.Transfers;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
@PreAuthorize("isAuthenticated()")
@RequestMapping("/user")
@RestController
public class TransferController {

    private TransfersDAO transfersDAO;
    private UserDao userDao;
    private AccountDao accountDao;

    public TransferController(TransfersDAO transfersDAO, UserDao userDao, AccountDao accountDao) {
        this.transfersDAO = transfersDAO;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/transfer/{id}",method = RequestMethod.GET)
    public Transfers transferById(@PathVariable int id) {
        Transfers transfers = transfersDAO.getTransferById(id);
        if (transfers == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
        return transfers;

    }
    // 5. As an authenticated user of the system, I need to be able to see transfers I have sent or received.
    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
    public List<Transfers> getTransfersByUserId(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        try {
            return this.transfersDAO.getTransfersByUserId(user.getId());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/pending", method = RequestMethod.GET)
    public List<Transfers> getUsersPendingTransfers(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        try {
            return this.transfersDAO.getUsersPendingtransfers(user.getId());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfers createTransfer(@Valid @RequestBody TransferDTO transferDTO, Principal principal) {
        int status;
        int type;
        if (accountDao.getAccountByUserId(userDao.getUserByUsername(principal.getName()).getId()).getAccountId() == transferDTO.getUserTo()) {
            status = 3;
            type = 2;
        } else {
            status = 1;
            type = 1;
        }
        Transfers transfers = transfersDAO.createTransfer(transferDTO, status, type);
        if (transfers.getAmount_transferred().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer amount must be greater than zero");
        }
        if (transfers.getWithdraw_account_id() == transfers.getDeposit_account_id()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot request transfer from yourself");
        }
        if (type == 1) {
            accountDao.moveMoney(transfers.getAmount_transferred(), transfers.getWithdraw_account_id(), transfers.getDeposit_account_id());
        }

        return transfers;
    }

}
    //    @PutMapping("user/transfer? status = Pending")
//    public void updateTransferStatusToApproved(@PathVariable int status_id,@RequestBody Transfers transfers){
//        transfers.setStatus_id(transfers.getStatus_id());
//        try{
//            this.transfersDAO.updateTransferStatusToApproved(transfers);
//        }catch(DaoException e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Unsuccesful Transfer");
//        }
//    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/user/account/transfer/")
//    public Transfers add(@RequestBody Transfers transfers, @RequestParam(defaultValue = "") String type) {
//        if ("request".equalsIgnoreCase(type)) {
//            transfers.setStatus_id(3); // Pending
//
//            transfersDAO.createTransfer(transfers);
//        } else if ("send".equalsIgnoreCase(type)) {
//            transfers.setStatus_id(1); // Approved
//            Transfers updatedBalance = transfersDAO.moveMoney(transfers.getAmount_transferred(), transfers.getWithdraw_account_id(), transfers.getDeposit_account_id());
//            return transfersDAO.createTransfer(updatedBalance);
//        }
//        return transfersDAO.createTransfer(transfers);
//    }




