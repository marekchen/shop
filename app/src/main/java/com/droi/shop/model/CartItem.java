package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

/**
 * Created by marek on 2017/2/22.
 */

public class CartItem extends DroiObject {
    @DroiExpose
    private String id;
    @DroiReference
    private Item item;
    @DroiExpose
    private int num;
    private boolean checked = false;

    public CartItem() {

    }

    public CartItem(String mId, Item mItem, int mNum) {
        this.id = mId;
        this.item = mItem;
        this.num = mNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
