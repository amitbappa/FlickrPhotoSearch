package com.demo.flickr.ui.home.di;


import com.demo.flickr.ui.base.HomeBase;
import com.demo.flickr.ui.photo.di.PhotoModule;
import com.demo.flickr.ui.photo.search.PhotoSearchFragment;

import dagger.Subcomponent;

@HomeScope
@Subcomponent(modules = {PhotoModule.class})
public interface HomeComponent {

    void inject(HomeBase activity);

    void inject(PhotoSearchFragment fragment);


}