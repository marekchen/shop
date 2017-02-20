package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

/**
 * Created by marek on 2017/2/8.
 */

public class Address extends DroiObject {
    @DroiExpose
    String userId;
    @DroiExpose
    String name;
    @DroiExpose
    String phoneNum;
    @DroiExpose
    String location;
    @DroiExpose
    String address;

    public Address(){

    }
    public Address(String userId, String name, String phoneNum, String location, String address) {
        this.userId = userId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.location = location;
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
