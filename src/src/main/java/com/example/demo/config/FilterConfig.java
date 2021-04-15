package com.example.demo.config;

import com.example.demo.filter.CORSFilter;
import com.example.demo.filter.JWTFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(corsFilter());
        registration.addUrlPatterns("/api/*");
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean(name = "corsFilter")
    public Filter corsFilter() {
        return new CORSFilter();
    }

    /*@Bean
    public FilterRegistrationBean ssoFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(ssoFilter());
        registration.addUrlPatterns("/*");
        registration.setName("ssoFilter");
        registration.setOrder(2);
        return registration;
    }

    @Bean(name = "ssoFilter")
    public Filter ssoFilter() {
        return new SSOLoginFilter();
    }*/

    @Bean
    public FilterRegistrationBean jwtFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(jwtFilter());
        registration.addUrlPatterns("/api/*", "/rest/*");
        registration.setName("jwtFilter");
        registration.setOrder(3);
        return registration;
    }

    @Bean(name = "jwtFilter")
    public Filter jwtFilter() {
        return new JWTFilter();
    }
}