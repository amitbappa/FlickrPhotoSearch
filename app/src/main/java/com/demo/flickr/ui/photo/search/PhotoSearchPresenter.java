package com.demo.flickr.ui.photo.search;


import com.demo.flickr.ui.base.BasePresenter;

public abstract class PhotoSearchPresenter extends BasePresenter<PhotoSearchView> {

    abstract void fetchPhotos(int page, int limit, String searchPhoto);

    abstract void fetchMore(int page, int limit, String searchPhoto);


}