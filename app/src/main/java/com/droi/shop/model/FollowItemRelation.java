package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiReference;

/**
 * Created by chenpei on 16/9/3.
 */
//关注商品
public class FollowItemRelation extends DroiObject {
    @DroiExpose
    public String questionId;
    @DroiReference
    public Item item;
    @DroiExpose
    public String followerId;//关注者

    public FollowItemRelation(Item item, String followerId) {
        this.questionId = item.getObjectId();
        this.item = item;
        this.followerId = followerId;
    }

    public FollowItemRelation() {

    }
}
