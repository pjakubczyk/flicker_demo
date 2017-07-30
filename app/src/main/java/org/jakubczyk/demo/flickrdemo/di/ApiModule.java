package org.jakubczyk.demo.flickrdemo.di;

import org.jakubczyk.demo.flickrdemo.BuildConfig;
import org.jakubczyk.demo.flickrdemo.data.api.FlickrConnector;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // logging BODY leads to StrictMode complaining about request.body().closeScreen() not being called
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG)
            builder.addInterceptor(logging);

        return builder.build();
    }

    @Provides
    FlickrConnector provideFlickrConnector(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.flickr.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(FlickrConnector.class);
    }
}
