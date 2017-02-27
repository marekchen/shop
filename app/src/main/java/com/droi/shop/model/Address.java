package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

/**
 * Created by marek on 2017/2/8.
 */

public class Address extends DroiObject {
    @DroiExpose
    private String userObjectId;
    @DroiExpose
    private String name;
    @DroiExpose
    private String phoneNum;
    @DroiExpose
    private String location;
    @DroiExpose
    private String address;

    public Address(){

    }
    public Address(String userObjectId, String name, String phoneNum, String location, String address) {
        this.userObjectId = userObjectId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.location = location;
        this.address = address;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
