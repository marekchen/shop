package com.droi.shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droi.shop.R;
import com.droi.shop.interfaces.OnFragmentInteractionListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindCompleteFragment extends BackHandledFragment {

    private static final String TAG = "BindConfirmPinFragment";
    private OnFragmentInteractionListener mListener;

    public BindCompleteFragment() {
        // Required empty public constructor
    }

    public static BindCompleteFragment newInstance() {
        BindCompleteFragment fragment = new BindCompleteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bind_complete, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @OnClick(R.id.confirm_button)
    public void onConfirmButtonPressed() {
        if (mListener != null) {
            //成功 回到ProfileActivity
            mListener.onFragmentInteraction(2);
        }
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
}
