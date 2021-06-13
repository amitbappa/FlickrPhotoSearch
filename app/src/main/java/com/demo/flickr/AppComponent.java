package com.demo.flickr;



import com.demo.flickr.data.prefs.PrefsModule;
import com.demo.flickr.media.MediaModule;
import com.demo.flickr.network.NetworkModule;
import com.demo.flickr.ui.home.di.HomeComponent;
import com.demo.flickr.ui.photo.di.PhotoModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, PrefsModule.class, MediaModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(BaseApplication application);

        Builder appModule(AppModule appModule);

        Builder networkModule(NetworkModule networkModule);

        Builder prefsModule(PrefsModule prefsModule);

        Builder mediaModule(MediaModule mediaModule);

        AppComponent build();
    }

    HomeComponent plus(PhotoModule photoModule);
}