package com.revature.model;

import java.util.Objects;

public class Account {

	private int accountNo;
	private String accountType;
	private double balance;
	private int client_id;

	public Account() {
		super();
	}

	public Account(int accountNo, String accountType, double balance, int client_id) {
		super();
		this.accountNo = accountNo;
		this.accountType = accountType;
		this.balance = balance;
		this.client_id = client_id;
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

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNo, accountType, balance, client_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return accountNo == other.accountNo && Objects.equals(accountType, other.accountType)
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& client_id == other.client_id;
	}

	@Override
	public String toString() {
		return "Account [accountNo=" + accountNo + ", accountType=" + accountType + ", balance=" + balance
				+ ", client_id=" + client_id + "]";
	}
}
