package com.droi.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.feedback.DroiFeedback;
import com.droi.sdk.push.DroiPush;
import com.droi.sdk.selfupdate.DroiUpdate;
import com.droi.shop.R;
import com.droi.shop.activity.AboutUsActivity;
import com.droi.shop.activity.AddressListActivity;
import com.droi.shop.activity.LoginActivity;
import com.droi.shop.activity.MyOrderActivity;
import com.droi.shop.activity.ProfileActivity;
import com.droi.shop.model.Item;
import com.droi.shop.model.ShopUser;
import com.droi.shop.view.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenpei on 2016/5/12.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "MineFragment";
    private Context mContext;
    @BindView(R.id.head_icon)
    CircleImageView titleImg;
    @BindView(R.id.user_name)
    TextView nameTextView;
    @BindView(R.id.push_switch)
    Switch pushSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        initUI(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshView();
        DroiAnalytics.onFragmentStart(getActivity(), "MineFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "MineFragment");
    }

    /**
     * 当登录成功或者登出时刷新View
     */
    private void refreshView() {
        ShopUser user = DroiUser.getCurrentUser(ShopUser.class);
        if (user != null && user.isAuthorized() && !user.isAnonymous()) {
            nameTextView.setText(user.getUserId());
            if (user.getAvatar() != null) {
                user.getAvatar().getInBackground(new DroiCallback<byte[]>() {
                    @Override
                    public void result(byte[] bytes, DroiError error) {
                        if (error.isOk()) {
                            if (bytes == null) {
                                Log.i(TAG, "bytes == null");
                            } else {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                titleImg.setImageBitmap(bitmap);
                            }
                        }
                    }
                }, null);
            }
        } else {
            titleImg.setImageResource(R.drawable.default_avatar);
            nameTextView.setText(R.string.fragment_mine_login);
        }
    }

    private void initUI(View view) {
        view.findViewById(R.id.mine_frag_follow).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_address).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_order).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_update).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_feedback).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_upload).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_push).setOnClickListener(this);
        view.findViewById(R.id.mine_about_us).setOnClickListener(this);
        view.findViewById(R.id.head_icon).setOnClickListener(this);
        view.findViewById(R.id.right_layout).setOnClickListener(this);
        pushSwitch.setChecked(DroiPush.getPushEnabled(mContext));
        pushSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DroiPush.setPushEnabled(mContext, isChecked);
            }
        });
    }

    @Override
    public void onClick(View v) {
        ShopUser user = DroiUser.getCurrentUser(ShopUser.class);
        switch (v.getId()) {
            case R.id.head_icon:

            case R.id.right_layout:
                if (user != null && user.isAuthorized() && !user.isAnonymous()) {
                    toProfile();
                } else {
                    toLogin();
                }
                break;
            case R.id.mine_frag_order:
                Intent orderIntent = new Intent(getActivity(), MyOrderActivity.class);
                startActivity(orderIntent);
                break;
            case R.id.mine_frag_address:
                Intent addressIntent = new Intent(getActivity(), AddressListActivity.class);
                addressIntent.putExtra(AddressListActivity.TYPE, 1);
                startActivity(addressIntent);
                break;
            case R.id.mine_frag_follow:
                /*Intent followIntent = new Intent(getActivity(), MyFollowActivity.class);
                startActivity(followIntent);*/
                break;
            case R.id.mine_frag_update:
                //手动更新
                DroiUpdate.manualUpdate(mContext);
                break;
            case R.id.mine_frag_feedback:
                //自定义部分颜色
                DroiFeedback.setTitleBarColor(getResources().getColor(R.color.colorPrimary));
                DroiFeedback.setSendButtonColor(getResources().getColor(R.color.colorPrimaryDark),
                        getResources().getColor(R.color.colorPrimary));
                //打开反馈页面
                DroiFeedback.callFeedback(mContext);
                break;
            case R.id.mine_about_us:
                Intent aboutUsIntent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(aboutUsIntent);
                break;
            case R.id.mine_frag_upload:
                uploadData();
                break;
            default:
                break;
        }
    }

    /**
     * 转到登录页面
     */
    private void toLogin() {
        Intent loginIntent = new Intent(mContext, LoginActivity.class);
        startActivity(loginIntent);
    }

    /**
     * 转到个人信息页面
     */
    private void toProfile() {
        Intent profileIntent = new Intent(mContext, ProfileActivity.class);
        startActivity(profileIntent);
    }

    private void uploadData() {
        ArrayList<Item> list = new ArrayList<>();
        Item item1 = new Item();
        item1.setName("Name1");
        item1.setCommentCount(1);
        item1.setPraiseCount(1);
        item1.setPrice(1.1f);
        item1.setDescription("描述");
        item1.setUrl("https://m.baidu.com");
        ArrayList<String> images = new ArrayList<>();
        images.add("https://m.360buyimg.com/n12/jfs/t3235/100/1618018440/139400/44fd706e/57d11c33N5cd57490.jpg!q70.jpg");
        images.add("https://m.360buyimg.com/n12/jfs/t3271/63/1662792438/102212/520aadea/57d11c33N17fca17c.jpg!q70.jpg");
        images.add("https://m.360buyimg.com/n12/jfs/t3250/243/1621682752/58357/ff68e7a4/57d11c33N1cb90a82.jpg!q70.jpg");
        images.add("https://m.360buyimg.com/n12/jfs/t3205/201/1641534867/20579/160ed304/57d11c34Nb6c6ae50.jpg!q70.jpg");
        item1.setImages(images);
        list.add(item1);
        Item item2 = new Item();
        item2.setName("Name2");
        item2.setCommentCount(1);
        item2.setPraiseCount(1);
        item2.setPrice(1.1f);
        item2.setDescription("描述2");
        item2.setUrl("https://www.baidu.com");
        item2.setImages(images);
        list.add(item2);
        Item item3 = new Item();
        item3.setName("Name3");
        item3.setCommentCount(1);
        item3.setPraiseCount(1);
        item3.setPrice(1.1f);
        item3.setDescription("描述3");
        item3.setUrl("https://www.baidu.com");
        item3.setImages(images);
        list.add(item3);
        Item item4 = new Item();
        item4.setName("Name4");
        item4.setCommentCount(1);
        item4.setPraiseCount(1);
        item4.setPrice(1.1f);
        item4.setDescription("描述4");
        item4.setUrl("https://www.baidu.com");
        item4.setImages(images);
        list.add(item4);
        Item item5 = new Item();
        item5.setName("Name5");
        item5.setCommentCount(1);
        item5.setPraiseCount(1);
        item5.setPrice(1.1f);
        item5.setDescription("描述5");
        item5.setUrl("https://www.baidu.com");
        item5.setImages(images);
        list.add(item5);
        Item item6 = new Item();
        item6.setName("Name6");
        item6.setCommentCount(1);
        item6.setPraiseCount(1);
        item6.setPrice(1.1f);
        item6.setDescription("描述6");
        item6.setUrl("https://www.baidu.com");
        item6.setImages(images);
        list.add(item6);
        Item item7 = new Item();
        item7.setName("Name7");
        item7.setCommentCount(1);
        item7.setPraiseCount(1);
        item7.setPrice(1.1f);
        item7.setDescription("描述7");
        item7.setUrl("https://www.baidu.com");
        item7.setImages(images);
        list.add(item7);
        Item item8 = new Item();
        item8.setName("Name8");
        item8.setCommentCount(1);
        item8.setPraiseCount(1);
        item8.setPrice(1.1f);
        item8.setDescription("描述8");
        item8.setUrl("https://www.baidu.com");
        item8.setImages(images);
        list.add(item8);
        DroiObject.saveAllInBackground(list, new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                Log.i("upload", "result:" + aBoolean);
            }
        });
    }
}
