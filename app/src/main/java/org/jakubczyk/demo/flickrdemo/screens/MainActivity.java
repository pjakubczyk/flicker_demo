package org.jakubczyk.demo.flickrdemo.screens;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import org.jakubczyk.demo.flickrdemo.BaseActivity;
import org.jakubczyk.demo.flickrdemo.R;
import org.jakubczyk.demo.flickrdemo.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter presenter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new MainActivityPresenter();
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
    public void showSearchText(CharSequence charSequence) {
        binding.theText.setText(charSequence);
    }
}
