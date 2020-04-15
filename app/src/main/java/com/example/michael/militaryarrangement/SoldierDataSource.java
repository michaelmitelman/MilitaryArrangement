package com.example.michael.militaryarrangement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.michael.militaryarrangement.MySQLiteHelper.COLUMN_ID;
import static com.example.michael.militaryarrangement.MySQLiteHelper.COLUMN_PASSWORD;
import static com.example.michael.militaryarrangement.MySQLiteHelper.COLUMN_PHONE;
import static com.example.michael.militaryarrangement.MySQLiteHelper.COLUMN_USERNAME;
import static com.example.michael.militaryarrangement.MySQLiteHelper.DATABASE_NAME;
import static com.example.michael.militaryarrangement.MySQLiteHelper.TABLE_NAME;

/**
 * Class SoldierDataSource is the class that hold a variety of operations on Soldiers database///
 */

public class SoldierDataSource {
    private final static Long ERROR_INSERT = -1l;
    private MySQLiteHelper _dbHelper;
    private String[] _allColumns =
            {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERNAME,
                    MySQLiteHelper.COLUMN_PHONE, MySQLiteHelper.COLUMN_PASSWORD};


    /**
     * Constructor SoldierDataSource
     * @param context is of type Context and holds the context of the MySQLiteHelper
     */
    public SoldierDataSource(Context context) {
        _dbHelper = new MySQLiteHelper(context);
    }

    public void close() {
        _dbHelper.close();
    }

    /**
     * Function insertSoldier is getting a soldierToSave from the user ,
     * and then inserts new Soldier to Soldiers database , the function returns true if the soldier insert to Soldiers database succeeded , or false if the soldier insert to Soldiers database failed.
     * @param soldierToSave is of type Soldier and holds the description of the soldier
     * @return a boolean - true or false.
     */
    public boolean insertSoldier(Soldier soldierToSave) {

        if (soldierToSave != null) {

            long id = soldierToSave.getMyID();
            String username = soldierToSave.getMyUsername();
            String phone = soldierToSave.getMyPhone();
            String password = soldierToSave.getMyPassword();
            ContentValues values = new ContentValues();

            if ((username != null) && (phone != null) && (password != null) && (id != 0)) {
                values.put(MySQLiteHelper.COLUMN_ID, id);
                values.put(MySQLiteHelper.COLUMN_USERNAME, username);
                values.put(MySQLiteHelper.COLUMN_PHONE, phone);
                values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
                boolean returnedResponse = this._dbHelper.addData(values);
                if (returnedResponse) {
                    Log.d("Text", "Soldier: DB: inserted new Soldier: id: " + id + " username: " + username + " phone: " + phone + " password: " + password);
                    return true;
                } else {
                    Log.d("Text", "Soldier: DB: ERROR in inserting Soldier: id: " + id + " username: " + username + " phone: " + phone + " password: " + password);
                    return false;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("finally")

    /**
     * Function getCursorALL is function that create cursor from query all table colums in Soldiers database
     * @return cursor.
     */
    public Cursor getCursorALL()
    {
        Cursor cursor;
        SQLiteDatabase database = _dbHelper.getReadableDatabase();
        cursor = database.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PHONE, COLUMN_PASSWORD},
                null, null, null, null, null);
        return cursor;
    }

}