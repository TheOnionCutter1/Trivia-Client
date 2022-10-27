package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.triviaclient.databinding.ActivityHomeScreenBinding;

public class HomeScreenActivity extends AppCompatActivity {
    private String _username;
    private ActivityHomeScreenBinding _binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._binding = ActivityHomeScreenBinding.inflate(this.getLayoutInflater());
        setContentView(this._binding.getRoot());

        this._username = this.getIntent().getStringExtra("username");

        this._binding.textviewHomeScreenTitle.append(this._username + "!");
    }
}
