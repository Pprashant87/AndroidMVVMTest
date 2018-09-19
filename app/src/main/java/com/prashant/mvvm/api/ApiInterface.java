package com.prashant.mvvm.api;
/**
 * @author : Prashant P
 *
 * Created in 2018 as an unpublished copyright work.
 * All rights reserved.
 */
import com.prashant.mvvm.model.Country;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("s/2iodh4vg0eortkl/facts.json")
    Observable<Country> getResult();
}
