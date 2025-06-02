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
    public ResponseEntity<Response> handeInvalidDataException(InvalidDataException e, HttpServletRequest req){
        LogMessage.errorLog("Request URI-->" + req.getRequestURI() + " Exception class -->" + e.getClass() + " Exception message-->" + e.getMessage());
        Response<?> responseDto = new Response<>();
        responseDto.setUrl(req.getRequestURI());
        responseDto.setMessage(e.getMessage());
        responseDto.setErrorDesc(e.getMessage());
        responseDto.setErrorType(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDto.setError(Collections.singletonList("True"));
        responseDto.setTotalRecords(0L);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Response> handeTransactionNotFoundException(TransactionNotFoundException e, HttpServletRequest req){
        LogMessage.errorLog("Request URI-->" + req.getRequestURI() + " Exception class -->" + e.getClass() + " Exception message-->" + e.getMessage());
        Response<?> responseDto = new Response<>();
        responseDto.setUrl(req.getRequestURI());
        responseDto.setMessage(e.getMessage());
        responseDto.setErrorDesc(e.getMessage());
        responseDto.setErrorType(HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseDto.setError(Collections.singletonList("True"));
        responseDto.setTotalRecords(0L);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleAllExceptions(Exception e,HttpServletRequest req){
        LogMessage.errorLog("Request URI-->" + req.getRequestURI() + " Exception class -->" + e.getClass() + " Exception message-->" + e.getMessage());
        Response<?> responseDto = new Response<>();
        responseDto.setUrl(req.getRequestURI());
        responseDto.setMessage(e.getMessage());
        responseDto.setErrorDesc(e.getMessage());
        responseDto.setErrorType(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        responseDto.setError(Collections.singletonList("True"));
        responseDto.setTotalRecords(0L);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
