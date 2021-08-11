package com.revature.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrEditAccountDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;
import com.revature.model.Account;
import com.revature.model.Client;

public class AccountServiceTest {

	private AccountService accountService;
	private AccountDAO accountDao;
	private ClientDAO clientDao;
	
	@Before
	public void setUp() throws Exception {
		this.accountDao = mock(AccountDAO.class);
		this.clientDao = mock(ClientDAO.class);
	}

	
	// work on this
	@Test
	public void test_getAllAccountsOfClient_posPath() throws SQLException, DatabaseException, ClientNotFoundException, BadParameterException {
		when(clientDao.getClientById(eq(10))).thenReturn(new Client(100, "Regina", "George", "Washington", "DC", "20001", "1211211212"));
		
		List<Account> mockAccounts = new ArrayList<>();
		mockAccounts.add(new Account(1, "checking", 100.00, 1));
		mockAccounts.add(new Account(2, "savings", 200.00, 2));
		
		when(accountDao.getAllAccountsOfClient(eq(10))).thenReturn(mockAccounts);
		
		List<Account> actualAccounts = accountService.getAllAccountsOfClient("10");
		
		assertEquals(mockAccounts, actualAccounts);
		
	}
	
	@Test 
	public void test_addAccount_posPath() throws ClientNotFoundException, DatabaseException, BadParameterException, SQLException {
		AddOrEditAccountDTO dto = new AddOrEditAccountDTO();
		dto.setAccountNo(500);
		dto.setAccountType("checking");
		dto.setBalance(500.00);
		dto.setClientId(88);
		
		when(accountDao.addAccount(eq(dto))).thenReturn(new Account(500, "checking", 500.00, 88)); //mock account
		
		Account actual = accountDao.addAccount(dto);
		
		
		Account expected = new Account(500, "checking", 500.00, 88);
		
		assertEquals(expected, actual);
	}
	
//	@Test
//	public void test_addAccount_clientDNE() {
//		
//		try {
//			when(accountDao.getAccountByAccountNo(eq(10))).thenReturn(null);
//			
//			accountService.getAllAccountsOfClient("10");
//			
//			fail();
//		}
//		catch(ClientNotFoundException e)
//	}


}
