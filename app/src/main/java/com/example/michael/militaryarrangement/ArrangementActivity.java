package com.example.michael.militaryarrangement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity ArrangementActivity is the activity that present to admin the options that he can choose in the application///
 */
public class ArrangementActivity extends AppCompatActivity {
TextView Welcome ;
Button Exit2 , ScheduleButton , Call , SoldiersButton , SendSMS;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrangement);
        startService(new Intent(this, MediaPlayerService.class));
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        String temp = " ";
        Welcome = (TextView) findViewById(R.id.textView8);
        Welcome.setText("Hello"+temp+username+"");
        Exit2 = (Button) findViewById(R.id.exit2);
        Exit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ScheduleButton = (Button) findViewById(R.id.schedulebutton);
        ScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Schedule = new Intent(ArrangementActivity.this , ScheduleActivity.class);
                startActivity(Schedule);
                stopService(new Intent(ArrangementActivity.this, MediaPlayerService.class));
            }
        });
        Call = (Button) findViewById(R.id.call);
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent CalltoSoldier = new Intent(ArrangementActivity.this ,CallActivity.class);
                startActivity(CalltoSoldier);
                stopService(new Intent(ArrangementActivity.this, MediaPlayerService.class));
            }
        });
        SoldiersButton = (Button) findViewById(R.id.soldierbutton);
        SoldiersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditSoldiersTable = new Intent(ArrangementActivity.this , SoldiersTableActivity.class);
                startActivity(EditSoldiersTable);
                stopService(new Intent(ArrangementActivity.this, MediaPlayerService.class));
            }
        });
        SendSMS = (Button) findViewById(R.id.smssend);
        SendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SMS = new Intent(ArrangementActivity.this , SendSMSActivity.class);
                startActivity(SMS);
                stopService(new Intent(ArrangementActivity.this, MediaPlayerService.class));
            }
        });
    }
}
