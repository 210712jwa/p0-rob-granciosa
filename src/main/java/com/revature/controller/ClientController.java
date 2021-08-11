package com.revature.controller;



import java.util.List;

import com.revature.dto.AddOrEditClientDTO;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController implements Controller{
	
	private ClientService clientService;
	
	public ClientController() {
		this.clientService = new ClientService(); 
	}
	
	private Handler getAllClients = (ctx) -> {
		List<Client> clients = clientService.getAllClients();
		
		ctx.status(200); 
		ctx.json(clients);
	};
	
	private Handler addClient = (ctx) -> {
		AddOrEditClientDTO clientToAdd = ctx.bodyAsClass(AddOrEditClientDTO.class);
		
		Client addedClient = clientService.addClient(clientToAdd);
		ctx.json(addedClient);
	};
	
	private Handler getClientById = (ctx) -> {
		String client_id = ctx.pathParam("client_id");
		
		Client client = clientService.getClientById(client_id);
		ctx.json(client);
	};
	
	private Handler editClient = (ctx) -> {
		AddOrEditClientDTO clientToEdit = ctx.bodyAsClass(AddOrEditClientDTO.class);
		
		String clientId = ctx.pathParam("client_id");
		Client editedClient = clientService.editClient(clientId, clientToEdit);
		
		ctx.json(editedClient);
	};
	
	private Handler deleteClient = (ctx) -> {
		String client_id = ctx.pathParam("client_id");
		clientService.deleteClient(client_id);
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/client", getAllClients);
		app.post("/client", addClient);
		app.get("/client/:client_id", getClientById);
		app.put("/client/:client_id", editClient);
		app.delete("/client/:client_id", deleteClient);
		
	}


	
}
