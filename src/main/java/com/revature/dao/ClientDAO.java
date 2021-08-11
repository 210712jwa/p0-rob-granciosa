package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.dto.AddOrEditClientDTO;
import com.revature.model.Client;

public interface ClientDAO {
	
	// POST /clients: Creates a new client
	public abstract Client addClient(AddOrEditClientDTO client) throws SQLException;
	
	
	// GET /CLIENTS/{client_id}: Gets client with an id of X (if the client exists)
	/**
	 * This method returns a Client from the database
	 * @param id is an int that represents the client id
	 * @return Client, null if not found
	 */
	public abstract Client getClientById(int id) throws SQLException;
	
	// GET /CLIENTS/{client_id}: Gets client with an id of X (if the client exists)
	public abstract List<Client> getAllClients() throws SQLException;
	
	// PUT /clients/{client_id}: Updates client with an id of X (if the client exists)
	public abstract Client editClient(int clientId, AddOrEditClientDTO client) throws SQLException;
	
	// DELETE /clients/{id}: Delete client with an id of X (if the client exists)
	public abstract void deleteClient(int clientId) throws SQLException; 
	

	
	
	
}
