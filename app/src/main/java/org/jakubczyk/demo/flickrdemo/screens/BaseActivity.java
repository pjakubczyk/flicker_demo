package org.jakubczyk.demo.flickrdemo.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.jakubczyk.demo.flickrdemo.DemoApplication;
import org.jakubczyk.demo.flickrdemo.di.AppComponent;

public class BaseActivity extends AppCompatActivity {

    protected AppComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DemoApplication companionApplication = (DemoApplication) getApplicationContext();
        component = companionApplication.getAppComponent();
    }
}
