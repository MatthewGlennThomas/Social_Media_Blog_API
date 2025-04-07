package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import Model.Message;
import Model.Account;
import Service.AccountService;
import Util.ConnectionUtil;

public class MessageRepository {

    public Message createMessage(int posted_by, String message_text, long time_posted_epoch) throws SQLException {
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        //try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, posted_by);
            statement.setString(2, message_text);
            statement.setLong(3, time_posted_epoch);

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                int message_id = keys.getInt(1);
                return new Message(message_id, posted_by, message_text, time_posted_epoch);
            }
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        return null;
    }

    public List<Message> getAllMessages() throws SQLException {
        String sql = "SELECT * FROM message";
        List<Message> messages = new ArrayList<>();
        //try {
            Connection connection = ConnectionUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch")));
            }
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        return messages;
    }

    public Message getMessageById(int message_id) throws SQLException {
        String sql = "SELECT * FROM message WHERE message_id = ?";
        //try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, message_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch"));
            }
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        return null;
    }

    public Message deleteMessage(int message_id) throws SQLException {
        String sql = "DELETE FROM message WHERE message_id = ?";
        //try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            if (getMessageById(message_id) != null) {
                statement.setInt(1, message_id);
            }
            else {
                return null;
            }
            Message message = getMessageById(message_id);
            statement.executeUpdate();
            if (message != null) {
                return message;
            }
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        return null;
    }

    public Message updateMessage(int message_id, String message_text) throws SQLException {
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
        //try {
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, message_text);
            statement.setInt(2, message_id);
            statement.executeUpdate();
            Message message = getMessageById(message_id);
            if (message != null) {
                return message;
            }
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        return null;
    }

    public List<Message> getMessagesByAccount(int account_id) throws SQLException {
        AccountService accountService = new AccountService();
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        List<Message> messages = new ArrayList<>();
        //try {
            if (accountService.findAccountById(account_id) == null) {
                return messages;
            }
            Connection connection = ConnectionUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, account_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                messages.add(new Message(resultSet.getInt("message_id"), resultSet.getInt("posted_by"), resultSet.getString("message_text"), resultSet.getLong("time_posted_epoch")));
            }
        //}
        //catch (SQLException e) {
            //e.printStackTrace();
        //}
        return messages;
    }
}
