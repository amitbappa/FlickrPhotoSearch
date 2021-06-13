package com.demo.flickr.ui.photo.search;

import android.view.View;


import com.demo.flickr.ui.base.BaseView;
import com.demo.flickr.ui.photo.search.model.Photo;

import java.util.List;

interface PhotoSearchView extends BaseView {

    void showProgress();

    void hideProgress();

    void showList(List<Photo> photos);

    void fetchMore();

    void showRetry();

    void hideLoadMore();

    void onItemClicked(View itemView, Photo photo);

}