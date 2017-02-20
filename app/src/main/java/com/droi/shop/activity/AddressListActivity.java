package com.droi.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.droi.shop.R;
import com.droi.shop.adapter.AddressAdapter;
import com.droi.shop.model.Address;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/8.
 */

public class AddressListActivity extends AppCompatActivity {

    public static final int ADD_ADDRESS_REQUEST = 10;
    public static final int UPDATE_ADDRESS_REQUEST = 11;

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
        HorizontalDividerItemDecoration itemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.bg_main)
                .size(30)
                .build();

        mRecyclerView.addItemDecoration(itemDecoration);
        List<Address> list = new ArrayList<>();
        RecyclerView.Adapter mAdapter = new AddressAdapter(list);
        mRecyclerView.setAdapter(mAdapter);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((TextView) toolbar.findViewById(R.id.title)).setText(R.string.activity_address_title);
        toolbar.findViewById(R.id.address_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddressEditActivity.class);
                startActivityForResult(intent, ADD_ADDRESS_REQUEST);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void fetchAddress(){

    }
}
