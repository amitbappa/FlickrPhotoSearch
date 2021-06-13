package com.demo.flickr.ui.photo.search;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhotoSearchPresenterImpl extends PhotoSearchPresenter {

    private PhotoSearchView view;
    private PhotoSearchInteractor interactor;

    public PhotoSearchPresenterImpl(PhotoSearchInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void setView(PhotoSearchView view) {
        super.setView(view);
        this.view = view;
    }

      @Override
    void fetchPhotos(int page, int limit, String searchPhoto) {
        showProgressBar();

        disposable = interactor.getSearchPhotosList(page, limit, searchPhoto)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(photos -> {
                    hideProgressBar();
                    if (isViewAttached()) {
                        view.showList(photos);
                    }
                }, error -> {
                    hideProgressBar();
                    showMessage(error.getMessage());
                    showEmptyView();
                });
    }


    void fetchMore(int page, int limit, String searchPhoto) {
        showProgress();

        disposable = interactor.getSearchPhotosList(page, limit,searchPhoto)
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(newPhotos -> {
                    hideProgress();

                    if (isViewAttached()) {
                        view.showList(newPhotos);
                    }
                }, error -> {
                    if (isViewAttached()) {
                        view.showRetry();
                    }
                    hideProgress();
                    showMessage(error.getMessage());
                });
    }

    private void showProgress() {
        if (isViewAttached()) {
            view.showProgress();
        }
    }

    private void hideProgress() {
        if (isViewAttached()) {
            view.hideProgress();
        }
    }
}