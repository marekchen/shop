package com.droi.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.model.Item;
import com.droi.shop.view.UWebView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/7.
 */

public class DetailActivity extends Activity {
    @BindView(R.id.view_pager)
    RollPagerView mViewPager;
    @BindView(R.id.webView)
    UWebView mWebView;
    Context mContext;
    Item item;
    public static final String ITEM_ENTRY = "ITEM_ENTRY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        mContext = this;
        item = getIntent().getParcelableExtra(ITEM_ENTRY);
        mViewPager.setAdapter(new ImageNormalAdapter(mViewPager));
        mWebView.loadUrl(item.getUrl());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mWebView.loadData("<html><body>xxx</body></html>", "text/html;charset=UTF-8", "UTF-8");

    }

    private class ImageNormalAdapter extends LoopPagerAdapter {


        public ImageNormalAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            Log.i("test", "po:" + position);
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //view.setImageResource(imgs[position]);
            Glide.with(DetailActivity.this).load(item.getImages().get(position)).placeholder(R.drawable.loading).into(view);
            return view;
        }

        @Override
        public int getRealCount() {
            //return imgs.length;
            return item.getImages().size();
        }
    }
}

/*
public class DetailActivity extends Activity {
    @BindView(R.id.view_pager)
    RollPagerView mViewPager;
    @BindView(R.id.webView)
    UWebView mWebView;
    Context mContext;
    Item item;
    public static final String ITEM_ENTRY = "ITEM_ENTRY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        mContext = this;
        item = getIntent().getParcelableExtra(ITEM_ENTRY);
        mViewPager.setAdapter(new ImageNormalAdapter());
        mWebView.loadUrl(item.getUrl());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private class ImageNormalAdapter extends StaticPagerAdapter {
        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Log.i("chenpei",position+">>>"+item.getImages().get(position));
            Glide.with(mContext).load(item.getImages().get(position)).into(view);
            return view;
        }

        @Override
        public int getCount() {
            return item.getImages().size();
        }
    }*/
