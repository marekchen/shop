package com.droi.shop.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.adapter.OrderAdapter;
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
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;

    RecyclerView.Adapter mAdapter;
    boolean isRefreshing = false;
    private int offset = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initToolBar();
        mOrders = new ArrayList<>();
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrderAdapter(mOrders);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                fetchOrders();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && mLayoutManager.findLastVisibleItemPosition() + 1
                        == mOrders.size()) {
                    if (mOrders.size() != 0 && mOrders.size() >= 10) {
                        fetchOrders();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
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
        setRefreshing(true);
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        DroiCondition cond = DroiCondition.cond("userObjectId", DroiCondition.Type.EQ, DroiUser.getCurrentUser().getObjectId());
        DroiQuery query = DroiQuery.Builder.newBuilder().where(cond).orderBy("_ModifiedTime", false).limit(10).query(Order.class).build();
        query.runQueryInBackground(new DroiQueryCallback<Order>() {
            @Override
            public void result(List<Order> list, DroiError droiError) {
                if (droiError.isOk() && list.size() > 0) {
                    if (offset == 0) {
                        mOrders.clear();
                    }
                    mOrders.clear();
                    mOrders.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    offset = mOrders.size();
                    emptyLayout.setVisibility(View.GONE);
                } else {
                    emptyLayout.setVisibility(View.VISIBLE);
                }
                setRefreshing(false);
                isRefreshing = false;
            }
        });

    }

    void setRefreshing(final boolean b) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(b);
            }
        });
    }
}
