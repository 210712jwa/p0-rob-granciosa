package com.revature.controller;

import java.util.List;

import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

import com.revature.dto.AddOrEditAccountDTO;
import com.revature.model.Account;

public class AccountController implements Controller {
	
	private AccountService accountService;
	
	public AccountController() {
		this.accountService = new AccountService();
	}
	
	private Handler getAllAccountsOfClient = (ctx) -> {
		String clientId = ctx.pathParam("client_id");
		
		List<Account> accountsOfClient = accountService.getAllAccountsOfClient(clientId);
		ctx.json(200);
		ctx.json(accountsOfClient);
	};
	
	private Handler addAccount = (ctx) -> {
		AddOrEditAccountDTO accountToAdd = ctx.bodyAsClass(AddOrEditAccountDTO.class);
		// String clientId = ctx.pathParam("client_id");
		
		Account addedAccount = accountService.addAccount(accountToAdd);
		ctx.json(200);
		ctx.json(addedAccount);	
	};
	
	private Handler editAccount = (ctx) -> {
		AddOrEditAccountDTO accountToEdit = ctx.bodyAsClass(AddOrEditAccountDTO.class);
		String accountNo = ctx.pathParam("account_no");
		String clientId = ctx.pathParam("client_id");
		Account editedAccount = accountService.editAccount(accountNo, clientId, accountToEdit);
		
		ctx.json(200);
		ctx.json(editedAccount);
	};
	
	private Handler getSpecificAccount = (ctx) -> {
		String accountNo = ctx.pathParam("account_no");
		String clientId = ctx.pathParam("client_id");
		
		Account account = accountService.getSpecificAccount(accountNo, clientId); 
		
		
		ctx.json(200);
		ctx.json(account);
	};
	
	private Handler deleteAccount = (ctx) -> {
		String clientId = ctx.pathParam("client_id");
		String accountNo = ctx.pathParam("account_no");
		accountService.deleteAccount(clientId, accountNo);
	};
	
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/client/:client_id/account", getAllAccountsOfClient); // this works
		app.get("/client/:client_id/account/:account_no", getSpecificAccount); // This works 
		
		app.post("/client/:client_id/account", addAccount); //This works 
		app.put("/client/:client_id/account/:account_no", editAccount); //This works 
		app.delete("/client/:client_id/account/:account_no", deleteAccount); //This works 
		
	}

}