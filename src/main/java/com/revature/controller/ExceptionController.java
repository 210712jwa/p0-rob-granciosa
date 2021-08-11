package com.revature.controller;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.ExceptionMessageDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;



public class ExceptionController implements Controller{

	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	private ExceptionHandler<DatabaseException> databaseExceptionHandler = (e, ctx) -> {
		logger.error("DatabaseException occurred from " + ctx.method() + " " + ctx.path() + ". Message is " + e.getMessage());
		
		ctx.status(500);
		
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO(); 
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	private ExceptionHandler<ClientNotFoundException> clientNotFoundExceptionHandler = (e, ctx) -> {
		logger.info("ClientNotFOundException occurred from " + ctx.method() + " " + ctx.path() + ". Message is " + e.getMessage());
		
		ctx.status(404);
		
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO();
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	private ExceptionHandler<BadParameterException> badParameterExceptionHandler = (e, ctx) -> {
		logger.info("BadParameterException occurred from " + ctx.method() + " " + ctx.path() + ". Message is " + e.getMessage());
		
		ctx.status(400);
		
		ExceptionMessageDTO messageDTO = new ExceptionMessageDTO(); 
		messageDTO.setMessage(e.getMessage());
		
		ctx.json(messageDTO);
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.exception(DatabaseException.class, databaseExceptionHandler);
		app.exception(ClientNotFoundException.class, clientNotFoundExceptionHandler);
		app.exception(BadParameterException.class, badParameterExceptionHandler);
		
		
	}

}
