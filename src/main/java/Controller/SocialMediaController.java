package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.sql.SQLException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessage);
        app.patch("/messages/{message_id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccount);

        return app;
    }

    private void register(Context context) throws SQLException {
        Account account = context.bodyAsClass(Account.class);
        //if (account.getUsername() == null || account.getPassword() == null || account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            //context.status(400).json("Invalid input");
            //return;
        //}
        Account registeredAccount = accountService.registerAccount(account.getUsername(), account.getPassword());
        if (registeredAccount != null) {
            context.status(200).json(registeredAccount);
        }
        else {
            context.status(400).json("");
        }
    }

    private void login(Context context) throws SQLException {
        Account account = context.bodyAsClass(Account.class);
        Account loggedInAccount = accountService.loginAccount(account.getUsername(), account.getPassword());
        if (loggedInAccount != null) {
            context.status(200).json(loggedInAccount);
        }
        else {
            context.status(401).json("");
        }
    }

    private void createMessage(Context context) throws SQLException {
        Message message = context.bodyAsClass(Message.class);
        //if (message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {
            //context.status(400).json("");
            //return;
        //}
        Message createdMessage = messageService.createMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        if (createdMessage != null) {
            context.status(200).json(createdMessage);
        }
        else {
            context.status(400).json("");
        }
    }

    private void getAllMessages(Context context) throws SQLException {
        context.status(200).json(messageService.getAllMessages());
    }

    private void getMessageById(Context context) throws SQLException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if (message != null) {
            context.status(200).json(message);
        }
        else {
            context.status(200).json("");
        }
    }

    private void deleteMessage(Context context) throws SQLException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(message_id);
        if (deletedMessage != null) {
            context.status(200).json(deletedMessage);
        }
        else {
            context.status(200).json("");
        }
    }

    private void updateMessage(Context context) throws SQLException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message newMessage = context.bodyAsClass(Message.class);
        String updatedText = newMessage.getMessage_text();
        if (updatedText == null || updatedText.isEmpty() || updatedText.length() > 255) {
            context.status(400).json("");
            return;
        }
        Message updatedMessage = messageService.updateMessage(message_id, updatedText);
        if (updatedMessage != null) {
            context.status(200).json(updatedMessage);
        }
        else {
            context.status(400).json("");
        }
    }

    private void getMessagesByAccount(Context context) throws SQLException {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        context.status(200).json(messageService.getMessagesByAccount(account_id));
    }
}