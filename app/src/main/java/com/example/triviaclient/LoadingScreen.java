package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.triviaclient.communicator.Communicator;

import java.io.IOException;

public class LoadingScreen extends AppCompatActivity {
    private Communicator _communicator;
    private Toast _connectionFailedMessage;

    public Button connectToServer;

    private void _initializeButtons() {
        this.connectToServer = this.findViewById(R.id.button_connect);

        this.connectToServer.setOnClickListener(v -> {
            Button b = (Button) v;

            b.setClickable(false);
            b.setText(R.string.connect_button_pressed);
            b.setBackgroundColor(0xff000000);
            new Thread(() -> {
                try {
                    this._communicator.connectToServer();
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
        this._communicator = new Communicator();

        this._initializeButtons();
        this.connectToServer.performClick();
    }
}
