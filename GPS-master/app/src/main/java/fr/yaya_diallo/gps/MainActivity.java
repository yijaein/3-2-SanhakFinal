package fr.yaya_diallo.gps;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private final String TAG = MainActivity.class.getName();
    public static LocationManager locationManager;
    public static Location location;
    GridView gridView;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static String bestProvider;
    public static String mapTitle = "Votre position ";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new CustomAndroidGridViewAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(location == null)
                {
                    mapTitle = "Votre dernière position ";
                    location = locationManager.getLastKnownLocation(bestProvider);
                }
                if (position == 0) {
                    if (location == null) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                        adb.setTitle("Désolé votre position est introuvable");
                        adb.setPositiveButton("Ok", null);
                        adb.show();
                    } else {
                        Intent map = new Intent(MainActivity.this, MapsActivity.class);
                        MainActivity.this.startActivity(map);
                    }
                }else if (position == 1) {
                    if (location == null) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                        adb.setTitle("Désolé votre position est introuvable");
                        adb.setPositiveButton("Ok", null);
                        adb.show();
                    } else {
                        Intent sendSMS = new Intent(MainActivity.this, SendGpsActivity.class);
                        MainActivity.this.startActivity(sendSMS);
                    }
                }else if (position == 2) {
                    Intent receiveGps = new Intent(MainActivity.this, ReceiveGpsActivity.class);
                    MainActivity.this.startActivity(receiveGps);
                }else if (position == 3) {
                    Intent lieux = new Intent(MainActivity.this, LieuxImportantsActivity.class);
                    MainActivity.this.startActivity(lieux);
                }
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        int hasGPSPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (hasGPSPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Utils.showMessageOKCancel(MainActivity.this, "Vous devez donner accès au GPS pour avoir vos coordonnées",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        Criteria critere = new Criteria();
        // Pour indiquer la précision voulue
        // On peut mettre ACCURACY_FINE pour une haute précision ou ACCURACY_COARSE pour une moins bonne précision
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        // Est-ce que le fournisseur peut être payant ?
        critere.setCostAllowed(false);
        // Pour indiquer la consommation d'énergie demandée
        // Criteria.POWER_HIGH pour une haute consommation, Criteria.POWER_MEDIUM pour une consommation moyenne et Criteria.POWER_LOW pour une basse consommation
        critere.setPowerRequirement(Criteria.POWER_LOW);
        // Est-ce que le fournisseur doit être capable de donner une vitesse ?
        critere.setSpeedRequired(true);
        bestProvider = locationManager.getBestProvider(critere,true);

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 150, pending);
        locationManager.requestLocationUpdates(bestProvider, 60000, 150, this);
    }

    @Override
    public void onLocationChanged(Location p_location) {
        mapTitle = "Votre position ";
        location = p_location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
        /*if(LocationManager.GPS_PROVIDER.equalsIgnoreCase(provider)){
            location = null;
        }*/
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
                Intent contact = new Intent(MainActivity.this, ContactActivity.class);
                MainActivity.this.startActivity(contact);
                return true;
            case R.id.menu_help:
                Intent aide = new Intent(MainActivity.this, AideActivity.class);
                MainActivity.this.startActivity(aide);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
