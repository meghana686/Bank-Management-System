package org.jsp.bank.restcontroller;

import org.jsp.bank.entity.Address;
import org.jsp.bank.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.jsp.bank.dto.ResponseStructure;

@RestController
public class AddressController {
	@Autowired
	AddressService  addressService;
	@GetMapping("/address/{id}")
	public ResponseEntity<ResponseStructure<Address>> getAddressById(
	        @PathVariable Integer id) {
	    ResponseStructure<Address> res = addressService.getAddressId(id);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@PutMapping("/address/{id}")
	public ResponseEntity<ResponseStructure<Address>> updateAddress(
	        @PathVariable Integer id,@RequestBody Address address) {
	    ResponseStructure<Address> res = addressService. updateAddress(id,address);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/address/bank/{bankId}")
	public ResponseEntity<ResponseStructure<Address>> getAddressByBank(
	        @PathVariable Integer bankId) {
	    ResponseStructure<Address> res = addressService.getAddressByBank(bankId);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/address/city/{city}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCity(
	        @PathVariable String city) {
	    ResponseStructure<List<Address>> res =
	            addressService.getAddressesByCity(city);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/address/{city}/{street}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByStreetCity(
	        @PathVariable String street,@PathVariable String city) {
	    ResponseStructure<List<Address>> res =
	            addressService.getAddressesBySteetCity(street,city);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
	@GetMapping("/address/pincode/{pincode}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByPincode(
	        @PathVariable String pincode) {
	    ResponseStructure<List<Address>> res =
	            addressService.getAddressesByPincode(pincode);
	    return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
