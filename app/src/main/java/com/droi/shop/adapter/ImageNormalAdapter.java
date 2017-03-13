package com.droi.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.model.Item;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

/**
 * Created by marek on 2017/2/17.
 */

public class ImageNormalAdapter extends LoopPagerAdapter {

    private Context mContext;
    private Item mItem;

    public ImageNormalAdapter(Context context, RollPagerView viewPager, Item item) {
        super(viewPager);
        mContext = context.getApplicationContext();
        mItem =item;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(mContext).load(mItem.getImages().get(position)).placeholder(R.drawable.loading).into(view);
        return view;
    }

    @Override
    public int getRealCount() {
        return mItem.getImages().size();
    }
}