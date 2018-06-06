package com.smad.bsnassignment.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.smad.bsnassignment.BaseActivity;
import com.smad.bsnassignment.R;
import com.smad.bsnassignment.module.main.MainActivity;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });
    }

    @Override
    protected int getFragmentContainerId() {
        return 0;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_start;
    }
}
