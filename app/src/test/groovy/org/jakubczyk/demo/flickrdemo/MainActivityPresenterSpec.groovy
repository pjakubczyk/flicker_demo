package org.jakubczyk.demo.flickrdemo

import rx.Observable
import spock.lang.Specification

class MainActivityPresenterSpec extends Specification {

    // mocks
    def view = Mock(MainActivityContract.View)

    // object under test
    MainActivityContract.Presenter presenter

    def "setup"() {
        presenter = new MainActivityPresenter()
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
        presenter.create(view)

        when:
        presenter.observeSearch(searchStream)

        then:
        1 * view.showSearchText("hey there")
    }
}
