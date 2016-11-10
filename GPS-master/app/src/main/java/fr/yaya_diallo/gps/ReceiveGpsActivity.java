package fr.yaya_diallo.gps;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;
import java.util.Date;

public class ReceiveGpsActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks {

    private final String TAG = ReceiveGpsActivity.class.getName();
    ListView smsListView;
    TextView textView;
    ArrayAdapter arrayAdapter;
    ArrayList<GPSReceive> gpsReceives = new ArrayList<>();
    SharedPreference sharedPreference = new SharedPreference();
    private final int REQUEST_CODE_ASK_PERMISSIONS_READ_CONTACT = 124;
    private final int REQUEST_CODE_ASK_PERMISSIONS_RECEIVE_SMS = 126;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_gps);

        textView = (TextView) findViewById(R.id.textView_receivegps);
        getSmsInbox(this);
    }

    private void afficherMessage() {
        Log.d(TAG,"afficherMessage");
        ArrayList<GPSReceive> favorites = sharedPreference.loadFavorites(this);
        final Typeface titleTypeFace = Typeface.createFromAsset(this.getAssets(), "fonts/JosefinSans-Bold.ttf");
        final Typeface subTypeFace =   Typeface.createFromAsset(this.getAssets(), "fonts/JosefinSans-SemiBoldItalic.ttf");
        smsListView = (ListView) findViewById(R.id.SMSList);
        if(favorites != null && !favorites.isEmpty()){
            gpsReceives.addAll(favorites);
        }
        if(gpsReceives.size() == 0 )
        {
            textView.setText("Vous n'avez pas reçu de coordonnées GPS");
        }
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2,gpsReceives){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                TwoLineListItem row;
                if(convertView == null){
                    LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    row = (TwoLineListItem)inflater.inflate(android.R.layout.simple_list_item_2, null);
                }else{
                    row = (TwoLineListItem)convertView;
                }
                GPSReceive data = gpsReceives.get(position);
                String smsDe = (data.getNom() == null || data.getNom().isEmpty()) ? data.getTelephone() : data.getNom();
                row.getText1().setText("Lieu : "+data.getLieu());
                row.getText2().setText("Message de "+smsDe);
                row.getText1().setTextColor(Color.BLACK);
                row.getText2().setTextColor(Color.BLACK);
                row.getText1().setTypeface(titleTypeFace);
                row.getText2().setTypeface(subTypeFace);

                return row;
            }
        };
        smsListView.setAdapter(arrayAdapter);
        smsListView.setOnItemClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        try {
            final GPSReceive receive = gpsReceives.get(position);
            PopupMenu popupMenu = new PopupMenu(ReceiveGpsActivity.this, view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_coordonnees, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.item_aller_a:
                            Uri gmmIntentUri = Uri.parse("google.navigation:q="+receive.getCoordonnees()+"&mode=d");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                            return true;
                        case R.id.item_modifier_nom:
                            sharedPreference.removeFavorite(ReceiveGpsActivity.this,receive);
                            gpsReceives = sharedPreference.loadFavorites(ReceiveGpsActivity.this);
                            arrayAdapter.clear();
                            arrayAdapter.addAll(gpsReceives);
                            arrayAdapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getSmsInbox(Context context) {
        int hasReceiveSMSPermission = ContextCompat.checkSelfPermission(ReceiveGpsActivity.this, Manifest.permission.READ_SMS);
        if (hasReceiveSMSPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                Utils.showMessageOKCancel(ReceiveGpsActivity.this, "Vous devez donner l'accès à vos SMS",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.READ_SMS},
                                        REQUEST_CODE_ASK_PERMISSIONS_RECEIVE_SMS);
                            }
                        });
                return;
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    REQUEST_CODE_ASK_PERMISSIONS_RECEIVE_SMS);
            return;
        }
        int hasContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (hasContactPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                Utils.showMessageOKCancel(ReceiveGpsActivity.this,"Vous devez donner accès à vos contacts",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
                                        REQUEST_CODE_ASK_PERMISSIONS_READ_CONTACT);
                            }
                        });
                return;
            }
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS_READ_CONTACT);
            return;
        }
        readSms();
    }

    private void readSms() {
        Log.d(TAG, " readSms ");
        SharedPreference sharedPreference = new SharedPreference();
        ArrayList<GPSReceive> gpsReceives = sharedPreference.loadFavorites(this);
        if(gpsReceives == null || gpsReceives.size()==0) {
            getSupportLoaderManager().initLoader(1, null, this);
        }else{
            afficherMessage();
        }
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
                Intent contact = new Intent(ReceiveGpsActivity.this, ContactActivity.class);
                ReceiveGpsActivity.this.startActivity(contact);
                return true;
            case R.id.menu_help:
                Intent aide = new Intent(ReceiveGpsActivity.this, AideActivity.class);
                ReceiveGpsActivity.this.startActivity(aide);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        Log.d(TAG, " onCreateLoader ");
        final String SMS_INBOX = "content://sms/inbox";
        Uri uri = Uri.parse(SMS_INBOX);
        String[] projection = new String[]{"_id", "thread_id", "address", "person", "body", "date", "type"};
        return new CursorLoader(this, uri, projection, "body LIKE '%Kiraah%' and body LIKE '%Position%' and body LIKE '%GPS%' ", null, "date desc");
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        Log.d(TAG,"onLoadFinished");
        Cursor cursor = (Cursor)data;
        String id = null;
        String smsContent = null;
        String nom = null;
        String telephone = null;
        String coordonnees = null;
        String lieu = null;
        String dateReception = null;
        GPSReceive gpsReceive = null;
        while (cursor.moveToNext()) {
            id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
            telephone = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            smsContent = cursor.getString(cursor.getColumnIndexOrThrow("body"));
            nom = Utils.getContactName(this, telephone);
            coordonnees = smsContent.substring(smsContent.indexOf("[") + 1, smsContent.indexOf("]"));
            lieu = smsContent.substring(smsContent.indexOf("(") + 1, smsContent.indexOf(")"));
            long millis = cursor.getLong(cursor.getColumnIndexOrThrow("date"));
            dateReception = DateFormat.format("dd-MM-yyyy hh:mm:ss", new Date(millis)).toString();
            gpsReceive = new GPSReceive(id, nom, telephone, coordonnees, dateReception, lieu.trim());
            if (gpsReceives == null || gpsReceives.size()==0) {

                gpsReceives = new ArrayList<>();
               // gpsReceives.add(gpsReceive);
                sharedPreference.addFavorite(this, gpsReceive);
            } else if (!gpsReceives.contains(gpsReceive)) {
                //gpsReceives.add(gpsReceive);
                sharedPreference.addFavorite(this, gpsReceive);
            }
        }
        afficherMessage();
    }
    @Override
    public void onLoaderReset(Loader loader) {
    }
}
