package com.example.demo.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestSoap {
    public static void main(String[] args) {
        try {
            String urlString = "http://omc.hna.net/hnasReport/system.asmx";
            String soapActionString = "http://tempuri.org/getHostInfo";
            URL url = new URL(urlString);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            String soap = ""
                +"<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                +"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                +"<soap:Body>"
                +"<getHostInfo xmlns=\"http://tempuri.org/\" />"
                +"</soap:Body>"
                +"</soap:Envelope>";
            byte[] buf = soap.getBytes();
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("Content-Length", String.valueOf(buf.length));
            httpConn.setRequestProperty("SOAPAction", soapActionString);
            httpConn.setRequestMethod("POST");
            
            //输入参数和输出结果
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            OutputStream out = httpConn.getOutputStream();
            out.write(buf);
            out.close();
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            String str = sb.toString();
            String a=str.replaceAll("&gt;", ">").replaceAll("&lt;", "<");
            System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
