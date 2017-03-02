package com.droi.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.shop.R;
import com.droi.shop.activity.ScanActivity;
import com.droi.shop.activity.SearchActivity;
import com.droi.shop.adapter.ItemAdapter;
import com.droi.shop.adapter.ItemTypeAdapter;
import com.droi.shop.adapter.MainBannerAdapter;
import com.droi.shop.model.Banner;
import com.droi.shop.model.Item;
import com.droi.shop.model.ItemType;
import com.droi.shop.model.Test;
import com.jude.rollviewpager.RollPagerView;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenpei on 2016/5/11.
 */
public class MainFragment extends Fragment {
    private Context mContext;

    List<Item> mItems;
    List<Banner> mBanners;
    List<ItemType> mItemTypes;
    MainBannerAdapter mBannerAdapter;
    ItemTypeAdapter mItemTypeAdapter;
    RecyclerView.Adapter mAdapter;
    HeaderAndFooterWrapper wrapper;
    View view;
    boolean isRefreshing = false;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @OnClick(R.id.toolbar_search)
    void clickSearch() {
        Intent intent = new Intent(mContext, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            ButterKnife.bind(this, view);
            mItems = new ArrayList<>();
            mBanners = new ArrayList<>();
            mItemTypes = new ArrayList<>();
            initData();
            fetchItems();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new ItemAdapter(mItems, 1);
            wrapper = new HeaderAndFooterWrapper(mAdapter);

            View headView = inflater.inflate(R.layout.view_head_main, null);
            RollPagerView rollPagerView = (RollPagerView) headView.findViewById(R.id.view_pager);
            GridView gridView = (GridView) headView.findViewById(R.id.grid_view);
            mItemTypeAdapter = new ItemTypeAdapter(mContext, mItemTypes);
            gridView.setAdapter(mItemTypeAdapter);

            mBannerAdapter = new MainBannerAdapter(getActivity(), rollPagerView, mBanners);
            rollPagerView.setAdapter(mBannerAdapter);
            wrapper.addHeaderView(headView);

            mRecyclerView.setAdapter(wrapper);

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchItems();
                }
            });
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        initToolbar(view);
        return view;
    }

    private void initToolbar(View view) {
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_scan) {
                    Intent intent = new Intent(getActivity(), ScanActivity.class);
                    getActivity().startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (getChildFragmentManager().getBackStackEntryCount() == 0) {
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "MainFragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "MainFragment");
    }

    void initData() {
        DroiQuery query1 = DroiQuery.Builder.newBuilder().limit(10).query(Banner.class).build();
        query1.runQueryInBackground(new DroiQueryCallback<Banner>() {
            @Override
            public void result(List<Banner> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        mBanners.clear();
                        mBanners.addAll(list);
                        mBannerAdapter.notifyDataSetChanged();
                    }
                } else {
                    //做请求失败处理
                }
            }
        });
        DroiQuery query2 = DroiQuery.Builder.newBuilder().limit(10).query(ItemType.class).build();
        query2.runQueryInBackground(new DroiQueryCallback<ItemType>() {
            @Override
            public void result(List<ItemType> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        mItemTypes.clear();
                        mItemTypes.addAll(list);
                        mItemTypeAdapter.notifyDataSetChanged();
                    }
                } else {
                    //做请求失败处理
                }
            }
        });
    }

    void fetchItems() {
        setRefreshing(true);
        if (isRefreshing) {
            return;
        }
        DroiQuery query = DroiQuery.Builder.newBuilder().limit(10).query(Item.class).build();
        query.runQueryInBackground(new DroiQueryCallback<Item>() {
            @Override
            public void result(List<Item> list, DroiError droiError) {
                if (droiError.isOk() && list.size() > 0) {
                    mItems.clear();
                    mItems.addAll(list);
                    wrapper.notifyDataSetChanged();
                } else {

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
