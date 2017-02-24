package com.droi.shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.shop.R;
import com.droi.shop.adapter.ItemAdapter;
import com.droi.shop.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/13.
 */

public class ItemListFragment extends Fragment {

    public final static String ITEM_NAME = "ITEM_NAME";
    public final static String TYPE = "TYPE";
    public final static int TYPE_SEARCH = 1;
    public final static int TYPE_TYPE = 2;

    Context mContext;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<Item> mItems;
    String name;
    int type;
    RecyclerView.Adapter mAdapter;
    boolean isRefreshing = false;
    private int offset = 0;
    View view;

    public ItemListFragment() {
    }

    public static ItemListFragment newInstance(int type, String name) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putString(ITEM_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            name = getArguments().getString(ITEM_NAME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_item_list, container, false);
            ButterKnife.bind(this, view);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(layoutManager);
            mItems = new ArrayList<>();
            mAdapter = new ItemAdapter(mItems, 0);
            mRecyclerView.setAdapter(mAdapter);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    offset = 0;
                    fetchItems();
                }
            });

            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView,
                                                 int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && layoutManager.findLastVisibleItemPosition() + 1
                            == mItems.size()) {
                        if (mItems.size() != 0 && mItems.size() >= 10) {
                            fetchItems();
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
            initToolbar(view);
            fetchItems();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.clearOnScrollListeners();
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (type == TYPE_SEARCH) {
            toolbar.setTitle(R.string.item_list_title);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        } else {
            toolbar.setTitle(R.string.activity_main_tab_category);
        }

    }

    void fetchItems() {
        DroiQuery query;
        if (type == TYPE_SEARCH) {
            DroiCondition cond = DroiCondition.cond("name", DroiCondition.Type.CONTAINS, name);
            DroiCondition cond1 = DroiCondition.cond("description", DroiCondition.Type.CONTAINS, name);
            query = DroiQuery.Builder.newBuilder().where(cond.or(cond1)).offset(offset).limit(10).query(Item.class).build();
        } else {
            //DroiCondition cond = DroiCondition.cond("type", DroiCondition.Type.EQ, type);
            query = DroiQuery.Builder.newBuilder().offset(offset).limit(10).query(Item.class).build();
        }
        query.runQueryInBackground(new DroiQueryCallback<Item>() {
            @Override
            public void result(List<Item> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        if (offset == 0) {
                            mItems.clear();
                        }
                        mItems.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        offset = mItems.size();
                    }
                } else {
                    //做请求失败处理
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
