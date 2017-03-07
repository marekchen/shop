package com.droi.shop.activity;

import android.Manifest;
import android.os.Bundle;

import com.droi.shop.R;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droi.sdk.selfupdate.DroiUpdate;
import com.droi.shop.fragment.ItemListFragment;
import com.droi.shop.fragment.MainFragment;
import com.droi.shop.fragment.MineFragment;
import com.droi.shop.fragment.ShoppingCartFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String MAIN_TAB_INDEX = "index";
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
                }).check();
        DroiUpdate.update(this.getApplicationContext());
        initTab();
    }

    private void initTab() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.addTab(mTabHost.newTabSpec("mainTab").setIndicator(getTabView(R.drawable.btn_home, R.string.activity_main_tab_home)),
                MainFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("categoryTab").setIndicator(getTabView(R.drawable.btn_category, R.string.activity_main_tab_category)),
                ItemListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("shopTab").setIndicator(getTabView(R.drawable.btn_shop, R.string.activity_main_tab_shop)),
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
