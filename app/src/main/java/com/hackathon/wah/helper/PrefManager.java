package com.hackathon.wah.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    private static String TAG = PrefManager.class.getSimpleName();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "wahackathon";

    private static final String IS_LOGGEDIN = "isLoggedIn";
    private static final String USER_TOKEN = "userToken";
    private static final String COUNTER_OPEN = "counterOpen";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(String userToken) {
        editor.putBoolean(IS_LOGGEDIN, true);
        editor.putString(USER_TOKEN, userToken);
        editor.commit();
    }

    public void setLogout(){
        editor.putBoolean(IS_LOGGEDIN, false);
        editor.putString(USER_TOKEN, null);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGEDIN, false);
    }

    public String getUserToken(){
        return pref.getString(USER_TOKEN, "-");
    }

    public int getCounterOpen(){
        return pref.getInt(COUNTER_OPEN, 0);
    }

    public void opened(){
        editor.putInt(COUNTER_OPEN, getCounterOpen()+1);
        editor.commit();
    }

}
