package com.example.triviaclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.triviaclient.communicator.Communicator;
import com.example.triviaclient.communicator.RequestCode;
import com.example.triviaclient.communicator.Requests;
import com.example.triviaclient.communicator.ResponseCode;
import com.example.triviaclient.communicator.Serializer;

import java.io.IOException;
import java.util.ArrayList;

public class Login extends AppCompatActivity {
    private Communicator _communicator;

    private EditText _username;
    private EditText _password;

    /**
     * Initialize the activity's components.
     */
    private void _initializeComponents() {
        this._username = this.findViewById(R.id.edittext_username);
        this._password = this.findViewById(R.id.edittext_password);
        Button login = this.findViewById(R.id.button_login);

        login.setOnClickListener((v) -> {
            Button b = (Button) v;
            Requests.Login req = new Requests.Login();

            b.setClickable(false);
            b.setBackgroundColor(0xff000000);

            req.username = this._username.getText().toString();
            req.password = this._password.getText().toString();
            new Thread(() -> {
                ArrayList<Byte> response;

                try {
                    this._communicator.sendMessage(
                            Serializer.serializeRequest(RequestCode.LOGIN, req)
                    );
                    response = this._communicator.receiveMessage();
                    if (response.get(0) == ResponseCode.ERROR.value) {
                    } else {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this._communicator = Communicator.getInstance();
    }
}