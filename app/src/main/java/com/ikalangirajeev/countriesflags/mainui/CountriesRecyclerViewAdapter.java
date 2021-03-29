package com.ikalangirajeev.countriesflags.mainui;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.google.android.material.textview.MaterialTextView;
import com.ikalangirajeev.countriesflags.R;
import com.ikalangirajeev.countriesflags.room.CountryEntity;

import java.util.ArrayList;
import java.util.List;

public class CountriesRecyclerViewAdapter extends RecyclerView.Adapter<CountriesRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "CountriesRecyclerViewAd";

    private LayoutInflater layoutInflator;
    private List<CountryEntity> countriesList;

    public CountriesRecyclerViewAdapter(Context context) {
        layoutInflator = LayoutInflater.from(context);
        countriesList = new ArrayList<>();
    }

    public void setCountriesList(List<CountryEntity> countriesList) {
        this.countriesList = countriesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflator.inflate(R.layout.card_country, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CountryEntity country = countriesList.get(position);
        holder.setData(country);
        holder.setListeners(country);
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView imageView;
        MaterialTextView name, capital, region, population, borders, subRegion;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            capital = itemView.findViewById(R.id.capital);
            region = itemView.findViewById(R.id.region);
            population = itemView.findViewById(R.id.population);
            borders = itemView.findViewById(R.id.borders);
            subRegion = itemView.findViewById(R.id.subRegion);
        }

        public void setData(CountryEntity country) {
            GlideToVectorYou.init().with(itemView.getContext()).load(Uri.parse(country.getFlag()), imageView);
            name.setText(country.getName());
            capital.setText(country.getCapital());
            region.setText(country.getRegion());
            subRegion.setText(country.getSubregion());
            population.setText(String.valueOf(country.getPopulation()) + " ppl");
            borders.setText(country.getBorders());
        }

        public void setListeners(CountryEntity country) {
        }
    }
}
