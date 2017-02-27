package com.droi.shop.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.droi.shop.interfaces.BackHandlerInterface;

/**
 * Created by chenpei on 16/9/14.
 */
public abstract class BackHandledFragment extends Fragment {

    protected BackHandlerInterface mBackHandlerInterface;

    public abstract boolean onBackPressed();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BackHandlerInterface) {
            mBackHandlerInterface = (BackHandlerInterface) getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mBackHandlerInterface.setSelectedFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBackHandlerInterface = null;
    }

}
