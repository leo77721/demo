/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.example.demo.utils;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;

/**
 * @author Administrator
 * @date 2020/6/30 14:52
 */
public class JasyptUtils {
    public static void jasyptTest() {
        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        // application.properties, jasypt.encryptor.password
        encryptor.setPassword("Demo@FFX!1984");
        // encrypt root
        System.out.println(encryptor.encrypt("root"));
        System.out.println(encryptor.encrypt("mysql"));

        // decrypt, the result is root
        System.out.println(encryptor.decrypt("H2HFPDoDgF/NIyGD6rzd6A=="));
        System.out.println(encryptor.decrypt("EEkTo2+j70ZeodkyHyTYxA=="));
    }

    public static void main(String[] args) {
        JasyptUtils.jasyptTest();
    }
}