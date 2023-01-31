package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;

public class AccountService {
    public AccountDAO accountDAO; 

    public AccountService() {
        accountDAO = new AccountDAO(); 
    }

    public Account createAccount(Account account) {
        if (account.getUsername() != "" && account.getPassword().length() >= 4 && accountDAO.verifyUsername(account.getUsername()) == null) {
            return accountDAO.createAccount(account); 
        } else {
            return null; 
        }
    }

    // Checks the existence of username in provided login attempt and returns all info for user if it exists
    public Account loginAccount(Account account) {
        if (accountDAO.verifyUsername(account.getUsername()) != null) {
            return accountDAO.checkExistingAccount(account);
        } else {
            return null; 
        }
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts(); 
    }
}
