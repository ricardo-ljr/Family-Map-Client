package com.example.family_map_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import Request.RegisterRequest;
import Result.RegisterResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    RegisterRequest reqRegister = new RegisterRequest(); // Shared jar working!
}
