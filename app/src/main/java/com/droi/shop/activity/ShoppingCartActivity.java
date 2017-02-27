package com.droi.shop.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.droi.shop.R;
import com.droi.shop.fragment.ShoppingCartFragment;

import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/13.
 */

public class ShoppingCartActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = ShoppingCartFragment.newInstance(1);
        fm.beginTransaction().add(R.id.frame, fragment).commit();
    }
}


