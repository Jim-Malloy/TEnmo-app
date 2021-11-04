package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(),
                newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());

        if(newId != null) {
            newTransfer.setTransferId(newId);
        }

        return newTransfer;
    }
}
