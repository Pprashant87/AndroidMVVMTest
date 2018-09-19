package com.prashant.mvvm.app;
/**
 * @author : Prashant P
 *
 * Created in 2018 as an unpublished copyright work.
 * All rights reserved.
 */
import android.app.Application;
import android.content.Context;

import com.prashant.mvvm.api.ApiFactory;
import com.prashant.mvvm.api.ApiInterface;
import com.prashant.mvvm.utils.network.ConnectivityReceiver;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


public class AppController extends Application {

    private ApiInterface apiInterfaceService;
    private Scheduler scheduler;
    private static AppController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    private static AppController get(Context context) {
        return (AppController) context.getApplicationContext();
    }

    public static AppController create(Context context) {
        return AppController.get(context);
    }

    public ApiInterface getUserService() {
        if (apiInterfaceService == null) {
            apiInterfaceService = ApiFactory.create();
        }

        return apiInterfaceService;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setUserService(ApiInterface apiInterfaceService) {
        this.apiInterfaceService = apiInterfaceService;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

}
