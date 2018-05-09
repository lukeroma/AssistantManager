package com.example.luke.assistantmanager;

import android.app.Fragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.luke.assistantmanager.fragments.PlayersFragment;

public class AddPlayerActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    PlayersDB mPlayersDB;
    private Button btnAdd, btnClose;
    private EditText editName, goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player);
        editName = (EditText) findViewById(R.id.editName);
        goals = (EditText) findViewById(R.id.goals);
        btnAdd = (Button) findViewById(R.id.add_player_button);
        btnClose = (Button) findViewById(R.id.cancel_button);
        mPlayersDB = new PlayersDB(this);
        final Spinner spinner = (Spinner) findViewById(R.id.positions_spinner);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.positions_array, R.layout.spinner);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.Fragment fragment;
                String playerName = editName.getText().toString();
                String goalsNum = goals.getText().toString();
                String position = spinner.getSelectedItem().toString();
                if (editName.length() != 0 && goals.length() !=0) {
                    AddPlayer(playerName, position, goalsNum);
                    editName.setText("");
                    goals.setText("");
                    spinner.setSelection(0);
                    finish();
                } else {
                    toastMessage("You must put something in the text fields!");
                }

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

