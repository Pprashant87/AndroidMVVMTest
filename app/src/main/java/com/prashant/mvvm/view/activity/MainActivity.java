package com.prashant.mvvm.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;



import com.prashant.mvvm.R;
import com.prashant.mvvm.app.AppController;
import com.prashant.mvvm.utils.Logger;
import com.prashant.mvvm.utils.network.ConnectivityReceiver;
import com.prashant.mvvm.view.fragment.MainCountryFragment;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener  {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization(savedInstanceState);
    }

    private void initialization(Bundle savedInstanceState) {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        boolean addNewFragment = true;
        if(savedInstanceState!=null)
        {
            addNewFragment = false;
        }
        if(addNewFragment) {
            checkConnection();
            MainCountryFragment fragment = new MainCountryFragment();
            setFragment(fragment);
        }
        else
        {
            Logger.d(TAG,"Use already exist fragment");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppController.getInstance().setConnectivityListener(this);
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (!isConnected)
        {
            showSnack(isConnected);
        }

    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = getResources().getString(R.string.connected_to_internet);
            color = Color.WHITE;
        } else {
            message = getResources().getString(R.string.not_connected_to_internet);
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    public void setTitleForActionBar(String mTitle) {
        if (!TextUtils.isEmpty(mTitle) && mToolbar != null) {
            mToolbar.setTitle(mTitle);
        }

    }
}
