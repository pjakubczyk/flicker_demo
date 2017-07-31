package org.jakubczyk.demo.flickrdemo.di;

import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;

import dagger.Component;

@Component(modules = {AppModule.class, ApiModule.class, ThreadingModule.class})
public interface AppComponent {

    FlickrRepository getFlickrRepository();
}
