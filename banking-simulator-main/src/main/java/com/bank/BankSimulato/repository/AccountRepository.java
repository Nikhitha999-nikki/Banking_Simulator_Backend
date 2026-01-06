package com.bank.BankSimulato.repository;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.bank.BankSimulator.model.Account;
public class AccountRepository {
	
	private final Map<String,Account>accounts = new HashMap<>();
	
	public void save(Account account) {
		accounts.put(account.getAccountNumber(), account);
	}
	
	public Account findAccountByNumber(String accountNumber){
		Account account = accounts.get(accountNumber);
		return account;
	}
	
	
	public Collection<Account> findAll(){
		return accounts.values();
	}
	
}




