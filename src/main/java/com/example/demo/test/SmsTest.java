/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.example.demo.test;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @date 2020/7/21 11:08
 */
public class SmsTest {
    public static void main(String[] args) {
        int status = 0;
        String smsUrl = "http://esb.hna.net/api";
        String smsMethod = "DOP_eking sms platform_sendSms";
        String smsAppsecret = "ln3dnjg8l9irxersx14t2kxb7zk8sy3g";
        String smsAccessToken = "86B52BCD279C6AF86EF3272180F1F72314554981";
        String smsCorpID = "5001502";
        String smsLoginName = "JCYPTsms";
        String smsPassword = "llqfo93ks";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format( new Date());
       /* String message = new ODPRequest(smsUrl, smsAppsecret)
                // 系统参数
                .addTextSysPara("Method", smsMethod)
                .addTextSysPara("AccessToken", smsAccessToken)
                .addTextSysPara("Timestamp", timestamp)
                .addTextSysPara("Format", "xml")
                // 应用参数
                .addTextAppPara("corpID", smsCorpID)
                .addTextAppPara("loginName", smsLoginName)
                .addTextAppPara("password", smsPassword)
                .addTextAppPara("Mobs", "15041230677")
                .addTextAppPara("msg", "content").post();
        status = isSuccess(message) ? 1 : -1;

        System.out.println(status);*/
    }

    private static boolean isSuccess(String response) {
        String code = StringUtils.substringBetween(response, "<Result>", "</Result>");
        return "1".equalsIgnoreCase(code);// 1表示成功-1表示失败
    }
}