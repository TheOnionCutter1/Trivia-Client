package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeScreen extends AppCompatActivity {
    private String _username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        this._username = this.getIntent().getStringExtra("username");
    }
}