package fr.yaya_diallo.gps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * Created by yayacky on 11/09/2016.
 */
public class Utils {

    public static void showMessageOKCancel(Activity activity,String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public static void showPopup(final Context context) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        if (MainActivity.location == null) {
            adb.setTitle("Votre position est introuvable, activez votre GPS");
        } else {
            String latitude = MainActivity.location.getLatitude()+"";
            String longitude = MainActivity.location.getLongitude()+"";
            adb.setTitle("Votre position actuelle :");
            adb.setMessage("Latitude " + latitude + "\nLongitude " + longitude);
        }
        adb.setPositiveButton("Ok", null);
        if (MainActivity.location != null) {
            adb.setNegativeButton("Envoyer vos coord.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent sendSMS = new Intent(context, SendGpsActivity.class);
                    context.startActivity(sendSMS);
                }
            });
        }
        adb.show();
    }

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return phoneNumber;
        }
        String contactName = phoneNumber;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

}
