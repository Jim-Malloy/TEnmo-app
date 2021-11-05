package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRecord;
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
    public TransferRecord createTransfer(@RequestBody TransferRecord newTransfer) {
        return transferDao.create(newTransfer);
    }

    @RequestMapping(path = "/from", method = RequestMethod.GET)
    public List<Transfer> sendTransfer(@RequestParam int accountTo) {
        return transferDao.getTransferFrom(accountTo);
    }

    @RequestMapping(path = "/to", method = RequestMethod.GET)
    public List<Transfer> receiveTransfer(@RequestParam int accountFrom) {
        return transferDao.getTransferTo(accountFrom);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Transfer getById(@PathVariable int id) {
        return transferDao.getById(id);
    }
}
