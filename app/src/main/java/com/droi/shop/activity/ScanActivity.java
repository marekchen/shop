package com.droi.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
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
            case R.id.itemMore:
                showPopupMenu();
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
            Toast.makeText(this, "没有查询到信息", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ScanActivity.this, "没有查询到信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private void showPopupMenu() {
        /*if (mView == null) {
            mView = new FrameLayout(this);
            mView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mView.setBackgroundResource((R.color.white));

            TextView tv = new TextView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, UIUtils.dip2Px(45));
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            tv.setPadding(UIUtils.dip2Px(20), 0, 0, 0);
            tv.setTextColor(UIUtils.getColor(R.color.gray0));
            tv.setTextSize(14);
            tv.setText("从相册选取二维码");
            mView.addView(tv);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                    Intent intent = new Intent(ScanActivity.this, ImageGridActivity.class);
                    startActivityForResult(intent, IMAGE_PICKER);
                }
            });
        }
        mPopupWindow = PopupWindowFactory.getPopupWindowAtLocation(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopupWindowFactory.makeWindowLight(ScanActivity.this);
            }
        });
        PopupWindowFactory.makeWindowDark(this);*/
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//返回多张照片
            if (data != null) {
                //是否发送原图
//                boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
                final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {
                    //取第一张照片
                    ThreadPoolFactory.getNormalPool().execute(new Runnable() {
                        @Override
                        public void run() {
                            String result = QRCodeDecoder.syncDecodeQRCode(images.get(0).path);
                            if (TextUtils.isEmpty(result)) {
                                UIUtils.showToast("扫描失败");
                            } else {
                                handleResult(result);
                            }
                        }
                    });
                }
            }
        }*/
    }
}
