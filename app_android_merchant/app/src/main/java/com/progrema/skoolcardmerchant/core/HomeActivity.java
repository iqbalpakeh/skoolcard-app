package com.progrema.skoolcardmerchant.core;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.progrema.skoolcardmerchant.R;
import com.progrema.skoolcardmerchant.api.model.Product;
import com.progrema.skoolcardmerchant.api.model.Transaction;
import com.progrema.skoolcardmerchant.core.account.AccountFragment;
import com.progrema.skoolcardmerchant.core.auth.LoginActivity;
import com.progrema.skoolcardmerchant.core.history.TransactionFragment;
import com.progrema.skoolcardmerchant.core.shop.ProductFragment;

public class HomeActivity extends AppCompatActivity implements
        ProductFragment.OnListFragmentInteractionListener,
        TransactionFragment.OnListFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_shop:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_container,
                                    new ProductFragment(), ProductFragment.TAG).commit();
                    return true;
                case R.id.navigation_history:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_container,
                                    new TransactionFragment(), TransactionFragment.TAG).commit();
                    return true;
                case R.id.navigation_account:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_container,
                                    new AccountFragment(), AccountFragment.TAG).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_activity_container, new ProductFragment(), ProductFragment.TAG).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            handleLogout();
            return true;
        } else if (id == R.id.about) {
            handleAbout();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void handleLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Leaving app?")
                .setMessage("Please confirm to logout")
                .setIcon(R.drawable.ic_warning_orange_24dp)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private void handleAbout() {
        Log.d("DBG", "About is pressed!");
    }

    @Override
    public void onListFragmentInteraction(Product item) {

    }

    @Override
    public void onListFragmentInteraction(Transaction item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
