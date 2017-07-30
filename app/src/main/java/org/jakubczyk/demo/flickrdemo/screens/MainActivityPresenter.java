package org.jakubczyk.demo.flickrdemo.screens;


import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;
import org.jakubczyk.demo.flickrdemo.data.api.json.SearchResponse;
import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;
    private FlickrRepository flickrRepository;

    Scheduler scheduler = Schedulers.io();

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
                .debounce(3, TimeUnit.SECONDS, scheduler)
                .filter(charSequence -> charSequence.length() > 0)
                .doOnNext(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence charSequence) {

                    }
                })
                .flatMap(new Func1<CharSequence, Observable<SearchResponse>>() {
                    @Override
                    public Observable<SearchResponse> call(CharSequence charSequence) {
                        return flickrRepository.searchFlickr(charSequence.toString());
                    }
                })
                .map(new Func1<SearchResponse, List<Photo>>() {
                    @Override
                    public List<Photo> call(SearchResponse searchResponse) {
                        return searchResponse.photos.photoList;
                    }
                })
                .subscribe(new Subscriber<List<Photo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Photo> photoList) {
                        view.addPhotos(photoList);
                    }
                });
    }

}
