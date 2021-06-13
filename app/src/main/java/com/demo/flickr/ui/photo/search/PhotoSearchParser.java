package com.demo.flickr.ui.photo.search;


import androidx.annotation.NonNull;

import com.demo.flickr.ui.photo.search.model.Photo;
import com.demo.flickr.ui.photo.search.model.PhotoSearchResponse;

import java.util.List;

import retrofit2.Response;

class PhotoSearchParser {

    @NonNull
    static List<Photo> parse(Response<PhotoSearchResponse> response) throws NullPointerException {

        if (response.isSuccessful()) {
            PhotoSearchResponse body = response.body();
            if (body.getStat().equalsIgnoreCase("ok")) {
                List<Photo> photos = body.getPhotos().getPhoto();
                if (photos != null && !photos.isEmpty()) {
                    return photos;
                } else {
                    throw new RuntimeException("Response payload is empty!");
                }
            } else {
                throw new RuntimeException(body.getMsg());
            }
        } else {
            throw new RuntimeException(response.message());
        }
    }
}