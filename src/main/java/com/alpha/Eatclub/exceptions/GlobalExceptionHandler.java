package com.alpha.Eatclub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	    public ResponseEntity<String> handleSqlIntegrity(SQLIntegrityConstraintViolationException ex){

	        return new ResponseEntity<>("Duplicate entry or Constraint Voilation.Please Check your Input Bro", HttpStatus.BAD_REQUEST);
	    }
	    @ExceptionHandler(ItemNotFoundException.class)
	    public ResponseEntity<String> handleItemNotFound(ItemNotFoundException ex){
	        return new ResponseEntity<>("Item Not Found with this ID",HttpStatus.BAD_REQUEST);
	    }
	    @ExceptionHandler(DifferentResturtantItem.class)
	    public ResponseEntity<String> handleDifferentRest(DifferentResturtantItem ex){
	        return new ResponseEntity<>("Cannot Add Item from Different Restaurant Brother",HttpStatus.BAD_REQUEST);
	    }
	    @ExceptionHandler(CustomerNotFound.class)
	    public ResponseEntity<String> handleCustomer(CustomerNotFound ex){
	        return new ResponseEntity<>("Customer Not Found with given ID Brother",HttpStatus.BAD_REQUEST);
	    }
	    @ExceptionHandler(OrderNotfoundException.class)
	    public ResponseEntity<String> handeOrder(OrderNotfoundException ex){
	        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	    }
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException e){
	    Map<String, String>map=new HashMap<String, String>();
	    List<ObjectError>objerr= e.getAllErrors();
	    for(ObjectError objectError : objerr) {
	    	FieldError fr = (FieldError) objectError;
	    	map.put(fr.getField(), fr.getDefaultMessage());
	    }
	    return map;
	    
}
}