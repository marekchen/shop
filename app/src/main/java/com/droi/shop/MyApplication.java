package com.droi.shop;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.analytics.SendPolicy;
import com.droi.sdk.core.Core;
import com.droi.sdk.core.DroiCloudCache;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.core.DroiPreference;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.oauth.DroiOauth;
import com.droi.sdk.push.DroiMessageHandler;
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
import com.droi.shop.util.ShoppingCartManager;
/*import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;*/

/**
 * Created by chenpei on 2016/5/11.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
        Core.initialize(this);
        DroiObject.registerCustomClass(Item.class);
        DroiObject.registerCustomClass(Address.class);
        DroiObject.registerCustomClass(Banner.class);
        DroiObject.registerCustomClass(ShopUser.class);
        DroiObject.registerCustomClass(Order.class);
        DroiObject.registerCustomClass(CartItem.class);
        DroiObject.registerCustomClass(ItemType.class);

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

        /*
        //初始化
        DroiAnalytics.initialize(this);
        //方式一:
        DroiAnalytics.enableActivityLifecycleCallbacks(this);
        //默认为true；关闭可以设为false
        DroiAnalytics.setCrashReport(true);
        //设置是否附带log，默认为false；Logger类记录的log才会被上传
        //发送策略，默认实时
        DroiAnalytics.setDefaultSendPolicy(SendPolicy.SCHEDULE);
        //设置非实时策略下，是否只在wifi下发送以及发送间隔(分钟)
        DroiAnalytics.setScheduleConfig(false, 5);

        Log.i(TAG, "DroiOauth");
        //初始化
        DroiOauth.initialize(this);
        //设置语言
        DroiOauth.setLanguage("zh_CN");


        //权限设置
        */
    }
}
