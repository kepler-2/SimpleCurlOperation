package com.smad.bsnassignment.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ShardPref {
    public static final int TYPE_USER = 0;
    public static final int TYPE_ADMIN = 1;
    static SharedPreferences sharedPreferences;
    static ShardPref shardPref;

    private ShardPref() {

    }

    public static ShardPref getInstance(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (shardPref == null)
            shardPref = new ShardPref();
        return shardPref;
    }

    public void logout() {
        sharedPreferences.edit().clear().commit();
    }

    public int getLoggedUserType() {
        return sharedPreferences.getInt("user_type", -1);
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean("is_logged", false);
    }
    public String getUserId() {
        return sharedPreferences.getString("user_id", "");
    }

    public void setLogged(int typeUser, String user_id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_logged", true);
        editor.putInt("user_type", typeUser);
        editor.putString("user_id", user_id);
        editor.commit();
    }
}
