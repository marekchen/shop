package com.droi.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.droi.shop.R;
import com.droi.shop.model.Address;
import com.droi.shop.util.ShoppingCartManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marek on 2017/2/20.
 */

public class OrderConfirmActivity extends Activity {

    public static final int ADDRESS_REQUEST_CODE = 10;

    private ArrayList<ShoppingCartManager.CartItem> cartItems;
    private Address address;

    @OnClick(R.id.order_confirm_input_address)
    void selectAddress(View view) {
        Intent intent = new Intent(this, AddressListActivity.class);
        startActivityForResult(intent, ADDRESS_REQUEST_CODE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
