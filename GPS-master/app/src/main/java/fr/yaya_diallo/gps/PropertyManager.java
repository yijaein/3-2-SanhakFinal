package fr.yaya_diallo.gps;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Jan on 2016-11-15.
 */

public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";


    private PropertyManager() {
        Context context = MyApplication.getContext();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPrefs.edit();
    }


    public void setLat(float lat) {
        mEditor.putFloat(KEY_LAT, lat);
        mEditor.commit();
    }

    public float getLat() {
        return mPrefs.getFloat(KEY_LAT, 0);
    }

    public void setLng(float lng) {
        mEditor.putFloat(KEY_LNG, lng);
        mEditor.commit();
    }

    public float getLng() {
        return mPrefs.getFloat(KEY_LNG , 0);
    }


}