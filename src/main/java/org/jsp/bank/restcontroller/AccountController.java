package org.jsp.bank.restcontroller;

import java.net.http.HttpRequest;
import java.util.List;

import org.jsp.bank.dto.ResponseStructure;
import org.jsp.bank.entity.Account;
import org.jsp.bank.entity.AccountType;
import org.jsp.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;
	@PostMapping("/account")
	public ResponseEntity<ResponseStructure<Account>> saveAccount(
	        @RequestBody Account account) {

	    ResponseStructure<Account> res = accountService.saveAccount(account);

	    return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	@GetMapping("/account")
	public ResponseEntity<ResponseStructure<List<Account>>> getAllAccount() {

	    ResponseStructure<List<Account>> res = accountService.getAllAccounts();

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/account/{id}")
	public ResponseEntity<ResponseStructure<Account>> getAccountById(@PathVariable Integer id) {
	    ResponseStructure<Account> res = accountService.getAccountId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/account/bank/{bankId}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountByBankId(@PathVariable Integer bankId) {
	    ResponseStructure<List<Account>> res = accountService.getAccountByBankId(bankId);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/account/type/{accountType}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountByAccountType(
	        @PathVariable AccountType accountType) {

	    ResponseStructure<List<Account>> res =
	            accountService.getAccountsByType(accountType);

	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/account/number/{accountNumber}")
	public ResponseEntity<ResponseStructure<Account>> getAccountByAccountNumber(
	        @PathVariable String accountNumber) {
	    ResponseStructure<Account> res =
	            accountService.getAccountByAccountNumber(accountNumber);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/account/balance/{balance}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountByBalanceGreaterThanAValue(
	        @PathVariable Double balance) {
	    ResponseStructure<List<Account>> res =
	            accountService. getAccountsWithBalanceGreaterThan(balance);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/account/sort/{fieldName}/{direction}")
	public ResponseEntity<ResponseStructure<List<Account>>> getAccountsBySorting(
	        @PathVariable String fieldName,
	        @PathVariable String direction) {
		 ResponseStructure<List<Account>> res =
		            accountService.getAccountsBySorting(fieldName,direction);
		    return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	@PatchMapping("/account/deposit/{accountNumber}/{amount}")
	public ResponseEntity<ResponseStructure<Account>> depositAmount(
	        @PathVariable String accountNumber,
	        @PathVariable  Double amount) {
		 ResponseStructure<Account> res =
		            accountService.depositAmount(accountNumber,amount);
		    return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	@PatchMapping("/account/withdraw/{accountNumber}/{amount}")
	public ResponseEntity<ResponseStructure<Account>> withdrawAmount(
	        @PathVariable String accountNumber,
	        @PathVariable  Double amount) {
		 ResponseStructure<Account> res =
		            accountService.withdrawAmount(accountNumber,amount);
		    return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	@PatchMapping("/account/transfer/{senderAccountNumber}/{receiverAccountNumber}/{amount}")
	public ResponseEntity<ResponseStructure<List<Account>>> transferAmount(
	        @PathVariable String senderAccountNumber,
	        @PathVariable String receiverAccountNumber,
	        @PathVariable Double amount) {
		 ResponseStructure<List<Account>> res =
		            accountService.transferAmount(
		        	        senderAccountNumber,
		        	        receiverAccountNumber,
		        	         amount);
		    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@PatchMapping("/account/update-details/{accountNumber}")
	public ResponseEntity<ResponseStructure<Account>> updateAccountDetails(
	        @PathVariable String accountNumber,
	        @RequestParam String accountHolder,
	        @RequestParam AccountType accountType){
		 ResponseStructure<Account> res =
		            accountService.updateAccountDetails(accountNumber,accountHolder,accountType);
		    return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	@DeleteMapping("/account/{accountId}")
	public ResponseEntity<ResponseStructure<Account>> deleteAccount(
	        @PathVariable Integer accountId){
		 ResponseStructure<Account> res =
		            accountService.deleteAccount(accountId);
		    return new ResponseEntity<>(res, HttpStatus.OK);
		
	}
	

}
