package com.example.demo.baidu;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @program: demo
 * @description: demo
 * @author: qilei
 * @create: 2021/4/13
 */
public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "23982743";
    public static final String API_KEY = "ZQPoOe8hBzHPhuOHST6wm4BA";
    public static final String SECRET_KEY = "xPxsmmV0jaSxkcH3MowVgMYN4kVkKbX7";

    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = "E:\\pic.jpg";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));



    }
}
