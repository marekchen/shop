package com.droi.shop.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.droi.shop.R;
import com.droi.shop.view.UWebView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by marek on 2017/2/7.
 */

public class DetailActivity extends Activity {
    private RollPagerView mViewPager;
    private UWebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        mViewPager = (RollPagerView) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new ImageNormalAdapter());
        mWebView = (UWebView)findViewById(R.id.webview);
        mWebView.loadUrl("http://www.qq.com");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mWebView.loadData("<html><body>xxx</body></html>", "text/html;charset=UTF-8", "UTF-8");

    }

    private class ImageNormalAdapter extends StaticPagerAdapter {
        int[] imgs = new int[]{
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            Log.i("test", "po:" + position);
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setImageResource(imgs[position]);
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
