package fr.yaya_diallo.gps;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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

        if(distance<200){
            Toast.makeText(this,"메시지 입력",Toast.LENGTH_LONG).show();
            Log.d("알림","거리계산"+distance);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent = new Intent(this,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(this);
            builder.setTicker("알람 설명");
            builder.setContentText("알람 내용");
            builder.setContentTitle("알람 제목");



        }
    }



}
