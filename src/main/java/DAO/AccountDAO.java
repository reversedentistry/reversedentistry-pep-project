package DAO; 

import Model.Account; 
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    //Inserts new account into table
    public Account createAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection(); 
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 

            preparedStatement.setString(1, account.getUsername()); 
            preparedStatement.setString(2, account.getPassword()); 
            preparedStatement.executeUpdate(); 
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys(); 
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }
        return null; 
    }

    //Retrieves an account from table by its ID - used in MessageService
    public Account getAccount(int accountId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")); 
                return account;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null; 
    }

    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection(); 
        List<Account> accounts = new ArrayList<>(); 
        try {
            String sql = "SELECT * FROM account"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql); 
            ResultSet rs = preparedStatement.executeQuery(); 
            while (rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password")); 
                accounts.add(account); 
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage()); 
        }
        return accounts; 
    }

    // Checks table for specified username
    public Account verifyUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")); 
                return account;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null; 
    }

    // Retrieves all records given an existing user 
    public Account checkExistingAccount(Account login) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?"; 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.setString(2, login.getPassword()); 
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password")); 
                return account;
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null; 
    }
}