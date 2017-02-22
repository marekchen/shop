package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

/**
 * Created by marek on 2017/2/22.
 */

public class CartItem extends DroiObject {
    @DroiExpose
    public String id;
    @DroiReference
    public Item item;
    @DroiExpose
    public int num;
    public boolean checked = false;

    public CartItem(){

    }

    public CartItem(String mId, Item mItem, int mNum) {
        this.id = mId;
        this.item = mItem;
        this.num = mNum;
    }
}
