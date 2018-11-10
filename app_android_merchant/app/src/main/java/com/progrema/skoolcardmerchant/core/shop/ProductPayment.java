package com.progrema.skoolcardmerchant.core.shop;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.api.firebase.FbPayment;
import com.progrema.skoolcardmerchant.api.model.Payload;
import com.progrema.skoolcardmerchant.api.model.Transaction;
import com.progrema.skoolcardmerchant.core.HomeActivity;
import com.wang.avi.AVLoadingIndicatorView;

public class ProductPayment extends AppCompatActivity implements FbPayment.FbPayAble {

    private static final String TAG = "PaymentActivity";

    private TextView mTotalPayment;
    private TextView mUserIndicator;
    private Button mActionButton;
    private AVLoadingIndicatorView mWaitingIndicator;
    private AVLoadingIndicatorView mServerIndicator;
    private ImageView mApprovedIndicator;
    private ImageView mRejectIndicator;

    private FbPayment mFbPayment;
    private Transaction mTransaction;

    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mFbPayment = FbPayment.build(this, this);

        mUserIndicator = findViewById(R.id.user_message);
        mTotalPayment = findViewById(R.id.total_payment_amount);
        mActionButton = findViewById(R.id.action_button);
        mWaitingIndicator = findViewById(R.id.tap_waiting_indicator);
        mServerIndicator = findViewById(R.id.server_waiting_indicator);
        mApprovedIndicator = findViewById(R.id.approved_indicator);
        mRejectIndicator = findViewById(R.id.reject_indicator);

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backHome();
            }
        });

        Bundle extras = getIntent().getExtras();
        String transaction = extras.getString(ProductFragment.TAG);
        Gson gson = new Gson();
        mTransaction = gson.fromJson(transaction, Transaction.class);
        mTotalPayment.setText(mTransaction.getAmount());

        Log.d(TAG, transaction);
        Log.d(TAG, mTransaction.getAmount());

        initNFC();
    }

    private void initNFC() {
        try {
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
            mPendingIntent = PendingIntent.getActivity(
                    this, 0, new Intent(this,
                            getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            ndef.addDataType("*/*");
            mIntentFilter = new IntentFilter[]{ndef};
            transactionNfcWaiting();
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
    }

    private void dummyNfcTapEvent(final String transaction) {
        transactionNfcWaiting();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                transactionServerWaiting();
                mFbPayment.doPayment(transaction);
            }
        }, 2000);
    }

    private void backHome() {
        Intent intent = new Intent(ProductPayment.this, HomeActivity.class);
        startActivity(intent);
    }

    private void transactionNfcWaiting() {
        mWaitingIndicator.setVisibility(View.VISIBLE);
        mServerIndicator.setVisibility(View.GONE);
        mApprovedIndicator.setVisibility(View.GONE);
        mRejectIndicator.setVisibility(View.GONE);
        mActionButton.setText(R.string.cancel);
        mUserIndicator.setText(R.string.please_tap_to_pay);
    }

    private void transactionServerWaiting() {
        mWaitingIndicator.setVisibility(View.GONE);
        mServerIndicator.setVisibility(View.VISIBLE);
        mApprovedIndicator.setVisibility(View.GONE);
        mRejectIndicator.setVisibility(View.GONE);
        mActionButton.setText(R.string.cancel);
        mUserIndicator.setText(R.string.waiting_server);
    }

    private void transactionApproved() {
        mWaitingIndicator.setVisibility(View.GONE);
        mServerIndicator.setVisibility(View.GONE);
        mApprovedIndicator.setVisibility(View.VISIBLE);
        mRejectIndicator.setVisibility(View.GONE);
        mActionButton.setText(R.string.done);
        mUserIndicator.setText(R.string.transaction_approved);
    }

    private void transactionRejected() {
        mWaitingIndicator.setVisibility(View.GONE);
        mServerIndicator.setVisibility(View.GONE);
        mApprovedIndicator.setVisibility(View.GONE);
        mRejectIndicator.setVisibility(View.VISIBLE);
        mActionButton.setText(R.string.done);
        mUserIndicator.setText(R.string.transaction_rejected);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilter, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String nfcData = "";
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Log.d(TAG, "Found NDEF tag!");
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {

                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                    nfcData += new String(messages[i].getRecords()[0].getPayload());
                    Log.d(TAG, "readFromNFC payload: " + nfcData);
                }

                Gson gson = new Gson();
                Payload payload = gson.fromJson(nfcData, Payload.class);
                transactionServerWaiting();
                mTransaction.setConsumer(payload.getConsumerUid());
                mFbPayment.doPayment(mTransaction.json());
            }
        } else {
            Log.d(TAG, "Found Non-NDEF tag!");
        }
    }

    @Override
    public void onPaymentApproved() {
        transactionApproved();
    }

    @Override
    public void onPaymentRejected() {
        transactionRejected();
    }

}
