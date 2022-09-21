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

import java.io.IOException;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private Communicator _communicator;

    private EditText _username;
    private EditText _password;
    private TextView _errorTextView;
    private Button _loginButton;

    private void _login(Requests.Login req) {
        ArrayList<Byte> response;
        Intent nextScreen;

        try {
            this._communicator.sendMessage(
                    Serializer.serializeRequest(RequestCode.LOGIN, req)
            );
            response = this._communicator.receiveMessage();
            if (response.get(0) == ResponseCode.ERROR.value) {
                // Display the error message
                this._loginButton.setClickable(true);
                this._errorTextView.post(() -> this._errorTextView.setText(
                        Serializer.<Responses.Error>deserializeResponse(
                                response, Responses.Error.class
                        ).message)
                );
            } else {
                // Login successful, move to the home screen
                nextScreen = new Intent(this, HomeScreen.class);
                nextScreen.putExtra("username", req.username);
                this.startActivity(nextScreen);
                this.finish();
            }
        } catch (IOException e) {
            // Connectivity error
            nextScreen = new Intent(this, LoadingScreen.class);
            this.startActivity(nextScreen);
            this.finish();
        }
    }

    /**
     * Initialize the activity's components.
     */
    private void _initializeComponents() {
        Button signupButton = this.findViewById(R.id.button_signup);

        this._loginButton = this.findViewById(R.id.button_login);

        this._errorTextView = this.findViewById(R.id.textview_login_error);
        this._username = this.findViewById(R.id.edittext_username);
        this._password = this.findViewById(R.id.edittext_password);

        this._loginButton.setOnClickListener((v) -> {
            Button b = (Button) v;
            Requests.Login req = new Requests.Login();

            b.setClickable(false);

            req.username = this._username.getText().toString();
            req.password = this._password.getText().toString();
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
        setContentView(R.layout.activity_login);

        this._communicator = Communicator.getInstance();
        this._initializeComponents();
    }
}