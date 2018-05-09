package com.example.luke.assistantmanager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by User on 2/28/2017.
 */

public class EditPlayerActivity extends AppCompatActivity {

    private static final String TAG = "EditDataActivity";

    private Button btnUpdate, btnClose, btnDelete;
    private EditText editName, goals;

    PlayersDB mPlayersDB;

    private String selectedName, selectedPosition, selectedGoals;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_player);
        editName = (EditText) findViewById(R.id.editName);
        goals = (EditText) findViewById(R.id.goals);
        btnUpdate = (Button) findViewById(R.id.edit_player_button);
        btnDelete = (Button) findViewById(R.id.delete_player);
        btnClose = (Button) findViewById(R.id.cancel_button);
        mPlayersDB = new PlayersDB(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");
        selectedPosition = receivedIntent.getStringExtra("position");
        selectedGoals = receivedIntent.getStringExtra("goals");

        //set the text to show the current selected name
        editName.setText(selectedName);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editName.getText().toString();
                if(!item.equals("")){
                    mPlayersDB.updateName(item,selectedID,selectedName);
                    editName.setText("");
                    goals.setText("");
                    finish();
                    finish();
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayersDB.deleteName(selectedID,selectedName);
                editName.setText("");
                toastMessage("Player removed from database");
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

    public void AddPlayer(String playerName, String position, String goals) {
        boolean insertPlayer = mPlayersDB.addPlayer(playerName, position, goals);

        if (insertPlayer) {
            toastMessage("Player Successfully Added!");
        } else {
            toastMessage("Something went wrong");
        }
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
























