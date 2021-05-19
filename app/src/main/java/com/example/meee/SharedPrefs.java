package com.example.meee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import java.util.HashMap;

public class SharedPrefs {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context con;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "activeUser";
    public static final String USER_ID = "id";
    public static final String USERNAME = "username";
    public static final String PROJEKT_ID = "projekt_id";
    public static final String LOG_IN = "isLoggedIn";
    public static final String STAVKA = "stavka";

    public SharedPrefs(Context context){
        this.con = context;
        pref = con.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void logInSession(String name, String id){
        editor.putBoolean(LOG_IN, true);
        editor.putString(USERNAME, name);
        editor.putString(USER_ID, id);
        editor.commit();
    }
    public void projectSession(String id){
        editor.putString(PROJEKT_ID, id);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(Context context){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(USERNAME, pref.getString(USERNAME, null));
        user.put(USER_ID, pref.getString(USER_ID, null));
        return user;
    }
    public String getProjectDetails(Context context){
        String i = pref.getString(PROJEKT_ID, null);
        return i;
    }
    public void stavkaSession(String stavka){
        editor.putString(STAVKA, stavka);
        editor.commit();
    }

    public String getStavka(Context context){
        String i = pref.getString(STAVKA, null);
        return i;
    }

    public void logOutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(con, MainActivity.class);
        con.startActivity(i);
    }
}
