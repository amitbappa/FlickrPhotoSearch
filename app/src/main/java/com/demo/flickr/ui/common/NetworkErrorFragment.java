package com.demo.flickr.ui.common;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.flickr.R;
import com.demo.flickr.ui.base.BaseFragment;


public class NetworkErrorFragment extends BaseFragment {

    public static NetworkErrorFragment newInstance() {
        return new NetworkErrorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.fragment_network_error, container, false);
        return dialogView;
    }
}