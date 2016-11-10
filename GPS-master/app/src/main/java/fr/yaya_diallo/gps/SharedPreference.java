package fr.yaya_diallo.gps;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yayacky on 04/09/2016.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "GPS_RECEIVED";
    public static final String FAVORITES = "Favorite";
    public SharedPreference() {
        super();
    }
    public void storeFavorites(Context context, List favorites) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);;
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }
    public ArrayList<GPSReceive> loadFavorites(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        List favorites ;
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            GPSReceive[] favoriteItems = gson.fromJson(jsonFavorites,GPSReceive[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList(favorites);
        } else
            return null;
        return (ArrayList) favorites;
    }
    public void addFavorite(Context context, GPSReceive gpsReceive) {
        List favorites = loadFavorites(context);
        if (favorites == null)
            favorites = new ArrayList();
        favorites.add(gpsReceive);
        storeFavorites(context, favorites);
    }
    public void removeFavorite(Context context, GPSReceive gpsReceive) {
        ArrayList favorites = loadFavorites(context);
        if (favorites != null) {
            favorites.remove(gpsReceive);
            storeFavorites(context, favorites);
        }
    }

    public void removeAllFavorite(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}
