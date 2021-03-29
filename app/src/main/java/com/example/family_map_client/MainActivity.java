package com.example.family_map_client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import Request.RegisterRequest;
import Result.RegisterResult;
import ui.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment login = fm.findFragmentById(R.id.mainContainer);


        if (login == null) {
            login = LoginFragment.newInstance(this);
            fm.beginTransaction().add(R.id.mainContainer, login).commit();
        }
    }


}
