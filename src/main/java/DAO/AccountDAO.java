package DAO; 

import Model.Account; 
import Util.ConnectionUtil;

import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

public class AccountDAO {
    //Create new account
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

    // Retrieve account by ID
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

    public Account getUsername(String username) {
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
}