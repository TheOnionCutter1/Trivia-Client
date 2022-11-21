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
import com.example.triviaclient.databinding.ActivitySignupBinding;

import java.io.IOException;
import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {
    private Communicator _communicator;
    private ActivitySignupBinding _binding;

    private void _signup(Requests.Signup req) {
        ArrayList<Byte> response;

        try {
            this._communicator.sendMessage(
                    Serializer.serializeRequest(RequestCode.SIGNUP, req)
            );
            response = this._communicator.receiveMessage();
        } catch (IOException e) {
            // Connectivity error
            this.startActivity(new Intent(this, LoadingScreenActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            this.finish();

            return;
        }
        if (response.get(0) == ResponseCode.ERROR.value) {
            // Display the error message
            this._binding.buttonSignup.setClickable(true);
            this._binding.textviewSignupError.post(() -> this._binding.textviewSignupError.setText(
                    Serializer.<Responses.Error>deserializeResponse(
                            response, Responses.Error.class
                    ).message)
            );
        } else {
            // Signup successful, return to login screen
            this.finish();
        }
    }

    private void _initializeComponents() {
        this._binding.buttonSignup.setOnClickListener((v) -> {
            Button b = (Button) v;
            Requests.Signup req = new Requests.Signup();

            b.setClickable(false);

            req.email = this._binding.edittextEmail.getText().toString();
            req.username = this._binding.edittextUsername.getText().toString();
            req.password = this._binding.edittextPassword.getText().toString();
            new Thread(() -> this._signup(req)).start();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._binding = ActivitySignupBinding.inflate(this.getLayoutInflater());
        this.setContentView(this._binding.getRoot());

        this._communicator = Communicator.getInstance();
        this._initializeComponents();
    }
}