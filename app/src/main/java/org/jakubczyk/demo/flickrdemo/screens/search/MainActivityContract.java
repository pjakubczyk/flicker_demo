package org.jakubczyk.demo.flickrdemo.screens.search;

import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;

import java.util.List;

import rx.Observable;

public class MainActivityContract {

    public interface View {

        void addPhotos(List<Photo> photoList);
    }

    public interface Presenter {

        void create(View view);

        void observeSearch(Observable<CharSequence> charSequenceObservable);

        void destroy();
    }
}
