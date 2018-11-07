package com.progrema.skoolcardconsumer.core.account;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.progrema.skoolcardconsumer.R;
import com.progrema.skoolcardconsumer.api.firebase.FbPayment;

public class AccountFragment extends Fragment implements FbPayment.FbPayAble {

    public static final String TAG = "Account";

    private OnFragmentInteractionListener mListener;

    private TextView mRemainingBalance;

    private FbPayment mFbPayment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFbPayment = FbPayment.build(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        mRemainingBalance = view.findViewById(R.id.remaining_balance);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFbPayment.monitorBalance();
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

    @Override
    public void onBalanceChange(String newBalance) {
        mRemainingBalance.setText(newBalance);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
