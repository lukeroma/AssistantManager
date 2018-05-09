package com.example.luke.assistantmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.luke.assistantmanager.fragments.TeamBuilderFragment;
import com.example.luke.assistantmanager.fragments.FixturesFragment;
import com.example.luke.assistantmanager.fragments.PlayersFragment;


public class HomeActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Assistant Manager");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    deleteFragment();
                    toolbar.setTitle("Assistant Manager");
                    return true;
                case R.id.team_builder:
                    toolbar.setTitle("Team Builder");
                    fragment = new TeamBuilderFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.fixtures:
                    toolbar.setTitle("Fixtures");
                    fragment = new FixturesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.players:
                    toolbar.setTitle("Players");
                    fragment = new PlayersFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void deleteFragment(){
        for (Fragment fragment1:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment1).commit();
        }
    }
    public void onAddPlayerSelected(View view) {
        // react to the menu item being selected...
        startActivity(new Intent(HomeActivity.this, AddPlayerActivity.class));

    }
    public void onAddFixtureSelected(View view) {
        // react to the menu item being selected...
        startActivity(new Intent(HomeActivity.this, AddFixtureActivity.class));

    }



}
