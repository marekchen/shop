package com.droi.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.shop.R;
import com.droi.shop.activity.OrderDetailActivity;
import com.droi.shop.model.CartItem;
import com.droi.shop.model.Order;
import com.droi.shop.util.ProgressDialogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/22.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> mOrders;
    private Context mContext;

    public OrderAdapter(List<Order> list) {
        mOrders = list;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        holder.mOrderNoTextView.setText(mOrders.get(position).getObjectId());
        Date date = mOrders.get(position).getCreationTime();
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateText = String.format(
                mContext.getResources().getString(R.string.order_time),
                dateFm.format(date));
        holder.mOrderTimeTextView.setText(dateText);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new OrderItemAdapter(mOrders.get(position).getCartItems());
        holder.mRecyclerView.setAdapter(mAdapter);
        String priceText = String.format(
                mContext.getResources().getString(R.string.total_price),
                computeTotal(mOrders.get(position).getCartItems()));
        holder.mTotalPriceTextView.setText(priceText);
        int orderStateNum;
        holder.mOrderStateTextView.setOnClickListener(null);
        switch (mOrders.get(position).getState()) {
            case 1:
                orderStateNum = R.string.order_state_1;
                holder.mOrderStateTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ProgressDialogUtil dialog = new ProgressDialogUtil(mContext);
                        dialog.showDialog(R.string.order_paying);
                        Order order = mOrders.get(holder.getAdapterPosition());
                        order.setState(3);
                        order.saveInBackground(new DroiCallback<Boolean>() {
                            @Override
                            public void result(Boolean aBoolean, DroiError droiError) {
                                if (aBoolean) {
                                    holder.mOrderStateTextView.setText(R.string.order_state_3);
                                } else {
                                    Toast.makeText(mContext, R.string.order_pay_fail, Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismissDialog();
                            }
                        });
                    }
                });
                break;
            case 2:
                orderStateNum = R.string.order_state_2;
                break;
            case 3:
                orderStateNum = R.string.order_state_3;
                break;
            default:
                orderStateNum = R.string.order_state_0;
        }
        holder.mOrderStateTextView.setText(orderStateNum);
        holder.mOrderNoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra(OrderDetailActivity.ORDER, mOrders.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.order_no)
        TextView mOrderNoTextView;
        @BindView(R.id.order_state)
        TextView mOrderStateTextView;
        @BindView(R.id.total_price)
        TextView mTotalPriceTextView;
        @BindView(R.id.order_time)
        TextView mOrderTimeTextView;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;

        OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private float computeTotal(List<CartItem> cartItems) {
        float sum = 0.0f;
        if (cartItems.size() != 0) {
            for (CartItem cartItem : cartItems) {
                sum += cartItem.getNum() * cartItem.getItem().getPrice();
            }
        }
        return sum;
    }
}
