package com.smad.bsnassignment.module.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.smad.bsnassignment.BaseFragment;
import com.smad.bsnassignment.R;

public class Send extends BaseFragment {
    @Override
    protected int getFragmentTitle() {
        return R.string.send;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TextView)view.findViewById(R.id.text)).setText(R.string.send);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragmet_home;
    }
}
