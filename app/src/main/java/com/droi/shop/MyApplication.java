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
import com.droi.sdk.core.DroiPermission;
import com.droi.sdk.core.DroiPreference;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.oauth.DroiOauth;
import com.droi.sdk.push.DroiMessageHandler;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.droi.sdk.selfupdate.UpdateUIStyle;
/*import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;*/

/**
 * Created by chenpei on 2016/5/11.
 */
public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Context mContext;
    private Toast toast = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Core");
        /*EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);*/

        TypefaceProvider.registerDefaultIconSets();
        mContext = this;
       /* //初始化
        Core.initialize(this);
        //注册DroiObject
        Log.i(TAG, "DroiPush");
        //初始化
        DroiPush.initialize(this);
        //设置标签
        DroiPush.addTag(this, new String[]{"test1", "test2"}, false);
        DroiPush.removeTag(this, new String[]{"test2,test3"});
        //设置静默时间为0:30 到 8:00
        DroiPush.setSilentTime(this, 0, 30, 8, 0);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                //使用DroiPreference设置是否打开push功能
                boolean enablePush = DroiPreference.instance().getBoolean("push", true);
                Log.i(TAG, "DroiPreference:" + enablePush);
                DroiPush.setPushEnabled(getApplicationContext(), enablePush);
                //DroiCloudCache
                // 设定数据
                DroiCloudCache.set("keyName", "54678");
                // 由云端取回设定数据
                DroiError error = new DroiError();
                String value = DroiCloudCache.get("keyName", error);
                Log.i(TAG, "DroiCloudCache:" + value);
            }
        };
        thread.start();

        //透传消息
        DroiPush.setMessageHandler(new DroiMessageHandler() {
            @Override
            public void onHandleCustomMessage(Context context, String s) {
                //String st = decodeFromBase64Data(s);
                //showToastInUiThread(st);
            }
        });

        Log.i(TAG, "DroiAnalytics");
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

        Log.i(TAG, "DroiUpdate");
        //初始化
        DroiUpdate.initialize(this);
        //是否只在wifi下更新，默认true
        DroiUpdate.setUpdateOnlyWifi(true);
        //UI类型，默认BOTH
        DroiUpdate.setUpdateUIStyle(UpdateUIStyle.STYLE_BOTH);

        Log.i(TAG, "DroiFeedback");
        //初始化
        DroiFeedback.initialize(this);

        //权限设置
        DroiPermission permission = DroiPermission.getDefaultPermission();
        if (permission == null)
            permission = new DroiPermission();
        // 设置默认权限为所有用户可读不可写
        permission.setPublicReadPermission(true);
        permission.setPublicWritePermission(true);
        DroiPermission.setDefaultPermission(permission);*/
    }
}
