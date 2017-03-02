package com.droi.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.adapter.AddressAdapter;
import com.droi.shop.interfaces.MyItemClickListener;
import com.droi.shop.model.Address;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marek on 2017/2/8.
 */

public class AddressListActivity extends AppCompatActivity {

    public static final int ADDRESS_REQUEST = 10;
    public static final String ADDRESS = "ADDRESS";
    public static final String TYPE = "TYPE";
    int type;

    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    List<Address> mAddressList;
    AddressAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra(TYPE, 0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        HorizontalDividerItemDecoration itemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .colorResId(R.color.bg_main)
                .size(30)
                .build();
        mRecyclerView.addItemDecoration(itemDecoration);
        mAddressList = new ArrayList<>();
        mAdapter = new AddressAdapter(mAddressList);
        mAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("chenpei", "type:" + type);
                if (type == 0) {
                    Intent intent = new Intent();
                    intent.putExtra(ADDRESS, mAddressList.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent(AddressListActivity.this, AddressEditActivity.class);
                    intent.putExtra(AddressEditActivity.ADDRESS, mAddressList.get(position));
                    startActivity(intent);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        initToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchAddress();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ((TextView) toolbar.findViewById(R.id.title)).setText(R.string.activity_address_title);
        toolbar.findViewById(R.id.address_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddressEditActivity.class);
                startActivityForResult(intent, ADDRESS_REQUEST);
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void fetchAddress() {
        DroiCondition cond = DroiCondition.cond("userObjectId", DroiCondition.Type.EQ, DroiUser.getCurrentUser().getObjectId());
        DroiQuery query = DroiQuery.Builder.newBuilder().where(cond).orderBy("_ModifiedTime", false).offset(0).query(Address.class).build();
        query.runQueryInBackground(new DroiQueryCallback<Address>() {
            @Override
            public void result(List<Address> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        mAddressList.clear();
                        mAddressList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    //做请求失败处理
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fetchAddress();
    }
}
