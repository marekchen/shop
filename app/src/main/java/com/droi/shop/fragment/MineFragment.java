package com.droi.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
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
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.push.DroiPush;
import com.droi.shop.R;
import com.droi.shop.model.ShopUser;
import com.droi.shop.view.CircleImageView;

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
            if (user.avatar != null) {
                user.avatar.getInBackground(new DroiCallback<byte[]>() {
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
        view.findViewById(R.id.mine_frag_favorite).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_answer).setOnClickListener(this);
        view.findViewById(R.id.mine_frag_question).setOnClickListener(this);
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
            /*case R.id.mine_frag_follow:
                Log.i("test", "follow");
                Intent followIntent = new Intent(getActivity(), MyFollowActivity.class);
                startActivity(followIntent);
                break;
            case R.id.mine_frag_favorite:
                Log.i("test", "favorite");
                Intent favoriteIntent = new Intent(getActivity(), MyFavoriteActivity.class);
                startActivity(favoriteIntent);
                break;
            case R.id.mine_frag_answer:
                Log.i("test", "answer");
                Intent answerIntent = new Intent(getActivity(), MyAnswerActivity.class);
                startActivity(answerIntent);
                break;
            case R.id.mine_frag_question:
                Log.i("test", "question");
                if (user == null) {
                    break;
                }
                Intent questionIntent = new Intent(getActivity(), QuestionListActivity.class);
                questionIntent.putExtra(QuestionFragment.QUESTIONER, user.getObjectId());
                startActivity(questionIntent);
                break;
            case R.id.mine_frag_update:
                //手动更新
                DroiUpdate.manualUpdate(mContext);
                break;
            case R.id.mine_frag_feedback:
                //自定义部分颜色
                DroiFeedback.setTitleBarColor(getResources().getColor(R.color.top_bar_background));
                DroiFeedback.setSendButtonColor(getResources().getColor(R.color.top_bar_background),
                        getResources().getColor(R.color.top_bar_background));
                //打开反馈页面
                DroiFeedback.callFeedback(mContext);
                break;
            case R.id.mine_about_us:
                Log.i("test", "about");
                Intent aboutUsIntent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(aboutUsIntent);
                break;
            case R.id.mine_frag_upload:
                Log.i("TEST", "mine_frag_upload");
                uploadBanner();
                //uploadAppInfo();
                //uploadAppType();
                uploadOfficialGuide();
                break;*/
            default:
                break;
        }
    }

    /**
     * 转到登录页面
     */
    private void toLogin() {
        /*Intent loginIntent = new Intent(mContext, LoginActivity.class);
        startActivity(loginIntent);*/
    }

    /**
     * 转到个人信息页面
     */
    private void toProfile() {
        /*Intent profileIntent = new Intent(mContext, ProfileActivity.class);
        startActivity(profileIntent);*/
    }
}
