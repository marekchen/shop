package com.droi.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.shop.R;
import com.droi.shop.activity.AddressEditActivity;
import com.droi.shop.interfaces.MyItemClickListener;
import com.droi.shop.model.Address;
import com.droi.shop.util.ProgressDialogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/8.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private Context mContext;
    private List<Address> mAddresses;
    private MyItemClickListener mItemClickListener;

    public AddressAdapter(List<Address> addresses) {
        mAddresses = addresses;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view, mItemClickListener);
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(final AddressViewHolder holder, int position) {
        holder.mNameTextView.setText(mAddresses.get(position).getName());
        holder.mPhoneTextView.setText(mAddresses.get(position).getPhoneNum());
        holder.mAddressTextView.setText(mAddresses.get(position).getAddress());
/*        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 更新默认地址云代码实现
            }
        });*/
        holder.mEditImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddressEditActivity.class);
                intent.putExtra(AddressEditActivity.ADDRESS, mAddresses.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
        holder.mEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddressEditActivity.class);
                intent.putExtra(AddressEditActivity.ADDRESS, mAddresses.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
        holder.mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialogUtil dialog = new ProgressDialogUtil(mContext);
                dialog.showDialog("正在删除");
                mAddresses.get(holder.getAdapterPosition()).deleteInBackground(new DroiCallback<Boolean>() {
                    @Override
                    public void result(Boolean aBoolean, DroiError droiError) {
                        if (aBoolean) {
                            mAddresses.remove(holder.getAdapterPosition());
                            dialog.dismissDialog();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        holder.mDeleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialogUtil dialog = new ProgressDialogUtil(mContext);
                dialog.showDialog("正在删除");
                mAddresses.get(holder.getAdapterPosition()).deleteInBackground(new DroiCallback<Boolean>() {
                    @Override
                    public void result(Boolean aBoolean, DroiError droiError) {
                        if (aBoolean) {
                            mAddresses.remove(holder.getAdapterPosition());
                            dialog.dismissDialog();
                            notifyDataSetChanged();
                        } else {
                            //错误
                            dialog.dismissDialog();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddresses.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.name_text)
        TextView mNameTextView;
        @BindView(R.id.phone_text)
        TextView mPhoneTextView;
        @BindView(R.id.address_text)
        TextView mAddressTextView;
        /*        @BindView(R.id.checkbox)
                CheckBox mCheckBox;*/
        @BindView(R.id.edit_image)
        ImageView mEditImageView;
        @BindView(R.id.edit_text)
        TextView mEditTextView;
        @BindView(R.id.delete_image)
        ImageView mDeleteImageView;
        @BindView(R.id.delete_text)
        TextView mDeleteTextView;
        private MyItemClickListener mListener;

        AddressViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
