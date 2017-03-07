package com.droi.shop.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.model.Address;
import com.droi.shop.util.CommonUtils;
import com.droi.shop.util.ProgressDialogUtil;

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
    void save() {
        String userId = DroiUser.getCurrentUser().getObjectId();
        String name = mNameEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();
        String location = mLocationEditText.getText().toString();
        String addressText = mAddressEditText.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(this, "收件人不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (name.length() > 10) {
            Toast.makeText(this, "收件人姓名过长", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!CommonUtils.isPhoneNumberValid(phone)) {
            Toast.makeText(this, "手机号码非法", Toast.LENGTH_SHORT).show();
            return;
        }
        if (location.isEmpty()) {
            Toast.makeText(this, "所在地区不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (addressText.isEmpty()) {
            Toast.makeText(this, "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Address address;
        if (mAddress == null) {
            address = new Address(userId, name, phone, location, addressText);
        } else {
            address = mAddress;
            address.setName(name);
            address.setPhoneNum(phone);
            address.setLocation(location);
            address.setAddress(addressText);
        }
        final ProgressDialogUtil dialog = new ProgressDialogUtil(this);
        dialog.showDialog(R.string.saving);
        address.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if (aBoolean) {
                    dialog.dismissDialog();
                    setResult(RESULT_OK, null);
                    finish();
                } else {
                    dialog.dismissDialog();
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        ButterKnife.bind(this);
        mAddress = getIntent().getParcelableExtra(ADDRESS);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mAddress == null) {
            toolbar.setTitle(R.string.activity_add_address_add);
        } else {
            toolbar.setTitle(R.string.activity_add_address_modify);
            mNameEditText.setText(mAddress.getName());
            mPhoneEditText.setText(mAddress.getPhoneNum());
            mLocationEditText.setText(mAddress.getLocation());
            mAddressEditText.setText(mAddress.getAddress());
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
