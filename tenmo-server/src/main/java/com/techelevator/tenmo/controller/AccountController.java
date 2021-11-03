package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    private AccountDao dao;

    public AccountController(AccountDao dao) {
        this.dao = dao;
    }

    @RequestMapping(path = "/accounts", method = RequestMethod.GET)
    public List<Account> list() {
        return dao.list();
    }

    @RequestMapping(path = "/accounts/{userId}", method = RequestMethod.GET)
    public Account getAccount(@PathVariable Long userId) {
        return dao.getAccount(userId);
    }
}
