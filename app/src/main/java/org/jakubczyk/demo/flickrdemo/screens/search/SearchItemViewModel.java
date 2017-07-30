package org.jakubczyk.demo.flickrdemo.screens.search;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class SearchItemViewModel extends BaseObservable {

    private String url = "";

    public void setUrl(String url) {
        this.url = url;
        notifyChange();
    }

    @Bindable
    public String getUrl() {
        return url;
    }
}
