package org.jakubczyk.demo.flickrdemo.screens.search;


import com.paginate.Paginate;

public class SearchResultAdapterCallback implements Paginate.Callbacks {

    private MainActivityContract.Presenter presenter;

    public SearchResultAdapterCallback(MainActivityContract.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void onLoadMore() {
        presenter.loadNextPage();
        // Load next page of data (e.g. network or database)
    }

    @Override
    public boolean isLoading() {
        // Indicate whether new page loading is in progress or not
        return presenter.isLoadingNextPage();
    }

    @Override
    public boolean hasLoadedAllItems() {
        // Indicate whether all data (pages) are loaded or not
        return presenter.hasLoadedAllItems();
    }
}
