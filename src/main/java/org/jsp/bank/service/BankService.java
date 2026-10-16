package org.jsp.bank.service;

import java.util.List;
import java.util.Optional;


import org.jsp.bank.dto.ResponseStructure;
import org.jsp.bank.entity.Account;
import org.jsp.bank.entity.Address;
import org.jsp.bank.entity.Bank;
import org.jsp.bank.exception.IdNotFoundException;
import org.jsp.bank.exception.NoRecordAvailableException;
import org.jsp.bank.repository.AddressRepository;
import org.jsp.bank.repository.BankRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseStructure<Bank> saveBank(Bank bank) {

        // Check whether the bank object is present
        if (bank == null) {
            throw new IllegalArgumentException("Bank details are required");
        }

        // Check whether the IFSC code already exists
        if (bank.getIfsc() == null || bank.getIfsc().isBlank()) {
            throw new IllegalArgumentException("IFSC code is required");
        }

        if (bankRepository.existsByIfsc(bank.getIfsc())) {
            throw new IllegalArgumentException("IFSC code already exists");
        }

        // Validate contact number
        String contact = bank.getContact();

        if (contact == null || !contact.matches("[0-9]{10}")) {
            throw new IllegalArgumentException(
                    "Contact number must contain exactly 10 digits");
        }

        // Check whether the contact number already exists
        if (bankRepository.existsByContact(contact)) {
            throw new IllegalArgumentException(
                    "Contact number already exists");
        }

        // Get the address from the bank object
        Address address = bank.getAddress();

        if (address == null) {
            throw new IllegalArgumentException("Address is required");
        }

        // Validate pincode
        if (address.getPincode() == null
                || !address.getPincode().matches("[0-9]{6}")) {

            throw new IllegalArgumentException(
                    "Pincode must contain exactly 6 digits");
        }
        if (address.getAddressId() == null) {

            address = addressRepository.save(address);

           
            bank.setAddress(address);

        } else {
            if (!addressRepository.existsById(address.getAddressId())) {
                throw new IllegalArgumentException(
                        "Address does not exist");
            }
        }
        Bank savedBank = bankRepository.save(bank);

     
        ResponseStructure<Bank> res = new ResponseStructure<>();

        res.setData(savedBank);
        res.setMessage("Bank and address saved successfully");
        res.setStatusCode(HttpStatus.CREATED.value());

        return res;
    }
    public ResponseStructure<List<Bank>> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        if (banks.isEmpty()) {
            throw new NoRecordAvailableException("No bank records found");
        }
        ResponseStructure<List<Bank>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Bank records retrieved successfully");
        response.setData(banks);
        return response;
    }
    public ResponseStructure<Bank> getBankById(Integer bankId){
    	Optional<Bank> opt=bankRepository.findById(bankId);
    	  if (opt.isEmpty()) {
    	        throw new IdNotFoundException("Bank not found with ID: " + bankId);
    	    }
    	  Bank bank=opt.get();
    	  ResponseStructure<Bank> response = new ResponseStructure<>();
          response.setStatusCode(HttpStatus.OK.value());
          response.setMessage("Bank record retrieved successfully");
          response.setData(bank);
          return response;	
    }
    public ResponseStructure<Bank> getBankByIfsc(String ifsc){
    	Bank bank=bankRepository.findByIfsc(ifsc);
    	if(bank==null) {
    		throw new IdNotFoundException("Bank not found with IFSC : " + ifsc);
    	}
    	ResponseStructure<Bank> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Bank record retrieved successfully");
        response.setData(bank);
        return response;
}
    public ResponseStructure<Bank> getBankByContact(String contact){
    	Bank bank=bankRepository.findByContact(contact);
    	if(bank==null) {
    		throw new IdNotFoundException("Bank not found with contact : " + contact);
    	}
    	ResponseStructure<Bank> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Bank record retrieved successfully");
        response.setData(bank);
        return response;
}
    public ResponseStructure<List<Bank>> getBanksByCity(String city){
    	List<Bank>banks=bankRepository.findByAddressCity(city);
    	if(banks.isEmpty()) {
    		throw new IdNotFoundException("Banks are  not found in the give city : " + city);
    	}
    	ResponseStructure<List<Bank>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Banks record retrieved successfully");
        response.setData(banks);
        return response;
    }
    public ResponseStructure<Bank> getBankByAddressId(Integer id){
    	Bank bank=bankRepository.findByAddress_AddressId(id);
    	if(bank==null) {
    		throw new IdNotFoundException("Bank not found with address id : " + id);
    	}
    	ResponseStructure<Bank> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Bank record retrieved successfully");
        response.setData(bank);
        return response;
}
    public ResponseStructure<Bank> updateBankContact(Integer bankId, String newContact) {
        if (newContact == null || !newContact.matches("\\d{10}")) {
            throw new IllegalArgumentException(
                    "Contact number must contain exactly 10 digits");
        }
        Bank bank = bankRepository.findById(bankId)
                .orElseThrow(() ->
                        new IdNotFoundException("Bank not found with ID: " + bankId));
        Bank existingBank = bankRepository.findByContact(newContact);
        if (existingBank != null && !existingBank.getBankId().equals(bankId)) {
            throw new IllegalArgumentException(
                    "Contact number already exists");
        }
        bank.setContact(newContact);
        Bank updatedBank = bankRepository.save(bank);
        ResponseStructure<Bank> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Bank contact updated successfully");
        response.setData(updatedBank);
        return response;
    }
    public ResponseStructure<Page<Bank>> getBankByPaginationSorting(
            int pageNumber, int pageSize, String fieldName) {

        Page<Bank> page = bankRepository.findAll(
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.by(fieldName).descending()
                )
        );
        if(page.isEmpty()) {
        	throw new NoRecordAvailableException("NO records available");
        }
        ResponseStructure<Page<Bank>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Bank record displayed successfully");
        response.setData(page);
        return response;
        
    }
    public ResponseStructure<Bank> deleteBank(Integer bankId){
    	if(bankId==null) {
    		 throw new IllegalArgumentException(
                     "Bank Id is reqired");
    	}
    	Optional<Bank> opt=bankRepository.findById(bankId);
  	  if (opt.isEmpty()) {
  	        throw new IdNotFoundException("Bank not found with ID: " + bankId);
  	    }
  	  Bank bank=opt.get();
  	if (bank.getAccounts() != null && !bank.getAccounts().isEmpty()) {
  	    throw new IllegalArgumentException(
  	            "Cannot delete bank because accounts are associated with it");
  	}
  	  bankRepository.delete(bank);
  	  ResponseStructure<Bank> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Bank record deleted successfully");
        response.setData(bank);
        return response;	
    	
    }
    	
     	
}