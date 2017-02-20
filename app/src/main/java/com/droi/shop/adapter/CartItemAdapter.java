package com.droi.shop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.util.ShoppingCartManager;
import com.droi.shop.view.AmountView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/13.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<ShoppingCartManager.CartItem> mItems;
    private TextView mTotalPrice;
    private CheckBox mCheckBox;
    private BootstrapButton mCheckoutButton;
    private LinearLayout mEmptyLayout;


    public CartItemAdapter(List<ShoppingCartManager.CartItem> items, TextView totalPrice,
                           CheckBox checkBox, BootstrapButton checkoutButton, LinearLayout emptyLayout) {
        mItems = items;
        mTotalPrice = totalPrice;
        mCheckBox = checkBox;
        mCheckoutButton = checkoutButton;
        mEmptyLayout = emptyLayout;
    }

    @Override
    public CartItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cartitem_list, parent, false);
        return new CartItemAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartItemAdapter.ItemViewHolder holder, final int position) {
        holder.mNameTextView.setText(mItems.get(position).item.getName());

        String priceText = String.format(
                mContext.getResources().getString(R.string.item_price),
                mItems.get(position).item.getPrice());
        holder.mPriceTextView.setText(priceText);
        holder.mAmountView.setNum(mItems.get(position).num);
        ArrayList<String> images = mItems.get(position).item.getImages();
        if (images != null && images.size() > 0) {
            String imageUrl = images.get(0);
            Glide.with(mContext).load(imageUrl).into(holder.mItemImageView);
        }
        holder.mCheckBox.setChecked(mItems.get(position).checked);

        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShoppingCartManager.getInstance(mContext.getApplicationContext())
                        .setChecked(mItems.get(position).id, isChecked);
                changeTotal();
            }
        });
        holder.mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                if (amount != 0) {
                    ShoppingCartManager.getInstance(mContext.getApplicationContext())
                            .setNum(mItems.get(position).id, amount);
                    changeTotal();
                } else {
                    showNormalDialog(holder.mAmountView, mItems.get(position).id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void changeTotal() {
        String checkoutText = String.format(
                mContext.getResources().getString(R.string.checkout), ShoppingCartManager
                        .getInstance(mContext.getApplicationContext()).getCheckNum());
        mCheckoutButton.setText(checkoutText);
        mCheckBox.setChecked(ShoppingCartManager.getInstance(mContext.getApplicationContext()).isAllChecked());
        String priceText = String.format(
                mContext.getResources().getString(R.string.total_price),
                ShoppingCartManager.getInstance(mContext.getApplicationContext()).computeSum());
        mTotalPrice.setText(priceText);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView mItemImageView;
        @BindView(R.id.name_text)
        TextView mNameTextView;
        @BindView(R.id.price_text)
        TextView mPriceTextView;
        @BindView(R.id.amount)
        AmountView mAmountView;
        @BindView(R.id.checkbox)
        CheckBox mCheckBox;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void showNormalDialog(final AmountView mAmountView, final String id) {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        normalDialog.setCancelable(false);
        normalDialog.setTitle(R.string.confirm);
        normalDialog.setMessage(R.string.confirm_remove);
        normalDialog.setPositiveButton(R.string.confirm,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShoppingCartManager.getInstance(mContext.getApplicationContext()).removeFromCart(id, true);
                        refresh();
                    }
                });
        normalDialog.setNegativeButton(R.string.wrong_click,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAmountView.setNum(mAmountView.getLastAmount());
                        ShoppingCartManager.getInstance(mContext.getApplicationContext())
                                .setNum(id, mAmountView.getLastAmount());
                    }
                });
        normalDialog.show();
    }

    void refresh() {
        mItems.clear();
        mItems.addAll(ShoppingCartManager.getInstance(mContext.getApplicationContext()).getList());
        if (mItems.size() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
        } else {
            mEmptyLayout.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
        changeTotal();
    }

}