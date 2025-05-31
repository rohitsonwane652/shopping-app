package com.shopping.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LogMessage {

    @Autowired
    static Logger logger = LoggerFactory.getLogger(LogMessage.class);

    public static void infoLog(String message){
        logger.info(message);
    }
}