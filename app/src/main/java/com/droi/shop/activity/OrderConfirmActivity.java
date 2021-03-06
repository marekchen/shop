package com.droi.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.adapter.OrderItemAdapter;
import com.droi.shop.model.Address;
import com.droi.shop.model.CartItem;
import com.droi.shop.model.Order;
import com.droi.shop.util.ProgressDialogUtil;
import com.droi.shop.util.ShoppingCartManager;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marek on 2017/2/20.
 */

public class OrderConfirmActivity extends AppCompatActivity {

    public static final int ADDRESS_REQUEST_CODE = 10;
    public static final String ORDER = "ORDER";

    private List<CartItem> cartItems;
    private Address address;
    TextView remarkTextView;
    TextView addressSelect;
    CheckBox payType;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.total)
    TextView mTotalTextView;

    @OnClick(R.id.checkout)
    void checkout() {
        if (address == null) {
            Toast.makeText(this, R.string.select_address, Toast.LENGTH_SHORT).show();
            return;
        }
        final Order order = new Order();
        order.setUserObjectId(DroiUser.getCurrentUser().getObjectId());
        order.setAddress(address);
        order.setCartItems(cartItems);
        order.setPayType(payType.isChecked() ? 1 : 2);
        order.setReceiptType(1);
        order.setState(1);
        order.setRemark(remarkTextView.getText().toString());
        final ProgressDialogUtil dialog = new ProgressDialogUtil(this);
        dialog.showDialog(R.string.submitting_order);
        order.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                dialog.dismissDialog();
                if (aBoolean) {
                    ShoppingCartManager.getInstance(OrderConfirmActivity.this).clearOrder();
                    Intent intent = new Intent(OrderConfirmActivity.this, OrderDetailActivity.class);
                    intent.putExtra(OrderDetailActivity.ORDER, order);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.submit_order_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        String gson = getIntent().getStringExtra(ORDER);
        cartItems = new ArrayList<>();
        cartItems.addAll(ShoppingCartManager.getInstance(this).getOrderList(gson));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new OrderItemAdapter(cartItems);
        HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(mAdapter);

        View headView = getLayoutInflater().inflate(R.layout.view_head_order_confirm, null);
        remarkTextView = (TextView) headView.findViewById(R.id.order_confirm_remarks);
        addressSelect = (TextView) headView.findViewById(R.id.order_confirm_input_address);
        payType = (CheckBox) headView.findViewById(R.id.order_confirm_pay_type);
        addressSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmActivity.this, AddressListActivity.class);
                intent.putExtra(AddressListActivity.ADDRESS, 0);
                startActivityForResult(intent, ADDRESS_REQUEST_CODE);
            }
        });
        wrapper.addHeaderView(headView);
        mRecyclerView.setAdapter(wrapper);
        initToolbar();
        String totalText = String.format(
                getResources().getString(R.string.total_price),
                computeTotal());
        mTotalTextView.setText(totalText);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.activity_confirm_title);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_REQUEST_CODE && resultCode == RESULT_OK) {
            address = data.getParcelableExtra(AddressListActivity.ADDRESS);
            String addressString = address.getName() + " " + address.getPhoneNum() + "\n" + address.getLocation() + " " + address.getAddress();
            addressSelect.setText(addressString);
        }
    }

    float computeTotal() {
        float sum = 0.0f;
        if (cartItems.size() != 0) {
            for (CartItem cartItem : cartItems) {
                sum += cartItem.getNum() * cartItem.getItem().getPrice();
            }
        }
        return sum;
    }
}
