package com.ikalangirajeev.countriesflags.mainui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.ikalangirajeev.countriesflags.R;
import com.ikalangirajeev.countriesflags.room.CountryEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String selectedRegion = "asia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        TextInputLayout region = findViewById(R.id.region);
        MaterialButton saveDataToDB = findViewById(R.id.saveDataToDB);
        MaterialButton deleteDataInDB = findViewById(R.id.deleteDataInDB);

        List<String> regionsList = new ArrayList<>(Arrays.asList("Africa", "Americas", "Asia", "Europe", "Oceania"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.text_view_select, regionsList);
        ((MaterialAutoCompleteTextView) region.getEditText()).setAdapter(arrayAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CountriesRecyclerViewAdapter countriesRecyclerViewAdapter = new CountriesRecyclerViewAdapter(this);
        recyclerView.setAdapter(countriesRecyclerViewAdapter);

        mainViewModel.getDBSize().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer > 0) {
                    saveDataToDB.setEnabled(false);
                } else {
                    saveDataToDB.setEnabled(true);
                }
            }
        });

        saveDataToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.saveCountriestoDB();
            }
        });

        deleteDataInDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.deleteCountriesInDB();
            }
        });

        mainViewModel.getCountriesListFromDB(selectedRegion).observe(this, new Observer<List<CountryEntity>>() {
            @Override
            public void onChanged(List<CountryEntity> countries) {
                countriesRecyclerViewAdapter.setCountriesList(countries);
                getSupportActionBar().setSubtitle(countries.size() + "Countries");
            }
        });

        region.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                selectedRegion = charSequence.toString().toLowerCase();
                mainViewModel.getCountriesListFromDB(selectedRegion).observe(MainActivity.this, new Observer<List<CountryEntity>>() {
                    @Override
                    public void onChanged(List<CountryEntity> countries) {
                        countriesRecyclerViewAdapter.setCountriesList(countries);
                        getSupportActionBar().setSubtitle(countries.size() + "Countries");
                    }
                });
                region.getEditText().clearFocus();
                hideKeyboard(MainActivity.this);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}