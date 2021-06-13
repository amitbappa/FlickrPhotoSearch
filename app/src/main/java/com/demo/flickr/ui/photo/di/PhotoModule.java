package com.demo.flickr.ui.photo.di;



import com.demo.flickr.network.RestService;
import com.demo.flickr.ui.photo.search.PhotoSearchInteractor;
import com.demo.flickr.ui.photo.search.PhotoSearchInteractorImpl;
import com.demo.flickr.ui.photo.search.PhotoSearchPresenter;
import com.demo.flickr.ui.photo.search.PhotoSearchPresenterImpl;


import dagger.Module;
import dagger.Provides;

@Module
public class PhotoModule {

    @Provides
    PhotoSearchInteractor providePhotoExploreInteractor(RestService restService) {
        return new PhotoSearchInteractorImpl(restService);
    }

    @Provides
    PhotoSearchPresenter providePhotoExplorePresenter(PhotoSearchInteractor interactor) {
        return new PhotoSearchPresenterImpl(interactor);
    }


}