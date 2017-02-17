package com.droi.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.model.ItemType;

import java.util.List;

/**
 * Created by chenpei on 2016/5/11.
 */
public class ItemTypeAdapter extends BaseAdapter {
    private Context mContext;
    private List<ItemType> mList;

    public ItemTypeAdapter(Context mContext, List<ItemType> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_item_type, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.item_type_name);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_type_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getName());
        Glide.with(mContext).load(mList.get(position).getIconUrl()).into(holder.imageView);
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
