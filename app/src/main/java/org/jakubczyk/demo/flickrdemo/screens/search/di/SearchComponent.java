package org.jakubczyk.demo.flickrdemo.screens.search.di;

import org.jakubczyk.demo.flickrdemo.di.AppComponent;
import org.jakubczyk.demo.flickrdemo.di.ThreadingModule;
import org.jakubczyk.demo.flickrdemo.screens.search.MainActivityContract;

import dagger.Component;

@Component(modules = {SearchModule.class, ThreadingModule.class}, dependencies = AppComponent.class)
public interface SearchComponent {

    MainActivityContract.Presenter getPresenter();
}
