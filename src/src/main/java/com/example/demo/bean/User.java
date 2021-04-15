package com.example.demo.bean;

import java.io.Serializable;

public class User implements Serializable {

    private Integer uId;

    private String uName;

    private String password;

    private Integer uAge;

    private Boolean state;

    /**
     * @return the uId
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * @return the uAge
     */
    public Integer getuAge() {
        return uAge;
    }

    /**
     * @param uAge the uAge to set
     */
    public void setuAge(Integer uAge) {
        this.uAge = uAge;
    }

    /**
     * @return the uName
     */
    public String getuName() {
        return uName;
    }

    /**
     * @param uName the uName to set
     */
    public void setuName(String uName) {
        this.uName = uName;
    }

    /**
     * @param uId the uId to set
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}