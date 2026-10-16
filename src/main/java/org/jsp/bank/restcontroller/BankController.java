package org.jsp.bank.restcontroller;

import java.util.List;

import org.jsp.bank.dto.ResponseStructure;
import org.jsp.bank.entity.Bank;
import org.jsp.bank.service.BankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/bank")
    public ResponseEntity<ResponseStructure<Bank>> saveBank(
            @RequestBody Bank bank) {

        ResponseStructure<Bank> response = bankService.saveBank(bank);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/banks")
    public ResponseEntity<ResponseStructure<List<Bank>>> getAllBanks() { 
        ResponseStructure<List<Bank>> response = bankService.getAllBanks();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/bank/{id}")
    public ResponseEntity<ResponseStructure<Bank>> getBankById(@PathVariable Integer id) {
    	 ResponseStructure<Bank> response = bankService.getBankById(id);
    	 return new ResponseEntity<>(response, HttpStatus.OK);   	
    }
    @GetMapping("/bank/ifsc/{ifsc}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByIfsc(@PathVariable String ifsc) {
        ResponseStructure<Bank> response = bankService.getBankByIfsc(ifsc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/bank/contact/{contact}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByContact(@PathVariable String contact) {
        ResponseStructure<Bank> response = bankService.getBankByContact(contact);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/banks/city/{city}")
    public ResponseEntity<ResponseStructure<List<Bank>>> getBanksByCity(@PathVariable String city) {
        ResponseStructure<List<Bank>> response = bankService.getBanksByCity(city);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/bank/address/{add_id}")
    public ResponseEntity<ResponseStructure<Bank>> getBanksByAddressId(
            @PathVariable("add_id") Integer id) {

        ResponseStructure<Bank> response =
                bankService.getBankByAddressId(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/bank/{bankId}/contact")
    public ResponseEntity<ResponseStructure<Bank>> updateBankContact(
            @PathVariable Integer bankId,
            @RequestParam String contact) {
        ResponseStructure<Bank> response =
                bankService.updateBankContact(bankId, contact);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/bank/{pageNumber}/{pageSize}/{fieldName}")
    public ResponseEntity<ResponseStructure<Page<Bank>>> getBankByPaginationSorting(@PathVariable Integer pageNumber,
    		@PathVariable Integer pageSize, @PathVariable String fieldName){
    	ResponseStructure<Page<Bank>> response =
                bankService.getBankByPaginationSorting(pageNumber,pageSize,fieldName);
    	return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/bank/{bankId}")
    public ResponseEntity<ResponseStructure<Bank>> deleteBank(
            @PathVariable Integer bankId){
    	 ResponseStructure<Bank> response =
                 bankService.deleteBank(bankId);
         return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    
}
