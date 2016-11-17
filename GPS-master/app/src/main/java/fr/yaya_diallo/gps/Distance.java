package fr.yaya_diallo.gps;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jan on 2016-11-17.
 */

public class Distance extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distance);

        double distance;
        String meter;

        Location locationA = new Location("Mylocation");

        locationA.setLatitude(PropertyManager.getInstance().getLat());
        locationA.setLongitude(PropertyManager.getInstance().getLng());

        Location locationB = new Location("역동서원 ");

        locationB.setLatitude(36.542099);
        locationB.setLongitude(128.797705);

        distance = locationA.distanceTo(locationB);
        meter = Double.toString(distance);

        Log.v("알림","거리계산"+distance);

        if(distance<150){
            Toast.makeText(this,"메시지 입력",Toast.LENGTH_LONG).show();
            Log.d("알림","거리계산"+distance);
            Intent intent = new Intent(getApplicationContext(),Information.class);
            startActivity(intent);


        }
    }
}
