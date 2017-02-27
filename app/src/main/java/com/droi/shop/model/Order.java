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
    private String userObjectId;
    @DroiExpose
    private ArrayList<DroiReferenceObject> cartItems;
    @DroiReference
    private Address address;
    @DroiExpose
    private int payType; //1.货到付款 2.在线支付
    @DroiExpose
    private String remark;
    @DroiExpose
    private int receiptType;//1.无需发票
    @DroiExpose
    private int state;//1.付款中 2.准备发货 3.订单取消

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

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
            DroiReferenceObject ref = new DroiReferenceObject();
            ref.setDroiObject(cartItem);
            castList.add(ref);
        }
        this.cartItems = castList;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
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
