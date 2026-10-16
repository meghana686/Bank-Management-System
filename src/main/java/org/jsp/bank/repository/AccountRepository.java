package org.jsp.bank.repository;

import java.util.List;

import org.jsp.bank.entity.Account;
import org.jsp.bank.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;


public interface AccountRepository extends JpaRepository<Account,Integer>{
	boolean existsByAccountNumber(String accountNumber);
	List<Account> findByBank_BankId(Integer bankId);
	List<Account> findByAccountType(AccountType accountType);
	Account findByAccountNumber(String accountNumber);
	List<Account> findByBalanceGreaterThan(Double balance);
	List<Account> findAll(Sort sort);
}