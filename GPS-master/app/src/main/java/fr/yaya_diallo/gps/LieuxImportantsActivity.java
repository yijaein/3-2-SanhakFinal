package fr.yaya_diallo.gps;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

public class LieuxImportantsActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lieux_importants);

        mListView = (ListView) findViewById(R.id.listview_lieux_important);
        final ArrayList<LieuxMenu> lieuxmenuList = LieuxMenu.getLieuxMenuFromFile(this);
        LieuxMenuAdapter adapter = new LieuxMenuAdapter(this, lieuxmenuList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LieuxMenu selectedMenu = lieuxmenuList.get(position);
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+selectedMenu.coordonnees+"&mode=d");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aide_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_contact:
                Intent contact = new Intent(LieuxImportantsActivity.this, ContactActivity.class);
                LieuxImportantsActivity.this.startActivity(contact);
                return true;
            case R.id.menu_help:
                Intent aide = new Intent(LieuxImportantsActivity.this, AideActivity.class);
                LieuxImportantsActivity.this.startActivity(aide);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
