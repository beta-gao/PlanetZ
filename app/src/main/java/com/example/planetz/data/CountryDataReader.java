package com.example.planetz.data;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CountryDataReader {

    private static final String TAG = "CountryDataReader";

    public static Map<String, Double> readCountryEmissionsData(Context context, String filename) {
        Map<String, Double> countryEmissions = new HashMap<>();

        try {
            InputStream is = context.getAssets().open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    String country = tokens[0];
                    double emissions = Double.parseDouble(tokens[1]);
                    countryEmissions.put(country, emissions);
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e(TAG, "Error reading country emissions data", e);
        }

        return countryEmissions;
    }
}