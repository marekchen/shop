package com.droi.shop.fragment;

import android.content.Context;
import android.content.pm.ShortcutManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.droi.shop.R;
import com.droi.shop.adapter.CartItemAdapter;
import com.droi.shop.util.ShoppingCartManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.droi.shop.util.ShoppingCartManager.isAllChecked;

/**
 * Created by marek on 2017/2/14.
 */

public class ShoppingCartFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.sum)
    TextView mTotalPrice;
    @BindView(R.id.checkbox)
    CheckBox mCheckBox;
    @BindView(R.id.checkout)
    BootstrapButton mCheckoutButton;


    Context mContext;
    ArrayList<ShoppingCartManager.CartItem> list = new ArrayList<>();
    RecyclerView.Adapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CartItemAdapter(list, mTotalPrice, mCheckBox, mCheckoutButton);
        mRecyclerView.setAdapter(mAdapter);
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("chenpei", "check:" + mCheckBox.isChecked());
                ShoppingCartManager.checkAll(mCheckBox.isChecked());
                mAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    void refresh() {
        list.clear();
        for (ShoppingCartManager.CartItem item : ShoppingCartManager.getList()) {
            list.add(item);
        }
        mAdapter.notifyDataSetChanged();
        changeTotal();
    }

    public void changeTotal() {
        String checkoutText = String.format(
                mContext.getResources().getString(R.string.checkout),
                ShoppingCartManager.getCheckNum());
        mCheckoutButton.setText(checkoutText);
        mCheckBox.setChecked(isAllChecked());
        String priceText = String.format(
                mContext.getResources().getString(R.string.item_price),
                ShoppingCartManager.computeSum());
        mTotalPrice.setText(priceText);
    }
}
