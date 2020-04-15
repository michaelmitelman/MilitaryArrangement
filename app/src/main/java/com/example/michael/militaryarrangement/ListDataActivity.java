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
 * Activity ListDataActivity is the activity that shows all the soldiers in Soldiers database , also the activity enable to Admin delete Soldiers from the database , if the deleted soldier has arrangements , they deleted also///
 */
public class ListDataActivity extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    ListView table;
    SoldierDataSource soldierDataSource;
    EditText delUserName;
    String userToDelete ;
    Button delBut;
    Cursor cursor , cursor2 , cursor3 ;
    MyArrangementSQLiteHelper myArrangementSQLiteHelper ;
    SQLiteDatabase arrangement_db ;
    MySQLiteHelper _dbHelper ;
    SQLiteDatabase db ;
    private String[] _allColumns =
            {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERNAME,
                    MySQLiteHelper.COLUMN_PHONE, MySQLiteHelper.COLUMN_PASSWORD};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);
        myArrangementSQLiteHelper = new MyArrangementSQLiteHelper(this);
        arrangement_db = myArrangementSQLiteHelper.getReadableDatabase();
        _dbHelper = new MySQLiteHelper(this);
        db = _dbHelper.getReadableDatabase();
        delUserName = (EditText) findViewById(R.id.deletesoldiername);
        delBut = (Button) findViewById(R.id.delb);
        table = (ListView) findViewById(R.id.listview);
        soldierDataSource = new SoldierDataSource(this);
        showListView();
    }

    /**
     * Function showListView returns the ListView of all the soldiers from Soldiers database
     */
    private void showListView()
    {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.dataview,
                soldierDataSource.getCursorALL(), //
                _allColumns,
                new int[]{ R.id.soldierid, R.id.username, R.id.insertedphone, R.id.password},
                0);

        table.setAdapter(adapter);
    }

    /**
     * Function DeleteData is getting a userToDelete from the Admin ,
     * and then deletes the soldier from Soldiers database.
     * @param userToDelete is of type String and holds the username of the soldier that need to be deleted.
     */
    private void DeleteData (String userToDelete)
    {
     cursor = db.rawQuery("SELECT * FROM "+MySQLiteHelper.TABLE_NAME+" WHERE "+MySQLiteHelper.COLUMN_USERNAME+"=?",new String[] {userToDelete});
        if (cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                String whereClause = MySQLiteHelper.COLUMN_USERNAME+ "=?";
                String [] whereArgs = new String[] {userToDelete};
                db.delete(MySQLiteHelper.TABLE_NAME , whereClause , whereArgs);
                showListView();
            }
        }
    }

    /**
     * Function SoldierCheckChoice is alertdialog that warns the Admin before the soldier delete.
     * and then if the Admin press "yes"- the Soldier will be deleted , else , the delete will be canceled
     * @param view is of type View.
     */
    public void SoldierCheckChoice(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure , You want to remove the soldier and his arrangements?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        userToDelete = delUserName.getText().toString();
                        if (ifHasArrangement(userToDelete))
                        {
                            deleteSoldierArrangement(userToDelete);
                        }
                        DeleteData(userToDelete);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ListDataActivity.this,"You cancelled the Soldier delete!",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Function ifHasArrangement is getting userTodelete from the Admin
     * and then returns true if the soldier has arrangement , else , the function returns false.
     * @param userToDelete is of type String and holds the username of the Soldier
     * @return a boolean - true or false.
     */
    public boolean ifHasArrangement (String userToDelete)
    {
        cursor2 = arrangement_db.rawQuery("SELECT * FROM "+MyArrangementSQLiteHelper.TABLE_NAME+" WHERE "+MyArrangementSQLiteHelper.COLUMN_USERNAME+"=?",new String[] {userToDelete});
        if (cursor2!=null)
        {
            cursor2.moveToFirst();
            return true ;
        }
        return false ;
    }

    /**
     * Function deleteSoldierArrangement is getting userTodelete from the Admin
     * and then delete the soldier's arrangements.
     * @param userToDelete is of type String and holds the username of the Soldier.
     */
    public void deleteSoldierArrangement (String userToDelete)
    {
        cursor3 = arrangement_db.rawQuery("SELECT * FROM "+MyArrangementSQLiteHelper.TABLE_NAME+" WHERE "+MyArrangementSQLiteHelper.COLUMN_USERNAME+"=?",new String[] {userToDelete});
        if (cursor3!=null)
        {
            cursor3.moveToFirst();
            String whereClause = MyArrangementSQLiteHelper.COLUMN_USERNAME+ "=?";
            String [] whereArgs = new String[] {userToDelete};
            arrangement_db.delete(MyArrangementSQLiteHelper.TABLE_NAME , whereClause , whereArgs);
            Toast.makeText(ListDataActivity.this,"The arrangments of the deleted soldier deleted sucssesfully",Toast.LENGTH_LONG);
        }
        else Toast.makeText(ListDataActivity.this,"There are no arrangements for the deleted soldier",Toast.LENGTH_LONG);
    }

}
