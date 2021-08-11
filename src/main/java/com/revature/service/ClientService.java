package com.revature.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dao.AccountDAO;
import com.revature.dao.AccountDAOImpl;
import com.revature.dao.ClientDAO;
import com.revature.dao.ClientDAOImpl;
import com.revature.dto.AddOrEditClientDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;
import com.revature.model.Account;
import com.revature.model.Client;

public class ClientService {
	
	private Logger logger = LoggerFactory.getLogger(ClientService.class);

	private ClientDAO clientDao;
	private AccountDAO accountDao;

	public ClientService() {
		this.clientDao = new ClientDAOImpl();
		this.accountDao = new AccountDAOImpl();
	}
	
	public ClientService(ClientDAO mockedClientDaoObject, AccountDAO mockedAccountDaoObject) {
		this.clientDao = mockedClientDaoObject;
		this.accountDao = mockedAccountDaoObject;
	}

	public List<Client> getAllClients() throws DatabaseException {
		List<Client> clients;
		try {
			clients = clientDao.getAllClients();
			
			for (Client client : clients) {
				List <Account> accounts = accountDao.getAllAccountsOfClient(client.getClientId());
				client.setAccounts(accounts);
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations");
		}
		return clients;
		
	}
	
	
	public Client addClient(AddOrEditClientDTO client) throws BadParameterException, DatabaseException {
		if (client.getFirstName().trim().equals("") || client.getLastName().trim().equals("")) {
			throw new BadParameterException("Client name fields cannot be blank");
		} 
		
		try {
			Client addedClient = clientDao.addClient(client);
			addedClient.setAccounts(new ArrayList<>());
			return addedClient;
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations");
		}
	}
	
	public Client getClientById(String stringId) throws ClientNotFoundException, DatabaseException, BadParameterException {
		try {
			int id = Integer.parseInt(stringId);
			
			Client client = clientDao.getClientById(id);
			
			if (client == null ) {
				throw new ClientNotFoundException("Client with id " + id + " was not found");
			}
			
			List<Account> accounts = accountDao.getAllAccountsOfClient(id);
			client.setAccounts(accounts);
			
			return client;
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong with our DAO operations.");
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client ID #" + stringId + " is not a valid entry");
		}
	}
	
	public Client editClient(String stringId, AddOrEditClientDTO client) throws DatabaseException, ClientNotFoundException, BadParameterException {
		
		try {
			int clientId = Integer.parseInt(stringId);
			
			
			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Unable to edit; Client with id " + clientId + " was not found." );
			}
			
			Client editedClient = clientDao.editClient(clientId, client);
			
			List<Account> accounts = accountDao.getAllAccountsOfClient(clientId);
			editedClient.setAccounts(accounts);
			
			return editedClient;
		} catch (SQLException e) {
			throw new DatabaseException("e.getMessage()");
		}	catch (NumberFormatException e) {
			throw new BadParameterException(stringId + " is not an integer");
		}
	}
	
	
	
	public void deleteClient(String clientId) throws BadParameterException, DatabaseException, ClientNotFoundException{
		try {
			int id = Integer.parseInt(clientId);

			Client client = clientDao.getClientById(id);
			if (client == null) {
				throw new ClientNotFoundException("Unable to delete ship with ID of " + id + "; ship with ID of "+ id + " does not exist");
			}
			
			clientDao.deleteClient(id);
			
		} catch (SQLException e ) {
			throw new DatabaseException("Something went wrong with our DAO operations.");
		} catch (NumberFormatException e) {
			throw new BadParameterException(clientId + " was passed in by user as ID; ID not found");
		}
	}
	
	

}
