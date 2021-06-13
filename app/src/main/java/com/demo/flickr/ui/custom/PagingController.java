package com.demo.flickr.ui.custom;




import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;

import com.airbnb.epoxy.EpoxyController;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyViewHolder;

import java.util.Collections;
import java.util.List;


public abstract class PagingController<T> extends EpoxyController {
  private static final int DEFAULT_PAGE_SIZE_HINT = 10;
  private static final int DEFAULT_NUM_PAGES_T0_LOAD = 10;

  @Nullable
  private PagedList<T> pagedList;
  @NonNull
  private List<T> list = Collections.emptyList();

  private int pageSizeHint = DEFAULT_PAGE_SIZE_HINT;
  private int numPagesToLoad = DEFAULT_NUM_PAGES_T0_LOAD;

  private int lastBoundPositionWithinList = 0;
  private boolean scrollingTowardsEnd = true;
  private int numBoundModels;
  private int lastBuiltLowerBound = 0;
  private int lastBuiltUpperBound = 0;


  public void setPageSizeHint(int pageSizeHint) {
    this.pageSizeHint = pageSizeHint;
  }


  public void setNumPagesToLoad(int numPagesToLoad) {
    this.numPagesToLoad = numPagesToLoad;
  }

  @Override
  protected final void buildModels() {
    int numListItemsToUse =
        numBoundModels != 0 ? numBoundModels * numPagesToLoad : pageSizeHint * numPagesToLoad;

    // If we are scrolling towards one end of the list we can build slightly more models in that
    // direction in anticipation of needing to show more there soon
    float ratioOfEndItems = scrollingTowardsEnd ? .6f : .4f;

    int itemsToBuildTowardsEnd = (int) (numListItemsToUse * ratioOfEndItems);
    int itemsToBuildTowardsStart = numListItemsToUse - itemsToBuildTowardsEnd;

    int numItemsUntilEnd = list.size() - lastBoundPositionWithinList - 1;
    int leftOverItemsAtEnd = itemsToBuildTowardsEnd - numItemsUntilEnd;
    if (leftOverItemsAtEnd > 0) {
      itemsToBuildTowardsStart += leftOverItemsAtEnd;
      itemsToBuildTowardsEnd -= leftOverItemsAtEnd;
    }

    int numItemsUntilStart = lastBoundPositionWithinList;
    int leftOverItemsAtStart = itemsToBuildTowardsStart - numItemsUntilStart;
    if (leftOverItemsAtStart > 0) {
      itemsToBuildTowardsStart -= leftOverItemsAtStart;
      itemsToBuildTowardsEnd += leftOverItemsAtStart;
    }

    lastBuiltLowerBound = Math.max(lastBoundPositionWithinList - itemsToBuildTowardsStart, 0);
    lastBuiltUpperBound =
        Math.min(lastBoundPositionWithinList + itemsToBuildTowardsEnd, list.size());
    buildModels(list.subList(lastBuiltLowerBound, lastBuiltUpperBound));
  }


  protected abstract void buildModels(@NonNull List<T> list);

  @CallSuper
  @Override
  protected void onModelBound(@NonNull EpoxyViewHolder holder, @NonNull EpoxyModel<?> boundModel,
                              int positionWithinCurrentModels, @Nullable EpoxyModel<?> previouslyBoundModel) {

    int positionWithinList = positionWithinCurrentModels + lastBuiltLowerBound;

    if (pagedList != null) {
      pagedList.loadAround(positionWithinList);
    }

    scrollingTowardsEnd = lastBoundPositionWithinList < positionWithinCurrentModels;
    lastBoundPositionWithinList = positionWithinList;
    numBoundModels++;

    int prefetchDistance = numBoundModels;
    int currentModelCount = getAdapter().getItemCount();
    if (((currentModelCount - positionWithinCurrentModels - 1 < prefetchDistance)
        || (positionWithinCurrentModels < prefetchDistance && lastBuiltLowerBound != 0))) {
      requestModelBuild();
    }
  }

  @CallSuper
  @Override
  protected void onModelUnbound(@NonNull EpoxyViewHolder holder, @NonNull EpoxyModel<?> model) {
    numBoundModels--;
  }

  public void setList(@Nullable List<T> list) {
    if (list == this.list) {
      return;
    }

    if (pagedList != null) {
      setList((PagedList<T>) null);
    }

    this.list = list == null ? Collections.<T>emptyList() : list;
    requestModelBuild();
  }

  public void setList(@Nullable PagedList<T> list) {
    if (list == this.pagedList) {
      return;
    }
    this.pagedList = list;

    updatePagedListSnapshot();
  }


  @NonNull
  public List<T> getCurrentList() {
    return this.list;
  }

  /**
   * @return The pagedList currently being displayed. Null if one has not been set.
   */
  @Nullable
  public PagedList<T> getPagedList() {
    return this.pagedList;
  }


  private void updatePagedListSnapshot() {
    list = pagedList == null ? Collections.<T>emptyList() : pagedList.snapshot();
    requestModelBuild();
  }
}