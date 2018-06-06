package com.smad.bsnassignment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.smad.bsnassignment.support.DeferredFragmentTransaction;
import com.smad.bsnassignment.support.FragmentCommunicator;

import java.util.ArrayDeque;
import java.util.Queue;

public abstract class BaseActivity extends AppCompatActivity implements FragmentCommunicator {

    //in your Activity
    private final Queue<DeferredFragmentTransaction> deferredFragmentTransactions = new ArrayDeque<>();
    private ProgressDialog dialog;
    private SharedPreferences srdPrf;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    protected void openInBrowser(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {

        }
    }


    protected void hideToolbar() {
        if (getSupportActionBar() == null)
            return;
        getSupportActionBar().hide();
    }

    protected SharedPreferences getDefaultSharedPreferences() {
        if (srdPrf == null)
//            srdPrf = PreferenceManager.getDefaultSharedPreferences(this);
            srdPrf = getSharedPreferences(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        return srdPrf;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }


    protected void enableBackButton() {
        if (getSupportActionBar() == null)
            return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    protected void setPageTitle(int title) {
        if (getSupportActionBar() == null)
            return;
        getSupportActionBar().setTitle(title);
    }

    protected void startActivity(@NonNull Class<? extends BaseActivity> aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

    protected void startActivity(@NonNull Class<? extends BaseActivity> aClass, int flags) {
        Intent intent = new Intent(this, aClass);
        intent.setFlags(flags);
        startActivity(intent);
    }


    protected void startActivity(@NonNull Class<? extends BaseActivity> aClass, Bundle bundle) {
        Intent intent = new Intent(this, aClass);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void startActivityForResult(@NonNull Class<? extends BaseActivity> aClass, Bundle bundle, int request_code) {
        Intent intent = new Intent(this, aClass);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, request_code);
    }


    protected void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void showProgressDialog(int msg) {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(getResources().getString(msg));
            dialog.show();
        } else if (!dialog.isShowing()) {
            dialog.setMessage(getResources().getString(msg));
            dialog.show();
        }

    }

    private void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    private void replaceFragment(@NonNull Fragment fragment, int contentFrameId, final boolean addToStack) {
        if (!isRunning) {
            DeferredFragmentTransaction deferredFragmentTransaction = new DeferredFragmentTransaction() {
                @Override
                public void commit() {
                    replaceFragmentInternal(getContentFrameId(), getReplacingFragment(), addToStack);
                }
            };

            deferredFragmentTransaction.setContentFrameId(contentFrameId);
            deferredFragmentTransaction.setReplacingFragment(fragment);

            deferredFragmentTransactions.add(deferredFragmentTransaction);
        } else {
            replaceFragmentInternal(contentFrameId, fragment, addToStack);
        }
    }

    private void addFragment(@NonNull Fragment fragment, int contentFrameId, final boolean addToStack) {

        if (!isRunning) {
            DeferredFragmentTransaction deferredFragmentTransaction = new DeferredFragmentTransaction() {
                @Override
                public void commit() {
                    addFragmentInternal(getContentFrameId(), getReplacingFragment(), addToStack);
                }
            };
//            R.id.fragment_container
            deferredFragmentTransaction.setContentFrameId(contentFrameId);
            deferredFragmentTransaction.setReplacingFragment(fragment);

            deferredFragmentTransactions.add(deferredFragmentTransaction);
        } else {
            addFragmentInternal(contentFrameId, fragment, addToStack);
        }
    }

    private void addFragmentInternal(int contentFrameId, Fragment fragment, final boolean addToStack) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(contentFrameId, fragment);
        if (addToStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }


    private void replaceFragmentInternal(int contentFrameId, Fragment replacingFragment, boolean addToStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(contentFrameId, replacingFragment);
        if (addToStack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        dialog = null;
        super.onDestroy();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        while (!deferredFragmentTransactions.isEmpty()) {
            deferredFragmentTransactions.remove().commit();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void setFragmentTitle(int title) {
        if (title != 0)
            setTitle(title);
    }

    @Override
    public void showProgressBar(int message) {
        showProgressDialog(message);
    }

    @Override
    public void dismissProgressBar() {
        dismiss();
    }

    @Override
    public void replaceFragment(Fragment fragment, Bundle bundle, boolean addTo) {
        if (bundle != null)
            fragment.setArguments(bundle);
        replaceFragment(fragment, getFragmentContainerId(), addTo);
    }

    @Override
    public void addFragment(Fragment fragment, Bundle bundle, boolean addTo) {
        if (bundle != null)
            fragment.setArguments(bundle);
        addFragment(fragment, getFragmentContainerId(), addTo);
    }

    protected abstract int getFragmentContainerId();

    protected abstract int getContentView();

}