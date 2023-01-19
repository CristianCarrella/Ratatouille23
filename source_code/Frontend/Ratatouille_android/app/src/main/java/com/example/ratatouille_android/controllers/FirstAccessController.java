package com.example.ratatouille_android.controllers;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.FirstAccessActivity;
import com.example.ratatouille_android.views.HomeActivity;

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

public class FirstAccessController {
    FirstAccessActivity firstAccessActivity;
    User loggedUser;
    String url = "http://192.168.1.5:8080/user/first-access";

    public FirstAccessController(FirstAccessActivity firstAccessActivity, User loggedUser){
        this.firstAccessActivity = firstAccessActivity;
        this.loggedUser = loggedUser;
    }

    public void updatePassword(String password) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("id_utente", String.valueOf(loggedUser.getIdUtente()))
                .add("newPassword", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("Authorization", loggedUser.getToken().toString())
                .build();
        serverUpdatePasswordRequest(password, client, request);
    }

    private void serverUpdatePasswordRequest(String password, OkHttpClient client, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                firstAccessActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(myResponse);
                            loggedUser.setPassword(password);
                            Log.v("Prova", myResponse);
                            if(Jobject.getInt("idRistorante") == loggedUser.getIdRistorante())
                                goToHomeActivity();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    public void goToHomeActivity(){
        Intent switchActivityIntent = new Intent(firstAccessActivity, HomeActivity.class);
        switchActivityIntent.putExtra("loggedUser", loggedUser);
        firstAccessActivity.startActivity(switchActivityIntent);
    }
}
