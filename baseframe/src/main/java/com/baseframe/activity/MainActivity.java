package com.baseframe.activity;

import android.app.Activity;
import android.os.Bundle;

import com.baseframe.R;
import com.baseframe.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
