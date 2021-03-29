package com.ikalangirajeev.countriesflags.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "countries")
public class CountryEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int countryId;

    @NonNull
    private String name;

    @NonNull
    private String capital;

    @NonNull
    private String flag;

    @NonNull
    private String region;

    @NonNull
    private String subregion;

    @NonNull
    private long population;

    @NonNull
    private String borders;

    public CountryEntity(@NonNull String name, @NonNull String capital, @NonNull String flag, @NonNull String region, @NonNull String subregion, long population, @NonNull String borders) {
        this.name = name;
        this.capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.borders = borders;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getCapital() {
        return capital;
    }

    public void setCapital(@NonNull String capital) {
        this.capital = capital;
    }

    @NonNull
    public String getFlag() {
        return flag;
    }

    public void setFlag(@NonNull String flag) {
        this.flag = flag;
    }

    @NonNull
    public String getRegion() {
        return region;
    }

    public void setRegion(@NonNull String region) {
        this.region = region;
    }

    @NonNull
    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(@NonNull String subregion) {
        this.subregion = subregion;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    @NonNull
    public String getBorders() {
        return borders;
    }

    public void setBorders(@NonNull String borders) {
        this.borders = borders;
    }
}
