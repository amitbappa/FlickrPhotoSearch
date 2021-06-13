package com.demo.flickr.ui.photo.search;


import androidx.annotation.NonNull;

import com.airbnb.epoxy.AutoModel;

import com.demo.flickr.ui.custom.LoadMoreModel_;
import com.demo.flickr.ui.custom.PagingController;
import com.demo.flickr.ui.photo.search.model.Photo;
import com.demo.flickr.ui.photo.search.model.PhotoSize;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.BuildConfig;

public class PhotoSearchController extends PagingController<Photo> {

    @AutoModel
    LoadMoreModel_ loadMoreModel;

    private boolean retry;
    private boolean loadMore;

    private Picasso picasso;
    private PhotoSearchView view;

    PhotoSearchController(PhotoSearchView view, Picasso picasso) {
        this.view = view;
        this.picasso = picasso;
        setFilterDuplicates(true);
        setDebugLoggingEnabled(BuildConfig.DEBUG);
    }

    void retry(boolean retry) {
        this.retry = retry;
        requestModelBuild();
    }

    boolean isRetry() {
        return this.retry;
    }

    void loadMore(boolean loadMore) {
        this.loadMore = loadMore;
        requestModelBuild();
    }

    boolean isLoadMore() {
        return this.loadMore;
    }

    @Override
    protected void buildModels(@NonNull List<Photo> photos) {
        for (Photo item : photos) {
            new PhotoExploreItemModel_()
                    .id(item.getId())
                    .view(view)
                    .picasso(picasso)
                    .title(item.getTitle())
                    .url(item.getUrl(PhotoSize.MEDIUM))
                    .clickListener(itemView -> view.onItemClicked(itemView, item))
                    .addTo(this);
        }

        loadMoreModel
                .retry(retry)
                .clickListener(itemView -> view.fetchMore())
                .addIf(loadMore || retry, this);
    }
}