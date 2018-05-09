package com.example.luke.assistantmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFixtureActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FixturesDB mFixturesDB;
    private Button btnAdd, btnClose;
    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fixture);
        editName = (EditText) findViewById(R.id.editName);
        btnAdd = (Button) findViewById(R.id.add_fixture_button);
        btnClose = (Button) findViewById(R.id.cancel_button);
        mFixturesDB = new FixturesDB(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fixtureName = editName.getText().toString();
                if (editName.length() != 0 ) {
                    AddFixture(fixtureName);
                    editName.setText("");
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

    public void AddFixture(String fixtureName) {
        boolean insertPlayer = mFixturesDB.addFixture(fixtureName);

        if (insertPlayer) {
            toastMessage("Fixture Successfully Added!");
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

