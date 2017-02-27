package com.droi.shop.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.droi.sdk.DroiError;
import com.droi.sdk.analytics.DroiAnalytics;
import com.droi.sdk.core.DroiUser;
import com.droi.shop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordFragment extends Fragment {

    private static String TAG = "ResetPasswordFragment";
    private ConfirmTask mAuthTask = null;
    private RestTask mResetTask = null;

    @BindView(R.id.user_name)
    EditText mUserNameView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.pincode)
    EditText mPincodeView;
    ProgressDialog mProgressView;
    private Activity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void onResume() {
        super.onResume();
        DroiAnalytics.onFragmentStart(getActivity(), "ResetPasswordFragment");
    }

    @Override
    public void onPause() {
        super.onPause();
        DroiAnalytics.onFragmentEnd(getActivity(), "ResetPasswordFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset, container, false);
        mProgressView = new ProgressDialog(getActivity());
        mProgressView.setMessage("Changing...");
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
    }

    @OnClick(R.id.req_pincode)
    void reqPincode() {
        if (mResetTask != null) {
            return;
        }
        String userName = mUserNameView.getText().toString();
        mResetTask = new RestTask(userName);
        mResetTask.execute((Void) null);
    }

    @OnClick(R.id.login_button)
    void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String pincode = mPincodeView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        } else if (!isUserNameValid(userName)) {
            mUserNameView.setError(getString(R.string.error_invalid_user_name));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new ConfirmTask(userName, pincode, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUserNameValid(String userName) {
        return userName.length() >= 8;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    /**
     * Shows the progress
     */
    private void showProgress(final boolean show) {
        if (show) {
            mProgressView.show();
        } else {
            mProgressView.dismiss();
        }
    }

    public class RestTask extends AsyncTask<Void, Void, DroiError> {

        private final String mEmail;

        RestTask(String email) {
            mEmail = email;
        }

        @Override
        protected DroiError doInBackground(Void... params) {
            DroiError droiError = new DroiError();
            //droiError = DroiUser.resetPassword(mEmail, DroiUser.ResetType.EMAIL);
            //DroiUser.confirmResetPassword(mEmail)
            return droiError;
        }

        @Override
        protected void onPostExecute(final DroiError droiError) {
            mAuthTask = null;
            showProgress(false);
            if (droiError.isOk()) {
                Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();
                activity.finish();
            } else {
                if (droiError.getCode() == DroiError.USER_NOT_EXISTS) {
                    mUserNameView.setError(getString(R.string.error_user_not_exists));
                    mUserNameView.requestFocus();
                } else if (droiError.getCode() == DroiError.USER_PASSWORD_INCORRECT) {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                } else {
                    Log.i(TAG, "error:" + droiError.toString());
                    Toast.makeText(getActivity(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public class ConfirmTask extends AsyncTask<Void, Void, DroiError> {

        private final String mEmail;
        private final String mPassword;
        private final String mPincode;

        ConfirmTask(String email, String pincode, String password) {
            mEmail = email;
            mPassword = password;
            mPincode = pincode;
        }

        @Override
        protected DroiError doInBackground(Void... params) {
            DroiError droiError = new DroiError();
            //droiError = DroiUser.resetPassword(mEmail, DroiUser.ResetType.a);
            //droiError = DroiUser.confirmResetPassword(mEmail, mPincode, mPassword);
            return droiError;
        }

        @Override
        protected void onPostExecute(final DroiError droiError) {
            mAuthTask = null;
            showProgress(false);
            if (droiError.isOk()) {
                Toast.makeText(getActivity(), R.string.login_success, Toast.LENGTH_SHORT).show();
                activity.finish();
            } else {
                if (droiError.getCode() == DroiError.USER_NOT_EXISTS) {
                    mUserNameView.setError(getString(R.string.error_user_not_exists));
                    mUserNameView.requestFocus();
                } else if (droiError.getCode() == DroiError.USER_PASSWORD_INCORRECT) {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                } else {
                    Log.i(TAG, "error:" + droiError.toString());
                    Toast.makeText(getActivity(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
