package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.triviaclient.communicator.Communicator;
import com.example.triviaclient.databinding.ActivityLoadingScreenBinding;

import java.io.IOException;

public class LoadingScreenActivity extends AppCompatActivity {
    private Toast _connectionFailedMessage;

    private ActivityLoadingScreenBinding _binding;

    private void _connectToServer(Button connectButton) {
        Intent loginScreen = new Intent(this, LoginActivity.class);

        try {
            Communicator.getInstance().connectToServer(
                    this._binding.edittextServerAddress.getText().toString()
            );
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
        this._binding.buttonConnect.setOnClickListener(v -> {
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
        this._binding = ActivityLoadingScreenBinding.inflate(this.getLayoutInflater());
        setContentView(this._binding.getRoot());

        this._connectionFailedMessage = Toast.makeText(LoadingScreenActivity.this,
                R.string.connection_failed, Toast.LENGTH_LONG);

        this._initializeComponents();
        this._binding.buttonConnect.performClick();
    }
}
