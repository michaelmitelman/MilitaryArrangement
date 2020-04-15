package com.example.michael.militaryarrangement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Time;

import static com.example.michael.militaryarrangement.MyArrangementSQLiteHelper.DATABASE_NAME;

/**
 * Class ArrangementDataSource is the class that hold a variety of operations on Arrangements database///
 */

public class ArrangementDataSource {
    private final static Long ERROR_INSERT = -1l;
    private MyArrangementSQLiteHelper myArrangementSQLiteHelper;
    private String[] _allColumns =
            {MyArrangementSQLiteHelper.COLUMN_ID, MyArrangementSQLiteHelper.COLUMN_USERNAME, MyArrangementSQLiteHelper.COLUMN_TIMEOFSTART,
                    MyArrangementSQLiteHelper.COLUMN_TIMEOFEND, MyArrangementSQLiteHelper.COLUMN_CHECK};

    public ArrangementDataSource (Context context) {
        myArrangementSQLiteHelper = new MyArrangementSQLiteHelper(context);
    }

    public void close () {
        myArrangementSQLiteHelper.close();
    }

    /**
     * Function insertArrangement is getting a arrangementToSave from the user ,
     * and then inserts new Arrangement to Arrangements database , the function returns true if the arrangement insert to Arrangements database succeeded , or false if the arrangement insert to Arrangements database failed.
     * @param arrangementToSave is of type Arrangement and holds the description of the arrangement
     * @return a boolean - true or false.
     */
    public boolean insertArrangement(Arrangement arrangementToSave) {

        if (arrangementToSave != null) {
            long id = arrangementToSave.getMyID();
            String username = arrangementToSave.getMyUsername();
            Time timeofstart = arrangementToSave.getMyTimeOfStart();
            Time timeofend = arrangementToSave.getMyTimeOfEnd();
            String starttime  = timeofstart.toString();
            String endtime = timeofend.toString();
            String idcheck = Long.toString(id);
            String check = ""+idcheck+starttime;
            ContentValues values = new ContentValues();

            if ((id != 0) && (username != null) && (timeofstart != null)&&(timeofend !=null)&&(check!=null)) {
                values.put(MyArrangementSQLiteHelper.COLUMN_ID , id);
                values.put(MyArrangementSQLiteHelper.COLUMN_USERNAME, username);
                values.put(MyArrangementSQLiteHelper.COLUMN_TIMEOFSTART, starttime);
                values.put(MyArrangementSQLiteHelper.COLUMN_TIMEOFEND, endtime);
                values.put(MyArrangementSQLiteHelper.COLUMN_CHECK, check);
                boolean returnedResponse = this.myArrangementSQLiteHelper.addArrangementData(values);
                if (returnedResponse) {
                    Log.d("Text", "Arrangements: DB: inserted new Arrangement: id: " + id + " username: " + username + " start_time: " + starttime + " end_time: " + endtime);
                    return true;
                } else {
                    Log.d("Text", "Arrangements: DB: ERROR in inserting Arrangement: id: " + id + " username: " + username + " start_time: " + starttime + " end_time: " + endtime);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Function getCursorALLArrangements is function that create cursor from query all table colums in Arrangements database
     * @return cursor.
     */
    public Cursor getCursorALLArrangements()
    {
        Cursor cursor;
        SQLiteDatabase database = myArrangementSQLiteHelper.getReadableDatabase();
        cursor = database.query(MyArrangementSQLiteHelper.TABLE_NAME, new String[]{MyArrangementSQLiteHelper.COLUMN_ID, MyArrangementSQLiteHelper.COLUMN_USERNAME, MyArrangementSQLiteHelper.COLUMN_TIMEOFSTART, MyArrangementSQLiteHelper.COLUMN_TIMEOFEND, MyArrangementSQLiteHelper.COLUMN_CHECK},
                null, null, null, null, null, null);
        return cursor;
    }


}
