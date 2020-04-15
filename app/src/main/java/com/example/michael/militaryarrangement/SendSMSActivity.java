package com.example.michael.militaryarrangement;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Activity SendSMSActivity is the activity that enable to the user send SMS to the admin or to other users that existed in Soldiers database///
 */
public class SendSMSActivity extends AppCompatActivity {
    MySQLiteHelper _dbHelper ;
    SQLiteDatabase db ;
    Button btnSendSMS;
    EditText txtUserName;
    EditText txtMessage;
    Cursor cursor ;
    String phoneNo , message ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        _dbHelper = new MySQLiteHelper(this);
        db = _dbHelper.getReadableDatabase();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtUserName = (EditText) findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnSendSMS.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String userToPhone = txtUserName.getText().toString();
                if (userToPhone.equals("Admin"))
                {
                    phoneNo = "0528029130";
                }
                if (!userToPhone.equals("Admin")) {
                    phoneNo = getPhoneNum(userToPhone);
                }
                message = txtMessage.getText().toString();
                if (userToPhone.length()>0 && message.length()>0&&phoneNo!="0")
                    sendSMS(phoneNo, message);
                else
                if (phoneNo.equals("0"))
                {
                    Toast.makeText(getBaseContext(),
                            "The user doesn't exist in db!",
                            Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();

            }
        });
    }
    /**
     * Function getPhoneNum is getting a userNameToPhone from the user
     * and then find the Soldier in Soldiers database and returns Soldier's phone number if he existed in Soldiers database , if the userNameToPhone doesn't exist in Soldiers database , the function returns 0.
     * @param userNameToPhone is of type String and holds the username of the Soldier
     * @return a String –  Soldier's phonenum or 0.
     */
    public String getPhoneNum (String userNameToPhone)
    {
        cursor = db.rawQuery("SELECT * FROM "+MySQLiteHelper.TABLE_NAME+" WHERE "+MySQLiteHelper.COLUMN_USERNAME+"=?",new String[] {userNameToPhone});
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                String phonemum = cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_PHONE));
                return phonemum ;
            }
        }
        return "0";
    }

    /**
     * Function sendSMS is getting a phoneNumber and message from the user ,
     * and then send to the phonenumber the message.
     * @param phoneNumber is of type String and holds the phonenumber of the Soldier that the SMS should be send to him.
     * @param message is of type String and holds the message content that need to be send.
     */
    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }


}
