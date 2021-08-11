package com.revature.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.dao.AccountDAO;
import com.revature.dao.ClientDAO;
import com.revature.dto.AddOrEditClientDTO;
import com.revature.exception.BadParameterException;
import com.revature.exception.ClientNotFoundException;
import com.revature.exception.DatabaseException;
import com.revature.model.Client;

public class ClientServiceTest {

	private ClientService clientService;
	private ClientDAO clientDao;
	private AccountDAO accountDao;

	@Before
	public void setUp() throws Exception {
		this.clientDao = mock(ClientDAO.class);
		this.accountDao = mock(AccountDAO.class);

		this.clientService = new ClientService(clientDao, accountDao);
	}

//	@Test
//	public void test_getAllClients_positive() throws DatabaseException, SQLException {
//		
//		List<Client> mockReturnValues = new ArrayList<>();
//		mockReturnValues.add(new Client(1, "Regina", "George", "Washington", "DC", "20001", "1211211212"));
//		mockReturnValues.add(new Client(2, "Cady", "Heron", "Washington", "DC", "20010", "9999899989"));
//		
//		when(clientDao.getAllClients()).thenReturn(mockReturnValues);
//		
//		// List<Client> actual = clientService.getAllClients();
//
//		
//		List<Account> accountsOfRegina = new ArrayList<>();
//		accountsOfRegina.add(new Account(1, "checking", 500.00, 1));
//		accountsOfRegina.add(new Account(2, "savings", 1000.00, 2));
//		when(accountDao.getAllAccountsOfClient(eq(1))).thenReturn(accountsOfRegina);
//		
//		List<Account> accountsOfCady = new ArrayList<>();
//		accountsOfCady.add(new Account(88, "test1", 888.88, 55))
//		
//		Client c1 = new Client(1, "Regina", "George", "Washington", "DC", "20001", "1211211212");
//		c1.setAccounts(new ArrayList<>());
//		Client c2 = new Client(2, "Cady", "Heron", "Washington", "DC", "20010", "9999899989");
//		c2.setAccounts(new ArrayList<>());
//		
//		expected.add(c1);
//		expected.add(c2);
//		
//		assertEquals(expected, actual);
//	}

	@Test
	public void test_getAllClients_negative() throws SQLException {
		when(clientDao.getAllClients()).thenThrow(SQLException.class);

		try {
			clientService.getAllClients();
			fail();
		} catch (DatabaseException e) {
			String exceptionMessage = e.getMessage();
			assertEquals("Something went wrong with our DAO operations", exceptionMessage);
		}
	}

	@Test
	public void test_addClient_posPath() throws SQLException, DatabaseException, BadParameterException {

		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirstName("Amanda");
		dto.setLastName("Amarillo");
		dto.setCity("Palm Springs");
		dto.setState("CA");
		dto.setZipCode("90300");
		dto.setPhoneNumber("9999999999");

		when(clientDao.addClient(eq(dto)))
				.thenReturn(new Client(1, "Amanda", "Amarillo", "Palm Springs", "CA", "90300", "9999999999"));

		Client actual = clientService.addClient(dto);

		Client expected = new Client(1, "Amanda", "Amarillo", "Palm Springs", "CA", "90300", "9999999999");
		expected.setAccounts(new ArrayList<>());

		assertEquals(expected, actual);
	}

	@Test
	public void test_addClient_blankFirstName() throws DatabaseException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirstName("");
		dto.setLastName("Amarillo");
		dto.setCity("Palm Springs");
		dto.setState("CA");
		dto.setZipCode("90300");
		dto.setPhoneNumber("9999999999");

		try {
			clientService.addClient(dto);
			fail();
		} catch (BadParameterException e) {
			assertEquals("Client name fields cannot be blank", e.getMessage());
		}
	}

	@Test
	public void test_addClient_blankLastName() throws DatabaseException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirstName("Amanda");
		dto.setLastName("");
		dto.setCity("Palm Springs");
		dto.setState("CA");
		dto.setZipCode("90300");
		dto.setPhoneNumber("9999999999");

		try {
			clientService.addClient(dto);
			fail();
		} catch (BadParameterException e) {
			assertEquals("Client name fields cannot be blank", e.getMessage());
		}
	}

	@Test
	public void test_addClient_blankFirstAndLastName() throws DatabaseException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirstName("");
		dto.setLastName("");
		dto.setCity("Palm Springs");
		dto.setState("CA");
		dto.setZipCode("90300");
		dto.setPhoneNumber("9999999999");

		try {
			clientService.addClient(dto);
			fail();
		} catch (BadParameterException e) {
			assertEquals("Client name fields cannot be blank", e.getMessage());
		}
	}

	@Test
	public void test_getClientById_posPath()
			throws SQLException, DatabaseException, BadParameterException, ClientNotFoundException {
		when(clientDao.getClientById(eq(1)))
				.thenReturn(new Client(1, "Amanda", "Amarillo", "Palm Springs", "CA", "90300", "9999999999"));

		// When a string is passed into getClientById, it's turned into integer, then we
		// get a
		// DAO is used to get an new Client object.
		Client actual = clientService.getClientById("1");
		Client expected = new Client(1, "Amanda", "Amarillo", "Palm Springs", "CA", "90300", "9999999999");

		assertEquals(expected, actual);
	}

	@Test(expected = ClientNotFoundException.class)
	public void test_getClientById_clientDoesNotExist()
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		// testing for ShipNotFoundException
		// 10 should return null value, as mock response was not provided for
		// getClientByID
		clientService.getClientById("10");
	}

	@Test(expected = BadParameterException.class)
	public void test_getClientByID_idStringIsNotAnInt()
			throws DatabaseException, ClientNotFoundException, BadParameterException {
		clientService.getClientById("ILoveCake");
		// passing in a String should throw a bad parameter exception
	}

	@Test
	public void test_getClientById_sqlExceptionEncountered()
			throws SQLException, ClientNotFoundException, BadParameterException {
		try {
			when(clientDao.getClientById(anyInt())).thenThrow(SQLException.class);

			clientService.getClientById("1");
			fail();
		} catch (DatabaseException e) {
			assertEquals("Something went wrong with our DAO operations.", e.getMessage());
		}
	}

	@Test
	public void test_editClient_posPath() throws SQLException, DatabaseException, ClientNotFoundException, BadParameterException {

		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirstName("Amanda");
		dto.setLastName("Amarillo");
		dto.setCity("Palm Springs");
		dto.setState("CA");
		dto.setZipCode("90300");
		dto.setPhoneNumber("9999999999");
		
		Client clientOfId100 = new Client (100, "Tina", "Fey", "Alamagordo", "NM", "43922", "1111111111");
		when(clientDao.getClientById(eq(100))).thenReturn(clientOfId100);
		
		when(clientDao.editClient(eq(100), eq(dto))).thenReturn(new Client(1, "Amanda", "Amarillo", "Palm Springs", "CA", "90300", "9999999999"));

		Client actual = clientService.editClient("100", dto);

		Client expected = new Client(1, "Amanda", "Amarillo", "Palm Springs", "CA", "90300", "9999999999");
		expected.setAccounts(new ArrayList<>());

		assertEquals(expected, actual);
	}
	
	@Test
	public void test_editClient_clientDNE() throws DatabaseException, BadParameterException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirstName("Amanda");
		dto.setLastName("Amarillo");
		dto.setCity("Palm Springs");
		dto.setState("CA");
		dto.setZipCode("90300");
		dto.setPhoneNumber("9999999999");
		
		try {
			clientService.editClient("500", dto);
			fail();
		}
		catch (ClientNotFoundException e) {
			assertEquals("Unable to edit; Client with id 500 was not found.", e.getMessage());
		}
	}
	
	@Test(expected = DatabaseException.class)
	public void test_editShip_SQLExceptionEncountered() throws SQLException, DatabaseException, ClientNotFoundException, BadParameterException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirstName("Amanda");
		dto.setLastName("Amarillo");
		dto.setCity("Palm Springs");
		dto.setState("CA");
		dto.setZipCode("90300");
		dto.setPhoneNumber("9999999999");
		
		when(clientDao.getClientById(eq(10))).thenReturn(new Client(10, "Tina", "Fey", "Alamagordo", "NM", "43922", "1111111111"));
		when(clientDao.editClient(eq(10), eq(dto))).thenThrow(SQLException.class);
		
		clientService.editClient("10", dto);
	}
	
	@Test
	public void test_editClient_badClientId() throws DatabaseException, ClientNotFoundException {
		AddOrEditClientDTO dto = new AddOrEditClientDTO();
		dto.setFirstName("Amanda");
		dto.setLastName("Amarillo");
		dto.setCity("Palm Springs");
		dto.setState("CA");
		dto.setZipCode("90300");
		dto.setPhoneNumber("9999999999");
		
		try {
		clientService.editClient("coconut", dto);
		fail();
		}
		catch (BadParameterException e) {
			assertEquals("coconut is not an integer", e.getMessage());
		}
	}
}
