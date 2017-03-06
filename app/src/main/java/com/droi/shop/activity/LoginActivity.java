package com.droi.shop.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.droi.shop.R;
import com.droi.shop.fragment.LoginFragment;

/**
 * Created by marek on 2017/2/17.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentManager fm = getSupportFragmentManager();
        displayLoginFragment(fm);
    }

    private static void displayLoginFragment(FragmentManager fm) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment loginFragment = new LoginFragment();
        transaction.replace(R.id.droi_login_container, loginFragment);
        transaction.commit();
    }
}

