package com.example.ratatouille_android.views;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;


import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.LoginController;
import com.example.ratatouille_android.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements Observer<User> {
    TextView error;
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        error = findViewById(R.id.error);
        Button btn = findViewById(R.id.login_button);
        EditText emailField = findViewById(R.id.email_field);
        EditText passwordField = findViewById(R.id.password_field);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(emailField.getText());
                String password = String.valueOf(passwordField.getText());
                loginController = new LoginController(LoginActivity.this, email, password, error);
                loginController.requestToServer();
            }
        };
        btn.setOnClickListener(onClickListener);
    }


    @Override
    public void onChanged(User user) {
        
    }
}
