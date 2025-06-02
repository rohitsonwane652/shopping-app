package com.shopping.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
public class LogMessage {

    public static void infoLog(String message){
        log.info("Shopping app info message -->" + message);
    }

    public static void errorLog(String message){
        log.error("Shopping app error message -->" + message);
    }

    public static void startLog(String className, String methodName) {
        log.info("******* " + className + " ******* " + methodName + " ******* START *******");
    }

    public static void endLog(String className, String methodName) {
        log.info("******* " + className + " ******* " + methodName + " ******* END *******");
    }

    public static void logStackTrace(String exception, Exception e) {
        log.error("Info Message :: Exception Object -->" + exception + " :: Detailed Info ", e);
    }

}
