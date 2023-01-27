package Service; 

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import Model.Account;

public class MessageService {
    public MessageDAO messageDAO; 
    public AccountDAO accountDAO; 

    public MessageService() {
        messageDAO = new MessageDAO(); 
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO; 
    }

    // Check Message object for valid account ID before fully creating 
    public Message createMessage(Message message) {
        if (accountDAO.getAccount(message.getPosted_by()) != null) {
            return messageDAO.createMessage(message); 
        } else {
            return null; 
        }       
        
    }
}