package com.droi.shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droi.shop.R;
import com.droi.shop.adapter.AddressAdapter;
import com.droi.shop.adapter.ItemAdapter;
import com.droi.shop.model.Item;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/13.
 */

public class ItemListFragment extends Fragment {
    Context mContext;
    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);
        //initUI(view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        List<Item> list = new ArrayList<>();
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
        RecyclerView.Adapter mAdapter = new ItemAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
