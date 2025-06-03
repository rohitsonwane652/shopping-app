package com.shopping.exception;

import com.shopping.model.Response;
import com.shopping.utils.LogMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

/**
 * any exception that controller throws will come to this global exception handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<String> handeInvalidDataException(InvalidDataException e, HttpServletRequest req){
        LogMessage.errorLog("Request URI-->" + req.getRequestURI() + " Exception class -->" + e.getClass() + " Exception message-->" + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handeTransactionNotFoundException(TransactionNotFoundException e, HttpServletRequest req){
        LogMessage.errorLog("Request URI-->" + req.getRequestURI() + " Exception class -->" + e.getClass() + " Exception message-->" + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e,HttpServletRequest req){
        LogMessage.errorLog("Request URI-->" + req.getRequestURI() + " Exception class -->" + e.getClass() + " Exception message-->" + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
