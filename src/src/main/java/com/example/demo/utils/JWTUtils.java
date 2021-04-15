package com.example.demo.utils;

import com.example.demo.bean.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    /**
     * 生成token
     *
     * @param id     tokenId
     * @param issuer 签发者 CAAS
     * @param user   生成token的用户信息
     * @return
     */
    public static String createToken(String id, String issuer, User user) {
        // JWT签名算法 用HS256加密
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date now = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        //有效期
        nowTime.add(Calendar.HOUR, 1);
        Date expiresDate = nowTime.getTime();
        // 进行加密用的秘钥
        byte[] apiKeySecretBytes = DatatypeConverter
                .parseBase64Binary("Y2Fhc3NlY3JldAo=");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                signatureAlgorithm.getJcaName());
        // 设置JWT Claims
        // 用签名算法HS256和私钥key生成token
        JwtBuilder builder = Jwts.builder().setId(id)// 版本号
                .setIssuedAt(now)// 何时签发 时间戳 设置现在时间
                // 它可以用来做一些maxAge之类的验证，假如验证时间与这个claim指定的时间相差的时间大于通过maxAge指定的一个值，就属于验证失败
                .setIssuer(issuer)// 签发者
                .claim(Claims.SUBJECT, issuer + "," + id)
                .claim(Claims.ISSUED_AT, new Date())
                .claim("userId", user.getuId())
                .claim("userName", user.getuName())
                //.claim("authority", user.getPermissions())
                .signWith(signatureAlgorithm, signingKey); // 加密方法
        // 设置失效时间
        builder.setExpiration(expiresDate);// 过期时间
        // 设置序列化 URL安全化
        String tokenString = builder.compact();
        return tokenString;
    }

    public static String createChargeToken(String accessKey, String secretKey) {
        // JWT签名算法 用HS256加密
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 当前时间
//        long nowMillis = System.currentTimeMillis();
        Date now = new Date();
        long nowMillis = now.getTime();
        // 进行加密用的秘钥
        byte[] apiKeySecretBytes = secretKey.getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                signatureAlgorithm.getJcaName());
        // 设置JWT Claims
        // 用签名算法HS256和私钥key生成token
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)// 何时签发 时间戳 设置现在时间
                // 它可以用来做一些maxAge之类的验证，假如验证时间与这个claim指定的时间相差的时间大于通过maxAge指定的一个值，就属于验证失败
                .claim("accessKey", accessKey)
                .signWith(signatureAlgorithm, signingKey); // 加密方法
        // 设置失效时间
        Date exp = null;
        long expMillis = nowMillis + 360000000;
        exp = new Date(expMillis);
        builder.setExpiration(exp);// 过期时间
        // 设置序列化 URL安全化
        String tokenString = builder.compact();
        return tokenString;
    }

    /**
     * 刷新获取token
     *
     * @param oldToken 之前旧的token
     * @return
     */
    public static String refreshToken(String oldToken) {
        String newToken = "";
        // 解密旧token
        // 验证签名
        Claims claims = Jwts.parser() // 返回配置实例化后的实例
                .setSigningKey(DatatypeConverter.parseBase64Binary("Y2Fhc3NlY3JldAo=")) // 根据配置文件中的秘钥进行解密
                .parseClaimsJws(oldToken).getBody(); // 获取JWT中的载荷
        // 获取相关信息
        // 签发者
        String issuer = claims.getIssuer();
        // 用户信息
        String user = claims.get("user").toString();
        String userName = claims.get("userName").toString();
        User usersVo = new User();
        usersVo.setuId(Integer.parseInt(user));
        usersVo.setuName(userName);
        // 生成新token
        newToken = createToken("1", issuer, usersVo);
        return newToken;
    }

    //解密token
    public static Claims parseJWT(String token) {
        return Jwts.parser()   //返回配置实例化后的实例
                .setSigningKey(DatatypeConverter.parseBase64Binary("Y2Fhc3NlY3JldAo=")) //根据配置文件中的秘钥进行解密
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 验证token
     *
     * @param claims token解密后的信息集合
     * @return
     */
    public static Map<String, Object> validateToken(Claims claims) {
        Map<String, Object> result = new HashMap<String, Object>();
        String jwtId = claims.getId();
        //面向用户
        String subject = claims.getSubject();
        //签发者
        String issuer = claims.getIssuer();
        //何时签发
        Date issuedAt = claims.getIssuedAt();
//        UaUser user = claims.get("user", UaUser.class);

        //验证一 jwtId是否为1
        /*if (!"1".equals(jwtId)) {
            System.out.println("token序列不为1 token验证不通过");
            result.put("code", 1);
            result.put("flag", false);
            result.put("msg", "token 无效");
        } else if (!"CAAS".equals(issuer)) {
            //验证二 签发者是否是CAAS
            System.out.println("签发者不是CAAS token验证不通过");
            result.put("code", 2);
            result.put("flag", false);
            result.put("msg", "token 无效");
        } else if (null == subject) {
            //验证三 面向用户是否是当前用户
            System.out.println("当前用户为空 token验证不通过");
            result.put("code", 3);
            result.put("flag", false);
            result.put("msg", "token 无效");
        }*/
        if (null != claims.get("user")) {
            //用户信息
            String user = claims.get("user").toString();
            String temp = user.substring(user.indexOf("xl="));
            String[] s = temp.substring(0, temp.indexOf(",")).split("=");
//            String zhxl = s[1];

            /*HashMap<String, Object> tokenInfo = ApplicationCache.getTokeninfo();
//            Integer zhxl = user.getXl();
            //通过账户序列获取账户的过期时间
            Long gqsj = (Long) tokenInfo.get(zhxl);
            //获取当前时间
            Long nowMillis = System.currentTimeMillis();
            if (gqsj <= nowMillis) {
                gqsj += 3600000;
                tokenInfo.put(zhxl.toString(), gqsj);
                ApplicationCache.setTokenInfo(tokenInfo);
            }*/
            result.put("code", 0);
            result.put("flag", true);
            result.put("msg", "token 验证通过");
        } else {
            result.put("code", 4);
            result.put("flag", false);
            result.put("msg", "token 无效");
        }

        return result;
    }

    //验证token
    private boolean validateToken(HttpServletRequest request, HttpServletResponse response) {
        //获取token
        Map<String, Object> result = null;
        boolean tokenFlag = false;
        String token = null;
        String authHeader = request.getHeader("Authorization");
        if (null == authHeader || !authHeader.startsWith("Bearer ")) {
        } else {
            //截取掉Bearer 字符串
            token = authHeader.substring(7);
        }
        if (null != token) {
            //验证token
            result = JWTUtils.validateToken(JWTUtils.parseJWT(token));
            tokenFlag = (boolean) result.get("flag");
        }
        //token验证成功 如果过期但仍需操作 则默认刷新token 不必重写登录
        if (tokenFlag) {
            response.setHeader("Authorization", "Bearer " + token);
            //token验证成功
            return true;
        } else {
            //token验证失败
            return false;
        }
    }

    public static void main(String[] args) {
        Claims claims = JWTUtils.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTI3MTQ0MTEyLCJpc3MiOiJDQUFTIiwidXNlciI6InlxeXF5cSIsImV4cCI6MTUyNzUwNDExMn0.oQ3l-ZtYsycu1PxvkeM2pGSj8wCJVTx6r7-EfK1_uxI");
        System.out.println(claims.toString());
        /*String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTA5Nzc2NTIyLCJpc3MiOiJDQUFTIiwidXNlciI6ImNoZW5jdGVzdCIsImV4cCI6MTUxMDEzNjUyMn0.AplANfu0W82aaxqN-f8QAGcjrWnIhLsMTmxRSBfVVfI";
        Claims tokenInfo = parseJWT(token);*/
        /*String uuid = "";
        for(int i = 0; i < 22; i++){
            uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
        }*/
//        System.out.println(createChargeToken("6e16fd1876e76822e3cd", "9d2adbd662c3cdf2c558ddd6be79a6bc828d42d5"));
//        System.out.println(createChargeToken("fe06f1b63f962904e73d", "d170f88e97a7c2c3c1236533bccee39a40d4ac6b"));
//        System.out.println(createChargeToken("a010a4e6af028fd0d9e7", "f339c590248bd5e52ff2c017f33e872ef28f9491"));
        //beishu
//        System.out.println(createChargeToken("1f5beee269fbc030a049", "4ed77f22e80c11339d1096b8780468bc5f015b19"));
        //nanshu
//        System.out.println(createChargeToken("6e16fd1876e76822e3cd", "9d2adbd662c3cdf2c558ddd6be79a6bc828d42d5"));
        // test
//        System.out.println(createChargeToken("fe06f1b63f962904e73d", "d170f88e97a7c2c3c1236533bccee39a40d4ac6b"));
    }
}
