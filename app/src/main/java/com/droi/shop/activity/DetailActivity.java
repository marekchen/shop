package com.droi.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.shop.R;
import com.droi.shop.adapter.ImageNormalAdapter;
import com.droi.shop.fragment.ShoppingCartFragment;
import com.droi.shop.model.Item;
import com.droi.shop.util.ShoppingCartManager;
import com.droi.shop.view.UWebView;
import com.jude.rollviewpager.RollPagerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by marek on 2017/2/7.
 */

public class DetailActivity extends AppCompatActivity {
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
    void clickBuy() {
        ShoppingCartManager.getInstance(getApplicationContext()).addToCart(item);
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        intent.putExtra(ShoppingCartFragment.TYPE, 1);
        startActivity(intent);
    }

    @OnClick(R.id.add_cart)
    void clickAdd() {
        ShoppingCartManager.getInstance(getApplicationContext()).addToCart(item);
        Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.shopping_cart)
    void clickCart() {
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        intent.putExtra(ShoppingCartFragment.TYPE, 1);
        startActivity(intent);
    }

    Item item;
    public static final String ITEM_ENTRY = "ITEM_ENTRY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);
        item = getIntent().getParcelableExtra(ITEM_ENTRY);
        mViewPager.setAdapter(new ImageNormalAdapter(this, mViewPager, item));
        mWebView.loadUrl(item.getUrl());
        mNameTextView.setText(item.getName());
        mDescTextView.setText(item.getDescription());

        String priceText = String.format(
                this.getResources().getString(R.string.item_price),
                item.getPrice());
        mPriceTextView.setText(priceText);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.activity_confirm_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    private void destroyWebView() {
        if (mWebView != null) {
            mWebView.pauseTimers();
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}