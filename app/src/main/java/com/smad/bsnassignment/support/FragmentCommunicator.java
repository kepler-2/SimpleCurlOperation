package com.smad.bsnassignment.support;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * Created by special on 21/11/17.
 */

public interface FragmentCommunicator {

    void setFragmentTitle(int title);

    void dismissProgressBar();

    void showProgressBar(int message);

    void replaceFragment(Fragment fragment, Bundle bundle, boolean addTo);

    void addFragment(Fragment fragment, Bundle bundle, boolean addTo);
}
