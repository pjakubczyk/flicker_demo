package org.jakubczyk.demo.flickrdemo.screens;


import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;
    private FlickrRepository flickrRepository;

    public MainActivityPresenter(FlickrRepository flickrRepository) {
        this.flickrRepository = flickrRepository;
    }

    @Override
    public void create(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void observeSearch(Observable<CharSequence> charSequenceObservable) {
        charSequenceObservable
                // skip first because it's call during view setup
                .skip(1)
                // don't flood with requests
                .debounce(3, TimeUnit.SECONDS)
                .subscribe(charSequence -> flickrRepository.searchFlickr(charSequence.toString()));
    }

}
