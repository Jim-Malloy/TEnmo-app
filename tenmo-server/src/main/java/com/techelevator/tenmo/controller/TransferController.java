package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/transfer")
public class TransferController {

    private TransferDao transferDao;

    public TransferController(TransferDao dao) {
        this.transferDao = dao;
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer newTransfer) {
        return transferDao.create(newTransfer);
    }

    @RequestMapping(path = "/from", method = RequestMethod.GET)
    public List<Transfer> sendTransfer(@RequestParam int accountTo) {
        return transferDao.getTransferFrom(accountTo);
    }

    @RequestMapping(path = "/request", method = RequestMethod.GET)
    public List<Transfer> requestTransfer(@RequestParam String username) {
        return transferDao.getRequestTransfer(username);
    }
}
