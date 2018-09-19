package com.prashant.mvvm.api;

import com.prashant.mvvm.model.Country;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("s/2iodh4vg0eortkl/facts.json")
    Observable<Country> getResult();
}
