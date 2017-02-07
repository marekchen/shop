package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

/**
 * Created by marek on 2017/1/19.
 */

public class Item extends DroiObject {
    @DroiExpose
    String name;
    @DroiExpose
    String[] images;
    @DroiExpose
    float price;
    @DroiExpose
    String description;
}
