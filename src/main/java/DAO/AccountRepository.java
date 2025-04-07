package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountRepository {

    public Account createAccount(String username, String password) throws SQLException {
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
        //try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            statement.executeUpdate();

            //ResultSet keys = statement.getGeneratedKeys();
            //int account_id = keys.getInt(1);

            Account account = findAccountByUsername(username);
            int account_id = account.getAccount_id();

            return new Account(account_id, username, password);
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        //return null;
    }

    public Account findAccountByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM account WHERE username = ?";
        //try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
            }
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        return null;
    }

    public Account findAccountById(int account_id) throws SQLException {
        String sql = "SELECT * FROM account WHERE account_id = ?";
        //try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, account_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Account(resultSet.getInt("account_id"), resultSet.getString("username"), resultSet.getString("password"));
            }
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        return null;
    }
}
