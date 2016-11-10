package fr.yaya_diallo.gps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactActivity extends AppCompatActivity {

    private ListView maListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //Récupération de la listview créée dans le fichier main.xml
        maListView = (ListView) findViewById(R.id.listviewcontact);

        //Création de la ArrayList qui nous permettra de remplire la listView
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        //On déclare la HashMap qui contiendra les informations pour un item
        HashMap<String, String> map = new HashMap<String, String>();
        //on insère un élément titre que l'on récupérera dans le textView titre créé dans le fichier affichageitem.xml
        map.put("titre", "www.facebook.com/kiraahgps");
        //on insère la référence à l'image (convertit en String car normalement c'est un int) que l'on récupérera dans l'imageView créé dans le fichier affichageitem.xml
        map.put("img", String.valueOf(R.mipmap.facebook));
        //enfin on ajoute cette hashMap dans la arrayList
        listItem.add(map);

        //On refait la manip plusieurs fois avec des données différentes pour former les items de notre ListView

        map = new HashMap<String, String>();
        map.put("titre","www.twitter.com/kiraahgps");
        map.put("img", String.valueOf(R.mipmap.twitter));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "kiraahgps@gmail.com");
        map.put("img", String.valueOf(R.mipmap.mail));
        listItem.add(map);

        map = new HashMap<String, String>();
        map.put("titre", "(+224) 628.44.76.83 \n(+33) 06.43.40.82.54");
        map.put("img", String.valueOf(R.mipmap.telephone));
        listItem.add(map);

        //Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichagecontact
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.affichagecontact,
                new String[] {"img", "titre"}, new int[] {R.id.img, R.id.titre});

        //On attribut à notre listView l'adapter que l'on vient de créer
        maListView.setAdapter(mSchedule);
        maListView.setClickable(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int)(width * 0.9),(int)(height * 0.6));

    }
}
