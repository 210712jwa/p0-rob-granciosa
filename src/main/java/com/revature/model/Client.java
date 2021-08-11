package com.revature.model;

import java.util.List;
import java.util.Objects;

public class Client {

	private int clientId;
	private String firstName;
	private String lastName;
	private String city;
	private String state;
	private String zipCode;
	private String phoneNumber;
	
	List<Account> accounts;
	
	public Client() {
		super();
	}
	
	// Parameterized constructor
	public Client(int clientId, String firstName, String lastName, String city, String state, String zipCode, String phoneNumber) {
		super();
		this.clientId = clientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(city, clientId, firstName, lastName, phoneNumber, state, zipCode);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(city, other.city) && clientId == other.clientId
				&& Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(phoneNumber, other.phoneNumber) && Objects.equals(state, other.state)
				&& Objects.equals(zipCode, other.zipCode);
	}
	
	@Override
	public String toString() {
		return "client [clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName + ", city=" + city
				+ ", state=" + state + ", zipCode=" + zipCode + ", phoneNumber=" + phoneNumber + "]";
	}

}
