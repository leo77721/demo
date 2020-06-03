package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AESUtils {
    private IvParameterSpec ivSpec;
    private SecretKeySpec keySpec;
    public static Logger logger = LoggerFactory.getLogger("AESUtils");

    private static Map<String, AESUtils> map = new HashMap<>();
    public static AESUtils getInstance(String key){
        if(map.get(key) == null){
            map.put(key, new AESUtils(key));
        }
        return  map.get(key);
    }

    private AESUtils(String key) {
        try {
            byte[] keyBytes = key.getBytes();
            byte[] buf = new byte[16];

            for (int i = 0; i < keyBytes.length && i < buf.length; i++) {
                buf[i] = keyBytes[i];
            }

            this.keySpec = new SecretKeySpec(buf, "AES");
            this.ivSpec = new IvParameterSpec(keyBytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public byte[] encrypt(byte[] origData) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, this.keySpec, this.ivSpec);
            return cipher.doFinal(origData);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public byte[] decrypt(byte[] crypted) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.keySpec, this.ivSpec);
            return cipher.doFinal(crypted);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void checkLicense() throws Exception {
        AESUtils aes = new AESUtils("o4^RGaOfj!RVV^tD");

        String newString = "2021-12-31;ff:ff:ff:ff:ff:ff";
        byte[] newByte = newString.getBytes();
        byte[] newStr = aes.encrypt(newByte);
        String news = base64Encode(newStr);

        String lic_data = "P4jNnTOeSyba4RDEXzLMoQOQ2/plXQts4Xqbpno4j74=";
        // 测试数据
        //6W4Z22qsO5ba/RMJ+ST64qrKonRxz+RATNiKt9PS78LP/wYxrfLv/+BTyqDExcu8
        Map map = System.getenv();
        Iterator it = map.entrySet().iterator();
        String licStr = news;
        while(it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();
            if("lic_data".equals(entry.getKey())){
                licStr = String.valueOf(entry.getValue());
            }
        }
        byte[] licData = base64Decode(licStr);
        //aes.decrypt解密liscense
        String[] licInfo = new String(aes.decrypt(licData)).split(";");

        String[] licMacList = licInfo[1].split(",");
        List<String> macList = new ArrayList<String>();
        // 获取主机上所有网卡mac地址
        try {
            Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                StringBuffer stringBuffer = new StringBuffer();
                NetworkInterface networkInterface = enumeration.nextElement();
                if (networkInterface != null) {
                    byte[] bytes = networkInterface.getHardwareAddress();
                    if (bytes != null) {
                        for (int i = 0; i < bytes.length; i++) {
                            if (i != 0) {
                                stringBuffer.append(":");
                            }
                            int tmp = bytes[i] & 0xff; // 字节转换为整数
                            String str = Integer.toHexString(tmp);
                            if (str.length() == 1) {
                                stringBuffer.append("0" + str);
                            } else {
                                stringBuffer.append(str);
                            }
                        }
                        String mac = stringBuffer.toString().toUpperCase();
                        macList.add(mac);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception();
        }
        // 用解密的liscense检查日期
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bt = sdf.parse(sdf.format(new Date()));
            Date et = sdf.parse(licInfo[0]);
            if (et.before(bt)) {
                logger.error("license expired!");
                throw new Exception("license expired!");
            }
        } catch (ParseException e) {
            logger.error("valid license failed!");
            throw new Exception("valid license failed!");
        }
        if (licMacList!=null&&licMacList.length==1&&licMacList[0]!=null&&licMacList[0].equals("ff:ff:ff:ff:ff:ff")) {
            logger.error("lic mac list is undefine(ff:ff:ff:ff:ff:ff) !");
        } else {
            // 用解密的liscense检查mac地址
            int size = licMacList.length;
            boolean validFlag = false;
            for(int i = 0; i < size; i++){
                if (macList.contains(licMacList[i].toUpperCase())) {
                    validFlag = true;
                    break;
                }
            }
            if(!validFlag){
                logger.error("invalid mac address!");
                throw new Exception("invalid mac address!");
            }
            logger.info("license valid, please enjoy!!");
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        checkLicense();
    }

    public static String base64Encode(byte[] data) {
        return Base64Utils.encodeToString(data);
    }

    public static byte[] base64Decode(String license) {
        return Base64Utils.decodeFromString(license);
    }

}

