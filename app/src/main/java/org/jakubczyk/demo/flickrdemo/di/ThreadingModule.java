package org.jakubczyk.demo.flickrdemo.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ThreadingModule {

    public static final String IO = "io";
    public static final String MAIN = "main";

    @Provides
    @Named(IO)
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Named(MAIN)
    Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
