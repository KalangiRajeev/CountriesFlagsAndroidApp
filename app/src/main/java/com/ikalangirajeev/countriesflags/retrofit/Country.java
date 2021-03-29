package com.ikalangirajeev.countriesflags.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Country {

    @SerializedName("name")
    private String name;

    @SerializedName("capital")
    private String capital;

    @SerializedName("flag")
    private String flag;

    @SerializedName("region")
    private String region;

    @SerializedName("subregion")
    private String subregion;

    @SerializedName("population")
    private long population;

    @SerializedName("borders")
    private List<String> borders;


    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    public String getRegion() {
        return region;
    }

    public String getSubRegion() {
        return subregion;
    }

    public long getPopulation() {
        return population;
    }

    public List<String> getBorders() {
        return borders;
    }

}
