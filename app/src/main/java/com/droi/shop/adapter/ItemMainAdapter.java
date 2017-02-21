package com.droi.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.activity.DetailActivity;
import com.droi.shop.interfaces.MyItemClickListener;
import com.droi.shop.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/17.
 */

public class ItemMainAdapter extends RecyclerView.Adapter<ItemMainAdapter.ItemViewHolder> {

    Context mContext;
    List<Item> mItems;

    public ItemMainAdapter(List<Item> items) {
        mItems = items;
    }

    @Override
    public ItemMainAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item_main, parent, false);
        return new ItemMainAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemMainAdapter.ItemViewHolder holder, int position) {
        holder.mNameTextView.setText(mItems.get(position).getName());

        String priceText = String.format(
                mContext.getResources().getString(R.string.item_price),
                mItems.get(position).getPrice());
        holder.mPriceTextView.setText(priceText);

        ArrayList<String> images = mItems.get(position).getImages();
        if (images != null && images.size() > 0) {
            String imageUrl = images.get(0);
            Glide.with(mContext).load(imageUrl).into(holder.mItemImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.item_image)
        ImageView mItemImageView;
        @BindView(R.id.name_text)
        TextView mNameTextView;
        @BindView(R.id.price_text)
        TextView mPriceTextView;
        private MyItemClickListener mListener;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = new MyItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.putExtra(DetailActivity.ITEM_ENTRY, mItems.get(position));
                    mContext.startActivity(intent);
                }
            };
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
