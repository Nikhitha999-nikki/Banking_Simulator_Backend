
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
import static spark.Spark.get;
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
			return gson.toJson(new CreateAccountResponse(acc.getAccountNumber()));

		});


		//Deposit API
		post("/transactions/deposit",(req, res) ->{
			System.out.println("transactions/deposit api is called");
			res.type("application/json");
			TxRequest data = gson.fromJson(req.body(), TxRequest.class);
			trxService.deposite(data.accountNumber, data.amount);
			return gson.toJson(new SuccessResponse("Deposit successful"));
		});

		//Withdraw API
		post("/transactions/withdraw",(req, res) ->{
			System.out.println("/transactions/withdraw api is called");
			res.type("application/json");
			TxRequest data = gson.fromJson(req.body(), TxRequest.class);
			trxService.withdraw(data.accountNumber, data.amount);
			return gson.toJson(new SuccessResponse("Withdrawal successful"));
		});
		
		// View single account API
get("/accounts/:accountNumber", (req, res) -> {
    System.out.println("/accounts/{accountNumber} api is called");
    res.type("application/json");

    String accNo = req.params(":accountNumber");
    Account account = accountService.getAccount(accNo);

    if (account == null) {
        res.status(404);
        return gson.toJson(new SuccessResponse("Account not found"));
    }

    return gson.toJson(account);
});

// View all accounts API
get("/accounts", (req, res) -> {
    System.out.println("/accounts api is called");
    res.type("application/json");
    return gson.toJson(accountService.listAll());

});

		//Transfer API
		post("/transactions/transfer",(req, res) -> {
			System.out.println("/transactions/transfer api is called");
			res.type("application/json");
			TransferRequest data = gson.fromJson(req.body(), TransferRequest.class);
			trxService.tranfer(data.fromAccountNumber, data.toAccountNumber, data.amount);
			return gson.toJson(new SuccessResponse("Transfer successful"));

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
		String accountNumber;
		BigDecimal amount;
	}

	static class TransferRequest{
		String fromAccountNumber;
		String toAccountNumber;
		BigDecimal amount;
	}

	static class CreateAccountResponse {
		String accountNumber;

		CreateAccountResponse(String accountNumber) {
			this.accountNumber = accountNumber;
		}
	}

	static class SuccessResponse {
		String message;

		SuccessResponse(String message) {
			this.message = message;
		}
	}

}
