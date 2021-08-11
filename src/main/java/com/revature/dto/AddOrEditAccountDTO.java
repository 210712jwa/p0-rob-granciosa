package com.revature.dto;

import java.util.Objects;

public class AddOrEditAccountDTO {
	
	private int accountNo;
	private String accountType;
	private double balance;
	private int clientId;
	
	public AddOrEditAccountDTO() {
		super();
	}
	public int getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(accountNo, accountType, balance, clientId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrEditAccountDTO other = (AddOrEditAccountDTO) obj;
		return accountNo == other.accountNo && Objects.equals(accountType, other.accountType)
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& clientId == other.clientId;
	}

}
