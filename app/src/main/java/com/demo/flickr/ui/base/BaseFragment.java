package com.demo.flickr.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.demo.flickr.R;


public class BaseFragment extends Fragment implements BaseView {

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            if (toolbar != null) {
                if (this instanceof PhotoExploreFragment) {
                    toolbar.setTitle(getString(R.string.title_explore));
                }
            }
        }*/
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showEmptyView() {
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog != null) {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        } else {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.processing_msg));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String msg) {
    }

    protected void fragmentTransition(Fragment fragment, String tag) {
        if (!fragment.isVisible() && getFragmentManager() != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.layout_main, fragment, tag)
                    .addToBackStack(null)
                    .commit();
        }
    }
}