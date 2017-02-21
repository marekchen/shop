package com.droi.shop.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by marek on 2017/2/21.
 */

public class ProgressDialogUtil {
    private ProgressDialog mProgressDialog;
    private Context mContext;

    public ProgressDialogUtil(Context context) {
        mProgressDialog = new ProgressDialog(context);
        mContext = context;
    }

    public void showDialog(int resId) {
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage(mContext.getString(resId));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    public void showDialog(String message) {
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    public void dismissDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
