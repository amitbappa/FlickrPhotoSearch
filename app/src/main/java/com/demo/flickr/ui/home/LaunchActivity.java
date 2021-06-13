package com.demo.flickr.ui.home;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.demo.flickr.R;
import com.demo.flickr.ui.base.HomeBase;
import com.demo.flickr.ui.photo.search.PhotoSearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.demo.flickr.util.Constants.SEARCH_FRAGMENT;
import static com.demo.flickr.util.Constants.INT_CONST_FOR_EXPLORE_FRAGMENT;


public class LaunchActivity extends HomeBase {

    private Fragment currentFragment;
    private SparseArray<Fragment> fragmentStore = new SparseArray<>();


    @BindView(R.id.layout_main)
    FrameLayout layoutMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        showLandingScreen("flower");// Default search with "flower" while loading on screen
    }


    private void showLandingScreen(String photoSearch) {
        Bundle bundle = new Bundle();
        bundle.putString("PhotoSearch", photoSearch);
        currentFragment = fragmentStore.get(INT_CONST_FOR_EXPLORE_FRAGMENT);
        if (currentFragment == null) {
            currentFragment = PhotoSearchFragment.newInstance();
            fragmentStore.put(INT_CONST_FOR_EXPLORE_FRAGMENT, currentFragment);
        }

        currentFragment.setArguments(bundle);
        fragmentTransition(currentFragment, SEARCH_FRAGMENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView searchView;
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                fragmentStore.clear();
                showLandingScreen(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}