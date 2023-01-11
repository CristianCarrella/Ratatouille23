package com.example.ratatouille_android.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.HomeActivity;
import com.example.ratatouille_android.views.LoginActivity;
import com.example.ratatouille_android.views.SignUpActivity;

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

public class LoginController {
    public String url = "http://192.168.1.5:8080/login";
    public String email, password;
    TextView error;
    User loggedUser;
    LoginActivity loginActivity;


    public LoginController(){ }

    public LoginController(LoginActivity loginActivity, String email, String password, TextView error) {
        this.loginActivity = loginActivity;
        this.email = email;
        this.password = password;
        this.error = error;
    }

    public void requestToServer(){
        try {
            run(email, password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void run(String email, String password) throws IOException {
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
                error.setText(R.string.error);
                error.setTextColor(Color.RED);
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                loginActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(myResponse);
                            loggedUser = new User(loginActivity, Jobject.getInt("idUtente"), Jobject.getString("nome"), Jobject.getString("cognome"), Jobject.getString("dataNascita"), Jobject.getString("email"), password, Jobject.getString("ruolo"), Jobject.getBoolean("firstAccess"), Jobject.getInt("aggiuntoDa"), Jobject.getString("dataAggiunta"), Jobject.getInt("idRistorante"));
                            error.setText(R.string.successo_login);
                            error.setTextColor(Color.GREEN);
                            goToHomeActivity();

                        } catch (JSONException e) {
                            error.setText(R.string.error);
                            error.setTextColor(Color.RED);
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }

    public void goToHomeActivity(){
        Intent switchActivityIntent = new Intent(loginActivity, HomeActivity.class);
        loginActivity.startActivity(switchActivityIntent);
    }
}
