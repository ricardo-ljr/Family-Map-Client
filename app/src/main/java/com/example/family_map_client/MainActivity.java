package com.example.family_map_client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import Activity.SettingsActivity;
import Request.RegisterRequest;
import Result.RegisterResult;
import ui.LoginFragment;
import ui.MapFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataCache data = DataCache.getInstance();
        FragmentManager fm = getSupportFragmentManager();
        Fragment login = fm.findFragmentById(R.id.mainContainer);
        Fragment mapFragment = fm.findFragmentById(R.id.mainContainer);

        if (data.isLoggedIn()) { // keeps track whether logged in and returns to map fragment from activities
            if(mapFragment == null) {
                mapFragment = new MapFragment();
                fm.beginTransaction().add(R.id.mainContainer, mapFragment).commit();
            }
        } else {
            if (login == null) {
                login = LoginFragment.newInstance(this);
                fm.beginTransaction().add(R.id.mainContainer, login).commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.settingsMenuItem) {
            return false;
        } else if (id == R.id.searchMenuItem) {
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayMap() {
        MapFragment mapFragment = new MapFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, mapFragment);
        fragmentTransaction.commit();
    }

    public void displaySettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
