package org.jsp.bank.dto;

import java.util.List;

import org.jsp.bank.entity.Bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStructure<T> {
 private int statusCode;
 private String message;
 private T data;
}
