package com.droi.shop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.shop.R;
import com.droi.shop.adapter.OrderAdapter;
import com.droi.shop.adapter.OrderItemAdapter;
import com.droi.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/20.
 */

public class MyOrderActivity extends AppCompatActivity {

    List<Order> mOrders;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initToolBar();
        mOrders = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrderAdapter(mOrders);
        mRecyclerView.setAdapter(mAdapter);
        fetchOrders();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.fragment_mine_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void fetchOrders() {
        DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).query(Order.class).build();
        query.runQueryInBackground(new DroiQueryCallback<Order>() {
            @Override
            public void result(List<Order> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        mOrders.clear();
                        mOrders.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    //做请求失败处理
                }
            }
        });
    }
}
