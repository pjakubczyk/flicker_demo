package org.jakubczyk.demo.flickrdemo.di;


import org.jakubczyk.demo.flickrdemo.data.api.FlickrConnector;
import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class AppModule {


    @Provides
    FlickrRepository provideFlickrRepository(
            FlickrConnector flickrConnector,
            @Named(ThreadingModule.IO) Scheduler ioScheduler,
            @Named(ThreadingModule.MAIN) Scheduler mainScheduler
    ) {
        return new FlickrRepository(
                flickrConnector,
                ioScheduler,
                mainScheduler
        );
    }


}
