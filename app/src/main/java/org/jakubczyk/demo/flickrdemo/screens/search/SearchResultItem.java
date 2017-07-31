package org.jakubczyk.demo.flickrdemo.screens.search;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;
import org.jakubczyk.demo.flickrdemo.databinding.SearchResultItemBinding;

class SearchResultItem extends RecyclerView.ViewHolder {

    private final SearchResultItemBinding binding;
    private final SearchItemViewModel searchItemViewModel;

    public SearchResultItem(SearchResultItemBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
        searchItemViewModel = new SearchItemViewModel();
        binding.setViewModel(searchItemViewModel);
    }

    void bind(Photo photo) {
        // url template
        // http://farm{farm}.static.flickr.com/{server}/{id}_{secret}.jpg
        String url = String.format(
                "http://farm%s.static.flickr.com/%s/%s_%s_m.jpg",
                photo.farm,
                photo.server,
                photo.id,
                photo.secret
        );

        searchItemViewModel.setUrl(url);
    }
}
