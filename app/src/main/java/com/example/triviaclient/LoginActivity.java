package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.triviaclient.communicator.Communicator;
import com.example.triviaclient.communicator.RequestCode;
import com.example.triviaclient.communicator.Requests;
import com.example.triviaclient.communicator.ResponseCode;
import com.example.triviaclient.communicator.Responses;
import com.example.triviaclient.communicator.Serializer;
import com.example.triviaclient.databinding.ActivityLoginBinding;

import java.io.IOException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private Communicator _communicator;

    private ActivityLoginBinding _binding;

    private void _login(Requests.Login req) {
        ArrayList<Byte> response;
        Intent nextScreen;

        try {
            this._communicator.sendMessage(
                    Serializer.serializeRequest(RequestCode.LOGIN, req)
            );
            response = this._communicator.receiveMessage();
        } catch (IOException e) {
            // Connectivity error
            nextScreen = new Intent(this, LoadingScreenActivity.class);
            this.startActivity(nextScreen);
            this.finish();

            return;
        }
        if (response.get(0) == ResponseCode.ERROR.value) {
            // Display the error message
            this._binding.buttonLogin.setClickable(true);
            this._binding.textviewLoginError.post(() -> this._binding.textviewLoginError.setText(
                    Serializer.<Responses.Error>deserializeResponse(
                            response, Responses.Error.class
                    ).message)
            );
        } else {
            // Login successful, move to the home screen
            nextScreen = new Intent(this, HomeScreenActivity.class);
            nextScreen.putExtra("username", req.username);
            this.startActivity(nextScreen);
            this.finish();
        }
    }

    /**
     * Initialize the activity's components.
     */
    private void _initializeComponents() {
        Button signupButton = this.findViewById(R.id.button_signup);

        this._binding.buttonLogin.setOnClickListener((v) -> {
            Button b = (Button) v;
            Requests.Login req = new Requests.Login();

            b.setClickable(false);

            req.username = this._binding.edittextUsername.getText().toString();
            req.password = this._binding.edittextPassword.getText().toString();
            new Thread(() -> this._login(req)).start();
        });
        signupButton.setOnClickListener((v) -> {
            Intent signup = new Intent(this, SignupActivity.class);

            this.startActivity(signup);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._binding = ActivityLoginBinding.inflate(this.getLayoutInflater());
        setContentView(this._binding.getRoot());

        this._communicator = Communicator.getInstance();
        this._initializeComponents();
    }
}
