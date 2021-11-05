package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRecord;

import java.util.List;

public interface TransferDao {

    TransferRecord create(TransferRecord newTransfer);

    List<Transfer> getTransferFrom(int accountTo);
    List<Transfer> getTransferTo(int accountFrom);

    Transfer getById(int transferId);
}
