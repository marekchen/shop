package com.droi.shop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.shop.R;
import com.droi.shop.activity.SearchActivity;
import com.droi.shop.adapter.ItemMainAdapter;
import com.droi.shop.adapter.ItemTypeAdapter;
import com.droi.shop.adapter.MainBannerAdapter;
import com.droi.shop.model.Banner;
import com.droi.shop.model.Item;
import com.droi.shop.model.ItemType;
import com.droi.shop.view.DividerGridItemDecoration;
import com.jude.rollviewpager.RollPagerView;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by chenpei on 2016/5/11.
 */
public class MainFragment extends Fragment {
    private Context mContext;
    @OnClick(R.id.toolbar_search)
    void clickSearch(View view){
        Intent intent = new Intent(mContext, SearchActivity.class);
        startActivity(intent);
    }
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    List<Item> list;
    List<Banner> banners;
    List<ItemType> itemTypes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initData();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        RecyclerView.Adapter mAdapter = new ItemMainAdapter(list);
        HeaderAndFooterWrapper wrapper = new HeaderAndFooterWrapper(mAdapter);

        View headView = inflater.inflate(R.layout.view_head_main, null);
        RollPagerView rollPagerView = (RollPagerView) headView.findViewById(R.id.view_pager);
        GridView gridView = (GridView) headView.findViewById(R.id.grid_view);
        ItemTypeAdapter mItemTypeAdapter = new ItemTypeAdapter(mContext, itemTypes);
        gridView.setAdapter(mItemTypeAdapter);

        MainBannerAdapter bannerAdapter = new MainBannerAdapter(getActivity(), rollPagerView, banners);
        rollPagerView.setAdapter(bannerAdapter);
        wrapper.addHeaderView(headView);

        mRecyclerView.setAdapter(wrapper);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "MainFragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "MainFragment");
    }

    void initData(){
        itemTypes = new ArrayList<>();
        ItemType type = new ItemType();
        type.setName("男装");
        type.setIconUrl("http://7xoxqq.com1.z0.glb.clouddn.com/1.png?attname=&e=1487322852&token=nkrk_kj5nU8Nu1bKUM5mr9Zq6bVWC0_vAgApQnO1:Kfvs66zumBmvwcasPpQHqcfBPzY");
        ItemType type2 = new ItemType();
        type2.setName("女装");
        type2.setIconUrl("http://7xoxqq.com1.z0.glb.clouddn.com/2.png?attname=&e=1487322852&token=nkrk_kj5nU8Nu1bKUM5mr9Zq6bVWC0_vAgApQnO1:LunXms6SoLIqq4YMOXIhTyy73o4");
        ItemType type3 = new ItemType();
        type3.setName("电器");
        type3.setIconUrl("http://7xoxqq.com1.z0.glb.clouddn.com/3.png?attname=&e=1487322852&token=nkrk_kj5nU8Nu1bKUM5mr9Zq6bVWC0_vAgApQnO1:hHYgnfNu7GJMljKxIQ4dtnRzlyM");
        ItemType type4 = new ItemType();
        type4.setName("图书");
        type4.setIconUrl("http://7xoxqq.com1.z0.glb.clouddn.com/4.png?attname=&e=1487322852&token=nkrk_kj5nU8Nu1bKUM5mr9Zq6bVWC0_vAgApQnO1:uyu7FEQp4D9jX0fPNa1xlCysQAs");
        itemTypes.add(type);
        itemTypes.add(type2);
        itemTypes.add(type3);
        itemTypes.add(type4);
        list = new ArrayList<>();
        Item item1 = new Item();
        item1.setName("Name1");
        item1.setCommentCount(1);
        item1.setPraiseCount(1);
        item1.setPrice(1.1f);
        item1.setDescription("描述");
        item1.setUrl("https://m.baidu.com");
        ArrayList<String> images = new ArrayList<>();
        images.add("https://m.360buyimg.com/n12/jfs/t3235/100/1618018440/139400/44fd706e/57d11c33N5cd57490.jpg!q70.jpg");
        images.add("https://m.360buyimg.com/n12/jfs/t3271/63/1662792438/102212/520aadea/57d11c33N17fca17c.jpg!q70.jpg");
        images.add("https://m.360buyimg.com/n12/jfs/t3250/243/1621682752/58357/ff68e7a4/57d11c33N1cb90a82.jpg!q70.jpg");
        images.add("https://m.360buyimg.com/n12/jfs/t3205/201/1641534867/20579/160ed304/57d11c34Nb6c6ae50.jpg!q70.jpg");
        item1.setImages(images);
        list.add(item1);
        Item item2 = new Item();
        item2.setName("Name2");
        item2.setCommentCount(1);
        item2.setPraiseCount(1);
        item2.setPrice(1.1f);
        item2.setDescription("描述2");
        item2.setUrl("https://www.baidu.com");
        item2.setImages(images);
        list.add(item2);
        Item item3 = new Item();
        item3.setName("Name3");
        item3.setCommentCount(1);
        item3.setPraiseCount(1);
        item3.setPrice(1.1f);
        item3.setDescription("描述3");
        item3.setUrl("https://www.baidu.com");
        item3.setImages(images);
        list.add(item3);
        Item item4 = new Item();
        item4.setName("Name4");
        item4.setCommentCount(1);
        item4.setPraiseCount(1);
        item4.setPrice(1.1f);
        item4.setDescription("描述4");
        item4.setUrl("https://www.baidu.com");
        item4.setImages(images);
        list.add(item4);
        Item item5 = new Item();
        item5.setName("Name5");
        item5.setCommentCount(1);
        item5.setPraiseCount(1);
        item5.setPrice(1.1f);
        item5.setDescription("描述5");
        item5.setUrl("https://www.baidu.com");
        item5.setImages(images);
        list.add(item5);
        Item item6 = new Item();
        item6.setName("Name6");
        item6.setCommentCount(1);
        item6.setPraiseCount(1);
        item6.setPrice(1.1f);
        item6.setDescription("描述6");
        item6.setUrl("https://www.baidu.com");
        item6.setImages(images);
        list.add(item6);
        Item item7 = new Item();
        item7.setName("Name7");
        item7.setCommentCount(1);
        item7.setPraiseCount(1);
        item7.setPrice(1.1f);
        item7.setDescription("描述7");
        item7.setUrl("https://www.baidu.com");
        item7.setImages(images);
        list.add(item7);
        Item item8 = new Item();
        item8.setName("Name8");
        item8.setCommentCount(1);
        item8.setPraiseCount(1);
        item8.setPrice(1.1f);
        item8.setDescription("描述8");
        item8.setUrl("https://www.baidu.com");
        item8.setImages(images);
        list.add(item8);

        banners = new ArrayList<>();
        Banner banner = new Banner();
        banner.setImgUrl("http://newmarket.oo523.com:8080/market/img/2013/11/27/kosftkb7nv/1080x300.png");
        Banner banner2 = new Banner();
        banner2.setImgUrl("http://newmarket.oo523.com:8080/market/img/2013/11/27/0xmc6y9hxe/1080x300.png");
        Banner banner3 = new Banner();
        banner3.setImgUrl("http://newmarket.oo523.com:8080/market/img/2013/11/27/gjcdeo1up3/1080x300.png");
        banners.add(banner);
        banners.add(banner2);
        banners.add(banner3);
    }
}
