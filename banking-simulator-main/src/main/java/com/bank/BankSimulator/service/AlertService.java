package com.bank.BankSimulator.service;
import java.math.BigDecimal;
import com.bank.BankSimulator.util.EmailUtil;
import com.bank.BankSimulator.model.Account;

public class AlertService {
	private final BigDecimal threshold;
	
	public AlertService(BigDecimal threshold) {
		this.threshold = threshold;
	}
	
	public void checkBalance(Account account){
		
		if(account.getBalance().compareTo(threshold) <= 0) {
			
			String subject = "Low Balance Alert: "+account.getAccountNumber();
			String message = "Dear : "+account.getHolderName()+" ,\n\nYour account balance is Low: "+account.getBalance()+
					" \nPlease maintain minimum balance.";
			EmailUtil.sendEmail(account.getEmail(),subject,message);
		}
	}
	
	
}


