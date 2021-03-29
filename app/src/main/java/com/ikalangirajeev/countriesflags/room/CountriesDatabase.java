package com.ikalangirajeev.countriesflags.room;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;



@Database(entities = {CountryEntity.class}, version = 1, exportSchema = false)
public abstract class CountriesDatabase extends RoomDatabase {

    private static CountriesDatabase countriesDatabase;
    public abstract CountryDao countryDao();

    public static synchronized CountriesDatabase getCountriesDatabase(Context context) {
        if (countriesDatabase == null) {
            countriesDatabase = Room.databaseBuilder(context.getApplicationContext(), CountriesDatabase.class, "countries_database.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }

        return countriesDatabase;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(countriesDatabase).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private CountryDao countryDao;
        private PopulateDbAsyncTask(CountriesDatabase db) {
            countryDao = db.countryDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            countryDao.saveCountryData("Colombia", "Bogotá", "https://restcountries.eu/data/col.svg",
                    "Americas", "South America", 48759958, "BRA ECU PAN PER VEN");
            return null;
        }
    }

}
