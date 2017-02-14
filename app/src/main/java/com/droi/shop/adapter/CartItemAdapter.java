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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.activity.DetailActivity;
import com.droi.shop.util.ShoppingCartManager;
import com.droi.shop.view.AmountView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.droi.shop.util.ShoppingCartManager.isAllChecked;

/**
 * Created by marek on 2017/2/13.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ItemViewHolder> {

    Context mContext;
    List<ShoppingCartManager.CartItem> mItems;
    TextView mTotalPrice;
    CheckBox mCheckBox;


    public CartItemAdapter(List<ShoppingCartManager.CartItem> items, TextView totalPrice, CheckBox checkBox) {
        mItems = items;
        mTotalPrice = totalPrice;
        mCheckBox = checkBox;
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
                ShoppingCartManager.setChecked(mItems.get(position).id, isChecked);
                changeTotal();
            }
        });
        holder.mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                if (amount != 0) {
                    ShoppingCartManager.setNum(mItems.get(position).id, amount);
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

    public void changeTotal() {
        mCheckBox.setChecked(isAllChecked());
        String priceText = String.format(
                mContext.getResources().getString(R.string.item_price),
                ShoppingCartManager.computeSum());
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
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(mContext);
        //normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("确认");
        normalDialog.setMessage("你确认要移除该商品吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShoppingCartManager.removeFromCart(id, true);
                        changeTotal();
                    }
                });
        normalDialog.setNegativeButton("按错了",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAmountView.setNum(mAmountView.getLastAmount());
                        ShoppingCartManager.setNum(id, mAmountView.getLastAmount());
                        changeTotal();
                    }
                });
        normalDialog.show();
    }
}