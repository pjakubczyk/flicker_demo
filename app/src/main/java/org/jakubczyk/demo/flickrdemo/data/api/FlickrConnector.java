package org.jakubczyk.demo.flickrdemo.data.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrConnector {


    @GET("/services/rest/")
    Observable<ResponseBody> search(
            @Query("method") String method,
            @Query("api_key") String apiKey,
            @Query("format") String format,
            @Query("nojsoncallback") Integer noJsonCallback,
            @Query("text") String text
    );

}
