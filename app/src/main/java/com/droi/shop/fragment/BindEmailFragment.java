package com.droi.shop.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;
import com.droi.shop.interfaces.OnFragmentInteractionListener;
import com.droi.shop.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindEmailFragment extends BackHandledFragment {

    private static final String TAG = "BindEmailFragment";
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.email_et)
    EditText emailEditText;
    ProgressDialog mProgressDialog;

    public BindEmailFragment() {
        // Required empty public constructor
    }

    public static BindEmailFragment newInstance() {
        return new BindEmailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this.getActivity());
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
        if (!CommonUtils.isEmail(email)) {
            Toast.makeText(getActivity(), R.string.input_correct_email, Toast.LENGTH_SHORT).show();
            return;
        }
        user.setEmail(email);
        showInValidationProgress();
        user.saveInBackground(new DroiCallback<Boolean>() {
            @Override
            public void result(Boolean aBoolean, DroiError droiError) {
                if (aBoolean && droiError.isOk()) {
                    user.validateEmailInBackground(new DroiCallback<Boolean>() {
                        @Override
                        public void result(Boolean aBoolean, DroiError droiError) {
                            hideInValidationProgress();
                            if (droiError.isOk()) {
                                if (mListener != null) {
                                    mListener.onFragmentInteraction(1);
                                }
                            } else if (droiError.getCode() == DroiError.USER_CONTACT_HAD_VERIFIED) {
                                Toast.makeText(getActivity(), R.string.email_had_verified, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), R.string.verify_failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    hideInValidationProgress();
                    Toast.makeText(getActivity(), R.string.verify_failed, Toast.LENGTH_SHORT).show();
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
        mProgressDialog.setMessage(getString(R.string.verifying));
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
    }

    public void hideInValidationProgress() {
        mProgressDialog.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), TAG);

    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), TAG);
    }
}
