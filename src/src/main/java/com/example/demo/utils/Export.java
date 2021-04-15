package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Export {
    private static String harborUrl = "harbor.poc.com";
    private static String harborUsername = "admin";
    private static String harborPassword = "Caas12345";
    private static String harborDockerUrl = "tcp://harbor.poc.com:4789/";
    
    public static void main(String[] args) {
        
        List<Map<String,String>> images = new ArrayList<Map<String,String>>();
        Map<String,String> image1 = new HashMap<String,String>();
        image1.put("imageFullname", "harbor.poc.com/devprojectdev/mybusybox");
        image1.put("imageName", "mybusybox");
        image1.put("imageTag", "1.0");
        images.add(image1);
        Map<String,String> image2 = new HashMap<String,String>();
        image2.put("imageFullname", "harbor.poc.com/devprojectdev/testdocker");
        image2.put("imageName", "testdocker");
        image2.put("imageTag", "v1");
        images.add(image2);
        
        exportToFtp(images, "172.24.10.47", 21, "joe", "Ftp@1225", "/devprojectdev/package2.zip");
        try {
            //BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File("E:\\out.zip")));
            //exportToWeb(images,out);
            //out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportToWeb(
            List<Map<String,String>> images,
            OutputStream out) {
        try {
            String harborRegistry = harborUrl;
            if (!harborRegistry.contains("http://") && !harborRegistry.contains("https://")) {
                harborRegistry = "http://".concat(harborRegistry);
            }
            /*AuthConfig authConfig = new AuthConfig();
            authConfig.withRegistryAddress(harborRegistry);
            authConfig.withUsername(harborUsername);
            authConfig.withPassword(harborPassword);
            DockerClient client = DockerClientBuilder.getInstance(harborDockerUrl).build();*/

            ZipOutputStream zos = new ZipOutputStream(out);
            zos.setLevel(Deflater.BEST_COMPRESSION);
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(images);
            ZipEntry packagejson = new ZipEntry("package.json");
            zos.putNextEntry(packagejson);
            zos.write(json.getBytes("UTF-8"));
            zos.closeEntry();
            for (Map<String,String> image : images) {
               // client.pullImageCmd(image.get("imageFullname")).withTag(image.get("imageTag")).withAuthConfig(authConfig).exec(new PullImageResultCallback()).awaitCompletion(5, TimeUnit.MINUTES);
                ZipEntry ze = new ZipEntry(image.get("imageName").concat("_").concat(image.get("imageTag").concat(".tar")));
                zos.putNextEntry(ze);
//                BufferedInputStream bis = new BufferedInputStream(client.saveImageCmd(image.get("imageFullname")+":"+image.get("imageTag")).exec());
                BufferedInputStream bis = null;
                byte[] buffer = new byte[1024];
                int count = -1;
                while ((count = bis.read(buffer)) != -1) {
                  zos.write(buffer, 0, count);
                }
                bis.close();
                zos.closeEntry();
//                client.removeImageCmd(image.get("imageFullname").concat(":").concat(image.get("imageTag"))).withForce(true).exec();
            }
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void exportToFtp(
            List<Map<String,String>> images,
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
            DockerClient client = DockerClientBuilder.getInstance(harborDockerUrl).build();*/
            
            PipedInputStream pis = new PipedInputStream();
            PipedOutputStream pos = new PipedOutputStream(pis);
            new Thread(
                new Runnable() {
                    public void run() {
                        try {
                            ZipOutputStream zos = new ZipOutputStream(pos);
                            zos.setLevel(Deflater.BEST_COMPRESSION);
                            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(images);
                            ZipEntry packagejson = new ZipEntry("package.json");
                            zos.putNextEntry(packagejson);
                            zos.write(json.getBytes("UTF-8"));
                            zos.closeEntry();
                            for (Map<String,String> image : images) {
//                                client.pullImageCmd(image.get("imageFullname")).withTag(image.get("imageTag")).withAuthConfig(authConfig).exec(new PullImageResultCallback()).awaitCompletion(5, TimeUnit.MINUTES);
                                ZipEntry ze = new ZipEntry(image.get("imageName").concat("_").concat(image.get("imageTag").concat(".tar")));
                                zos.putNextEntry(ze);
                               //BufferedInputStream bis = new BufferedInputStream(client.saveImageCmd(image.get("imageFullname")+":"+image.get("imageTag")).exec());
                                BufferedInputStream bis = null;
                                byte[] buffer = new byte[1024];
                                int count = -1;
                                while ((count = bis.read(buffer)) != -1) {
                                  zos.write(buffer, 0, count);
                                }
                                bis.close();
                                zos.closeEntry();
//                                client.removeImageCmd(image.get("imageFullname").concat(":").concat(image.get("imageTag"))).withForce(true).exec();
                            }
                            zos.close();
                            pos.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            ).start();
            uploadToFtp(transferFtpIp, transferFtpPort, transferFtpUsername, transferFtpPassword, transferFtpFile, pis);
            pis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void uploadToFtp (
            String ftpIp,
            int ftpPort,
            String ftpUsername,
            String ftpPassword,
            String ftpFile,
            InputStream is) throws Exception {
       /* FTPClient ftpClient = new FTPClient();
        ftpClient.connect(ftpIp, ftpPort);
        ftpClient.login(ftpUsername, ftpPassword);
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.storeFile(ftpFile, is);
        ftpClient.logout();
        ftpClient.disconnect();*/
    }
}
