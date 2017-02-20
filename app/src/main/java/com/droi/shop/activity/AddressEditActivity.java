package com.droi.shop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.model.Address;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marek on 2017/2/9.
 */

public class AddressEditActivity extends AppCompatActivity {

    private Address mAddress;
    public static final String ADDRESS = "ADDRESS";
    @BindView(R.id.name_text)
    EditText mNameEditText;
    @BindView(R.id.phone_text)
    EditText mPhoneEditText;
    @BindView(R.id.location_text)
    EditText mLocationEditText;
    @BindView(R.id.address_text)
    EditText mAddressEditText;

    @OnClick(R.id.save)
    void save(View view) {
        String userId = DroiUser.getCurrentUser().getObjectId();
        String name = mNameEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();
        String location = mLocationEditText.getText().toString();
        String addressText = mAddressEditText.getText().toString();
        Address address;
        if (mAddress == null) {
            address = new Address(userId, name, phone, location, addressText);
        } else {
            address =mAddress;
            address.setName(name);
            address.setPhoneNum(phone);
            address.setLocation(location);
            address.setAddress(addressText);
        }
        address.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                Log.i("chenpei","save ok");
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.bind(this);
        mAddress = (Address) getIntent().getSerializableExtra(ADDRESS);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mAddress == null) {
            toolbar.setTitle(R.string.activity_add_address_add);
        } else {
            toolbar.setTitle(R.string.activity_add_address_modify);
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
