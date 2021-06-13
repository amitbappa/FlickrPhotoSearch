package com.demo.flickr;

import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.demo.flickr.data.event.NetworkEventMessage;
import com.demo.flickr.data.prefs.PrefsModule;
import com.demo.flickr.media.MediaModule;
import com.demo.flickr.network.NetworkModule;
import com.demo.flickr.ui.home.di.HomeComponent;
import com.demo.flickr.ui.photo.di.PhotoModule;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import static com.demo.flickr.util.Constants.EVENT_CONNECTIVITY_CONNECTED;
import static com.demo.flickr.util.Constants.EVENT_CONNECTIVITY_LOST;


public class BaseApplication extends MultiDexApplication {

    private Disposable disposable;
    private AppComponent appComponent;
    private HomeComponent homeComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = createAppComponent();

        if (disposable == null) {
            disposable = ReactiveNetwork.observeNetworkConnectivity(this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(Schedulers.io())
                    .subscribe(connectivity -> {
                        if (connectivity.getState() != NetworkInfo.State.CONNECTED) {
                            EventBus.getDefault().post(new NetworkEventMessage(EVENT_CONNECTIVITY_LOST));
                        } else {
                            EventBus.getDefault().post(new NetworkEventMessage(EVENT_CONNECTIVITY_CONNECTED));
                        }
                    });
        }
    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule())
                .networkModule(new NetworkModule())
                .prefsModule(new PrefsModule())
                .mediaModule(new MediaModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public HomeComponent getHomeComponent() {
        if (homeComponent != null) {
            return homeComponent;
        }
        return createHomeComponent();
    }

    private HomeComponent createHomeComponent() {
        homeComponent = appComponent.plus(new PhotoModule());
        return homeComponent;
    }

    public void releaseHomeComponent() {
        homeComponent = null;
    }
}