package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
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
    public Transfer create(Transfer newTransfer) {

        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)\n" +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getTransferType().getTransferTypeId(),
                newTransfer.getTransferStatus().getTransferStatusId(),
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
            transferList.add(newTransfer);
        }
        return transferList;
    }

    @Override
    public List<Transfer> getRequestTransfer(String username) {

        String sql = "SELECT * FROM transfers t\n" +
                "JOIN accounts a ON a.account_id = t.account_to \n" +
                "JOIN users u ON u.user_id = a.user_id\n" +
                "WHERE username = ?;";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);

        List<Transfer> transferList = new ArrayList<>();

        while(result.next()) {
            Transfer newTransfer = mapRowToTransfer(result);
            transferList.add(newTransfer);
        }
        return transferList;
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
        newTransfer.setFromUsername(row.getString("username"));
        return newTransfer;
    }
}
