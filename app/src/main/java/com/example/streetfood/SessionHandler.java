package com.example.streetfood;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;


public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "reso_username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_FULL_NAME = "reso_name";
    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param reso_username
     * @param reso_name
     */
    public void loginOper(String reso_username, String reso_name) {
        mEditor.putString(KEY_USERNAME, reso_username);
        mEditor.putString(KEY_FULL_NAME, reso_name);
        Date date = new Date();

        //Set user session for next 7 days
        long millis = date.getTime() + (7 * 24 * 60 * 60 * 1000);
        mEditor.putLong(KEY_EXPIRES, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean isLoggedIn() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(KEY_EXPIRES, 0);

        /* If shared preferences does not have a value
         then user is not logged in
         */
        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);

        /* Check if session is expired by comparing
        current date and Session expiry date
        */
        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return oper details
     */
    public Oper getOperDetails() {
        //Check if user is logged in first
        if (!isLoggedIn()) {
            return null;
        }
        Oper oper = new Oper();
        oper.setUsername(mPreferences.getString(KEY_USERNAME, KEY_EMPTY));
        oper.setName(mPreferences.getString(KEY_FULL_NAME, KEY_EMPTY));
        oper.setSessionExpiryDate(new Date(mPreferences.getLong(KEY_EXPIRES, 0)));

        return oper;
    }

    /**
     * Logs out user by clearing the session
     */
    public void logoutUser(){
        mEditor.clear();
        mEditor.commit();
    }

}