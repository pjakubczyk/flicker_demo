package org.jakubczyk.demo.flickrdemo.data.repository;

import org.jakubczyk.demo.flickrdemo.BuildConfig;
import org.jakubczyk.demo.flickrdemo.data.api.FlickrConnector;
import org.jakubczyk.demo.flickrdemo.data.api.json.SearchResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FlickrRepository {

    private static final String TAG = "DDD";

    private static final String FLICKR_API_METHOD = "flickr.photos.search";
    private static final String FLICKR_API_FORMAT = "json";
    private static final Integer FLICKR_API_NO_JSON_CALLBACK = 1;
    private static final Integer FLICKR_API_PAGE = 1;
    private static final Integer FLICKR_API_PAGE_SIZE = 20;


    private FlickrConnector flikrConnector;

    public FlickrRepository(FlickrConnector flikrConnector) {
        this.flikrConnector = flikrConnector;
    }

    public Observable<SearchResponse> searchFlickr(String textToSearch) {

        return flikrConnector
                .search(
                        FLICKR_API_METHOD,
                        BuildConfig.FLICKR_API_KEY,
                        FLICKR_API_FORMAT,
                        FLICKR_API_NO_JSON_CALLBACK,
                        FLICKR_API_PAGE_SIZE,
                        FLICKR_API_PAGE,
                        textToSearch
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
