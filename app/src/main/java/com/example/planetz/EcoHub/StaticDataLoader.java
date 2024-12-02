package com.example.planetz.EcoHub;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class StaticDataLoader {
    public static <T> List<T> loadJsonData(Context context, String fileName, Type type) {
        try (InputStream is = context.getAssets().open(fileName);
             InputStreamReader reader = new InputStreamReader(is)) {
            return new Gson().fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
