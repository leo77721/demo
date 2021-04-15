package com.example.demo.baidu;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @program: demo
 * @description: 行驶证
 * @author: qilei
 * @create: 2021/4/13
 */
public class VehicleLicense {
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

        sample(client);
    }


    public static void sample(AipOcr client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("accuracy", "normal");


        // 参数为本地图片路径
        String image = "E:\\vl.jpg";
        JSONObject res = client.vehicleLicense(image, options);
        System.out.println(res.toString(2));

        // 参数为本地图片二进制数组
        /*byte[] file = readImageFile(image);
        res = client.vehicleLicense(file, options);
        System.out.println(res.toString(2));*/

    }
}
