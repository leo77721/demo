package com.example.demo.utils;

import java.io.Serializable;

public class BaseRestResult implements Serializable {
    private Integer statusCode;
    private String status;
    private Boolean statusBool;

    public BaseRestResult(){}
    public BaseRestResult(Integer statusCode, String status){
        this.statusCode = statusCode;
        this.status = status;
    }

    public BaseRestResult(Integer statusCode, Boolean status){
        this.statusCode = statusCode;
        this.statusBool = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getStatusBool() {
        return statusBool;
    }

    public void setStatusBool(Boolean statusBool) {
        this.statusBool = statusBool;
    }
}
