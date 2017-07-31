package org.jakubczyk.demo.flickrdemo.screens.search

import org.jakubczyk.demo.flickrdemo.data.api.json.Photo
import org.jakubczyk.demo.flickrdemo.data.api.json.Photos
import org.jakubczyk.demo.flickrdemo.data.api.json.SearchResponse
import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository
import rx.Observable
import rx.schedulers.TestScheduler
import spock.lang.Specification

class SearchActivityPresenterSpec extends Specification {

    // mocks
    def view = Mock(SearchActivityContract.View)
    def flickrRepository = Mock(FlickrRepository)

    def testScheduler = new TestScheduler()

    // object under test
    SearchActivityContract.Presenter presenter

    def "setup"() {
        presenter = new SearchActivityPresenter(flickrRepository, testScheduler)
    }

    def "should assign view on create"() {
        when:
        presenter.create(view)

        then:
        presenter.view
    }

    def "should show empty UI on create"() {
        when:
        presenter.create(view)

        then:
        1 * view.showEmpty()
    }

    def "should release view on destroy"() {
        given:
        presenter.create(view)

        when:
        presenter.destroy()

        then:
        !presenter.view
    }

    def "should update list on search result"() {
        given:
        def searchStream = Observable.just("hey there")

        and:
        presenter.create(view)

        when:
        presenter.observeSearch(searchStream)

        and:
        testScheduler.triggerActions()

        then:
        1 * flickrRepository.searchFlickr("hey there") >> Observable.just(buildResponse())

        and:
        1 * view.refreshList()

        and:
        1 * view.showList()
    }

    def "should add new photos to collection"() {
        given:
        def searchStream = Observable.just("hey there")

        and:
        flickrRepository.searchFlickr("hey there") >> Observable.just(buildResponse())

        and:
        presenter.create(view)

        when:
        presenter.observeSearch(searchStream)

        and:
        testScheduler.triggerActions()

        then:
        presenter.getItemsCount() == 2

        and:
        presenter.getItemAtPosition(0).id == "photo1_id"
        presenter.getItemAtPosition(1).id == "photo2_id"
    }

    def "should not do any query on empty search"() {
        given:
        def searchStream = Observable.just("")

        and:
        presenter.create(view)

        when:
        presenter.observeSearch(searchStream)

        then:
        0 * flickrRepository.searchFlickr(_, _)
        0 * view.addPhotos(_)
    }

    def "should not show list if response has empty array"() {
        given:
        def searchStream = Observable.just("hey there")

        and:
        flickrRepository.searchFlickr("hey there") >> Observable.just(buildEmptyResponse())

        and:
        presenter.create(view)

        when:
        presenter.observeSearch(searchStream)

        and:
        testScheduler.triggerActions()

        then: "one for on create, one for observe"
        2 * view.showEmpty()
    }

    def "should return loading status"() {
        given:
        presenter.isLoadingNextPage = true

        expect:
        presenter.isLoadingNextPage()
    }

    def "should return if all items are loaded"() {
        given:
        presenter.hasLoadedAllItems = false

        expect:
        !presenter.hasLoadedAllItems()
    }

    def "should schedule next page to load"() {
        given:
        presenter.create(view)

        and:
        flickrRepository.loadNext() >> Observable.just(buildResponse())

        when:
        presenter.loadNextPage()

        and:
        testScheduler.triggerActions()

        then:
        1 * view.showList()
    }

    def "should set hasLoaded to true for last page"() {
        given:
        flickrRepository.searchFlickr("hey there") >> Observable.just(buildResponse())
        flickrRepository.loadNext() >> Observable.just(buildLastPageResponse())
        flickrRepository.getCurrentPage() >>> [1, 2]

        and:
        presenter.create(view)

        and:
        presenter.observeSearch(Observable.just("hey there"))

        and:
        testScheduler.triggerActions()

        assert !presenter.hasLoadedAllItems()

        when:
        presenter.loadNextPage()

        and:
        testScheduler.triggerActions()

        then:
        presenter.getItemsCount() == 4

        and:
        presenter.hasLoadedAllItems()
    }

    def "should check if first page can be last page"() {
        given:
        flickrRepository.searchFlickr("hey there") >> Observable.just(buildFirstPageAsLastPageResponse())
        flickrRepository.getCurrentPage() >> 1

        and:
        presenter.create(view)

        when:
        presenter.observeSearch(Observable.just("hey there"))

        and:
        testScheduler.triggerActions()

        then:
        presenter.getItemsCount() == 2

        and:
        presenter.hasLoadedAllItems()
    }

    def "should clear old data before querying for another results"() {
        given:
        flickrRepository.searchFlickr("hey there") >> Observable.just(buildResponse())
        flickrRepository.searchFlickr("other") >> Observable.just(buildThreeItemResponse())

        and:
        presenter.create(view)

        when: "first search"
        presenter.observeSearch(Observable.just("hey there"))

        and: "second search"
        presenter.observeSearch(Observable.just("other"))

        and:
        testScheduler.triggerActions()

        then:
        presenter.getItemsCount() == 3

        and:
        presenter.getItemAtPosition(1).id == "photo12_id"
    }

    def "should add new search subscription to composite subscription"() {
        given:
        presenter.create(view)

        and: "ensure nothing's there"
        assert !presenter.compositeSubscription.hasSubscriptions()

        when:
        presenter.observeSearch(Observable.just("hey there"))

        then:
        presenter.compositeSubscription.hasSubscriptions()
    }

    def "should add load next page subscription to composite subscription"() {
        given:
        presenter.create(view)

        and:
        flickrRepository.loadNext() >> Observable.just(buildLastPageResponse())

        and: "ensure nothing's there"
        assert !presenter.compositeSubscription.hasSubscriptions()

        when:
        presenter.loadNextPage()

        then:
        presenter.compositeSubscription.hasSubscriptions()
    }

    def "should clear composite subscription on destroy"() {
        given:
        presenter.create(view)

        and: "place some subscription"
        presenter.observeSearch(Observable.just("hey there"))

        when:
        presenter.destroy()

        then:
        !presenter.compositeSubscription.hasSubscriptions()
    }

    // test data
    def buildResponse() {
        def photo1 = new Photo(
                id: "photo1_id"
        )

        def photo2 = new Photo(
                id: "photo2_id"
        )

        def photos = new Photos(
                photoList: [photo1, photo2],
                totalPages: 2
        )

        return new SearchResponse(
                photos: photos
        )
    }

    def buildThreeItemResponse() {
        def photo1 = new Photo(
                id: "photo11_id"
        )

        def photo2 = new Photo(
                id: "photo12_id"
        )

        def photo3 = new Photo(
                id: "photo13_id"
        )

        def photos = new Photos(
                photoList: [photo1, photo2, photo3],
                totalPages: 2
        )

        return new SearchResponse(
                photos: photos
        )
    }


    def buildFirstPageAsLastPageResponse() {
        def photo1 = new Photo(
                id: "photo3_id"
        )

        def photo2 = new Photo(
                id: "photo4_id"
        )

        def photos = new Photos(
                photoList: [photo1, photo2],
                totalPages: 1
        )

        return new SearchResponse(
                photos: photos
        )
    }


    def buildLastPageResponse() {
        def photo1 = new Photo(
                id: "photo3_id"
        )

        def photo2 = new Photo(
                id: "photo4_id"
        )

        def photos = new Photos(
                photoList: [photo1, photo2],
                totalPages: 2
        )

        return new SearchResponse(
                photos: photos
        )
    }

    def buildEmptyResponse() {
        def photos = new Photos(
                photoList: []
        )

        return new SearchResponse(
                photos: photos
        )
    }
}
