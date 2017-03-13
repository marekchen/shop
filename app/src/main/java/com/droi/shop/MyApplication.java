package com.droi.shop;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.droi.sdk.selfupdate.UpdateUIStyle;
import com.droi.shop.model.Address;
import com.droi.shop.model.Banner;
import com.droi.shop.model.CartItem;
import com.droi.shop.model.Item;
import com.droi.shop.model.ItemType;
import com.droi.shop.model.Order;
import com.droi.shop.model.ShopUser;
import com.droi.shop.model.Test;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by chenpei on 2016/5/11.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        TypefaceProvider.registerDefaultIconSets();
        Core.initialize(this);
        DroiObject.registerCustomClass(Item.class);
        DroiObject.registerCustomClass(Address.class);
        DroiObject.registerCustomClass(Banner.class);
        DroiObject.registerCustomClass(ShopUser.class);
        DroiObject.registerCustomClass(Order.class);
        DroiObject.registerCustomClass(CartItem.class);
        DroiObject.registerCustomClass(ItemType.class);
        DroiObject.registerCustomClass(Test.class);

        /*DroiAnalytics.initialize(this);
        DroiAnalytics.enableActivityLifecycleCallbacks(this);*/

        DroiUpdate.initialize(this);
        DroiUpdate.setUpdateOnlyWifi(true);
        DroiUpdate.setUpdateUIStyle(UpdateUIStyle.STYLE_BOTH);

        DroiPush.initialize(this);

        DroiFeedback.initialize(this);

        DroiPermission permission = DroiPermission.getDefaultPermission();
        if (permission == null)
            permission = new DroiPermission();
        // 设置默认权限为所有用户可读不可写
        permission.setPublicReadPermission(true);
        permission.setPublicWritePermission(true);
        DroiPermission.setDefaultPermission(permission);
    }
}
