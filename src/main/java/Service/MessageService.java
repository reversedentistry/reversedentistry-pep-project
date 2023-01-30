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

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO; 
        this.accountDAO = accountDAO; 
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages(); 
    }
    
    // Check Message object for valid account ID before fully creating 
    public Message createMessage(Message message) {
        if (accountDAO.getAccount(message.getPosted_by()) != null && message.getMessage_text().length() != 0) {
            return messageDAO.createMessage(message); 
        } else {
            return null; 
        }       
        
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId); 
    }

    public List<Message> getMessagesByAccount(int accountId) {
        return messageDAO.getMessagesByAccount(accountId); 
    }

    public Message updateMessage(int messageId, Message message) {
        if (messageDAO.getMessageById(messageId) != null && message.getMessage_text().length() > 0 && message.getMessage_text().length() <= 255) {
            messageDAO.updateMessage(messageId, message);
            return messageDAO.getMessageById(messageId);
        } else {
            return null; 
        }
    }

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