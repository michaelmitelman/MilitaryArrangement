package com.example.michael.militaryarrangement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;

/**
 * Activity ScheduleActivity is the activity that enable to add new arrangement///
 */
public class ScheduleActivity extends AppCompatActivity {

    Button addArrangementButton , viewArrangementsData ;
    EditText idInsert, userInsert, startTime, endTime;
    ArrangementDataSource arrangementDataSource ;
    MySQLiteHelper _dbHelper ;
    SQLiteDatabase db ;
    Cursor cursor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        arrangementDataSource = new ArrangementDataSource(this);
        _dbHelper = new MySQLiteHelper(this);
        db = _dbHelper.getReadableDatabase();
       addArrangementButton = (Button) findViewById(R.id.addarrangementbut);
        viewArrangementsData = (Button) findViewById(R.id.viewarrangementbut);
        idInsert = (EditText) findViewById(R.id.insertedid);
        userInsert = (EditText) findViewById(R.id.userinsert);
        startTime = (EditText) findViewById(R.id.startinput);
        endTime = (EditText) findViewById(R.id.endinput);
        addArrangementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Arrangement newArrangement = new Arrangement();
                String username = userInsert.getText().toString();
                String time_of_start = startTime.getText().toString();
                Time startingtime = Time.valueOf(time_of_start);
                String time_of_end = endTime.getText().toString();
                String ids = idInsert.getText().toString();
                final long id = Long.valueOf(ids);
                Time endingtime = Time.valueOf(time_of_end);
                String check = ""+ids+time_of_start;
                newArrangement.setMyID(id);
                newArrangement.setMyUsername(username);
                newArrangement.setMyTimeOfStart(startingtime);
                newArrangement.setMyTimeOfEnd(endingtime);
                newArrangement.setMyCheck(check);
                if ((id!=0)&&(userInsert.length()!=0)&&(startTime.length()!=0)&&(endTime.length()!=0)&&(check.length()!=0)&&ifExist(username))
                {
                 AddDataArrangement(newArrangement);
                }
                else {
                    toastMessage("soldier not exist!");
                }
            }
        });
        viewArrangementsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this , ArrangementDataActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Function toastMessage is getting a message from the user ,
     * and make text.
     * @param message is of type String and holds the message.
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Function AddDataArrangement is getting newArrangement from the Admin and
     * and then send suitable toastMessage.
     * @param newArrangement is of type Arrangement and holds the Arrangement.
     */
    public void AddDataArrangement (Arrangement newArrangement)
    {
        if (arrangementDataSource.insertArrangement(newArrangement))
        {
            toastMessage("Arrangement Added Sucssesfully!");
        }
        else {
            toastMessage("Arrangement Doesn't added sucseesfully! the arragment already exists in database.");
        }
    }

    /**
     * Function ifExist is getting a username from the user
     * and then find the Soldier in Soldiers database , if the Soldier exists in Soldiers database , the function returns true , else , the function returns false.
     * @param username is of type String and holds the username of the Soldier
     * @return a boolean – true or false.
     */
    public boolean ifExist (String username)
    {
        cursor = db.rawQuery("SELECT * FROM "+MySQLiteHelper.TABLE_NAME+" WHERE "+MySQLiteHelper.COLUMN_USERNAME+"=?",new String[] {username});
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                return true;
            }
        }
        return false;
    }

}
