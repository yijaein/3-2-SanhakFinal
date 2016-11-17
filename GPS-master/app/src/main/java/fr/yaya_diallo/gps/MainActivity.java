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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener{
    double actual_distance;//실제거리를 담을 전역변수
    float[] distance= new float[0];
    private final String TAG = MainActivity.class.getName();
    public static LocationManager locationManager;
    public static Location location;
    GridView gridView;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static String bestProvider;
    public static String mapTitle = "니 위치 ";
    double distance1;



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로딩화면
        Intent intent1 = new Intent(this,Splash_Activity.class);
        startActivity(intent1);
//   =================================================

        gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new CustomAndroidGridViewAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(location == null)
                {
                    mapTitle = "니 위치";
                    location = locationManager.getLastKnownLocation(bestProvider);
                }
                if (position == 0) {
                    if (location == null) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                        adb.setTitle("죄송합니다. 위치를 찾을 수 없습니다.");
                        adb.setPositiveButton("Ok", null);
                        adb.show();
                    } else {
                        Intent map = new Intent(MainActivity.this, MapsActivity.class);
                        MainActivity.this.startActivity(map);
                    }
                }else if (position == 1) {
                    if (location == null) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                        adb.setTitle("죄송합니다. 위치를 찾을 수 없습니다.");
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
                Utils.showMessageOKCancel(MainActivity.this, "GPS를 켜주세요",
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


        /*Criteria
        GPS의 RPOVIDER, 기기 상황에 따른 옵션을 설정할 수 있음.
        해상도가 우수하거나 또는 그렇지 않는 기기에 따른 GPS옵션 설정.
        Ex) 정확도, 전원소비량 , 고도 사용여부 , 방위데이터, 속도 , 금전적비용

         */

        Criteria critere = new Criteria();

        critere.setAccuracy(Criteria.ACCURACY_FINE);// 정밀한 위치 정밀도 요구를 나타냄

        critere.setCostAllowed(false);

        critere.setPowerRequirement(Criteria.POWER_LOW);//저전력 조건

        critere.setSpeedRequired(true);
        bestProvider = locationManager.getBestProvider(critere,true);

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 150, pending);
        locationManager.requestLocationUpdates(bestProvider, 60000, 150, this);

        // 거리 계산
        //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

/*
        Location.distanceBetween(PropertyManager.getInstance().getLat(),PropertyManager.getInstance().getLng(),36.542099,128.797705,distance);
        actual_distance=distance[0];
        //50미터 안에 들어오면 액티비티가 켜진다.

        if(actual_distance<50){
            Intent intent = new Intent(this,Information.class);
            startActivity(intent);
        }
       //25미터에 들어오면 토스트 메시지가 뜸
        if(actual_distance<25){
            Toast toast = Toast.makeText(getApplicationContext(),"근처에 해당 건물에 대한 정보가 있습니다.",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        */
    }

    @Override
    public void onLocationChanged(Location p_location) {
        mapTitle = "당신의 위치";
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

    //두 거리 사이의 위치를 구하는 함수

        public  void CalDistance(double lat1, double lon1, double lat2, double lon2) {
            double theta,dist;
            theta =lon1-lon2;
            dist= Math.sin(deg2rad(lat1))*Math.sin(deg2rad(lat2))+Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist=Math.acos(dist);
            dist=rad2deg(dist);

            dist=dist*60*1.1515;
            dist= dist*1.609344;
            dist= dist*1000.0;


        }
    private double deg2rad(double deg){
        return  (double)(deg*Math.PI/(double)180d);
    }
    private double rad2deg(double rad){
        return  (double)(rad*(double)180d/Math.PI);
    }
     // 거리 계산




}
