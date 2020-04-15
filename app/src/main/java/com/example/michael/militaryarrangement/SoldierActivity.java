package com.example.michael.militaryarrangement;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.app.TimePickerDialog;


import org.w3c.dom.Text;

import java.sql.Time;
import java.text.SimpleDateFormat;

import static com.example.michael.militaryarrangement.R.id.StartTime;

/**
 * Activity SoldierActivity is the activity that enable to present to soldier the options that he can choose in the application///
 */
public class SoldierActivity extends AppCompatActivity {
    Button Exit, AlarmClock , Call2 , SMSSend ;
    TextView Welcome , TimeOfStart , TimeOfEnd , AlarmClockTime ;
    Cursor cursor ;
    String StartTime , EndTime, Alarm_time;
    MyArrangementSQLiteHelper myArrangementSQLiteHelper;
    SQLiteDatabase db ;
    static String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soldier);
        startService(new Intent(this, MediaPlayerService.class));
        myArrangementSQLiteHelper = new MyArrangementSQLiteHelper(this);
        db = myArrangementSQLiteHelper.getReadableDatabase();
        Bundle extras = getIntent().getExtras();
        if (extras.containsKey("username")) {
            username = extras.getString("username");
        }
        if (extras.containsKey("TimeOfAlarm")) {
            Alarm_time = extras.getString("TimeOfAlarm");
            AlarmClockTime = (TextView) findViewById(R.id.AlarmClockTime);
            AlarmClockTime.setText(Alarm_time);
        }
        String temp = " ";
        Welcome = (TextView) findViewById(R.id.textView7);
        Welcome.setText("Hello"+temp+username+"");
        TimeOfStart = (TextView) findViewById(R.id.StartTime);
        TimeOfStart.setText(GetStartTime(username));
        TimeOfEnd = (TextView) findViewById(R.id.EndTime);
        TimeOfEnd.setText(GetEndTime(username));
        Exit = (Button) findViewById(R.id.exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(SoldierActivity.this, MediaPlayerService.class));
                finish();
            }
        });
        Call2 = (Button) findViewById(R.id.callbut2);
        Call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent2 = new Intent(SoldierActivity.this , CallActivity.class);
                startActivity(callIntent2);
                stopService(new Intent(SoldierActivity.this, MediaPlayerService.class));
            }
        });

        AlarmClock = (Button) findViewById(R.id.alarmclock);
        AlarmClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Alarm = new Intent(SoldierActivity.this, AlarmClockActivity.class);
                Alarm.putExtra("TimeOfStart" , StartTime);
                startActivity(Alarm);
                stopService(new Intent(SoldierActivity.this, MediaPlayerService.class));
            }
        });
        SMSSend = (Button) findViewById(R.id.smssend);
        SMSSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SendingSMS = new Intent(SoldierActivity.this, SendSMSActivity.class);
                startActivity(SendingSMS);
                stopService(new Intent(SoldierActivity.this, MediaPlayerService.class));
            }
        });

    }

    /**
     * Function GetStartTime is getting a username , searching in Arrangements database the start time of Soldier's Arrangement ,
     * and then returns Soldier's arrangement start time if he has arrangement , else , the function will return "No Arrangement".
     * @param username is of type String and holds the username of the Soldier
     * @return an String – Soldier's arrangement start time or "No Arrangement".
     */
    public String GetStartTime (String username)
    {
        cursor = db.rawQuery("SELECT * FROM "+MyArrangementSQLiteHelper.TABLE_NAME+" WHERE "+MyArrangementSQLiteHelper.COLUMN_USERNAME+"=?",new String[] {username});
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                StartTime = cursor.getString(cursor.getColumnIndex(MyArrangementSQLiteHelper.COLUMN_TIMEOFSTART));
                return StartTime;
            }
        }
        return "No Arragement";
    }

    /**
     * Function GetEndTime is getting a username , searching in Arrangements database the end time of Soldier's Arrangement ,
     * and then returns Soldier's arrangement end time if he has arrangement , else , the function will return "No Arrangement".
     * @param username is of type String and holds the username of the Soldier
     * @return an String – Soldier's arrangement end time or "No Arrangement".
     */
    public String GetEndTime (String username)
    {
        cursor = db.rawQuery("SELECT * FROM "+MyArrangementSQLiteHelper.TABLE_NAME+" WHERE "+MyArrangementSQLiteHelper.COLUMN_USERNAME+"=?",new String[] {username});
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                EndTime = cursor.getString(cursor.getColumnIndex(MyArrangementSQLiteHelper.COLUMN_TIMEOFEND));
                return EndTime;
            }
        }
        return "No Arragement";
    }



}
