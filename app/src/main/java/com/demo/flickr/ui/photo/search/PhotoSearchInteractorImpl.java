package com.demo.flickr.ui.photo.search;



import com.demo.flickr.network.RestService;
import com.demo.flickr.ui.photo.search.model.Photo;
import com.demo.flickr.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;

public class PhotoSearchInteractorImpl implements PhotoSearchInteractor {

    private RestService restService;

    public PhotoSearchInteractorImpl(RestService restService) {
        this.restService = restService;
    }


    @Override
    public Single<List<Photo>> getSearchPhotosList(int page, int limit, String searchText) {

        Map<String, String> query = new HashMap<>();
        query.put("api_key", Constants.FLICKR_KEY);
        query.put("per_page", String.valueOf(limit));
        query.put("page", String.valueOf(page));
        query.put("text", searchText);


        return restService.search(query)
                .map(PhotoSearchParser::parse);
    }
}