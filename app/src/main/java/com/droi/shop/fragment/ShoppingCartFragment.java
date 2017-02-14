package com.droi.shop.fragment;

import android.content.Context;
import android.content.pm.ShortcutManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.droi.shop.R;
import com.droi.shop.adapter.CartItemAdapter;
import com.droi.shop.adapter.ItemAdapter;
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
        mAdapter = new CartItemAdapter(list, mTotalPrice, mCheckBox);
        mRecyclerView.setAdapter(mAdapter);
        /*mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShoppingCartManager.checkAll(isChecked);
                refresh();
            }
        });*/
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartManager.checkAll(mCheckBox.isChecked());
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
        mCheckBox.setChecked(isAllChecked());
        String priceText = String.format(
                mContext.getResources().getString(R.string.item_price),
                ShoppingCartManager.computeSum());
        mTotalPrice.setText(priceText);
    }
}
