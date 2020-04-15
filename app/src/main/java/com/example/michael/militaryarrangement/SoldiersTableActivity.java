package com.example.michael.militaryarrangement;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

/**
 * Activity SoldiersTableActivity is the activity that enable to the admin insert new Soldiers to Soldiers database///
 */
public class SoldiersTableActivity extends AppCompatActivity {
    SoldierDataSource soldierDataSource;
    private Button btnAdd, btnViewData;
    private EditText phoneNumSoldier, soldierId, passwordSoldier, usernameSoldier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soldiers_table);
        soldierId = (EditText) findViewById(R.id.soldierid);
        usernameSoldier = (EditText) findViewById(R.id.username);
        phoneNumSoldier = (EditText) findViewById(R.id.insertedphone);
        passwordSoldier = (EditText) findViewById(R.id.password);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.userdelete);
        soldierDataSource = new SoldierDataSource(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Soldier newSoldier = new Soldier();
                String ids = soldierId.getText().toString();
                final long id = Long.valueOf(ids);
                final String username = usernameSoldier.getText().toString();
                final String phone = phoneNumSoldier.getText().toString();
                final String password = passwordSoldier.getText().toString();
                newSoldier.setMyID(id);
                newSoldier.setMyUsername(username);
                newSoldier.setMyPhone(phone);
                newSoldier.setMyPassword(password);
                if ((soldierId.length() != 0)||(usernameSoldier.length() != 0)||(phoneNumSoldier.length() != 0)||(passwordSoldier.length() != 0)) {
                    AddData(newSoldier);

                } else {
                    toastMessage("You must put something in the text field!");
                }

            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SoldiersTableActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Function AddData is getting newSoldier from the user ,
     * and insert new Soldier to Soldiers database , if the insert succeeded , the function returns true ,else, the function returns false.
     * @param newSoldier is of type Soldier and holds the description of the soldier
     * @return a boolean – true or false.
     */
    public void AddData(Soldier newSoldier) {
        if(soldierDataSource.insertSoldier(newSoldier))
        {
            toastMessage("Soldier added sucssesfully!");
        }
        else {
            toastMessage("Soldier doesn't added sucssesfully! The soldier is already exists in database.");
        }
    }

    /**
     * Function toastMessage is getting a message from the user ,
     * and make text.
     * @param message is of type String and holds the message.
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
