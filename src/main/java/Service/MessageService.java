package Service; 

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
// import Model.Account;

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

    // Check Message object for valid account ID before fully creating 
    public Message createMessage(Message message) {
        if (accountDAO.getAccount(message.getPosted_by()) != null && message.getMessage_text().length() != 0) {
            return messageDAO.createMessage(message); 
        } else {
            return null; 
        }       
        
    }
}