package com.droi.shop.activity;

import android.os.Bundle;

import com.droi.shop.R;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droi.sdk.selfupdate.DroiUpdate;
import com.droi.sdk.selfupdate.DroiUpdateListener;
import com.droi.sdk.selfupdate.DroiUpdateResponse;
import com.droi.shop.fragment.ItemListFragment;
import com.droi.shop.fragment.MainFragment;
import com.droi.shop.fragment.MineFragment;
import com.droi.shop.fragment.ShoppingCartFragment;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_TAB_INDEX = "index";
    private FragmentTabHost mTabHost;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //自定义普通更新的通知方式，该方式只对普通更新有效，对默认更新、强制更新无效
        //是否弹出默认的更新提示
        //DroiUpdate.setUpdateAutoPopup(false);
        //设置监听
        DroiUpdate.setUpdateListener(new DroiUpdateListener() {
            @Override
            public void onUpdateReturned(int i, DroiUpdateResponse droiUpdateResponse) {
                switch (i) {
                    /*case UpdateStatus.NO:
                        Toast.makeText(mContext, R.string.update_status_no, Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.YES:
                        Toast.makeText(mContext, R.string.update_status_yes, Toast.LENGTH_SHORT).show();
                        //你可以在此回调处实现自己的更新UI
                        //在用户点击更新时调用下面的函数
                        DroiUpdate.downloadApp(mContext, droiUpdateResponse, null);
                        break;
                    case UpdateStatus.ERROR:
                        Toast.makeText(mContext, R.string.update_status_error, Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.TIMEOUT:
                        Toast.makeText(mContext, R.string.update_status_timeout, Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.NON_WIFI:
                        Toast.makeText(mContext, R.string.update_status_nonwifi, Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.UPDATING:
                        Toast.makeText(mContext, R.string.update_status_updating, Toast.LENGTH_SHORT).show();
                        break;*/
                }
            }
        });
        DroiUpdate.update(this);
        initTab();
    }

    private void initTab() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.addTab(mTabHost.newTabSpec("mainTab").setIndicator(getTabView(R.drawable.btn_home, R.string.activity_main_tab_home)),
                MainFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("itemsTab").setIndicator(getTabView(R.drawable.btn_home, R.string.activity_main_tab_home)),
                ItemListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("cartTab").setIndicator(getTabView(R.drawable.btn_home, R.string.activity_main_tab_home)),
                ShoppingCartFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("mineTab").setIndicator(getTabView(R.drawable.btn_mine, R.string.activity_main_tab_mine)),
                MineFragment.class, null);
        selectTab(getIntent().getIntExtra(MAIN_TAB_INDEX, 0));
    }

    public void selectTab(int index) {
        mTabHost.setCurrentTab(index);
    }

    private View getTabView(int imgId, int txtId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.main_bottom_tab, null);
        ((ImageView) view.findViewById(R.id.main_bottom_tab_img)).setImageResource(imgId);
        ((TextView) view.findViewById(R.id.main_bottom_tab_label)).setText(txtId);
        return view;
    }
}
