package org.jakubczyk.demo.flickrdemo.screens.search;


import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;
import org.jakubczyk.demo.flickrdemo.data.api.json.SearchResponse;
import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class SearchActivityPresenter implements SearchActivityContract.Presenter {

    // We don't know what is going to be size of the list
    private List<Photo> photos = new LinkedList<>();

    private SearchActivityContract.View view;
    private FlickrRepository flickrRepository;
    private Scheduler mainScheduler;

    CompositeSubscription compositeSubscription = new CompositeSubscription();

    public SearchActivityPresenter(
            FlickrRepository flickrRepository,
            Scheduler mainScheduler
    ) {
        this.flickrRepository = flickrRepository;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void create(SearchActivityContract.View view) {
        this.view = view;

        this.view.showEmpty();
    }

    @Override
    public void destroy() {
        view = null;
        compositeSubscription.unsubscribe();
    }

    @Override
    public void observeSearch(Observable<CharSequence> charSequenceObservable) {
        Subscription searchSubscription = charSequenceObservable
                // don't flood with requests
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(mainScheduler)
                // it means user deleted all text
                .doOnNext(charSequence -> {
                    photos.clear();
                    shouldShowList();
                })
                .filter(textToSearch -> textToSearch.length() > 0)
                .flatMap(textToSearch -> flickrRepository.searchFlickr(textToSearch.toString()))
                .map(this::processResponse)
                .subscribe(this::handleNewData);

        compositeSubscription.add(searchSubscription);
    }

    void shouldShowList() {
        if (photos.size() == 0) {
            view.showEmpty();
        } else {
            view.showList();
            view.refreshList();
        }
    }

    void handleNewData(List<Photo> photoList) {
        isLoadingNextPage = false;

        photos.addAll(photoList);

        shouldShowList();
    }

    // Adapter
    @Override
    public Photo getItemAtPosition(int position) {
        return photos.get(position);
    }

    @Override
    public int getItemsCount() {
        return photos.size();
    }

    // Pagination

    boolean isLoadingNextPage = false;

    boolean hasLoadedAllItems = true;

    @Override
    public void loadNextPage() {
        isLoadingNextPage = true;

        Subscription loadMoreSubscription = flickrRepository
                .loadNext()
                .map(this::processResponse)
                .observeOn(mainScheduler)
                .subscribe(this::handleNewData);

        compositeSubscription.add(loadMoreSubscription);
    }

    private List<Photo> processResponse(SearchResponse searchResponse) {
        hasLoadedAllItems = searchResponse.photos.totalPages == flickrRepository.getCurrentPage();
        return searchResponse.photos.photoList;
    }

    @Override
    public boolean isLoadingNextPage() {
        return isLoadingNextPage;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return hasLoadedAllItems;
    }
}
