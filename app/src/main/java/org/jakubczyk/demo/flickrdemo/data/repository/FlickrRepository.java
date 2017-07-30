package org.jakubczyk.demo.flickrdemo.data.repository;

import android.util.Log;

import org.jakubczyk.demo.flickrdemo.BuildConfig;
import org.jakubczyk.demo.flickrdemo.data.api.FlickrConnector;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FlickrRepository {

    private static final String TAG = "DDD";

    private static final String FLICKR_API_METHOD = "flickr.photos.search";
    private static final String FLICKR_API_FORMAT = "json";
    private static final Integer FLICKR_API_NO_JSON_CALLBACK = 1;


    private FlickrConnector flikrConnector;

    public FlickrRepository(FlickrConnector flikrConnector) {
        this.flikrConnector = flikrConnector;
    }

    public void searchFlickr(String textToSearch) {

        flikrConnector
                .search(
                        FLICKR_API_METHOD,
                        BuildConfig.FLICKR_API_KEY,
                        FLICKR_API_FORMAT,
                        FLICKR_API_NO_JSON_CALLBACK,
                        textToSearch
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted() called");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.d(TAG, "onNext() called with: responseBody = [" + responseBody + "]");
                    }
                });

    }
}
