package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferRecord;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public TransferRecord create(TransferRecord newTransfer) {

        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getTransferTypeId(),
                newTransfer.getTransferStatusId(),
                newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());

        if(newId != null) {
            newTransfer.setTransferId(newId);
        }

        return newTransfer;
    }

    @Override
    public List<Transfer> getTransferFrom(int accountTo) {

        String sql = "SELECT * FROM transfers t\n" +
                "JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id\n" +
                "JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id\n" +
                "JOIN accounts a ON t.account_from = a.account_id AND t.account_to = ?\n" +
                "JOIN users u ON a.user_id = u.user_id";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountTo);

        List<Transfer> transferList = new ArrayList<>();

        while(result.next()) {
            Transfer newTransfer = mapRowToTransfer(result);
            newTransfer.setFromUsername(result.getString("username"));
            transferList.add(newTransfer);
        }
        return transferList;
    }

    @Override
    public List<Transfer> getTransferTo(int accountFrom) {

        String sql = "SELECT * FROM transfers t\n" +
                "JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id\n" +
                "JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id\n" +
                "JOIN accounts a ON t.account_to = a.account_id AND t.account_from = ?\n" +
                "JOIN users u ON a.user_id = u.user_id";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountFrom);

        List<Transfer> transferList = new ArrayList<>();

        while(result.next()) {
            Transfer newTransfer = mapRowToTransfer(result);
            newTransfer.setToUsername(result.getString("username"));
            transferList.add(newTransfer);
        }
        return transferList;
    }

    @Override
    public Transfer getById(int transferId) {

        String sql = "SELECT *, (SELECT username FROM users u \n" +
                "JOIN accounts a ON a.user_id = u.user_id\n" +
                "JOIN transfers t ON a.account_id = t.account_to\n" +
                "WHERE transfer_id = ?) AS to_name\n" +
                "FROM transfers t\n" +
                "JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id\n" +
                "JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id\n" +
                "JOIN accounts a ON t.account_from = a.account_id\n" +
                "JOIN users u ON a.user_id = u.user_id\n" +
                "WHERE transfer_id = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, transferId, transferId);

        if (row.next()) {
            Transfer transfer = mapRowToTransfer(row);
            transfer.setFromUsername(row.getString("username"));
            transfer.setToUsername(row.getString("to_name"));
            return transfer;
        }
        return null;
    }

    private Transfer mapRowToTransfer(SqlRowSet row) {
        TransferType type = new TransferType();
        type.setTransferTypeId(row.getInt("transfer_type_id"));
        type.setTransferTypeDescription(row.getString("transfer_type_desc"));

        TransferStatus status = new TransferStatus();
        status.setTransferStatusId(row.getInt("transfer_status_id"));
        status.setTransferStatusDescription(row.getString("transfer_status_desc"));

        Transfer newTransfer = new Transfer();
        newTransfer.setTransferId(row.getInt("transfer_id"));
        newTransfer.setAccountFrom(row.getInt("account_from"));
        newTransfer.setAccountTo(row.getInt("account_to"));
        newTransfer.setAmount(row.getDouble("amount"));
        newTransfer.setTransferType(type);
        newTransfer.setTransferStatus(status);

        return newTransfer;
    }
}
