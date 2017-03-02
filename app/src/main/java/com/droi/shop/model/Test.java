package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

import java.util.Date;

/**
 * Created by marek on 2017/3/1.
 */

public class Test extends DroiObject {
    @DroiExpose
    public Date time;
}
