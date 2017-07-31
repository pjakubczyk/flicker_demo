package org.jakubczyk.demo.flickrdemo.data.repository;

import org.jakubczyk.demo.flickrdemo.BuildConfig;
import org.jakubczyk.demo.flickrdemo.data.api.FlickrConnector;
import org.jakubczyk.demo.flickrdemo.data.api.json.SearchResponse;

import rx.Observable;
import rx.Scheduler;

public class FlickrRepository {

    private static final String TAG = "DDD";

    private static final String FLICKR_API_METHOD = "flickr.photos.search";
    private static final String FLICKR_API_FORMAT = "json";
    private static final Integer FLICKR_API_NO_JSON_CALLBACK = 1;
    //    private static final Integer FLICKR_API_PAGE = 1;
    private static final Integer FLICKR_API_PAGE_SIZE = 20;

    private final FlickrConnector flickrConnector;
    private final Scheduler ioScheduler;
    private final Scheduler mainScheduler;

    String textToSearch = "";
    Integer currentPage = 1;

    public FlickrRepository(FlickrConnector flickrConnector, Scheduler ioScheduler, Scheduler mainScheduler) {
        this.flickrConnector = flickrConnector;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    public Observable<SearchResponse> searchFlickr(String textToSearch) {
        this.textToSearch = textToSearch;
        this.currentPage = 1;

        return flickrConnector
                .search(
                        FLICKR_API_METHOD,
                        BuildConfig.FLICKR_API_KEY,
                        FLICKR_API_FORMAT,
                        FLICKR_API_NO_JSON_CALLBACK,
                        FLICKR_API_PAGE_SIZE,
                        currentPage,
                        textToSearch
                )
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler);
    }

    public Observable<SearchResponse> loadNext() {
        int pageToSearch = currentPage + 1;

        return flickrConnector
                .search(
                        FLICKR_API_METHOD,
                        BuildConfig.FLICKR_API_KEY,
                        FLICKR_API_FORMAT,
                        FLICKR_API_NO_JSON_CALLBACK,
                        FLICKR_API_PAGE_SIZE,
                        pageToSearch,
                        textToSearch
                )
                .doOnNext(searchResponse -> currentPage = pageToSearch)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler);
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
}
