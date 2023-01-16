package com.example.ratatouille_android.controllers;

import android.content.Intent;
import android.database.Observable;
import android.util.Log;
import android.widget.EditText;

import com.example.ratatouille_android.models.Prodotto;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.DispensaActivity;
import com.example.ratatouille_android.views.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeController {
    User loggedUser;
    HomeController homeController;
    HomeActivity homeActivity;
    String url = "http://192.168.1.5:8080/user";
    String nome, cognome, dataNascita;
    EditText nomeField, cognomeField, dataNascitaField;

    public HomeController(User loggedUser, HomeActivity homeActivity){
        this.loggedUser = loggedUser;
        this.homeActivity = homeActivity;
    }

    public void goToDispensaActivity(){
        Intent switchActivityIntent = new Intent(homeActivity, DispensaActivity.class);
        switchActivityIntent.putExtra("loggedUser", loggedUser);
        homeActivity.startActivity(switchActivityIntent);
    }

    public void onClickChangeInfoUser(){
        try {
            run(nomeField.getText().toString(), cognomeField.getText().toString(), dataNascitaField.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User setInfoUser(){

        try {
            run(loggedUser.getIdUtente(), loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void run(Integer id_utente, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/" + id_utente.toString())
                .header("Authorization", token)
                .build();

        serverRequest(client, request);
    }

    void run(String nome, String cognome, String dataNascita) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("nome", nome)
                .add("cognome", cognome)
                .add("data_nascita", dataNascita)
                .build();
        Request request = new Request.Builder()
                .url(url + "/account")
                .post(formBody)
                .header("Authorization", loggedUser.getToken())
                .build();
        serverRequest(client, request);
    }

    private void serverRequest(OkHttpClient client, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("prova", myResponse);
                    }
                });

            }
        });
    }

    public void setNome(EditText nome) {
        this.nomeField = nome;
    }

    public void setCognome(EditText cognome) {
        this.cognomeField = cognome;
    }

    public void setDataNascita(EditText dataNascita) {
        this.dataNascitaField = dataNascita;
    }
}
