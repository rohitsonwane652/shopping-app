package com.shopping.exception;

import com.shopping.log.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e){
        LogMessage.infoLog(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>("An error occured:" + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
