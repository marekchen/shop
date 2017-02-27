package com.droi.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.activity.OrderConfirmActivity;
import com.droi.shop.adapter.CartItemAdapter;
import com.droi.shop.model.CartItem;
import com.droi.shop.model.ShopUser;
import com.droi.shop.util.ShoppingCartManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/14.
 */

public class ShoppingCartFragment extends Fragment {

    public static final String TYPE = "TYPE";
    int type = 0;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.sum)
    TextView mTotalPrice;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;
    @BindView(R.id.checkout)
    BootstrapButton mCheckoutButton;
    @BindView(R.id.empty_layout)
    LinearLayout mEmptyLayout;
    @BindView(R.id.bottom_layout)
    LinearLayout mBottomLayout;

    Context mContext;
    List<CartItem> list = new ArrayList<>();
    RecyclerView.Adapter mAdapter;

    public ShoppingCartFragment() {

    }

    public static ShoppingCartFragment newInstance(int type) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CartItemAdapter(list, mTotalPrice, mCheckBox, mCheckoutButton, mEmptyLayout);
        mRecyclerView.setAdapter(mAdapter);
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartManager.getInstance(getActivity().getApplicationContext()).checkAll(mCheckBox.isChecked());
                list.clear();
                list.addAll(ShoppingCartManager.getInstance(getActivity().getApplicationContext()).getList());
                mAdapter.notifyDataSetChanged();
            }
        });
        mCheckoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopUser user = DroiUser.getCurrentUser(ShopUser.class);
                if (user == null || !user.isLoggedIn() || user.isAnonymous()) {
                    Toast.makeText(getActivity(), R.string.login_first, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(mContext, OrderConfirmActivity.class);
                String gson = ShoppingCartManager.getInstance(mContext).getOrderJson();
                if (gson == null) {
                    Toast.makeText(mContext, R.string.no_select_item, Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra(OrderConfirmActivity.ORDER, gson);
                startActivity(intent);
            }
        });
        initToolbar(view);
        return view;
    }

    private void initToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.activity_main_tab_shop);
        if (type == 1) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "ItemListFragment");
        refresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "ItemListFragment");
    }
    void refresh() {
        list.clear();
        list.addAll(ShoppingCartManager.getInstance(getActivity().getApplicationContext()).getList());
        if (list.size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
        changeTotal();
    }

    public void changeTotal() {
        String checkoutText = String.format(
                mContext.getResources().getString(R.string.checkout),
                ShoppingCartManager.getInstance(getActivity().getApplicationContext()).getCheckNum());
        mCheckoutButton.setText(checkoutText);
        mCheckBox.setChecked(ShoppingCartManager.getInstance(getActivity().getApplicationContext()).isAllChecked());
        String priceText = String.format(
                mContext.getResources().getString(R.string.total_price),
                ShoppingCartManager.getInstance(getActivity().getApplicationContext()).computeSum());
        mTotalPrice.setText(priceText);
    }
}
