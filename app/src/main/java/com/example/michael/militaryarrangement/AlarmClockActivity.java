package com.example.michael.militaryarrangement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.provider.AlarmClock;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.sql.Time;
import java.lang.String;

import static android.content.Context.ALARM_SERVICE;
import static android.provider.AlarmClock.ACTION_SET_ALARM;
import static com.example.michael.militaryarrangement.R.id.AlarmClockTime;
import static com.example.michael.militaryarrangement.R.id.EndTime;
import static com.example.michael.militaryarrangement.R.id.StartTime;
import static com.example.michael.militaryarrangement.R.id.time;


/**
 * Activity AlarmClockActivity is the activity that enable to soldier choose his alarm clock time///
 */
public class AlarmClockActivity extends AppCompatActivity {
    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    String TimeOfAlarm;
    TimePicker timePicker;
    TextView Time ;
    Button Confirm ;
    private static AlarmClockActivity inst;
    private TextView alarmTextView;

    public static AlarmClockActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);
        timePicker = (TimePicker) findViewById(R.id.simpleTimePicker);
        timePicker.setIs24HourView(true);
        alarmTextView = (TextView) findViewById(R.id.alarmText);
        Time = (TextView) findViewById(R.id.nowtime);
        //Bundle extras2 = getIntent().getExtras();
        //ToggleButton alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Confirm = (Button) findViewById(R.id.confirm);
        final String second = ":00";
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), hourOfDay + " " + minute, Toast.LENGTH_SHORT).show();
                Time.setText(" Now Time is : " + hourOfDay + ":" + minute +second);
                TimeOfAlarm = ""+hourOfDay+":" +minute +second;
            }
        });
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SetAlarm = new Intent(AlarmClockActivity.this , SoldierActivity.class);
                SetAlarm.putExtra("TimeOfAlarm" ,TimeOfAlarm);
                startActivity(SetAlarm);
            }
        });
    }

    public void onToggleClicked(View view) {
        if (((ToggleButton) view).isChecked()) {
            Log.d("MyActivity", "Alarm On");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            Intent myIntent = new Intent(AlarmClockActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(AlarmClockActivity.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            setAlarmText("");
            Log.d("MyActivity", "Alarm Off");
        }
    }

    public void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    }
