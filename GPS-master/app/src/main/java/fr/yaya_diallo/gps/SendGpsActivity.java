package fr.yaya_diallo.gps;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SendGpsActivity extends AppCompatActivity {

    private Button sendBtn;
    private EditText txtphone;
    private Button contactButton;
    private EditText txtLieu;

    static final int PICK_CONTACT_REQUEST = 1;
    private final int REQUEST_CODE_ASK_PERMISSIONS_READ_CONTACT = 124;
    private final int REQUEST_CODE_ASK_PERMISSIONS_SEND_SMS = 125;
    private final String HAS_PHONE = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_gps);

        sendBtn = (Button) findViewById(R.id.btnSendSMS);
        txtphone = (EditText) findViewById(R.id.editText_phone);
        contactButton = (Button) findViewById(R.id.Button_retrieveContact);
        txtLieu = (EditText) findViewById(R.id.editText_lieu);

        contactButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                retrieveContacts();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder adb = new AlertDialog.Builder(SendGpsActivity.this);
                if (MainActivity.location == null) {
                    adb.setTitle("Votre position est introuvable, activer votre GPS");
                    adb.setPositiveButton("Ok", null);
                    adb.show();
                } else {
                    sendSMSMessage();
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void retrieveContacts() {
        int hasContactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (hasContactPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                Utils.showMessageOKCancel(SendGpsActivity.this, "Vous devez donner accès à votre repertoire de contacts",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                                        REQUEST_CODE_ASK_PERMISSIONS_READ_CONTACT);
                            }
                        });
                return;
            }
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS},
                    REQUEST_CODE_ASK_PERMISSIONS_READ_CONTACT);
            return;
        }

        pickContactIntent();
    }

    private void pickContactIntent() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER,ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts.HAS_PHONE_NUMBER};

                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                if(cursor.moveToFirst()) {
                    String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (hasPhone.equalsIgnoreCase(HAS_PHONE)) {
                        // Retrieve the phone number from the NUMBER column
                        int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        //  int nameColumn = cursor.getColumnIndex(Phone.DISPLAY_NAME);
                        String number = cursor.getString(column);
                        txtphone.setText(number);

                        //TODO à voir si à implementer pour la suite
                        //String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        /*if(name != null && !name.equals("")) {
                            int voiciIndex = messageToSend.indexOf("voici");
                            messageToSend.replace(0,voiciIndex,"Bonjour "+name+", ");
                            setTxtMessage();
                        }*/
                    }
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void sendSMSMessage() {
        String phoneNo = txtphone.getText().toString();
        String lieu = txtLieu.getText().toString();

        if(phoneNo == null || phoneNo.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Saisissez un numéro de téléphone", Toast.LENGTH_LONG).show();
            return;
        }
        if(lieu == null || lieu.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Donnez un nom à ce lieu", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            int hasSendSMSPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
            if (hasSendSMSPermission != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                    Utils.showMessageOKCancel(SendGpsActivity.this,"Vous devez donner les droits d'envoie de sms",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                            REQUEST_CODE_ASK_PERMISSIONS_SEND_SMS);
                                }
                            });
                    return;
                }
                requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                        REQUEST_CODE_ASK_PERMISSIONS_SEND_SMS);
                return;
            }
            SendSMS(phoneNo,lieu);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS pas envoyé, essayer encore.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void SendSMS(String phoneNo,String lieu) {
        phoneNo = phoneNo.trim();
        String messageToSend = formatMessageToSend();
        messageToSend = messageToSend.replace("].", "] (" + lieu + ")");
        SmsManager smsManager = SmsManager.getDefault();
        if(messageToSend.length() > 160)
        {
            ArrayList<String> parts = smsManager.divideMessage(messageToSend);
            smsManager.sendMultipartTextMessage(phoneNo, null, parts, null, null);
        }
        else
        {
            smsManager.sendTextMessage(phoneNo, null, messageToSend, null, null);
        }

        Toast.makeText(getApplicationContext(), "SMS envoyé.", Toast.LENGTH_LONG).show();
        Intent mainActivity = new Intent(SendGpsActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    private String formatMessageToSend() {
        StringBuilder messageToSend = new StringBuilder("Bonjour, voici les coordonnées GPS de ma position pour me rejoindre :");
        messageToSend.append("\n["+MainActivity.location.getLatitude()+","+MainActivity.location.getLongitude()+"].");
        messageToSend.append("\nEnvoyé à partir de l'appli. Kiraah http://bit.ly/2e57jqG");
        return messageToSend.toString();
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
                Intent contact = new Intent(SendGpsActivity.this, ContactActivity.class);
                SendGpsActivity.this.startActivity(contact);
                return true;
            case R.id.menu_help:
                Intent aide = new Intent(SendGpsActivity.this, AideActivity.class);
                SendGpsActivity.this.startActivity(aide);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
