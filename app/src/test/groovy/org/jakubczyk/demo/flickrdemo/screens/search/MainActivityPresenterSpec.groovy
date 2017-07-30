package org.jakubczyk.demo.flickrdemo.screens.search

import org.jakubczyk.demo.flickrdemo.data.api.json.Photo
import org.jakubczyk.demo.flickrdemo.data.api.json.Photos
import org.jakubczyk.demo.flickrdemo.data.api.json.SearchResponse
import org.jakubczyk.demo.flickrdemo.data.repository.FlickrRepository
import rx.Observable
import spock.lang.Specification

class MainActivityPresenterSpec extends Specification {

    // mocks
    def view = Mock(MainActivityContract.View)
    def flickrRepository = Mock(FlickrRepository)

    // object under test
    MainActivityContract.Presenter presenter

    def "setup"() {
        presenter = new MainActivityPresenter(flickrRepository)
    }

    def "should assign view on create"() {
        when:
        presenter.create(view)

        then:
        presenter.view
    }

    def "should release view on destroy"() {
        given:
        presenter.create(view)

        when:
        presenter.destroy()

        then:
        !presenter.view
    }

    def "should publish searched text"() {
        given:
        def searchStream = Observable.just("hey there")

        and:
        flickrRepository.searchFlickr("hey there") >> Observable.just(buildResponse())

        and:
        presenter.create(view)

        when:
        presenter.observeSearch(searchStream)

        then:
        view.addPhotos(_) >> { args ->
            def photoList = args[0] as List<Photo>

            assert photoList.size() == 2
        }
    }

    def "should not do any query on empty search"() {
        given:
        def searchStream = Observable.just("")

        and:
        presenter.create(view)

        when:
        presenter.observeSearch(searchStream)

        then:
        0 * flickrRepository.searchFlickr(_)
        0 * view.addPhotos(_)
    }

    def buildResponse() {
        def photo1 = new Photo(
                id: "photo1_id"
        )

        def photo2 = new Photo(
                id: "photo2_id"
        )

        def photos = new Photos(
                photoList: [photo1, photo2]
        )

        return new SearchResponse(
                photos: photos
        )
    }
}
