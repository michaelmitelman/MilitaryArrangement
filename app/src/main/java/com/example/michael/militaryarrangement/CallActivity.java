package com.example.michael.militaryarrangement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity CallActivity is the activity that enable to user call to other user that exist in Soldiers database///
 */
public class
CallActivity extends AppCompatActivity {
    Button Call ;
    EditText callName;
    Cursor cursor ;
    String phoneNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        final MySQLiteHelper _dbHelper = new MySQLiteHelper(this);
        final SQLiteDatabase db = _dbHelper.getReadableDatabase();
        callName = (EditText) findViewById(R.id.callname);
        Call = (Button) findViewById(R.id.callnow);
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Callto = callName.getText().toString();
                if (Callto.equals("Admin"))
                {
                    Intent myIntent1 = new Intent(Intent.ACTION_DIAL , Uri.parse("tel:0528029130"));
                    startActivity(myIntent1);
                }

                if (!Callto.equals("Admin"))
                {
                    cursor = db.rawQuery("SELECT * FROM "+MySQLiteHelper.TABLE_NAME+" WHERE "+MySQLiteHelper.COLUMN_USERNAME+"=?",new String[] {Callto});
                    if (cursor != null) {
                        if(cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            phoneNum = cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_PHONE));
                            Intent myIntent2 = new Intent(Intent.ACTION_DIAL , Uri.parse("tel:"+ phoneNum));
                            startActivity(myIntent2);
                        }
                    }
                }


            }
        });

    }

}
