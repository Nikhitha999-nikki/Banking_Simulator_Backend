package com.bank.BankSimulator.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class Account{
	
	private static final AtomicLong COUNTER = new AtomicLong(1000000L);
	
	private final String accountNumber;
	private String holderName;
	private String email;
	private BigDecimal balance;
	
	public Account(String holderName, String email, BigDecimal openingBalance) {
		this.accountNumber = String.valueOf(COUNTER.getAndIncrement());
		this.holderName = holderName;
		this.email = email;
		this.balance = openingBalance;
	}
	
	
	public synchronized void credit(BigDecimal amount) {
		this.balance = this.balance.add(amount);
	}

	public synchronized void debit(BigDecimal amount) {
		this.balance = this.balance.subtract(amount);
	}
	
	
	
	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}
	
	@Override
	public String toString(){
		return accountNumber+" "+holderName+" "+email+" "+balance;
	}
	 
	
	 
}
