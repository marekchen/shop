package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

import java.util.ArrayList;

/**
 * Created by marek on 2017/1/19.
 */

public class Item extends DroiObject {
    @DroiExpose
    String name;
    @DroiExpose
    ArrayList<String> images;
    @DroiExpose
    float price;
    @DroiExpose
    String description;
    @DroiExpose
    int commentCount;
    @DroiExpose
    int praiseCount;
    @DroiExpose
    String url;
    @DroiExpose
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
