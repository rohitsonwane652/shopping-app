package com.shopping.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogMessage {

    @Autowired
    static Logger logger = LoggerFactory.getLogger(LogMessage.class);

    public static void infoLog(String message){
        logger.info("Shopping app info message:" + message);
    }

    public static void errorLog(String message){
        logger.error("Shopping app error message:" + message);
    }
}