package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.triviaclient.communicator.Communicator;
import com.google.gson.Gson;

public class Login extends AppCompatActivity {
    private Communicator _communicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this._communicator = Communicator.getInstance();
    }
}