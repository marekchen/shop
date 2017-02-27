package com.droi.shop.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.droi.shop.R;
import com.droi.shop.fragment.BackHandledFragment;
import com.droi.shop.fragment.BindCompleteFragment;
import com.droi.shop.fragment.BindEmailFragment;
import com.droi.shop.interfaces.BackHandlerInterface;
import com.droi.shop.interfaces.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenpei on 16/9/12.
 */
public class BindEmailActivity extends AppCompatActivity implements OnFragmentInteractionListener, BackHandlerInterface {
    static FragmentManager fm;
    static Context context;
    private BackHandledFragment mBackHandedFragment;

    @BindView(R.id.top_bar_title)
    TextView topBarTitle;
    @BindView(R.id.top_bar_back_btn)
    ImageButton backArrowButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_bind);
        ButterKnife.bind(this);
        topBarTitle.setText(getString(R.string.email_bind));
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fm = getSupportFragmentManager();
        displayBindEmailFragment();
    }

    private static void displayBindEmailFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment bindNumFragment = BindEmailFragment.newInstance();
        transaction.replace(R.id.bind_container, bindNumFragment);
        transaction.commit();
    }

    public static void displayCompleteFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment confirmPinFragment = BindCompleteFragment.newInstance();
        transaction.replace(R.id.bind_container, confirmPinFragment);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(int action) {
        switch (action) {
            case 0:
                displayBindEmailFragment();
                break;
            case 1:
                displayCompleteFragment();
                break;
            case 2:
                finish();
                break;
        }
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {
        if (mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            displayBindEmailFragment();
        }
    }
}
