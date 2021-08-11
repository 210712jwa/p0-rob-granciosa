package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountDAOImpl;
import com.revature.dao.ClientDAO;
import com.revature.dao.ClientDAOImpl;
import com.revature.dto.AddOrEditAccountDTO;
import com.revature.exception.AccountNotFoundException;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;
import com.revature.model.Account;

public class AccountService {

	private AccountDAO accountDao;
	private ClientDAO clientDao;

	public AccountService() {
		this.accountDao = new AccountDAOImpl();
		this.clientDao = new ClientDAOImpl();
	}

	public List<Account> getAllAccountsOfClient(String clientIdString)
			throws DatabaseException, ClientNotFoundException, BadParameterException {

		try {
			int clientId = Integer.parseInt(clientIdString);

			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with ID " + clientId + " not found");
			}

			List<Account> accounts = accountDao.getAllAccountsOfClient(clientId);

			return accounts;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					"Client ID #" + clientIdString + " is an invalid input; please enter integer");
		}
	}
	
//	public List<Account> getAllAccountsOfClient(String clientIdString, String balanceOne, String balanceTwo)
//			throws DatabaseException, ClientNotFoundException, BadParameterException {
//
//		try {
//			int clientId = Integer.parseInt(clientIdString);
//			int balance1 = Integer.parseInt(balanceOne);
//			int balance2 = Integer.parseInt(balanceTwo);
//
//			if (clientDao.getClientById(clientId) == null) {
//				throw new ClientNotFoundException("Client with ID " + clientId + " not found");
//			}
//
//			List<Account> accounts = accountDao.getAllAccountsOfClient(clientId, balance1, balance2);
//
//			return accounts;
//		} catch (SQLException e) {
//			throw new DatabaseException(e.getMessage());
//		} catch (NumberFormatException e) {
//			throw new BadParameterException(
//					"Client ID #" + clientIdString + " is an invalid input; please enter integer");
//		}
//	}

/*	public List<Account> getAccountsOfBalanceRange(String clientIdString)
			throws ClientNotFoundException, DatabaseException, BadParameterException {



		try {
			int clientId = Integer.parseInt(clientIdString);

			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with ID " + clientId + " not found");
			}

			List<Account> accounts = accountDao.getAccountsOfBalanceRange(clientId);

			return accounts;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					"Client ID #" + clientIdString + " is an invalid input; please enter integer");
		}
	}
*/
		
	public Account addAccount(AddOrEditAccountDTO account) throws DatabaseException, BadParameterException {

		if (account.getAccountType().trim().equals("")) {
			throw new BadParameterException("Account type must be specified (checking or savings)");
		}
		if (account.getBalance() <= 0) {
			throw new BadParameterException("Balance cannot be below $0.00");
		}
		
		try {
			Account addedAccount = accountDao.addAccount(account);
			
			
			return addedAccount;
		}
		 catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations");
		}

	}

	public Account editAccount(String accountNo, String clientId, AddOrEditAccountDTO account)
			throws ClientNotFoundException, AccountNotFoundException, SQLException, BadParameterException,
			DatabaseException {

		try {
			int id = Integer.parseInt(clientId);
			int intAcctNo = Integer.parseInt(accountNo);
			Account acct = accountDao.getAccountByAccountNo(intAcctNo);

			// checks to make sure client exists.
			if (clientDao.getClientById(id) == null) {
				throw new ClientNotFoundException(
						"Unable to edit account; Client with id " + clientId + " was not found.");
			}
			if (accountDao.getAccountByAccountNo(intAcctNo) == null) {
				throw new AccountNotFoundException(
						"Unable to edit account; account with account #" + accountNo + " was not found");
			}
			if (acct.getClient_id() != id) {
				throw new AccountNotFoundException("Unable to match client ID with account number");
			}

			Account editedAccount = accountDao.editAccount(intAcctNo, id, account);

			return editedAccount;

		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations.");
		} catch (NumberFormatException e) {
			throw new BadParameterException(
					clientId + " & " + accountNo + " were passed as client ID and AccountNumber; match not found");
		}
	}

	public Account getSpecificAccount(String accountNo, String clientId)
			throws ClientNotFoundException, AccountNotFoundException, BadParameterException, DatabaseException {
		try {
			int accountNumber = Integer.parseInt(accountNo);
			int intClientId = Integer.parseInt(clientId);
			Account acct = accountDao.getAccountByAccountNo(accountNumber);

			/*
			 * Now we need to think about the conditions. We need to make sure that the
			 * client exists. We need to make sure that the account exits. We need to make
			 * sure that the clientID that we are given matches the clientID of the account
			 * 
			 * if all of these conditions are met, we can create an account instance with
			 * the information.
			 */

			if (clientDao.getClientById(intClientId) == null) {
				throw new ClientNotFoundException(
						"Unable to retrieve account; client #" + clientId + " does not exist.");
			}
			if (acct == null) {
				throw new AccountNotFoundException("Account with account #" + accountNo + " does not exist.");
			}
			if (acct.getClient_id() != intClientId) {
				throw new BadParameterException(
						"Unable to match account #" + accountNo + " with client ID #" + clientId);
			}
			return acct;
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations");
		} catch (NumberFormatException e) {
			throw new BadParameterException("Invalid entry");
		}
	}

	public void deleteAccount(String clientID, String accountNo)
			throws AccountNotFoundException, SQLException, DatabaseException, BadParameterException {
		try {
			int id = Integer.parseInt(clientID);
			int acctNo = Integer.parseInt(accountNo);

			Account account = accountDao.getAccountByAccountNo(acctNo);

			if (account == null) {
				throw new AccountNotFoundException("Unable to delete account with account #" + accountNo);
			}

			accountDao.deleteAccount(acctNo, id);
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations.");
		} catch (NumberFormatException e) {
			throw new BadParameterException("Bad entry; account not found");
		}
	}

}
