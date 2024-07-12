package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RestController
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao,UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/account")
    public Account get(Principal principal) {
        User user = userDao.getUserByUsername(principal.getName());
        Account account = accountDao.getAccountByUserId(user.getId());
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        } else {
            return account;
        }
    }
}
