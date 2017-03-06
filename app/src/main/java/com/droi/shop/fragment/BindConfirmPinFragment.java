package com.droi.shop.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.interfaces.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindConfirmPinFragment extends BackHandledFragment {

    private static final String TAG = "BindConfirmPinFragment";
    private OnFragmentInteractionListener mListener;
    CountDownTimerUtils mCountDownTimerUtils;

    @BindView(R.id.pin_code)
    EditText pinCodeEditText;
    @BindView(R.id.timer)
    TextView timerTextView;
    ProgressDialog mProgressDialog;

    public BindConfirmPinFragment() {
        // Required empty public constructor
    }

    public static BindConfirmPinFragment newInstance() {
        return new BindConfirmPinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_confirm_pin, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mCountDownTimerUtils = new CountDownTimerUtils(60000, 1000);
        mCountDownTimerUtils.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCountDownTimerUtils.cancel();
    }

    @OnClick(R.id.confirm_button)
    public void onConfirmButtonPressed() {
        DroiUser user = DroiUser.getCurrentUser();
        String pinCode = pinCodeEditText.getText().toString();
        if (pinCode.length() != 6) {
            Toast.makeText(getActivity(), "验证码长度不正确", Toast.LENGTH_SHORT).show();
        }
        showInValidationProgress();
        user.confirmPhoneNumberPinCodeInBackground(pinCode, new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                hideInValidationProgress();
                if (droiError.isOk()) {
                    Toast.makeText(getActivity(), "绑定成功", Toast.LENGTH_SHORT).show();
                    if (mListener != null) {
                        //成功 回到ProfileActivity
                        mListener.onFragmentInteraction(2);
                    }
                } else {
                    if (droiError.getCode() == 1040018) {
                        Toast.makeText(getActivity(), "绑定操作过于频繁，请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "绑定失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class CountDownTimerUtils extends CountDownTimer {

        public CountDownTimerUtils(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            timerTextView.setClickable(false); //设置不可点击
            timerTextView.setTextColor(getResources().getColor(R.color.text_color_gray));
            timerTextView.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }

        @Override
        public void onFinish() {
            timerTextView.setText("重新获取验证码");
            timerTextView.setClickable(true);//重新获得点击
            timerTextView.setTextColor(getResources().getColor(R.color.text_blue));
            timerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(60000, 1000);
                    mCountDownTimerUtils.start();
                    DroiUser user = DroiUser.getCurrentUser();
                    DroiError error = user.validatePhoneNumber();
                    if (error.isOk()) {
                        Log.i(TAG, "sendPinCode:success");
                    } else {
                        Log.i(TAG, "sendPinCode:failed:" + error.toString());
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "ConfirmPinFragment");

    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "ConfirmPinFragment");
    }

    public void showInValidationProgress() {
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle(null);
        mProgressDialog.setMessage("验证中");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    public void hideInValidationProgress() {
        mProgressDialog.cancel();
    }
}
