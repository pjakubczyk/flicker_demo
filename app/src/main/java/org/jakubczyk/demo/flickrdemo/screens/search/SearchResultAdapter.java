package org.jakubczyk.demo.flickrdemo.screens.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.jakubczyk.demo.flickrdemo.databinding.SearchResultItemBinding;

class SearchResultAdapter extends RecyclerView.Adapter<SearchResultItem> {

    private MainActivityContract.Presenter presenter;

    public SearchResultAdapter(MainActivityContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public SearchResultItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultItem(SearchResultItemBinding.inflate(
                LayoutInflater.from(parent.getContext())

        ));
    }

    @Override
    public void onBindViewHolder(SearchResultItem holder, int position) {
        holder.bind(presenter.getItemAtPosition(position));
    }

    @Override
    public int getItemCount() {
        return presenter.getItemsCount();
    }
}
