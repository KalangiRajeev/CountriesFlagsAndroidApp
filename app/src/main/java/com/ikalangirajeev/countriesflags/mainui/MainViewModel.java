package com.ikalangirajeev.countriesflags.mainui;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ikalangirajeev.countriesflags.retrofit.Country;
import com.ikalangirajeev.countriesflags.retrofit.JsonPlaceHolderApi;
import com.ikalangirajeev.countriesflags.room.CountriesDatabase;
import com.ikalangirajeev.countriesflags.room.CountryDao;
import com.ikalangirajeev.countriesflags.room.CountryEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    CountriesDatabase countriesDatabase;
    CountryDao countryDao;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = HandlerCompat.createAsync(Looper.myLooper());

    public MainViewModel(@NonNull Application application) {
        super(application);
        countriesDatabase = CountriesDatabase.getCountriesDatabase(application.getApplicationContext());
        countryDao = countriesDatabase.countryDao();
    }

    public LiveData<Integer> getDBSize() {
        MutableLiveData<Integer> mLiveSize = new MutableLiveData<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Integer size = countryDao.getCountriesList("asia").size();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLiveSize.setValue(size);
                    }
                });
            }
        });
        return mLiveSize;
    }

    public void saveCountriestoDB() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://restcountries.eu/rest/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            Call<List<Country>> call = jsonPlaceHolderApi.getCountriesList();

            call.enqueue(new Callback<List<Country>>() {
                @Override
                public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                    if (!response.isSuccessful()) {
                        Log.d(TAG, "onResponse: " + response.code() + ": " + response.message());
                    } else {
                        List<Country> countriesList = response.body();
                        Log.d(TAG, "onResponse: " + response.body().size());

                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                for(Country country: countriesList) {
                                    String borders = "";
                                    for (String border : country.getBorders()) {
                                        borders += border + " ";
                                    }
                                    countryDao.saveCountryData(
                                            country.getName(),
                                            country.getCapital(),
                                            country.getFlag(),
                                            country.getRegion(),
                                            country.getSubRegion(),
                                            country.getPopulation(),
                                            borders
                                    );
                                }
                            }
                        });

                    }
                }

                @Override
                public void onFailure(Call<List<Country>> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage(), t.fillInStackTrace());
                }
            });
    }

    public LiveData<List<CountryEntity>> getCountriesListFromDB(String region) {
        MutableLiveData<List<CountryEntity>> mLiveCountriesList = new MutableLiveData<>();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<CountryEntity> countries = countryDao.getCountriesList(region);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLiveCountriesList.setValue(countries);
                    }
                });
            }
        });
        return mLiveCountriesList;
    }

    public void deleteCountriesInDB() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                countryDao.deleteCountriesFromDB();
            }
        });
    }

}
