package com.droi.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.droi.shop.R;
import com.droi.shop.fragment.ItemListFragment;

import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/17.
 */
public class ItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra(ItemListFragment.ITEM_NAME);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = ItemListFragment.newInstance(ItemListFragment.TYPE_SEARCH, name);
        fm.beginTransaction().add(R.id.frame, fragment).commit();
    }
}
