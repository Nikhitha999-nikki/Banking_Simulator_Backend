package com.bank.BankSimulato.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static final String url = "jdbc:mysql://localhost:3306/banking_simulator";
	private static final String username = "root";
	private static final String password = "Nikhitha09";

	
	
	public static Connection getConnection(){
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
			
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
		
		return connection;
	}

}
