package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.triviaclient.communicator.Communicator;

import java.io.IOException;

public class LoadingScreen extends AppCompatActivity {
    private Toast _connectionFailedMessage;

    private Button _connectToServer;
    private EditText _serverIP;

    private void _initializeComponents() {
        this._connectToServer = this.findViewById(R.id.button_connect);
        this._serverIP = this.findViewById(R.id.edittext_server_address);

        this._connectToServer.setOnClickListener(v -> {
            Button b = (Button) v;

            b.setClickable(false);
            b.setText(R.string.connect_button_pressed);
            b.setBackgroundColor(0xff000000);
            new Thread(() -> {
                Intent loginScreen = new Intent(this, Login.class);

                try {
                    Communicator.getInstance().connectToServer(this._serverIP.getText().toString());
                    this.startActivity(loginScreen);
                } catch (IOException e) {
                    this._connectionFailedMessage.show();
                    b.setText(R.string.connect_button_unpressed);
                    b.setBackgroundColor(this.getResources().getColor(R.color.purple_500));
                    b.setClickable(true);
                }
            }).start();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        this._connectionFailedMessage = Toast.makeText(LoadingScreen.this,
                "Connection to the server has failed", Toast.LENGTH_LONG);

        this._initializeComponents();
        this._connectToServer.performClick();
    }
}
