package fr.yaya_diallo.gps;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
/**
 * Created by yayacky on 05/09/2016.
 */
public class LieuxMenu {

    public String id;
    public String title;
    public String imageUrl;
    public String description;
    public String coordonnees;

    public static ArrayList<LieuxMenu> getLieuxMenuFromFile(Context context){
        final ArrayList<LieuxMenu> lieuxMenuList = new ArrayList<>();

        try {
            // Load data
            String jsonString = loadJsonFromAsset("lieux_menu.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray lieuxmenus = json.getJSONArray("menus");

            // Get Menu objects from data
            for(int i = 0; i < lieuxmenus.length(); i++){
                LieuxMenu lieuxmenu = new LieuxMenu();

                lieuxmenu.id = lieuxmenus.getJSONObject(i).getString("id");
                lieuxmenu.title = lieuxmenus.getJSONObject(i).getString("title");
                lieuxmenu.imageUrl = lieuxmenus.getJSONObject(i).getString("image");
                lieuxmenu.description = lieuxmenus.getJSONObject(i).getString("description");
                lieuxmenu.coordonnees = lieuxmenus.getJSONObject(i).getString("coordonnees");

                lieuxMenuList.add(lieuxmenu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lieuxMenuList;
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
