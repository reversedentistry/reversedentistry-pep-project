package Service; 

import DAO.MessageDAO;
import Model.Message;
import Model.Account;

public class MessageService {
    public MessageDAO messageDAO; 

    public MessageService() {
        messageDAO = new MessageDAO(); 
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO; 
    }

    // Check Message object for valid account ID before fully creating 
    public Message createMessage(Message message) {
        return null; 
    }
}