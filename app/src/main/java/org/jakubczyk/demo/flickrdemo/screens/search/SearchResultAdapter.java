package org.jakubczyk.demo.flickrdemo.screens.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;
import org.jakubczyk.demo.flickrdemo.databinding.SearchResultItemBinding;

import java.util.ArrayList;
import java.util.List;

class SearchResultAdapter extends RecyclerView.Adapter<SearchResultItem> {

    List<Photo> photoList = new ArrayList<>();

    void addItems(List<Photo> morePhotos) {
        photoList.addAll(morePhotos);
        notifyDataSetChanged();
    }

    @Override
    public SearchResultItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultItem(SearchResultItemBinding.inflate(
                LayoutInflater.from(parent.getContext())

        ));
    }

    @Override
    public void onBindViewHolder(SearchResultItem holder, int position) {
        holder.bind(photoList.get(position));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
