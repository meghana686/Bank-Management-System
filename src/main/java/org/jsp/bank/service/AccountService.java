package org.jsp.bank.service;

import java.util.List;
import java.util.Optional;

import org.jsp.bank.dto.ResponseStructure;
import org.jsp.bank.entity.Account;
import org.jsp.bank.entity.AccountType;
import org.jsp.bank.exception.IdNotFoundException;
import org.jsp.bank.exception.NoRecordAvailableException;
import org.jsp.bank.repository.AccountRepository;
import org.jsp.bank.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private BankRepository bankRepository;
	public ResponseStructure<Account> saveAccount(Account account){
		if(accountRepository.existsByAccountNumber(account.getAccountNumber())) {
			throw new IllegalArgumentException("Account number already exists");
		}
		if (account.getBank() == null ||
			    !bankRepository.existsById(account.getBank().getBankId())) {

			    throw new IllegalArgumentException("Bank does not exist");
			}
		 if (account.getAccountType() == null) {
		        throw new IllegalArgumentException("Account type is required");
		    }
		switch (account.getAccountType()) {

	    case SAVINGS:
	        if (account.getBalance() < 1000) {
	            throw new IllegalArgumentException(
	                    "Savings account requires a minimum balance of 1000");
	        }
	        break;

	    case CURRENT:
	        if (account.getBalance() < 5000) {
	            throw new IllegalArgumentException(
	                    "Current account requires a minimum balance of 5000");
	        }
	        break;

	    case FIXED_DEPOSIT:
	        if (account.getBalance() < 10000) {
	            throw new IllegalArgumentException(
	                    "Fixed deposit account requires a minimum balance of 10000");
	        }
	        break;
	}
		
		ResponseStructure<Account> res= new ResponseStructure<>();
		res.setData(accountRepository.save(account));
		res.setMessage("Account record saved successfully");
		res.setStatusCode(HttpStatus.CREATED.value());
		return res;
	}
	public ResponseStructure<List<Account>> getAllAccounts(){
		List<Account> accounts=accountRepository.findAll();
		if(accounts.isEmpty()) {
			throw new NoRecordAvailableException("No records available in the account table");
		}
		ResponseStructure<List<Account>> res= new ResponseStructure<>();
		res.setData(accounts);
		res.setMessage("Account records retrieved successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public ResponseStructure<Account> getAccountId(Integer id){
		Optional<Account> account=accountRepository.findById(id);
		if(account.isEmpty()) {
			throw new IdNotFoundException("No record is available in the account table with id "+id);
		}
		ResponseStructure<Account> res= new ResponseStructure<>();
		res.setData(account.get());
		res.setMessage("Account record retrieved successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public ResponseStructure<List<Account>> getAccountByBankId(Integer id){
		if (!bankRepository.existsById(id)) {
		    throw new IdNotFoundException("Bank not found with ID: " + id);
		}
		List<Account> accounts=accountRepository.findByBank_BankId(id);
		if(accounts.isEmpty()) {
			throw new NoRecordAvailableException("No records available in the account table");
		}
		ResponseStructure<List<Account>> res= new ResponseStructure<>();
		res.setData(accounts);
		res.setMessage("Account records retrieved successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public ResponseStructure<List<Account>> getAccountsByType(AccountType accountType){
		if (accountType == null) {
		    throw new IllegalArgumentException("Account type is required");
		}
		List<Account> accounts=accountRepository.findByAccountType(accountType);
		if(accounts.isEmpty()) {
			throw new NoRecordAvailableException("No records available in the account table");
		}
		ResponseStructure<List<Account>> res= new ResponseStructure<>();
		res.setData(accounts);
		res.setMessage("Account records retrieved successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public ResponseStructure<Account> getAccountByAccountNumber(String accountNumber){
		 if (accountNumber == null || accountNumber.isBlank()) {
		        throw new IllegalArgumentException("Account number is required");
		    }
		Account account=accountRepository.findByAccountNumber(accountNumber);
		if(account==null) {
			throw  new IdNotFoundException("Account not found with account number: " + accountNumber);
		}
		ResponseStructure<Account> res= new ResponseStructure<>();
		res.setData(account);
		res.setMessage("Account record retrieved successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public ResponseStructure<List<Account>> getAccountsWithBalanceGreaterThan(Double balance){
		if (balance == null || balance < 0) {
	        throw new IllegalArgumentException("Balance must be a valid non-negative value");
	    }
		List<Account> accounts =accountRepository.findByBalanceGreaterThan(balance);
		if(accounts.isEmpty()) {
			throw new NoRecordAvailableException("No records available in the account table");
		}
		ResponseStructure<List<Account>> res= new ResponseStructure<>();
		res.setData(accounts);
		res.setMessage("Account records retrieved successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public ResponseStructure<List<Account>> getAccountsBySorting(String fieldName, String direction){
		 Sort sort;
		    if (direction.equalsIgnoreCase("asc")) {
		        sort = Sort.by(fieldName).ascending();
		    } else if (direction.equalsIgnoreCase("desc")) {
		        sort = Sort.by(fieldName).descending();
		    } else {
		        throw new IllegalArgumentException("Direction must be asc or desc");
		    }
		List<Account> accounts=accountRepository.findAll(sort);
		if(accounts.isEmpty()) {
			throw new NoRecordAvailableException("No records available in the account table");
		}
		ResponseStructure<List<Account>> res= new ResponseStructure<>();
		res.setData(accounts);
		res.setMessage("Account records retrieved successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
		
	}
	public ResponseStructure<Account> depositAmount(String accountNumber, Double amount){
		if(amount==null||amount<=0) {
			throw new IllegalArgumentException("amount should be positive");
		}
		if (accountNumber == null || accountNumber.isBlank()) {
	        throw new IllegalArgumentException("Account number is required");
	    }
		
		Account account=accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
		    throw new IdNotFoundException("Account not found with account number: " + accountNumber);
		}
		if (account.getBalance() == null) {
		    throw new IllegalArgumentException("Account balance cannot be null");
		}
		account.setBalance(account.getBalance()+amount);
		accountRepository.save(account);
		ResponseStructure<Account> res= new ResponseStructure<>();
		res.setData(account);
		res.setMessage("Amount deposited successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
		
	}
	public ResponseStructure<Account> withdrawAmount(String accountNumber, Double amount){
		if(amount==null||amount<=0) {
			throw new IllegalArgumentException("amount should be positive");
		}
		if (accountNumber == null || accountNumber.isBlank()) {
	        throw new IllegalArgumentException("Account number is required");
	    }
		Account account=accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
		    throw new IdNotFoundException("Account not found with account number: " + accountNumber);
		}
		if (account.getBalance() == null) {
		    throw new IllegalArgumentException("Account balance cannot be null");
		}

		if (account.getBalance() < amount) {
		    throw new IllegalArgumentException("Insufficient balance");
		}
		account.setBalance(account.getBalance()-amount);
		accountRepository.save(account);
		ResponseStructure<Account> res= new ResponseStructure<>();
		res.setData(account);
		res.setMessage("Amount withdrawn successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
		
	}
	public ResponseStructure<List<Account>> transferAmount(
	        String senderAccountNumber,
	        String receiverAccountNumber,
	        Double amount){
		if (senderAccountNumber == null || senderAccountNumber.isBlank()) {
	        throw new IllegalArgumentException(" sender Account number is required");
	    }
		if (receiverAccountNumber == null || receiverAccountNumber.isBlank()) {
	        throw new IllegalArgumentException(" receiver Account number is required");
	    }
		if (senderAccountNumber.equals(receiverAccountNumber)) {
		    throw new IllegalArgumentException("Sender and receiver accounts must be different");
		}
		if(amount==null||amount<=0) {
			throw new IllegalArgumentException("amount should be positive");
		}
		Account senderAccount=accountRepository.findByAccountNumber( senderAccountNumber);
		Account receiverAccount=accountRepository.findByAccountNumber( receiverAccountNumber);
		if (senderAccount == null) {
		     throw  new IdNotFoundException("Id must be present for transferring amount");
		}
		if (receiverAccount == null) {
		     throw  new IdNotFoundException("Id must be present for transferring amount");
		}
		if (senderAccount.getBalance() < amount) {
		    throw new IllegalArgumentException("sender has Insufficient balance");
		}
		senderAccount.setBalance(senderAccount.getBalance()-amount);
		receiverAccount.setBalance(receiverAccount.getBalance()+amount);
		accountRepository.save(senderAccount);
		accountRepository.save(receiverAccount);
		List<Account> accounts = List.of(senderAccount, receiverAccount);
		ResponseStructure<List<Account>> res= new ResponseStructure<>();
		res.setData(accounts);
		res.setMessage("Account transferred successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;	
	}
	public ResponseStructure<Account> updateAccountDetails(
	        String accountNumber,
	        String accountHolder,
	        AccountType accountType){
		if (accountNumber == null || accountNumber.isBlank()) {
	        throw new IllegalArgumentException("Account number is required");
	    }
		if (accountHolder == null || accountHolder.isBlank()) {
	        throw new IllegalArgumentException("Account holder is required");
	    }
		if (accountType == null) {
	        throw new IllegalArgumentException("accountType is required");
	    }
		
		Account account=accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
		    throw new IdNotFoundException("Account not found with account number: " + accountNumber);
		}
		account.setAccountHolder(accountHolder);
		account.setAccountType(accountType);
		accountRepository.save(account);
		ResponseStructure<Account> res= new ResponseStructure<>();
		res.setData(account);
		res.setMessage("Account details updated successfully");
		res.setStatusCode(HttpStatus.OK.value());
		return res;
	}
	public ResponseStructure<Account> deleteAccount(Integer accountId){
		if(accountId==null) {
			 throw new IllegalArgumentException("Account Id is required");
		}
	Optional<Account> account=accountRepository.findById(accountId);
	if(account.isEmpty()) {
		throw new IdNotFoundException("No record is available in the account table with id "+accountId);
	}
	Account account1 = account.get();
	accountRepository.delete(account1);
	ResponseStructure<Account> res= new ResponseStructure<>();
	res.setData(account1);
	res.setMessage("Account deleted successfully");
	res.setStatusCode(HttpStatus.OK.value());
	return res;
	
	}

}
