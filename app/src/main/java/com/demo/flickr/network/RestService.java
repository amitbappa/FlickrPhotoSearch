package com.demo.flickr.network;



import com.demo.flickr.ui.photo.search.model.PhotoSearchResponse;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface RestService {
    /**
     * Flicker search of photo API
     * @param query
     * @return
     */
    @Headers({"content-type: application/json"})
    @GET("/services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&safe_search=1")
    Single<Response<PhotoSearchResponse>> search(@QueryMap Map<String, String> query);

}