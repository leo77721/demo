package com.example.demo.task;

import com.example.demo.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoTask {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Scheduled(cron = "0 0 */5 * * ?")
    public void updateUser(){
        logger.info("task start ...");

        logger.info("task end ...");
    }
}
