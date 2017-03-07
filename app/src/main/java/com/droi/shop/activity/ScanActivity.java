package com.droi.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQueryCallback;
import com.droi.shop.R;
import com.droi.shop.model.Item;

import java.util.List;

/**
 * Created by marek on 2017/2/10.
 */

public class ScanActivity extends AppCompatActivity implements QRCodeView.Delegate {

    @BindView(R.id.zxingview)
    ZXingView mZxingview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        mZxingview.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZxingview.startCamera();
        mZxingview.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mZxingview.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mZxingview.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_more, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        handleResult(result);
    }

    private void handleResult(String result) {
        vibrate();
        mZxingview.startSpot();
        Log.i("chenpei", "result>>" + result);
        if (result.startsWith("droi_shop://")) {
            fetchItem(result);
        } else {
            Toast.makeText(this, R.string.get_no_info, Toast.LENGTH_SHORT).show();
        }
    }

    void fetchItem(String result) {
        result = result.replace("droi_shop://", "");
        DroiCondition cond = DroiCondition.cond("_Id", DroiCondition.Type.EQ, result);
        DroiQuery query = DroiQuery.Builder.newBuilder().where(cond).limit(10).query(Item.class).build();
        query.runQueryInBackground(new DroiQueryCallback<Item>() {
            @Override
            public void result(List<Item> list, DroiError droiError) {
                if (droiError.isOk() && list.size() == 1) {
                    Intent intent = new Intent(ScanActivity.this, DetailActivity.class);
                    intent.putExtra(DetailActivity.ITEM_ENTRY, list.get(0));
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ScanActivity.this, R.string.get_no_info, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, R.string.open_camera_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
