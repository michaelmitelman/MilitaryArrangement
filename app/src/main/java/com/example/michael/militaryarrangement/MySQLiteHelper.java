package com.example.michael.militaryarrangement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class MyArrangementSQLiteHelper is the class that defines the Soldiers database///
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";
    public static final String DATABASE_NAME = "Soldiers.db";
    public static final String TABLE_NAME = "Soldiersdb";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    "(" + COLUMN_ID + " long primary key, " +
                    COLUMN_USERNAME + " text not null," +
                    COLUMN_PHONE + " text not null," +
                    COLUMN_PASSWORD + " text not null" +
                    ")";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Text", "Soldier: " +
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//COLUMN_ID
    public boolean addData(ContentValues contentValues) {
        long result = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        if (contentValues.containsKey(COLUMN_ID) && contentValues.containsKey(COLUMN_USERNAME) &&
                contentValues.containsKey(COLUMN_PHONE)  &&
                contentValues.containsKey(COLUMN_PASSWORD)
                )
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
