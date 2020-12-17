package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Import {
    private static String harborUrl = "harbor.poc.com";
    private static String harborUsername = "admin";
    private static String harborPassword = "Caas12345";
    private static String harborDockerUrl = "tcp://harbor.poc.com:4789/";
    private static String temp_file_path = "/caas-temp";
    
    public static void main(String[] args) {
        String namespace = "devprojectprd";
        
        importFromFtp(namespace, "172.24.10.47", 21, "joe", "Ftp@1225", "/devprojectdev/package.zip");
        try {
            //InputStream in = new FileInputStream("E:\\out.zip");
            //importFromWeb(namespace, in);
            //in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void importFromWeb(
            String namespace,
            InputStream in) {
        try {
            String harborRegistry = harborUrl;
            if (!harborRegistry.contains("http://") && !harborRegistry.contains("https://")) {
                harborRegistry = "http://".concat(harborRegistry);
            }
           /* AuthConfig authConfig = new AuthConfig();
            authConfig.withRegistryAddress(harborRegistry);
            authConfig.withUsername(harborUsername);
            authConfig.withPassword(harborPassword);
            DockerClient client = DockerClientBuilder.getInstance(harborDockerUrl).build();
            
            FileUtil.judgeAndCreateDir(temp_file_path);*/
            String localFile = temp_file_path + "/ImagePackage_"+UUID.randomUUID().toString().replaceAll("-", "")+"_tmp.zip";
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(localFile)));
            byte[] buff = new byte[1024];
            int count = -1;
            while ((count = in.read(buff)) != -1) {
                bos.write(buff, 0, count);
            }
            bos.close();
            ZipFile zf = new ZipFile(localFile);
            ZipEntry packageEntry = zf.getEntry("package.json");
            InputStream packageInputStream = new BufferedInputStream(zf.getInputStream(packageEntry));
            byte[] b = new byte[1024];
            int c = -1;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            zf.getInputStream(packageEntry);
            while ((c = packageInputStream.read(b)) != -1) {
              bo.write(b, 0, c);
            }
            String json = bo.toString("UTF-8");
            List<Map<String,String>> images = new ObjectMapper().readValue(json, new ArrayList<Map<String,String>>().getClass());
            bo.close();
            packageInputStream.close();
            for (Map<String,String> image : images) {
                ZipEntry entry = zf.getEntry(image.get("imageName").concat("_").concat(image.get("imageTag").concat(".tar")));
                InputStream inputStream = zf.getInputStream(entry);
              /*  client.loadImageCmd(inputStream).exec();
                inputStream.close();
                String sourceImage = image.get("imageFullname");
                String sourceTag = image.get("imageTag");
                String targetImage = harborUrl+"/"+namespace+"/"+image.get("imageName");
                String targetTag = sourceTag;
                List<Image> is = client.listImagesCmd().withImageNameFilter(sourceImage.concat(":").concat(sourceTag)).exec();
                client.tagImageCmd(is.get(0).getId(), targetImage, targetTag).exec();
                client.pushImageCmd(targetImage).withTag(targetTag).withAuthConfig(authConfig).exec(new PushImageResultCallback()).awaitCompletion(5, TimeUnit.MINUTES);
                client.removeImageCmd(sourceImage.concat(":").concat(sourceTag)).withForce(true).exec();
                client.removeImageCmd(targetImage.concat(":").concat(targetTag)).withForce(true).exec();*/
            }
            zf.close();
           // FileUtil.delFile(localFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void importFromFtp(
            String namespace,
            String transferFtpIp,
            int transferFtpPort,
            String transferFtpUsername,
            String transferFtpPassword,
            String transferFtpFile) {
        try {
            String harborRegistry = harborUrl;
            if (!harborRegistry.contains("http://") && !harborRegistry.contains("https://")) {
                harborRegistry = "http://".concat(harborRegistry);
            }
            /*AuthConfig authConfig = new AuthConfig();
            authConfig.withRegistryAddress(harborRegistry);
            authConfig.withUsername(harborUsername);
            authConfig.withPassword(harborPassword);
            DockerClient client = DockerClientBuilder.getInstance(harborDockerUrl).build();
            
            FileUtil.judgeAndCreateDir(temp_file_path);*/
            String localFile = temp_file_path + "/ImagePackage_"+UUID.randomUUID().toString().replaceAll("-", "")+"_tmp.zip";
            downloadFromFtp(transferFtpIp, transferFtpPort, transferFtpUsername, transferFtpPassword, transferFtpFile, localFile);
            ZipFile zf = new ZipFile(localFile);
            ZipEntry packageEntry = zf.getEntry("package.json");
            InputStream packageInputStream = new BufferedInputStream(zf.getInputStream(packageEntry));
            byte[] buffer = new byte[1024];
            int count = -1;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            zf.getInputStream(packageEntry);
            while ((count = packageInputStream.read(buffer)) != -1) {
              bo.write(buffer, 0, count);
            }
            String json = bo.toString("UTF-8");
            List<Map<String,String>> images = new ObjectMapper().readValue(json, new ArrayList<Map<String,String>>().getClass());
            bo.close();
            packageInputStream.close();
            for (Map<String,String> image : images) {
                ZipEntry entry = zf.getEntry(image.get("imageName").concat("_").concat(image.get("imageTag").concat(".tar")));
                InputStream inputStream = zf.getInputStream(entry);
                /*client.loadImageCmd(inputStream).exec();
                inputStream.close();
                String sourceImage = image.get("imageFullname");
                String sourceTag = image.get("imageTag");
                String targetImage = harborUrl+"/"+namespace+"/"+image.get("imageName");
                String targetTag = sourceTag;
                List<Image> is = client.listImagesCmd().withImageNameFilter(sourceImage.concat(":").concat(sourceTag)).exec();
                client.tagImageCmd(is.get(0).getId(), targetImage, targetTag).exec();
                client.pushImageCmd(targetImage).withTag(targetTag).withAuthConfig(authConfig).exec(new PushImageResultCallback()).awaitCompletion(5, TimeUnit.MINUTES);
                client.removeImageCmd(sourceImage.concat(":").concat(sourceTag)).withForce(true).exec();
                client.removeImageCmd(targetImage.concat(":").concat(targetTag)).withForce(true).exec();*/
            }
            zf.close();
           // FileUtil.delFile(localFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void downloadFromFtp (
            String ftpIp,
            int ftpPort,
            String ftpUsername,
            String ftpPassword,
            String ftpFile,
            String localFile) throws Exception {
        /*FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpIp, ftpPort);
        ftpClient.login(ftpUsername, ftpPassword);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        FileOutputStream os = new FileOutputStream(new File(localFile));
        ftpClient.retrieveFile(ftpFile, os);
        os.close();
        ftpClient.logout();
        ftpClient.disconnect();*/
    }
}
