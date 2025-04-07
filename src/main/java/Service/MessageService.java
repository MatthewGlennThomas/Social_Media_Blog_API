package Service;

import DAO.MessageRepository;
import Model.Message;

import java.sql.SQLException;
import java.util.List;

public class MessageService {
    private MessageRepository messageRepository = new MessageRepository();
    private AccountService accountService = new AccountService();

    public Message createMessage(int posted_by, String message_text, long time_posted_epoch) throws SQLException {
        if (message_text == null || message_text.isEmpty() || message_text.length() > 255 || accountService.findAccountById(posted_by) == null) {
            return null;
        }
        return messageRepository.createMessage(posted_by, message_text, time_posted_epoch);
    }

    public List<Message> getAllMessages() throws SQLException {
        return messageRepository.getAllMessages();
    }

    public Message getMessageById(int message_id) throws SQLException {
        return messageRepository.getMessageById(message_id);
    }

    public Message deleteMessage(int message_id) throws SQLException {
        return messageRepository.deleteMessage(message_id);
    }

    public Message updateMessage(int message_id, String updatedText) throws SQLException {
        if (updatedText == null || updatedText.isEmpty() || updatedText.length() > 255) {
            return null;
        }
        return messageRepository.updateMessage(message_id, updatedText);
    }

    public List<Message> getMessagesByAccount(int account_id) throws SQLException {
        return messageRepository.getMessagesByAccount(account_id);
    }
}
