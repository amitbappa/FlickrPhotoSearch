package com.demo.flickr.ui.photo.search;

import android.os.Bundle;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.demo.flickr.BaseApplication;
import com.demo.flickr.R;
import com.demo.flickr.ui.base.BaseFragment;
import com.demo.flickr.ui.custom.RecyclerViewContainer;
import com.demo.flickr.ui.custom.InfiniteScrollListener;
import com.demo.flickr.ui.photo.search.model.Photo;
import com.demo.flickr.util.AlertUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



public class PhotoSearchFragment extends BaseFragment implements PhotoSearchView {

    private int page = 1;
    private int limit = 15;
    private Unbinder unbinder;
    private PhotoSearchController controller;
    private List<Photo> photos = new ArrayList<>();
    private  String searchText= "flower";
    private   String getSearchText() {
        return searchText;
    }

    @Inject
    Picasso picasso;

    @Inject
    PhotoSearchPresenter presenter;

    @BindView(R.id.swipe_layout)

    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.emptyView)
    View emptyView;

    @BindView(R.id.recyclerView)
    RecyclerViewContainer rvPhotos;

    public static PhotoSearchFragment newInstance() {
        return new PhotoSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseApplication) getActivity().getApplication()).getHomeComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        String myValue = bundle.getString("PhotoSearch");

        if(!TextUtils.isEmpty(myValue)){
            searchText=myValue;
        }

        LinearLayoutManager layoutManager = new GridLayoutManager(context(), 2);
        rvPhotos.setLayoutManager(layoutManager);
        rvPhotos.setHasFixedSize(true);
        rvPhotos.setEmptyView(emptyView);
        rvPhotos.addOnScrollListener(new InfiniteScrollListener(limit, layoutManager) {
            @Override
            public void onScrolledToEnd(int firstVisibleItemPosition) {
                if (!controller.isLoadMore() && !controller.isRetry()) {
                    presenter.fetchMore(++page, limit,getSearchText());
                }
            }
        });

        controller = new PhotoSearchController(this, picasso);
        rvPhotos.setAdapter(controller.getAdapter());

        swipeLayout.setOnRefreshListener(() -> {
            page = 1;
            presenter.fetchPhotos(page, limit,getSearchText());
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.setView(this);
        presenter.fetchPhotos(page, limit,getSearchText());
    }

    @Override
    public void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        rvPhotos.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        page = 1;
        swipeLayout.setRefreshing(true);
        emptyView.setVisibility(View.GONE);
        rvPhotos.setVisibility(View.GONE);
        photos.clear();
    }

    @Override
    public void hideProgressBar() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String msg) {
        AlertUtil.showSnackBar(swipeLayout, msg);
    }

    @Override
    public void showProgress() {
        controller.loadMore(true);
    }

    @Override
    public void hideProgress() {
        controller.loadMore(false);
    }

    @Override
    public void showList(List<Photo> newPhotos) {
        photos.addAll(newPhotos);
        controller.setList(photos);
        emptyView.setVisibility(View.GONE);
        rvPhotos.setVisibility(View.VISIBLE);
        hideLoadMore();
    }

    @Override
    public void fetchMore() {
        presenter.fetchMore(page, limit,getSearchText());
    }

    @Override
    public void showRetry() {
        controller.retry(true);
    }

    @Override
    public void hideLoadMore() {
        controller.retry(false);
        controller.loadMore(false);
    }

    @Override
    public void onItemClicked(View itemView, Photo photo) {

        // Todo check on click now nothing happen

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        page = 1;
        presenter.destroy();
        unbinder.unbind();
    }
}