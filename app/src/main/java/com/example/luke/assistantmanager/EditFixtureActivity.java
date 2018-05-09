package com.example.luke.assistantmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by User on 2/28/2017.
 */

public class EditFixtureActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnUpdate,btnDelete,btnClose;
    private EditText editName;

    FixturesDB mFixturesDB;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_fixture);
        btnUpdate = (Button) findViewById(R.id.edit_fixture_button);
        btnDelete = (Button) findViewById(R.id.delete_fixture);
        btnClose = (Button) findViewById(R.id.cancel_button);
        editName = (EditText) findViewById(R.id.editName);
        mFixturesDB = new FixturesDB(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        //set the text to show the current selected name
        editName.setText(selectedName);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editName.getText().toString();
                if(!item.equals("")){
                    mFixturesDB.updateFixture(item,selectedID,selectedName);
                    finish();
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFixturesDB.deleteName(selectedID,selectedName);
                editName.setText("");
                toastMessage("Fixture removed from database");
                finish();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
























