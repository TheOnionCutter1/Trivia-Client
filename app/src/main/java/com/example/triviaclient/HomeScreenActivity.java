package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.triviaclient.communicator.Communicator;
import com.example.triviaclient.communicator.RequestCode;
import com.example.triviaclient.communicator.ResponseCode;
import com.example.triviaclient.communicator.Serializer;
import com.example.triviaclient.databinding.ActivityHomeScreenBinding;

import java.io.IOException;
import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {
    private Communicator _communicator;
    private String _username;
    private ActivityHomeScreenBinding _binding;

    /**
     * Send a logout request and move to the login screen if the client has successfully logged out.
     */
    private void _logout() {
        ArrayList<Byte> response;

        try {
            this._communicator.sendMessage(
                    Serializer.serializeRequest(RequestCode.LOGOUT, null)
            );
            response = this._communicator.receiveMessage();
        } catch (IOException e) {
            // Connectivity error
            this.startActivity(new Intent(this, LoadingScreenActivity.class));
            this.finish();

            return;
        }
        if (response.get(0) == ResponseCode.ERROR.value) {
            // Display the error message
            this._binding.buttonLogout.setClickable(true);
            Toast.makeText(this, "Logout Failed", Toast.LENGTH_LONG).show();
        } else {
            // Logout successful, return to login screen
            this.startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }

    /**
     * Start an activity and pass the username as a parameter for the intent.
     *
     * @param cls The activity to start.
     */
    private void _changeActivity(Class<?> cls) {
        this.startActivity(
                new Intent(this, cls).putExtra("username", this._username)
        );
    }

    /**
     * Initialize the activity's components.
     */
    private void _initializeComponents() {
        this._communicator = Communicator.getInstance();
        this._username = this.getIntent().getStringExtra("username");
        this._binding.textviewHomeScreenTitle.append(this._username + "!");

        this._binding.buttonJoinRoom.setOnClickListener(
                (v) -> this._changeActivity(JoinRoomActivity.class)
        );
        this._binding.buttonCreateRoom.setOnClickListener(
                (v) -> this._changeActivity(CreateRoomActivity.class)
        );
        this._binding.buttonMyStatistics.setOnClickListener(
                (v) -> this._changeActivity(PersonalStatsActivity.class)
        );
        this._binding.buttonGlobalHighScores.setOnClickListener(
                (v) -> this._changeActivity(GlobalStatsActivity.class)
        );
        this._binding.buttonLogout.setOnClickListener((v) -> {
            v.setClickable(false);
            new Thread(this::_logout).start();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._binding = ActivityHomeScreenBinding.inflate(this.getLayoutInflater());
        setContentView(this._binding.getRoot());

        this._initializeComponents();
    }
}
