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

    private Button _connectButton;
    private EditText _serverIP;

    private void _connectToServer(Button connectButton) {
        Intent loginScreen = new Intent(this, Login.class);

        try {
            Communicator.getInstance().connectToServer(this._serverIP.getText().toString());
            this.startActivity(loginScreen);
            this.finish();
        } catch (IOException e) {
            this._connectionFailedMessage.show();
            connectButton.setText(R.string.connect_button_unpressed);
            connectButton.setBackgroundColor(this.getResources().getColor(R.color.purple_500));
            connectButton.setClickable(true);
        }
    }

    /**
     * Initialize the activity's components.
     */
    private void _initializeComponents() {
        this._connectButton = this.findViewById(R.id.button_connect);
        this._serverIP = this.findViewById(R.id.edittext_server_address);

        this._connectButton.setOnClickListener(v -> {
            Button b = (Button) v;

            b.setClickable(false);
            b.setText(R.string.connect_button_pressed);
            b.setBackgroundColor(0xff000000);
            new Thread(() -> this._connectToServer(b)).start();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        this._connectionFailedMessage = Toast.makeText(LoadingScreen.this,
                R.string.connection_failed, Toast.LENGTH_LONG);

        this._initializeComponents();
        this._connectButton.performClick();
    }
}
