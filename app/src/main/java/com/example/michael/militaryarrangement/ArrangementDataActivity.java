package com.example.michael.militaryarrangement;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
/**
 * Activity Arrangement is the activity that shows all the arrangements from Arrangement database in ListView , also  the activity enable to the Admin to delete arrangements///
 */
public class ArrangementDataActivity extends AppCompatActivity {
ListView arrangements ;
    ArrangementDataSource arrangementDataSource ;
    private String[] _allColumns =
            {MyArrangementSQLiteHelper.COLUMN_ID, MyArrangementSQLiteHelper.COLUMN_USERNAME, MyArrangementSQLiteHelper.COLUMN_TIMEOFSTART,
                    MyArrangementSQLiteHelper.COLUMN_TIMEOFEND, MyArrangementSQLiteHelper.COLUMN_CHECK};
    EditText arrangementLocationId, arrangementStartTime ;
    Button deleteArrangement;
    String startTime , idLoc, checkToDelete ;
    Cursor cursor ;
    MyArrangementSQLiteHelper myArrangementSQLiteHelper ;
    SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrangement_data);
        myArrangementSQLiteHelper = new MyArrangementSQLiteHelper(this);
        db = myArrangementSQLiteHelper.getReadableDatabase();
        arrangementLocationId = (EditText) findViewById(R.id.userarrangement);
        arrangementStartTime = (EditText) findViewById(R.id.starttimearrangement);
        deleteArrangement = (Button) findViewById(R.id.delarrangementb);
        arrangements = (ListView) findViewById(R.id.arrangementview);
        arrangementDataSource = new ArrangementDataSource(this);
        showListView();


    }

    /**
     * Function showListView returns the ListView of all the arrangements from arrangements database
     */
    private void showListView()
    {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list_of_arrangements,
                arrangementDataSource.getCursorALLArrangements(), //
                _allColumns,
                new int[]{ R.id.insertedid, R.id.userinsert, R.id.startinput, R.id.endinput},
                0);

        arrangements.setAdapter(adapter);
    }

    /**
     * Function DeleteArrangement is getting checkToDelete from the Admin , find the arrangement from checkToDelete ,
     * and then delete the arrangement.
     * @param checkToDelete is of type String and holds the description of the primary key of the arrangements.
     */
    private void DeleteArrangement (String checkToDelete)
    {
        cursor = db.rawQuery("SELECT * FROM "+MyArrangementSQLiteHelper.TABLE_NAME+" WHERE "+MyArrangementSQLiteHelper.COLUMN_CHECK+"=?",new String[] {checkToDelete});
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                String whereClause = MyArrangementSQLiteHelper.COLUMN_CHECK+ "=?";
                String [] whereArgs = new String[] {checkToDelete};
                db.delete(MyArrangementSQLiteHelper.TABLE_NAME , whereClause , whereArgs);
                showListView();
            }
        }
    }

    /**
     * Function ArrangementCheckChoice is alertdialog that warns the Admin before the arrangement delete
     * and then if the Admin press "yes"- the arrangement will be deleted , else , the delete will be canceled.
     * @param view is of type View.
     */
    public void ArragementCheckChoice(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure , You want to remove arrangement?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        idLoc = arrangementLocationId.getText().toString();
                        startTime = arrangementStartTime.getText().toString();
                        checkToDelete  = ""+startTime+ idLoc;
                        DeleteArrangement(checkToDelete);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ArrangementDataActivity.this,"You cancelled the arrangement delete!",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
