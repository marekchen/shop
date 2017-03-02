package com.droi.shop.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by marek on 2017/3/1.
 */

public class QRCodeActivity extends AppCompatActivity{

    void getpic(){
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode("",100);
    }
}
