package org.jsp.bank.service;

import java.util.List;
import java.util.Optional;

import org.jsp.bank.dto.ResponseStructure;
import org.jsp.bank.entity.Address;
import org.jsp.bank.entity.Bank;
import org.jsp.bank.exception.IdNotFoundException;
import org.jsp.bank.exception.NoRecordAvailableException;
import org.jsp.bank.repository.AddressRepository;
import org.jsp.bank.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



@Service
public class AddressService {
	@Autowired
private AddressRepository addressRepository;
	@Autowired
	private BankRepository bankRepository;
public ResponseStructure<Address> saveAddress(Address address) {
	//check pincode is 6 digits or not
	 String pincode = address.getPincode();

     if (pincode == null || !pincode.matches("[0-9]{6}")) {
         throw new IllegalArgumentException(
             "PIN code must contain exactly six digits"
         );
     }
     ResponseStructure<Address> res=new ResponseStructure<>();
     res.setData(addressRepository.save(address));
     res.setMessage("Address record saved successfully");
     res.setStatusCode(HttpStatus.CREATED.value());
     return res;
}
public ResponseStructure<Address> getAddressId(Integer id) {
	Optional<Address> address=addressRepository.findById(id);
	if(address.isEmpty()) {
		 throw new IdNotFoundException(
	                "Address not found with ID: " + id);
	}
	ResponseStructure<Address> res=new ResponseStructure<>();
    res.setData(address.get());
    res.setMessage("Address record retrieved successfully with given id "+id);
    res.setStatusCode(HttpStatus.OK.value());
    return res;
}
public ResponseStructure<Address> updateAddress(Integer id, Address newAddress) {
	Optional<Address> address = addressRepository.findById(id);
	if(address.isEmpty()) {
		throw new IdNotFoundException(
                "Address not found with ID: " + id);
	}
	 if (newAddress.getPincode() == null || !newAddress.getPincode().matches("[0-9]{6}")) {
         throw new IllegalArgumentException(
             "PIN code must contain exactly six digits"
         );
     }
	 Address address1=addressRepository.save(newAddress);
	 ResponseStructure<Address> res=new ResponseStructure<>();
	    res.setData(address1);
	    res.setMessage("Address record updated successfully ");
	    res.setStatusCode(HttpStatus.CREATED.value());
	    return res; 
}
public ResponseStructure<Address> getAddressByBank(Integer bankId) {
	Optional<Bank> bank=bankRepository.findById(bankId);
	if(bank.isEmpty()) {
		throw new IdNotFoundException(
                "Address not found with ID: " + bankId);
	}
	ResponseStructure<Address> res=new ResponseStructure<>();
    res.setData(bank.get().getAddress());
    res.setMessage("Address record retrieved successfully");
    res.setStatusCode(HttpStatus.OK.value());
    return res; 
}
public ResponseStructure<List<Address>> getAddressesByCity(String city){
	List<Address> address=addressRepository.findByCity(city);
	if(address.isEmpty()) {
		throw new NoRecordAvailableException("No records available related to city "+city);
	}
		ResponseStructure<List<Address>> response=new ResponseStructure<>();
		response.setData(address);
		response.setMessage("Address records retrieved successfully for city: " + city);
		response.setStatusCode(HttpStatus.OK.value());
		return response;
	}
public ResponseStructure<List<Address>> getAddressesBySteetCity(String street,String city){
	List<Address> address=addressRepository.findByStreetAndCity(street,city);
	if(address.isEmpty()) {
		throw new NoRecordAvailableException("No records available related to city and street "+city+", "+street);
	}
		ResponseStructure<List<Address>> response=new ResponseStructure<>();
		response.setData(address);
		response.setMessage("Address records retrieved successfully ");
		response.setStatusCode(HttpStatus.OK.value());
		return response;
	}
public ResponseStructure<List<Address>> getAddressesByPincode(String pincode){
	List<Address> address=addressRepository.findByPincode(pincode);
	if(address.isEmpty()) {
		throw new NoRecordAvailableException("No records available related to city "+pincode);
	}
		ResponseStructure<List<Address>> response=new ResponseStructure<>();
		response.setData(address);
		response.setMessage("Address records retrieved successfully for city: " + pincode);
		response.setStatusCode(HttpStatus.OK.value());
		return response;
	}
}
