package com.droi.shop.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.droi.shop.R;
import com.droi.shop.adapter.AddressAdapter;
import com.droi.shop.model.Address;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/8.
 */

public class AddressListActivity extends Activity {
    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        ArrayList<Address> list = new ArrayList<>();
        Address address1 = new Address();
        address1.setName("NAME1");
        list.add(address1);
        Address address2 = new Address();
        address2.setName("NAME2");
        list.add(address2);
        Address address3 = new Address();
        address3.setName("NAME3");
        list.add(address3);
        HorizontalDividerItemDecoration itemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.gray)
                .size(30)
                //.sizeResId(R.dimen.divider)
                .build();

//        Drawable drawable = getResources().getDrawable(R.drawable.shape);
//        GradientDrawable mDrawable = new GradientDrawable(
//                GradientDrawable.Orientation.TL_BR, new int[] { 0xFFFF0000,
//                0xFF00FF00, 0xFF0000FF });
//        mDrawable.setShape(GradientDrawable.RECTANGLE);
//        mDrawable.setGradientRadius((float) (Math.sqrt(2) * 60));
//        itemDecoration.setDrawable(mDrawable);
        mRecyclerView.addItemDecoration(itemDecoration);
        RecyclerView.Adapter mAdapter = new AddressAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
    }
}
