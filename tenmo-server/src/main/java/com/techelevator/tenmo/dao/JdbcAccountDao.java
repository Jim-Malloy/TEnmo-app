package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> list() {

        String sql = "SELECT * FROM accounts";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);

        List<Account> accounts = new ArrayList<>();

        while (rowSet.next()) {
            accounts.add(mapRowToAccount(rowSet));

        }
        return accounts;
    }

    @Override
    public Account getAccount(Long userId) {

        String sql = "SELECT * FROM accounts\n" +
                "WHERE user_id = ?";
        SqlRowSet row = jdbcTemplate.queryForRowSet(sql, userId);

        if (row.next()) {
            return mapRowToAccount(row);
        }
        return null;
    }

    @Override
    public void update(Account updatedAccount, long fromId) {
        String sql = "UPDATE accounts SET balance = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, updatedAccount.getBalance(), fromId);
    }


    private Account mapRowToAccount(SqlRowSet row) {
        Account account = new Account();
        account.setAccountId(row.getLong("account_id"));
        account.setBalance(row.getDouble("balance"));
        account.setUserId(row.getLong("user_id"));

        return account;
    }
}
