package org.jakubczyk.demo.flickrdemo.screens.search.di;

import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;
import org.jakubczyk.demo.flickrdemo.di.ThreadingModule;
import org.jakubczyk.demo.flickrdemo.screens.search.SearchActivityContract;
import org.jakubczyk.demo.flickrdemo.screens.search.SearchActivityPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class SearchModule {

    @Provides
    SearchActivityContract.Presenter providePresenter(
            FlickrRepository flickrRepository,
            @Named(ThreadingModule.MAIN) Scheduler mainScheduler
    ) {
        return new SearchActivityPresenter(flickrRepository, mainScheduler);
    }
}
