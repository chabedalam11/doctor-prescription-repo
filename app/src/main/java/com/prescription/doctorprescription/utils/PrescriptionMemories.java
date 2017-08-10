package com.prescription.doctorprescription.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by medisys on 10-Aug-17.
 */

public class PrescriptionMemories {

    private static final String TAG = "PreferenceManager";
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "loginPrefs";

    public static final String KEY_DOC_ID = "DOC_ID";

    public static final String KEY_DOC_NAME = "DOC_NAME";

    public static final String KEY_DOC_PHONE1 = "PHONE1";

    public static final String KEY_DOC_PHONE2 = "PHONE2";

    public static final String KEY_DOC_EMAIL = "DOC_EMAIL";



    private SharedPreferences prefs;
    private Context mContext;

    public PrescriptionMemories(Context aContext){
        this. mContext = aContext;
        prefs = aContext.getSharedPreferences(PREF_NAME,  PRIVATE_MODE);

    }

    public  void putPref(String key, String value) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public  String getPref(String key) {
        return prefs.getString(key, null);
    }

    /**
     *  Method used to delete Preferences */
    public void deletePreferences(String key) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key).commit();
    }

    public void clearPreferences() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear().commit();
    }
}
