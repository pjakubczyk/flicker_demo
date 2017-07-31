package org.jakubczyk.demo.flickrdemo.data.repository

import org.jakubczyk.demo.flickrdemo.BuildConfig
import org.jakubczyk.demo.flickrdemo.data.api.FlickrConnector
import org.jakubczyk.demo.flickrdemo.data.api.json.SearchResponse
import rx.Observable
import rx.schedulers.TestScheduler
import spock.lang.Specification

class FlickrRepositorySpec extends Specification {

    def connector = Mock(FlickrConnector)
    def testScheduler = new TestScheduler()

    def repository = new FlickrRepository(
            connector,
            testScheduler,
            testScheduler
    )

    def "should search flickr"() {
        when:
        repository.searchFlickr("hey there")

        and:
        testScheduler.triggerActions()

        then:
        repository.textToSearch == "hey there"
        repository.currentPage == 1

        and:
        1 * connector.search(
                FlickrRepository.FLICKR_API_METHOD,
                BuildConfig.FLICKR_API_KEY,
                FlickrRepository.FLICKR_API_FORMAT,
                FlickrRepository.FLICKR_API_NO_JSON_CALLBACK,
                FlickrRepository.FLICKR_API_PAGE_SIZE,
                1,
                "hey there"
        ) >> Observable.empty()
    }

    def "should load next page"() {
        given:
        connector.search(_, _, _, _, _, 1, "hey there") >> Observable.empty()
        connector.search(_, _, _, _, _, 2, "hey there") >> Observable.just(new SearchResponse())

        repository.searchFlickr("hey there")

        and:
        testScheduler.triggerActions()

        when:
        repository.loadNext().test()

        and:
        testScheduler.triggerActions()

        then:
        repository.currentPage == 2
    }

}
