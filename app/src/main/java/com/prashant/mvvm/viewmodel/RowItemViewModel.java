package com.prashant.mvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.prashant.mvvm.R;
import com.prashant.mvvm.model.Row;


public class RowItemViewModel extends BaseObservable {


    private Row mRow;
    private Context context;
    private String title;
    private String description;
    private String imageHref;


    public RowItemViewModel(Row mRow, Context context) {
        this.mRow = mRow;
        this.context = context;
    }


    public String getTitle() {
        return mRow.getTitle();
    }


    public String getDescription() {
        return mRow.getDescription();
    }


    public String getImageHref() {
        return mRow.getImageHref();
    }

    @BindingAdapter("imageHref")
    public static void setImageHref(ImageView imageView, String url) {

        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .fitCenter()
                        .error(R.mipmap.ic_launcher)
                        .dontAnimate()
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .dontTransform())
                .into(imageView);
    }


    public void setRow(Row mRow) {
        this.mRow = mRow;
        notifyChange();
    }
}
