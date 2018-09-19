package com.prashant.mvvm.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prashant.mvvm.R;
import com.prashant.mvvm.databinding.FragmentMainCountryBinding;
import com.prashant.mvvm.model.Row;
import com.prashant.mvvm.utils.ListUtils;
import com.prashant.mvvm.view.activity.MainActivity;
import com.prashant.mvvm.view.adapter.CountryRowAdapter;
import com.prashant.mvvm.viewmodel.CountryViewModel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MainCountryFragment extends Fragment implements Observer {

    private FragmentMainCountryBinding mBinding;
    private CountryViewModel mCountryViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_country, container, false);
        View mView = mBinding.getRoot();

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDataBinding();
    }

    private void initDataBinding() {
        mCountryViewModel = new CountryViewModel(getActivity());
        mBinding.setCountryViewModel(mCountryViewModel);
        setUpListOfUsersView(mBinding.recyclerView);
        setUpObserver(mCountryViewModel);
        // setSupportActionBar(userActivityBinding.toolbar);

    }

    // set up the list of user with recycler view
    private void setUpListOfUsersView(RecyclerView mRecyclerView) {
        CountryRowAdapter mAdapter = new CountryRowAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void setUpObserver(Observable observable) {
        observable.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof CountryViewModel) {
            CountryViewModel mCountryViewModel = (CountryViewModel) o;
            CountryRowAdapter mCountryRowAdapter = (CountryRowAdapter) mBinding.recyclerView.getAdapter();
            setTitleForActionBar(mCountryViewModel.getTitle());
            List<Row> mRowList = mCountryViewModel.getRowList();
            if (mRowList != null) {
                try {
                    mRowList = ListUtils.removeEmptyList(mRowList);
                    mCountryRowAdapter.setRowList(mRowList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    private void setTitleForActionBar(String mTitle) {
        MainActivity mMainActivity = (MainActivity) getActivity();
        mMainActivity.setTitleForActionBar(mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCountryViewModel.clearAll();
    }
}
