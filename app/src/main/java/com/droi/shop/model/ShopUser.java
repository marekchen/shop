package com.droi.shop.model;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiFile;
import com.droi.sdk.core.DroiUser;

/**
 * Created by marek on 2017/2/9.
 */

public class ShopUser extends DroiUser {

    @DroiExpose
    public DroiFile avatar;
}
