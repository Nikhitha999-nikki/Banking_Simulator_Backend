package com.bank.BankSimulato.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TransactionRepository {
	
	public void logTransaction(String type,String accNo,double amount,String target_number){
		String query = "INSERT INTO transactions(type,account_number,amount,target_account)values(?,?,?,?)";
	 
		try(Connection con = DBConnection.getConnection();
			PreparedStatement pstmt = con.prepareStatement(query);
				){
			pstmt.setString(1,type);
			pstmt.setString(2, accNo);
			pstmt.setDouble(3, amount);
			pstmt.setString(4, target_number);

			pstmt.executeUpdate();
			
			
	
		}
		catch(Exception e) {
			System.out.println("DB Insert Error :"+e.getMessage());
		}
	}
}