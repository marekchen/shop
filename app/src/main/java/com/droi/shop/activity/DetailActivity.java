package com.droi.shop.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.droi.shop.R;
import com.droi.shop.adapter.ImageNormalAdapter;
import com.droi.shop.model.Item;
import com.droi.shop.util.ShoppingCartManager;
import com.droi.shop.view.UWebView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marek on 2017/2/7.
 */

public class DetailActivity extends Activity {
    @BindView(R.id.view_pager)
    RollPagerView mViewPager;
    @BindView(R.id.webView)
    UWebView mWebView;
    @BindView(R.id.name_text)
    TextView mNameTextView;
    @BindView(R.id.desc_text)
    TextView mDescTextView;
    @BindView(R.id.price_text)
    TextView mPriceTextView;

    @OnClick(R.id.buy)
    void clickBuy(View view) {

    }

    @OnClick(R.id.add_cart)
    void clickAdd(View view) {
        ShoppingCartManager.getInstance(getApplicationContext()).addToCart(item);
    }

    @OnClick(R.id.shopping_cart)
    void clickCart(View view) {

    }

    @OnClick(R.id.like)
    void clickLike(View view) {

    }

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
        mViewPager.setAdapter(new ImageNormalAdapter(this, mViewPager, item));
        mWebView.loadUrl(item.getUrl());
        mNameTextView.setText(item.getName());
        mDescTextView.setText(item.getDescription());

        String priceText = String.format(
                mContext.getResources().getString(R.string.item_price),
                item.getPrice());
        mPriceTextView.setText(priceText);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}