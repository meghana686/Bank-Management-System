package org.jsp.bank.repository;


import java.util.List;

import org.jsp.bank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository  extends JpaRepository<Bank,Integer>{
	boolean existsByIfsc(String ifsc);
	boolean existsByContact(String contact);
	Bank findByIfsc(String ifsc);
	Bank findByContact(String contact);
	List<Bank> findByAddressCity(String city);
	Bank findByAddress_AddressId(Integer addressId);
}
