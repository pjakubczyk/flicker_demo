package org.jakubczyk.demo.flickrdemo.screens.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import org.jakubczyk.demo.flickrdemo.R;
import org.jakubczyk.demo.flickrdemo.data.api.json.Photo;
import org.jakubczyk.demo.flickrdemo.databinding.ActivityMainBinding;
import org.jakubczyk.demo.flickrdemo.screens.BaseActivity;

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


}
