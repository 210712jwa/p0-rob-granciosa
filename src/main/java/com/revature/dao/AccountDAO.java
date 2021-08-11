package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.dto.AddOrEditAccountDTO;
import com.revature.model.Account;

public interface AccountDAO {
	
	List<Account> getAllAccountsOfClient(int clientId) throws SQLException;
	
	List<Account> getAllAccountsOfClient(int clientId, double balance1, double balance2) throws SQLException;
	
	public abstract Account getAccountByAccountNo(int acctNo) throws SQLException;
	
	// Create new account for a client with id of X (if client exists)
	public abstract Account addAccount(AddOrEditAccountDTO account) throws SQLException;
	
	// Get all accounts for client id of X with balances between 400 and 2000 (if client exists)
	List<Account> getAccountsOfBalanceRange(int clientId, double balance1, double balance2) throws SQLException;
	
	// Get account with id of Y belonging to client with id of X 
	// (if client and account exist AND if account belongs to client)
	public abstract Account getSpecificAccount(int accountNo) throws SQLException;
	
	// Update account with id of Y belonging to client with id of X 
	// (if client and account exist AND if account belongs to client)
	public abstract Account editAccount(int accountNo, int clientId, AddOrEditAccountDTO account) throws SQLException;
	
	// Delete account with id of Y belonging to client with id of X 
	// (if client and account exist AND if account belongs to client)
	public abstract void deleteAccount(int accountNo, int clientId) throws SQLException;
}
