package com.ikalangirajeev.countriesflags.room;

import androidx.room.Dao;
import androidx.room.Query;

import com.ikalangirajeev.countriesflags.retrofit.Country;

import java.util.List;

@Dao
public interface CountryDao {

    @Query("SELECT * FROM countries WHERE region LIKE :region")
    List<CountryEntity> getCountriesList(String region);

    @Query("INSERT INTO countries (countryId, name, capital, flag, region, subregion, population, borders) VALUES (NULL, :name, :capital, :flag, :region, :subregion, :population, :borders)")
    void saveCountryData(String name, String capital, String flag, String region, String subregion, long population, String borders);

    @Query("DELETE FROM countries")
    void deleteCountriesFromDB();

}
