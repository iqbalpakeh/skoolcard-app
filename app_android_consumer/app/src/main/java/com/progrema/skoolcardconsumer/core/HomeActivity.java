package com.progrema.skoolcardconsumer.core;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.progrema.skoolcardconsumer.App;
import com.progrema.skoolcardconsumer.R;
import com.progrema.skoolcardconsumer.api.model.Payload;
import com.progrema.skoolcardconsumer.core.auth.LoginActivity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "NFC_DBG";

    private Ndef mNDef;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilter;

    private TextView mNfcPayloadTv;
    private CheckBox mWriteMode;
    private ProgressDialog mProgressDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNfcPayloadTv = findViewById(R.id.nfc_payload);
        mWriteMode = findViewById(R.id.write_mode);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TextView userId = findViewById(R.id.user_id);
        userId.setText("User Id = " + App.getUID(this));

        initNFC();
    }

    private void initNFC() {

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        mPendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this,
                        getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }

        mIntentFilter = new IntentFilter[] {ndef};

        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {

            Log.d(TAG, "Found NDEF tag!");

            if (mWriteMode.isChecked()) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

                if (tag != null) {

                    Ndef ndef = Ndef.get(tag);

                    Log.d(TAG, "Write NDEF tag");
                    showProgress(true);

                    if (ndef != null) {
                        mNDef = ndef;
                        Payload payload = new Payload(App.getUserEmail(this), App.getUID(this));
                        Gson gson = new Gson();
                        String message = gson.toJson(payload);
                        Log.d(TAG, "message = " + message);
                        new WriteNFCTask().execute(message);
                    }

                }

            } else {

                Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

                if (rawMessages != null) {

                    NdefMessage[] messages = new NdefMessage[rawMessages.length];

                    for (int i = 0; i < rawMessages.length; i++) {
                        messages[i] = (NdefMessage) rawMessages[i];

                        String message = new String(messages[i].getRecords()[0].getPayload());
                        mNfcPayloadTv.setText(message);
                        Log.d(TAG, "readFromNFC payload: " + message);

                    }

                }

            }

        } else {
            Log.d(TAG, "Found Non-NDEF tag!");
        }
    }

    void showProgress(final boolean show) {

        if (show) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Accessing server...");
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.show();

        } else {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    private NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {

        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");

        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + textBytes.length];

        data[0] = (byte) status;
        System.arraycopy(textBytes, 0, data, 1, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);

        return record;
    }

    private class WriteNFCTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {

            try {

                mNDef.connect();
                NdefRecord mimeRecord = createTextRecord(strings[0], Locale.US, true);
                mNDef.writeNdefMessage(new NdefMessage(mimeRecord));
                mNDef.close();

            } catch (IOException | FormatException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showProgress(false);
        }

    }
}
