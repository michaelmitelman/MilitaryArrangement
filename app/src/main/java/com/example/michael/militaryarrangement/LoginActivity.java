package com.example.michael.militaryarrangement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Activity LoginActivity is the activity that the user Connecting to the app with his username and password///
 */
public class LoginActivity extends Activity  {
    Button loginb, cancelb;
    EditText name, password;
    TextView tx1;
    Cursor cursor ;
    int counter = 3;
    String username , insertedPassword, storedUsername, storedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final MySQLiteHelper _dbHelper = new MySQLiteHelper(this);
        final SQLiteDatabase db = _dbHelper.getReadableDatabase();
        loginb = (Button)findViewById(R.id.login);
        name = (EditText)findViewById(R.id.insertedphone);
        password = (EditText)findViewById(R.id.insertedpassword);
        cancelb = (Button)findViewById(R.id.button2);
        tx1 = (TextView)findViewById(R.id.textView3);
        tx1.setVisibility(View.GONE);

        loginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = name.getText().toString();
                insertedPassword = password.getText().toString();
                if (!username.equals("Admin")) {

                    cursor = db.rawQuery("SELECT * FROM "+MySQLiteHelper.TABLE_NAME+" WHERE "+MySQLiteHelper.COLUMN_USERNAME+"=? AND "+MySQLiteHelper.COLUMN_PASSWORD+"=?",new String[] {username, insertedPassword});
                    if (cursor != null) {
                        if(cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            storedUsername = cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_USERNAME));
                            storedPassword = cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_PASSWORD));
                           }
                        }
                }
                    if ((username.equals("Admin"))&&(insertedPassword.equals("Admin")))
                    {
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();
                        Intent Arrangement = new Intent(LoginActivity.this , ArrangementActivity.class);
                        Arrangement.putExtra("username", username);
                        startActivity(Arrangement);
                    }

                    else if (username.equals(storedUsername)&&(insertedPassword.equals(storedPassword)))
                    {
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();
                        Intent Soldier = new Intent(LoginActivity.this , SoldierActivity.class);
                        Soldier.putExtra("username",username);
                        startActivity(Soldier);
                    }
                    else
                    {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials ",Toast.LENGTH_SHORT).show();
                    tx1.setVisibility(View.VISIBLE);
                    tx1.setBackgroundColor(Color.RED);
                    counter--;
                    tx1.setText(Integer.toString(counter));

                    if (counter == 0) {
                        loginb.setEnabled(false);
                    }
                    }

            }
        });

        cancelb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}