package org.jakubczyk.demo.flickrdemo.screens.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import org.jakubczyk.demo.flickrdemo.BaseActivity;
import org.jakubczyk.demo.flickrdemo.R;
import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;
import org.jakubczyk.demo.flickrdemo.databinding.ActivityMainBinding;
import org.jakubczyk.demo.flickrdemo.databinding.SearchResultItemBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter presenter;
    private ActivityMainBinding binding;
    private SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchResultList.setLayoutManager(new GridLayoutManager(this, 3));

        searchResultAdapter = new SearchResultAdapter();
        binding.searchResultList.setAdapter(searchResultAdapter);

        presenter = new MainActivityPresenter(component.getFlickrRepository());
        presenter.create(this);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        presenter.observeSearch(RxSearchView.queryTextChanges(searchView));

        return true;
    }

    @Override
    public void addPhotos(List<Photo> photoList) {
        searchResultAdapter.addItems(photoList);
    }

    static class SearchResultItem extends RecyclerView.ViewHolder {

        private final SearchResultItemBinding binding;

        public SearchResultItem(SearchResultItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        void bind(Photo photo) {

        }
    }


    static class SearchResultAdapter extends RecyclerView.Adapter<SearchResultItem> {

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
}
