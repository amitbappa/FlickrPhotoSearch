package com.demo.flickr.ui.base;

import android.view.View;

import androidx.annotation.CallSuper;

import com.airbnb.epoxy.EpoxyHolder;

import butterknife.ButterKnife;

public abstract class BaseHolder extends EpoxyHolder {

    private View itemView;

    @CallSuper
    @Override
    protected void bindView(View itemView) {
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public View getItemView() {
        return itemView;
    }
}