package org.jakubczyk.demo.flickrdemo.screens.search;

import android.support.v7.widget.RecyclerView;

import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;
import org.jakubczyk.demo.flickrdemo.databinding.SearchResultItemBinding;

class SearchResultItem extends RecyclerView.ViewHolder {

    private final SearchResultItemBinding binding;

    public SearchResultItem(SearchResultItemBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    void bind(Photo photo) {

    }
}
