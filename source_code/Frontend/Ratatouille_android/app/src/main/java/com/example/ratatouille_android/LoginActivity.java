package com.example.ratatouille_android;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    public String url= "http://192.168.1.47:8080/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn = findViewById(R.id.login_button);
        EditText emailField = findViewById(R.id.email_field);
        EditText passwordField = findViewById(R.id.password_field);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(emailField.getText());
                String password = String.valueOf(passwordField.getText());
                try {
                    run(email, password);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        btn.setOnClickListener(onClickListener);

    }

    void run(String email, String password) throws IOException {
        System.out.println("Prova stampa: "  + email + " " + password);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("Prova fallimento");
                e.getCause();
                e.printStackTrace();
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                System.out.println("Prova1");
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Prova: " + myResponse);
                    }
                });

            }
        });
    }

}
