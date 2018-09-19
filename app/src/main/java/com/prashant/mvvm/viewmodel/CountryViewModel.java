package com.prashant.mvvm.viewmodel;
/**
 * @author : Prashant P
 *
 * Created in 2018 as an unpublished copyright work.
 * All rights reserved.
 */
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.prashant.mvvm.R;
import com.prashant.mvvm.api.ApiInterface;
import com.prashant.mvvm.app.AppController;
import com.prashant.mvvm.model.Country;
import com.prashant.mvvm.model.Row;
import com.prashant.mvvm.utils.network.ConnectivityReceiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CountryViewModel extends Observable {

    private List<Row> mRowList;
    private String mTitle;
    private Context mContext;
    public ObservableInt mCountryRecycler;
    public ObservableInt mLayoutErrorView;
    public ObservableField<String> messageLabel;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ObservableBoolean isLoading;

    public CountryViewModel(@NonNull Context context) {
        this.mContext = context;
        this.mRowList = new ArrayList<Row>();
        isLoading = new ObservableBoolean();
        mCountryRecycler = new ObservableInt(View.VISIBLE);
        mLayoutErrorView = new ObservableInt(View.GONE);
        messageLabel = new ObservableField<>(context.getString(R.string.something_went_wrong));
        initialize();
    }

    public void onClickToLoad() {
        initialize();
    }


    public void initialize() {
        mCountryRecycler.set(View.VISIBLE);
        mLayoutErrorView.set(View.GONE);
        fetchUsersList();
    }

    private void fetchUsersList() {
        isLoading.set(true);
        AppController appController = AppController.create(mContext);
        ApiInterface apiInterface = appController.getUserService();

        Disposable disposable = apiInterface.getResult()
                .subscribeOn(appController.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Country>() {
                    @Override
                    public void accept(Country userResponse) throws Exception {
                        isLoading.set(false);
                        if (userResponse != null) {
                            mCountryRecycler.set(View.VISIBLE);
                            updateTitle(userResponse.getTitle());
                            updateUserDataList(userResponse.getRows());

                        } else {
                            mCountryRecycler.set(View.GONE);
                            mLayoutErrorView.set(View.VISIBLE);
                            messageLabel.set(mContext.getString(R.string.something_went_wrong));
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        isLoading.set(false);

                        if (!ConnectivityReceiver.isConnected()) {
                            messageLabel.set(mContext.getString(R.string.not_connected_to_internet));
                        } else {
                            messageLabel.set(mContext.getString(R.string.something_went_wrong));
                        }

                        mCountryRecycler.set(View.GONE);
                        mLayoutErrorView.set(View.VISIBLE);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private void updateUserDataList(List<Row> peoples) {
        mRowList.addAll(peoples);
        setChanged();
        notifyObservers();
    }

    private void updateTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<Row> getRowList() {
        return mRowList;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void clearAll() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        mContext = null;
    }


    public void onRefresh() {

        initialize();
    }


}
