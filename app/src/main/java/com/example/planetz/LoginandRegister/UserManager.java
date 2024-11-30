package com.example.planetz.LoginandRegister;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserManager {
    private static UserManager instance;
    private String userId;
    private static final String PREF_NAME = "UserManagerPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String TAG = "UserManager";

    private SharedPreferences sharedPreferences;

    private UserManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(KEY_USER_ID, null); // 加载存储的 userId
        Log.d(TAG, "UserManager initialized with userId: " + userId);
    }

    public static synchronized UserManager getInstance(Context context) {
        if (instance == null) {
            instance = new UserManager(context.getApplicationContext());
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply(); // 持久化 userId
        Log.d(TAG, "UserId set and saved: " + userId);
    }

    public void clearUserId() {
        this.userId = null;
        sharedPreferences.edit().remove(KEY_USER_ID).apply();
        Log.d(TAG, "UserId cleared");
    }
}
