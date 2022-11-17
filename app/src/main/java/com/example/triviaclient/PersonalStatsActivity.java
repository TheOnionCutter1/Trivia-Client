package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.triviaclient.communicator.Communicator;
import com.example.triviaclient.communicator.RequestCode;
import com.example.triviaclient.communicator.Serializer;
import com.example.triviaclient.databinding.ActivityPersonalStatsBinding;

import java.io.IOException;
import java.util.ArrayList;

public class PersonalStatsActivity extends AppCompatActivity {
    private ActivityPersonalStatsBinding _binding;
    private Communicator _communicator;

    private void _fetchStats() {
        ArrayList<Byte> response;

        try {
            this._communicator.sendMessage(
                    Serializer.serializeRequest(RequestCode.GET_PLAYER_STATS, null)
            );
        } catch (IOException e) {
            
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._communicator = Communicator.getInstance();
        this._binding = ActivityPersonalStatsBinding.inflate(this.getLayoutInflater());
        setContentView(this._binding.getRoot());

        this._fetchStats();
    }
}