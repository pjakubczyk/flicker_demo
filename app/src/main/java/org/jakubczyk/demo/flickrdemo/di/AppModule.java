package org.jakubczyk.demo.flickrdemo.di;


import org.jakubczyk.demo.flickrdemo.data.api.FlickrConnector;
import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AppModule {


    static final String IO = "io";
    static final String MAIN = "main";

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

    @Provides
    FlickrRepository provideFlickrRepository(
            FlickrConnector flickrConnector,
            @Named(IO) Scheduler ioScheduler,
            @Named(MAIN) Scheduler mainScheduler
    ) {
        return new FlickrRepository(
                flickrConnector,
                ioScheduler,
                mainScheduler
        );
    }


}
