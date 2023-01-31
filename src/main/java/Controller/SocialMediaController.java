package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService; 
    MessageService messageService; 

    public SocialMediaController() {
        this.accountService = new AccountService(); 
        this.messageService = new MessageService(); 
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler); 
        app.get("/accounts", this::getAllAccountsHandler); 
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);  
        app.get("/messages/{message_id}", this::getMessageByIdHandler); 
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountHandler); 
        app.patch("/messages/{message_id}", this::patchMessageHandler); 
        app.delete("/messages/{message_id}", this::deleteMessageHandler); 
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    //Endpoint to create new accounts
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        Account account = mapper.readValue(ctx.body(), Account.class); 
        Account newAccount = accountService.createAccount(account); 

        if (newAccount != null) {
            ctx.json(mapper.writeValueAsString(newAccount)); 
        } else {
            ctx.status(400); 
        }
    }
    
    //Endpoint to verify account credentials and login
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginCreds = mapper.readValue(ctx.body(), Account.class);
        Account loginAttempt = accountService.loginAccount(loginCreds); 

        if (loginAttempt != null) {
            ctx.json(loginAttempt);
        } else {
            ctx.status(401); 
        }
    }

    //Retrieves all accounts (for testing purposes)
    private void getAllAccountsHandler(Context ctx) {
        List<Account> allAccounts = accountService.getAllAccounts(); 
        ctx.json(allAccounts); 
    }

    //Retrieves all messages
    private void getAllMessagesHandler(Context ctx) {
        List<Message> allMessages = messageService.getAllMessages(); 
        ctx.json(allMessages); 
    }

    //Retrieves all messages of a specified user
    private void getAllMessagesByAccountHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id")); 
        List<Message> messages = messageService.getMessagesByAccount(accountId); 
        ctx.json(messages); 
    }

    //Retrieves a message by its ID
    private void getMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id")); 
        Message retrievedId = messageService.getMessageById(messageId); 
        if (retrievedId != null) {
            ctx.json(retrievedId); 
        }
        
    }; 
    
    //Create a new message
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        Message message = mapper.readValue(ctx.body(), Message.class); 
        Message newMessage = messageService.createMessage(message); 

        if(newMessage != null){
            ctx.json(mapper.writeValueAsString(newMessage));
        }else{
            ctx.status(400);
        }
    }

    //Edits text of a message
    private void patchMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(); 
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id")); 
        Message updatedMessage = messageService.updateMessage(message_id, message); 
        System.out.println(updatedMessage); 
        if (updatedMessage == null) {
            ctx.status(400); 
        } else {
            ctx.json(mapper.writeValueAsString(updatedMessage)); 
        }
    }    

    //Deletes a message 
    private void deleteMessageHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id")); 
        Message deletedMessage = messageService.deleteMessage(messageId); 
        if (deletedMessage != null) {
            ctx.json(deletedMessage); 
        } 
}


}