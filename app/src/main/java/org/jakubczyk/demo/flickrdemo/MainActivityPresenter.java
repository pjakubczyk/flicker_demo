package org.jakubczyk.demo.flickrdemo;


import rx.Observable;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View view;

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
        // TODO: print searched text on screen
    }

}
