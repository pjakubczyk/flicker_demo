package org.jakubczyk.demo.flickrdemo.di;


import org.jakubczyk.demo.flickrdemo.data.api.FlickrConnector;
import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {


    @Provides
    FlickrRepository provideFlickrRepository(FlickrConnector flickrConnector) {
        return new FlickrRepository(flickrConnector);
    }


}
