package com.example.michael.militaryarrangement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

/**
 * Class MyArrangementSQLiteHelper is the class that defines the Arrangements database///
 */

public class MyArrangementSQLiteHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "_id"; //Arrangement location ID
    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_TIMEOFSTART = "time_of_start";
    public static final String COLUMN_TIMEOFEND = "time_of_end";
    public static final String COLUMN_CHECK = "check_dup";
    public static final String DATABASE_NAME = "Arrangements.db";
    public static final String TABLE_NAME = "Arrangementsdb";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " long not null, " +
                    COLUMN_USERNAME + " text not null," +
                    COLUMN_TIMEOFSTART + " text not null," +
                    COLUMN_TIMEOFEND + " text not null," +
                    COLUMN_CHECK + " text primary key not null " +
                    ")";

    public MyArrangementSQLiteHelper (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Text", "Arrangement: " +
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addArrangementData(ContentValues contentValues) {
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        if (contentValues.containsKey(COLUMN_USERNAME) && contentValues.containsKey(COLUMN_TIMEOFSTART) &&
                contentValues.containsKey(COLUMN_TIMEOFEND)&&contentValues.containsKey(COLUMN_ID) && contentValues.containsKey(COLUMN_CHECK))

        {
            result = db.insert(TABLE_NAME, null, contentValues);
        }

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

}
