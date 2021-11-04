package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    private AccountDao dao;
    private TransferDao transferDao;
    private UserDao userDao;

    public AccountController(AccountDao dao, TransferDao transferDao, UserDao userDao) {
        this.dao = dao;
        this.transferDao = transferDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> list() {
        return dao.list();
    }

    @RequestMapping(path = "/accounts/{userId}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable Long userId) {
        return dao.getAccount(userId);
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer newTransfer) {
        return transferDao.create(newTransfer);
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> userList() {
        return userDao.findAll();
    }

    @RequestMapping(path = "/accounts/{fromId}", method = RequestMethod.PUT)
    public void update(@RequestBody Account updatedAccount, @PathVariable long fromId) {
        dao.update(updatedAccount, fromId);
    }

}
