// // package com.bank.BankSimulator;

// // public class ApiServer {
	
// // 	public static void main(String[] args) {
		
// // 	}

// // }

// package com.bank.BankSimulator;
// import java.math.BigDecimal;
	
// import com.bank.BankSimulato.repository.AccountRepository;
// import com.bank.BankSimulato.repository.TransactionRepository;
// import com.bank.BankSimulator.model.Account;
// import com.bank.BankSimulator.service.AccountService;
// import com.bank.BankSimulator.service.AlertService;
// import com.bank.BankSimulator.service.TransactionService;
// import com.google.gson.Gson;

// import static spark.Spark.before;
// import static spark.Spark.get;
// import static spark.Spark.options;
// import static spark.Spark.port;
// import static spark.Spark.post;

// public class ApiServer {
	
// 	public static void main(String[] args) {
// 		port(8080);
// 		Gson gson = new Gson();
// 		AccountRepository accRepo = new AccountRepository();	
// 		AccountService accountService = new AccountService(accRepo);	
// 		TransactionRepository trxRepo = new TransactionRepository();
// 		AlertService alertService = new AlertService(new BigDecimal("10000"));		
// 		TransactionService trxService = new TransactionService(accountService, trxRepo, alertService);	
// 		System.out.println("Bank Simulator API Server is running...");
// 		//create account 
// 		post("/accounts/create", (req, res) -> {
// 			System.out.println("/accounts/create called");
// 			try {
// 				res.type("application/json");
// 				AccountRequest data = gson.fromJson(req.body(), AccountRequest.class);
// 				if (data == null || data.name == null || data.email == null || data.balance == null) {
// 					res.status(400);
// 					return gson.toJson(new ErrorResponse("Invalid request: name, email, and balance are required"));
// 				}
// 				Account acc = accountService.createAccount(data.name, data.email, data.balance);
// 				res.status(201);
// 				return gson.toJson(acc);
// 			} catch (NumberFormatException e) {
// 				res.status(400);
// 				return gson.toJson(new ErrorResponse("Invalid balance: must be a valid number"));
// 			} catch (Exception e) {
// 				res.status(400);
// 				return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
// 			}
// 		});
		
// 		//deposit api
// 		post("/transactions/deposit", (req, res) -> {
// 			try {
// 				res.type("application/json");
// 				TxRequest data = gson.fromJson(req.body(), TxRequest.class);
// 				if (data == null || data.accountNumber == null || data.amount == null) {
// 					res.status(400);
// 					return gson.toJson(new ErrorResponse("Invalid request: accountNumber and amount are required"));
// 				}
// 				trxService.deposite(data.accountNumber, data.amount);
// 				return gson.toJson(new SuccessResponse("Deposit successful"));
// 			} catch (Exception e) {
// 				res.status(400);
// 				return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
// 			}
// 		});
		
// 		//withdraw api
// 		post("/transactions/withdraw", (req, res) -> {
// 			try {
// 				res.type("application/json");
// 				TxRequest data = gson.fromJson(req.body(), TxRequest.class);
// 				if (data == null || data.accountNumber == null || data.amount == null) {
// 					res.status(400);
// 					return gson.toJson(new ErrorResponse("Invalid request: accountNumber and amount are required"));
// 				}
// 				trxService.withdraw(data.accountNumber, data.amount);
// 				return gson.toJson(new SuccessResponse("Withdrawal successful"));
// 			} catch (Exception e) {
// 				res.status(400);
// 				return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
// 			}
// 		});
		
// 		//transfer api
// 		post("/transactions/transfer", (req, res) -> {
// 			try {
// 				res.type("application/json");
// 				TransferRequest data = gson.fromJson(req.body(), TransferRequest.class);
// 				if (data == null || data.fromAccountNumber == null || data.toAccountNumber == null || data.amount == null) {
// 					res.status(400);
// 					return gson.toJson(new ErrorResponse("Invalid request: fromAccountNumber, toAccountNumber, and amount are required"));
// 				}
// 				trxService.tranfer(data.fromAccountNumber, data.toAccountNumber, data.amount);
// 				return gson.toJson(new SuccessResponse("Transfer successful"));
// 			} catch (Exception e) {
// 				res.status(400);
// 				return gson.toJson(new ErrorResponse("Error: " + e.getMessage()));
// 			}
// 		});
		
// 		//view account
// 		get("/accounts/:accountNumber", (req, res) -> {
// 			try {
// 				String accountNumber = req.params(":accountNumber");
// 				Account account = accountService.getAccount(accountNumber);
// 				res.type("application/json");
// 				return gson.toJson(account);
// 			} catch (Exception e) {
// 				res.status(404);
// 				return "Error: " + e.getMessage();
// 			}
// 		});
		
// 		//list all accounts
// 		get("/accounts/all", (req, res) -> {
// 			res.type("application/json");
// 			return gson.toJson(accountService.listAll());
// 		});
		
// 		enableCORS();
// 	}
// 	public static void enableCORS(){
// 		before((request, response) -> {
// 			response.header("Access-Control-Allow-Origin", "*");
// 			response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
// 			response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
// 		});
		
// 		options("/*", (request, response) -> {
// 			String reqheaders = request.headers("Access-Control-Request-Headers");
// 			if (reqheaders != null) {
// 				response.header("Access-Control-Allow-Headers", reqheaders);
// 			}
// 			response.header("Access-Control-Allow-Origin", "*");
// 			response.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
// 			return "OK";
// 		});
// 	}


// 	static class AccountRequest {
// 		 String name;
// 		 String email;
// 		 BigDecimal balance;
//     }

// 	static class TxRequest {
// 		 String accountNumber;
// 		 BigDecimal amount;
//     }
	
// 	static class TransferRequest {
// 		 String fromAccountNumber;
// 		 String toAccountNumber;
// 		 BigDecimal amount;
//     }

// 	static class ErrorResponse {
// 		String error;
		
// 		ErrorResponse(String error) {
// 			this.error = error;
// 		}
// 	}

// 	static class SuccessResponse {
// 		String message;
		
// 		SuccessResponse(String message) {
// 			this.message = message;
// 		}
// 	}

// }

package com.bank.BankSimulator;

import java.math.BigDecimal;

import com.bank.BankSimulato.repository.AccountRepository;
import com.bank.BankSimulato.repository.TransactionRepository;
import com.bank.BankSimulator.model.Account;
import com.bank.BankSimulator.service.AccountService;
import com.bank.BankSimulator.service.AlertService;
import com.bank.BankSimulator.service.TransactionService;
import com.google.gson.Gson;

import static spark.Spark.before;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;

public class ApiServer {

	public static void main(String[] args) {
		port(8080);
		enableCORS();

		Gson gson = new Gson();

		AccountRepository accRepo = new AccountRepository();

		AccountService accountService = new AccountService(accRepo);

		TransactionRepository trxRepo = new TransactionRepository();

		AlertService alertService = new AlertService(new BigDecimal("1000"));
		TransactionService trxService = new TransactionService(accountService,trxRepo,alertService);

		System.out.println("Spark server started on port number 8080");

		//create Account API
		post("/accounts/create",(req, res) -> {
			System.out.println("/accounts/create api is called");
			res.type("application/json");

			AccountRequest data = gson.fromJson(req.body(), AccountRequest.class);
			Account acc = accountService.createAccount(data.name, data.email, data.balance);
			return gson.toJson(acc);

		});


		//Deposite API
		post("/transactions/deposite",(req, res) ->{
			System.out.println("transactions/deposite api is called");
			  TxRequest data = gson.fromJson(req.body(), TxRequest.class);
			  trxService.deposite(data.accNo, data.amount);
			  return "Deposite successfully..!";
		});

		//Withdraw API
		post("/transactions/withdraw",(req, res) ->{
			System.out.println("/transactions/withdraw api is called");
			TxRequest data = gson.fromJson(req.body(), TxRequest.class);
			trxService.withdraw(data.accNo, data.amount);
			return "Withdraw successfully..!";
		});

		post("/transactions/transfer",(req, res) -> {
			System.out.println("/transactions/tranfer api is called");
			TransferRequest data = gson.fromJson(req.body(), TransferRequest.class);
			trxService.tranfer(data.fromAcc, data.toAcc, data.amount);
			return "Transfer successfully..!";

		});

	}

	public static void enableCORS(){
		options("/*",(request ,response) ->{
			String reqheaders = request.headers("Access-Control-Request-Headers");

			if(reqheaders != null) {
				response.header("Access-Control-Allow-Headers",reqheaders);
			}
			return "OK";
		});

		before((request,response) ->{
			response.header("Access-Control-Allow-Origin","*");
			response.header("Access-Control-Allow-Methods","GET,POST,DELETE,OPTIONS,PUT");
			response.header("Access-Control-Allow-Headers","Content-Type,Authorization");

		});



	}



	static class AccountRequest{
			String name;
			String email;
			BigDecimal balance;
		}

	static class TxRequest{
		String accNo;
		BigDecimal amount;
	}

	static class TransferRequest{
		String fromAcc;
		String toAcc;
		BigDecimal amount;
	}

}
