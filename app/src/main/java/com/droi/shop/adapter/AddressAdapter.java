package com.droi.shop.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.droi.shop.R;
import com.droi.shop.model.Address;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/8.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    Context mContext;
    List<Address> mAddresses;

    public AddressAdapter(List<Address> addresses) {
        mAddresses = addresses;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddressViewHolder holder, int position) {
        holder.mNameTextView.setText(mAddresses.get(position).getName());
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 更新默认地址云代码实现
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_text)
        TextView mNameTextView;
        @BindView(R.id.phone_text)
        TextView mPhoneTextView;
        @BindView(R.id.address_text)
        TextView mAddressTextView;
        /*@BindView(R.id.radio)
        RadioButton mRadioButton;*/

        @BindView(R.id.checkbox)
        CheckBox mCheckBox;
        @BindView(R.id.edit_image)
        ImageView mEditImageView;
        @BindView(R.id.edit_text)
        TextView mEditTextView;
        @BindView(R.id.delete_image)
        ImageView mDeleteImageView;
        @BindView(R.id.delete_text)
        TextView mDeleteTextView;

        AddressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
