package com.example.demo;

import com.example.demo.task.InitDataConfig;
import com.example.demo.thread.SystemThreadRunner;
import com.example.demo.utils.AESUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        AESUtils.checkLicense();
        SpringApplication.run(DemoApplication.class, args);
        SystemThreadRunner.run();
        InitDataConfig.setTableNameMap();
    }

}
