package org.jakubczyk.demo.flickrdemo;

import android.app.Application;

import org.jakubczyk.demo.flickrdemo.di.AppComponent;
import org.jakubczyk.demo.flickrdemo.di.AppModule;
import org.jakubczyk.demo.flickrdemo.di.DaggerAppComponent;

public class DemoApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildApplicationComponent();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private AppComponent buildApplicationComponent() {
        return DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();
    }
}
