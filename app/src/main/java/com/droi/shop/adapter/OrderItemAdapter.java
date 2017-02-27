package com.droi.shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.model.CartItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/21.
 */

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<CartItem> mItems;


    public OrderItemAdapter(List<CartItem> items) {
        mItems = items;
    }

    @Override
    public OrderItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderitem, parent, false);
        return new OrderItemAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderItemAdapter.ItemViewHolder holder, final int position) {
        holder.mNameTextView.setText(mItems.get(position).getItem().getName());

        String priceText = String.format(
                mContext.getResources().getString(R.string.item_price),
                mItems.get(position).getItem().getPrice());
        holder.mPriceTextView.setText(priceText);
        ArrayList<String> images = mItems.get(position).getItem().getImages();
        if (images != null && images.size() > 0) {
            String imageUrl = images.get(0);
            Glide.with(mContext).load(imageUrl).into(holder.mItemImageView);
        }
        String numText = String.format(
                mContext.getResources().getString(R.string.item_num),
                mItems.get(position).getNum());
        holder.mNumTextView.setText(numText);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView mItemImageView;
        @BindView(R.id.name_text)
        TextView mNameTextView;
        @BindView(R.id.price_text)
        TextView mPriceTextView;
        @BindView(R.id.num_text)
        TextView mNumTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
