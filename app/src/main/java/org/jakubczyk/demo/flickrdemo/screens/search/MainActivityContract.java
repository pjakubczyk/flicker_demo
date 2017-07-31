package org.jakubczyk.demo.flickrdemo.screens.search;

import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;

import rx.Observable;

public interface MainActivityContract {

    interface View {

        void refreshList();

        void showEmpty();

        void showList();
    }

    interface Presenter {

        void create(View view);

        void observeSearch(Observable<CharSequence> charSequenceObservable);

        void destroy();

        Photo getItemAtPosition(int position);

        int getItemsCount();

        void loadNextPage();

        boolean isLoadingNextPage();

        boolean hasLoadedAllItems();
    }
}
