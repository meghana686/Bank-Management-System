package org.jsp.bank.exception;

import org.jsp.bank.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleINFE(
            IdNotFoundException exception) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(exception.getMessage());
        res.setData("Failure");

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoRecordAvailableException.class)
    public ResponseEntity<ResponseStructure<String>> handleNRAE(
            NoRecordAvailableException exception) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.NOT_FOUND.value());
        res.setMessage(exception.getMessage());
        res.setData("Failure");

        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseStructure<String>> handleIllegalArgumentException(
            IllegalArgumentException exception) {

        ResponseStructure<String> res = new ResponseStructure<>();

        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setMessage(exception.getMessage());
        res.setData("Failure");

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}