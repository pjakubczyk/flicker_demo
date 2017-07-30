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
                // don't flood with requests
                .debounce(3, TimeUnit.SECONDS)
                .filter(textToSearch -> textToSearch.length() > 0)
                .flatMap(textToSearch -> flickrRepository.searchFlickr(textToSearch.toString()))
                .map(searchResponse -> searchResponse.photos.photoList)
                .subscribe(
                        photoList -> view.addPhotos(photoList)
                );
    }

}
