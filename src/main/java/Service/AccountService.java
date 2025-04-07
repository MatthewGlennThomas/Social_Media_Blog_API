package Service;

import java.sql.SQLException;

import DAO.AccountRepository;
import Model.Account;

public class AccountService {
    private AccountRepository accountRepository = new AccountRepository();

    public Account registerAccount(String username, String password) throws SQLException {
        if (username == null || password == null || username.isEmpty() || password.length() < 4 || accountRepository.findAccountByUsername(username) != null) {
            return null;
        }

        //Account priorAccount = accountRepository.findAccountByUsername(username);
        //if (priorAccount != null) {
            //return null;
        //}

        return accountRepository.createAccount(username, password);
    }

    public Account loginAccount(String username, String password) throws SQLException {
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    public Account findAccountById(int account_id) throws SQLException {
        Account account = accountRepository.findAccountById(account_id);
        if (account != null) {
            return account;
        }
        return null;
    }

    public Account findAccountByUsername(String username) throws SQLException {
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            return account;
        }
        return null;
    }
}
