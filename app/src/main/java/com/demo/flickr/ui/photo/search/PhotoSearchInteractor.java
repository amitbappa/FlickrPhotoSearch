package com.demo.flickr.ui.photo.search;


import com.demo.flickr.ui.photo.search.model.Photo;

import java.util.List;

import io.reactivex.Single;

public interface PhotoSearchInteractor {


    Single<List<Photo>> getSearchPhotosList(int page, int limit, String searchText);

}