package org.jakubczyk.demo.flickrdemo.data.api;

import org.jakubczyk.demo.flickrdemo.data.api.json.SearchResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrConnector {


    @GET("/services/rest/")
    Observable<SearchResponse> search(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") Integer noJsonCallback,
            @Query("per_page") Integer perPage,
            @Query("page") Integer page,
            @Query("text") String text
    );

}
