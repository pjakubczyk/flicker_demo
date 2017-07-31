package org.jakubczyk.demo.flickrdemo.screens.search.di;

import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;
import org.jakubczyk.demo.flickrdemo.di.ThreadingModule;
import org.jakubczyk.demo.flickrdemo.screens.search.MainActivityContract;
import org.jakubczyk.demo.flickrdemo.screens.search.MainActivityPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class SearchModule {

    @Provides
    MainActivityContract.Presenter providePresenter(
            FlickrRepository flickrRepository,
            @Named(ThreadingModule.MAIN) Scheduler mainScheduler
    ) {
        return new MainActivityPresenter(flickrRepository, mainScheduler);
    }
}
