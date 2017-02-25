package com.droi.shop.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.shop.R;
import com.droi.shop.adapter.OrderItemAdapter;
import com.droi.shop.model.CartItem;
import com.droi.shop.model.Order;
import com.droi.shop.util.ProgressDialogUtil;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/25.
 */

public class OrderDetailActivity extends AppCompatActivity {

    public static final String ORDER = "ORDER";
    private List<CartItem> cartItems;
    private Order order;
    Context mContext;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pay)
    BootstrapButton mPayButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initToolbar();
        Intent intent = getIntent();
        order = intent.getParcelableExtra(ORDER);
        cartItems = order.getCartItems();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new OrderItemAdapter(cartItems);
        HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(mAdapter);

        View footView = getLayoutInflater().inflate(R.layout.view_bottom_detail_order, null);
        TextView totalPriceText = (TextView) footView.findViewById(R.id.total_price);
        TextView addressNameText = (TextView) footView.findViewById(R.id.address_name);
        TextView addressText = (TextView) footView.findViewById(R.id.address);
        TextView orderTimeText = (TextView) footView.findViewById(R.id.order_time);
        TextView payTypeText = (TextView) footView.findViewById(R.id.pay_type);
        TextView remarkText = (TextView) footView.findViewById(R.id.remark);
        String totalString = String.format(
                this.getResources().getString(R.string.total_price),
                computeTotal());
        totalPriceText.setText(totalString);

        String addressNameString = String.format(
                this.getResources().getString(R.string.address_name_text),
                order.getAddress().getName());
        addressNameText.setText(addressNameString);

        String addressString = String.format(
                this.getResources().getString(R.string.address_address_text),
                order.getAddress().getAddress());
        addressText.setText(addressString);

        Date date = order.getCreationTime();
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderTimeString = String.format(
                this.getResources().getString(R.string.order_time),
                dateFm.format(date));
        orderTimeText.setText(orderTimeString);

        String payType = "";
        if (order.getPayType() == 1) {
            payType = "货到付款";
        }
        String payTypeString = String.format(
                this.getResources().getString(R.string.pay_type),
                payType);
        payTypeText.setText(payTypeString);

        String remarkString = String.format(
                this.getResources().getString(R.string.order_remark),
                order.getRemark());
        remarkText.setText(remarkString);
        wrapper.addFootView(footView);

        View headView = getLayoutInflater().inflate(R.layout.view_head_detail_order, null);
        TextView orderNoText = (TextView) headView.findViewById(R.id.order_no);
        TextView orderState = (TextView) headView.findViewById(R.id.order_state);
        orderNoText.setText(order.getObjectId());
        orderState.setText(""+order.getState());
        wrapper.addHeaderView(headView);
        mContext = this;
        mRecyclerView.setAdapter(wrapper);
        mPayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialogUtil dialog = new ProgressDialogUtil(mContext);
                dialog.showDialog(R.string.submitting_order);
                order.setState(2);
                order.saveInBackground(new DroiCallback<Boolean>() {
                    @Override
                    public void result(Boolean aBoolean, DroiError droiError) {
                        dialog.dismissDialog();
                        if (aBoolean){
                            //finish();
                        }else{
                            Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.order_detail);
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

    float computeTotal() {
        float sum = 0.0f;
        if (cartItems.size() != 0) {
            for (CartItem cartItem : cartItems) {
                sum += cartItem.num * cartItem.item.getPrice();
            }
        }
        return sum;
    }
}
