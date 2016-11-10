package fr.yaya_diallo.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by yayacky on 04/09/2016.
 */
public class SmsBroadcastReceiver  extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    private final String TAG = SmsBroadcastReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] messages = (Object[]) intentExtras.get(SMS_BUNDLE);
            SmsMessage smsMessage[] = new SmsMessage[messages.length];
            for (int i = 0; i < messages.length; i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
            }

            String receivedMessage = smsMessage[0].getMessageBody().toString();
            String originatingAddress = smsMessage[0].getOriginatingAddress();


            if((receivedMessage != null && !receivedMessage.isEmpty()) && receivedMessage.contains("Kiraah") && receivedMessage.contains("GPS") && receivedMessage.contains("[") && receivedMessage.contains("]")) {
                SharedPreference sharedPreference = new SharedPreference();
                ArrayList<GPSReceive> gpsReceives = sharedPreference.loadFavorites(context);
                String id = gpsReceives== null ? 1+"" : gpsReceives.size()+1+"";
                String nom = Utils.getContactName(context,originatingAddress);
                String coordonnees = receivedMessage.substring(receivedMessage.indexOf("[") + 1, receivedMessage.indexOf("]"));
                String lieu = receivedMessage.substring(receivedMessage.indexOf("(")+1,receivedMessage.indexOf(")"));
                String dateReception = DateTime.now().toString();
                GPSReceive gpsReceive = new GPSReceive(id,nom,originatingAddress,coordonnees,dateReception,lieu.trim());
                if(gpsReceives == null)
                {
                    sharedPreference.addFavorite(context, gpsReceive);
                }else if(!gpsReceives.contains(gpsReceive)) {
                    sharedPreference.addFavorite(context, gpsReceive);
                }
            }
        }
    }
}
