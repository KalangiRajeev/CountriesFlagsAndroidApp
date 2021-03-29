package com.ikalangirajeev.countriesflags.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface JsonPlaceHolderApi {
    @GET("all")
    Call<List<Country>> getCountriesList();
}
