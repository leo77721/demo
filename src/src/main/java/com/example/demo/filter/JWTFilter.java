package com.example.demo.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.BaseRestResult;
import com.example.demo.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class JWTFilter implements Filter{
    Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if (StringUtils.isEmpty(uri)){
            return;
        }
        if (uri.startsWith("/api/test") || uri.startsWith("/druid") || uri.startsWith("/api/hello") || uri.startsWith("/api/login") ){
            //放行
            filterChain.doFilter(request, response);
            return;
        }
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)){
            fail(response,401);
            response.setStatus(401);
            logger.error("Authorization info no found");
            return;
        }
        String prefix = "Bearer ";
        if (!authorization.startsWith(prefix)){
            //fail(response,2);
            response.setStatus(401);
            logger.error("Authorization token is not start with Bearer");
            return;
        }
        String token = authorization.substring(prefix.length());
        if (StringUtils.isEmpty(token)){
            //fail(response,3);
            response.setStatus(401);
            logger.error("Authorization token is error");
            return;
        }

        Claims claims = JWTUtils.parseJWT(token);
        if(claims != null){
            String user = claims.get("userName").toString();

            Date exp = claims.getExpiration();
            Date now = new Date();
            if(exp.getTime() > now.getTime()){
                filterChain.doFilter(request, response);
            }else{
                response.setStatus(401);
                logger.error("Expiration of authorization");
                return;
            }
        }
    }

    private void fail(HttpServletResponse response,Integer resultCode) throws IOException {
        // 失败
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        BaseRestResult result = new BaseRestResult(resultCode,"token验证失败");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", result);
        writer.print(jsonObject);
        writer.close();
    }

    @Override
    public void destroy() {
        logger.info("filter destroyed ...");
    }
}
