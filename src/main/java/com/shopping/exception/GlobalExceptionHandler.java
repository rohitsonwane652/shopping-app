package com.shopping.exception;

import com.shopping.utils.LogMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(Exception e){
        LogMessage.infoLog(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>("An error occured:" + e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e){
        LogMessage.infoLog(e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>("An error occured:" + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
