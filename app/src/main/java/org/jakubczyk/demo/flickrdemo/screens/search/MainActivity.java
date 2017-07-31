package org.jakubczyk.demo.flickrdemo.screens.search;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.paginate.Paginate;

import org.jakubczyk.demo.flickrdemo.R;
import org.jakubczyk.demo.flickrdemo.databinding.ActivityMainBinding;
import org.jakubczyk.demo.flickrdemo.screens.BaseActivity;


public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter presenter;
    private ActivityMainBinding binding;
    private SearchResultAdapter searchResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        presenter = new MainActivityPresenter(component.getFlickrRepository());

        setContentView(binding.getRoot());

        searchResultAdapter = new SearchResultAdapter(presenter);
        binding.searchResultList.setLayoutManager(new GridLayoutManager(this, 3));
        binding.searchResultList.setAdapter(searchResultAdapter);

        Paginate.with(binding.searchResultList, new SearchResultAdapterCallback(presenter))
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .build();

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
    public void refreshList() {
        searchResultAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmpty() {
        binding.searchResultList.setVisibility(View.GONE);
        binding.listEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList() {
        binding.searchResultList.setVisibility(View.VISIBLE);
        binding.listEmpty.setVisibility(View.GONE);
    }

}