package com.smad.bsnassignment.module.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.smad.bsnassignment.BaseFragment;
import com.smad.bsnassignment.R;

public class Tools extends BaseFragment {
    @Override
    protected int getFragmentTitle() {
        return R.string.tools;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TextView)view.findViewById(R.id.text)).setText(R.string.tools);
    }

    @Override
    protected int getViewResource() {
        return R.layout.fragmet_home;
    }
}
