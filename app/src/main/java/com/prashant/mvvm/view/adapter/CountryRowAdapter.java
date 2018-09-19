package com.prashant.mvvm.view.adapter;
/**
 * @author : Prashant P
 *
 * Created in 2018 as an unpublished copyright work.
 * All rights reserved.
 */
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.prashant.mvvm.R;
import com.prashant.mvvm.databinding.ItemRowBinding;
import com.prashant.mvvm.model.Row;
import com.prashant.mvvm.viewmodel.RowItemViewModel;

import java.util.Collections;
import java.util.List;


public class CountryRowAdapter extends RecyclerView.Adapter<CountryRowAdapter.CountryAdapterViewHolder> {

    private List<Row> mRowList;

    public CountryRowAdapter() {
        this.mRowList = Collections.emptyList();
    }


    @NonNull
    @Override
    public CountryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowBinding itemRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_row, parent, false);
        return new CountryAdapterViewHolder(itemRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapterViewHolder mHolder, int position) {
        mHolder.bindRow(mRowList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRowList.size();
    }

    public void setRowList(List<Row> mRowList) {
        this.mRowList = mRowList;
        notifyDataSetChanged();
    }

    public static class CountryAdapterViewHolder extends RecyclerView.ViewHolder {

        ItemRowBinding mItemRowBinding;

        public CountryAdapterViewHolder(@NonNull ItemRowBinding itemView) {
            super(itemView.getRoot());
            this.mItemRowBinding = itemView;
        }

        void bindRow(Row mRow) {
            if (mItemRowBinding.getRowViewModel() == null) {
                mItemRowBinding.setRowViewModel(new RowItemViewModel(mRow, itemView.getContext()));
            } else {
                mItemRowBinding.getRowViewModel().setRow(mRow);
            }
        }
    }
}
