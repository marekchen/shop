package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;
import com.droi.sdk.core.DroiReferenceObject;
import com.droi.shop.util.ShoppingCartManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marek on 2017/2/21.
 */

public class Order extends DroiObject {
    @DroiExpose
    ArrayList<DroiReferenceObject> cartItems;
    @DroiReference
    Address address;
    @DroiExpose
    int payType;
    @DroiExpose
    String remark;
    @DroiExpose
    int receiptType;

    public ArrayList<CartItem> getCartItems() {
        ArrayList<CartItem> lists = new ArrayList<>();
        if (cartItems != null) {
            for (DroiReferenceObject ref : cartItems) {
                lists.add((CartItem) ref.droiObject());
            }
        }
        return lists;
    }

    public void setCartItems(List<CartItem> cartItems) {
        ArrayList<DroiReferenceObject> castList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            DroiReferenceObject ref1 = new DroiReferenceObject();
            ref1.setDroiObject(cartItem);
            castList.add(ref1);
        }
        this.cartItems = castList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(int receiptType) {
        this.receiptType = receiptType;
    }
}
