package com.droi.shop.adapter;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droi.shop.R;
import com.droi.shop.model.Address;

import java.util.List;

/**
 * Created by marek on 2017/2/8.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    Context mContext;
    List<Address> mAddresses;

    public void AddressAdapter(List<Address> addresses) {
        mAddresses = addresses;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        holder.mNameTextView.setText(mAddresses.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {
        TextView mNameTextView;

        AddressViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.name_text);
        }
    }
}
