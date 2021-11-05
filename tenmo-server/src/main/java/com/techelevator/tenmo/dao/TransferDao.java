package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer create(Transfer newTransfer);

    List<Transfer> getTransferFrom(int accountTo);
    List<Transfer> getRequestTransfer(String username);
}
