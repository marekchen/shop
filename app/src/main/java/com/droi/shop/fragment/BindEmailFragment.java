package com.droi.shop.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.interfaces.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindEmailFragment extends BackHandledFragment {

    private static final String TAG = "BindEmailFragment";
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.email_et)
    EditText emailEditText;
    ProgressDialog mProgressDialog;
    Context mContext;

    public BindEmailFragment() {
        // Required empty public constructor
    }

    public static BindEmailFragment newInstance() {
        BindEmailFragment fragment = new BindEmailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this.getActivity());
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_email, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @OnClick(R.id.confirm_button)
    void onConfirmButtonPressed() {
        final DroiUser user = DroiUser.getCurrentUser();
        String email = emailEditText.getText().toString();
        user.setEmail(email);
        showInValidationProgress();
        user.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if (aBoolean && droiError.isOk()) {
                    /*DroiError error = user.validateEmail();
                    if (error.isOk()) {
                        //跳转 pincode验证fragment
                        Log.i(TAG, "sendPinCode:success");
                        if (mListener != null) {
                            mListener.onFragmentInteraction(1);
                        }
                    } else {
                        Log.i(TAG, "sendPinCode:failed:" + error.toString());
                        Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                    }*/
                    user.validateEmailInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            hideInValidationProgress();
                            if (droiError.isOk()) {
                                //跳转 pincode验证fragment
                                Log.i(TAG, "sendPinCode:success");
                                if (mListener != null) {
                                    mListener.onFragmentInteraction(1);
                                }
                            } else {
                                Log.i(TAG, "sendPinCode:failed:" + droiError.toString());
                                Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    hideInValidationProgress();
                    Log.i(TAG, "sendPinCode:user:" + droiError.toString());
                    Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
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
