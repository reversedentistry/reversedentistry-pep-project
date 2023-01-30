package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO; 

    public AccountService() {
        accountDAO = new AccountDAO(); 
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO; 
    }

    public Account createAccount(Account account) {
        if (account.getUsername() != "" && account.getPassword().length() >= 4 && accountDAO.getUsername(account.getUsername()) == null) {
            return accountDAO.createAccount(account); 
        } else {
            return null; 
        }
    }

    public Account loginAccount(Account account) {
        return accountDAO.checkExistingAccount(account);
    }
}
