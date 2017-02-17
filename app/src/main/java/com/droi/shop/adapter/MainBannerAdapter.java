package com.droi.shop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.model.Banner;
import com.droi.shop.model.Item;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

/**
 * Created by marek on 2017/2/17.
 */

public class MainBannerAdapter extends LoopPagerAdapter {

    private Context mContext;
    private List<Banner> mBanners;

    public MainBannerAdapter(Context context, RollPagerView viewPager, List<Banner> banners) {
        super(viewPager);
        mContext = context;
        mBanners = banners;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        Glide.with(mContext).load(mBanners.get(position).getImgUrl()).placeholder(R.drawable.loading2).into(view);
        return view;
    }

    @Override
    public int getRealCount() {
        return mBanners.size();
    }
}