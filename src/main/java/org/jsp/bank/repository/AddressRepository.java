package org.jsp.bank.repository;


import java.util.List;

import org.jsp.bank.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;


	public interface AddressRepository  extends JpaRepository<Address,Integer>{
     boolean existsById(Integer id);
     List<Address> findByCity(String city);
     List<Address> findByStreetAndCity(String street, String city);
     List<Address> findByPincode(String pincode);
	}
