package Service; 

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO; 
    public AccountDAO accountDAO; 

    public MessageService() {
        messageDAO = new MessageDAO(); 
        accountDAO = new AccountDAO(); 
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages(); 
    }
    
    // Check Message object for valid account ID and correct text length before creating 
    public Message createMessage(Message message) {
        if (accountDAO.getAccount(message.getPosted_by()) != null && message.getMessage_text().length() != 0) {
            return messageDAO.createMessage(message); 
        } else {
            return null; 
        }       
        
    }

    //Retrieve message by specified ID if it exists
    public Message getMessageById(int messageId) {
        if (messageDAO.getMessageById(messageId) == null) {
            return null;
        } else {
            return messageDAO.getMessageById(messageId); 
        }
    }

    //Retrieves all messages of a specified account given it exists
    public List<Message> getMessagesByAccount(int accountId) {
           return messageDAO.getMessagesByAccount(accountId); 
        
    }

    //Edits an existing message after checking for existing ID and appropriate text length
    public Message updateMessage(int messageId, Message message) {
        if (messageDAO.getMessageById(messageId) != null && message.getMessage_text().length() > 0 && message.getMessage_text().length() <= 255) {
            messageDAO.updateMessage(messageId, message);
            return messageDAO.getMessageById(messageId);
        } else {
            return null; 
        }
    }

    //Deletes a message if it exists
    public Message deleteMessage (int messageId) {
        if (messageDAO.getMessageById(messageId) != null) {
            Message deletedMessage = messageDAO.getMessageById(messageId);
            messageDAO.deleteMessage(messageId);
            return deletedMessage; 
        } else {
            return null;
        }
    }
}