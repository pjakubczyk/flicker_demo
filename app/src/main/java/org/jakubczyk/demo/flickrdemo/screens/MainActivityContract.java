package org.jakubczyk.demo.flickrdemo.screens;

import rx.Observable;

public class MainActivityContract {

    public interface View {

        void showSearchText(CharSequence charSequence);
    }

    public interface Presenter {

        void create(View view);

        void observeSearch(Observable<CharSequence> charSequenceObservable);

        void destroy();
    }
}
