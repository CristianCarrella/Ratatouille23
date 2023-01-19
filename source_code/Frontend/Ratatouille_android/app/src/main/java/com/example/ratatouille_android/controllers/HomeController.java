package com.example.ratatouille_android.controllers;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.example.ratatouille_android.models.Attivita;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.DispensaActivity;
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

public class HomeController {
    User loggedUser;
    Attivita attivita;
    HomeController homeController;
    HomeActivity homeActivity;
<<<<<<< Updated upstream
    String url = "http://192.168.1.47:8080/";
=======
    String url = "http://192.168.1.5:8080/";
>>>>>>> Stashed changes

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

    public User setInfoUser(){

        try {
            run(loggedUser.getIdUtente(), loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getNomeRistorante() {
        try {
            runAttivita(loggedUser.getIdRistorante(), loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void run(Integer id_utente, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "user/" + id_utente.toString())
                .header("Authorization", token)
                .build();

        serverRequest(client, request);
    }

    void runAttivita(Integer id_ristorante, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "business/nomeAttivita?id_ristorante=" + id_ristorante.toString())
                .header("Authorization", token)
                .build();

        serverRequestAttivita(client, request);
    }

    public void run(String nome, String cognome, String dataNascita) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("nome", nome)
                .add("cognome", cognome)
                .add("dataNascita", dataNascita)
                .build();
        Request request = new Request.Builder()
                .url(url + "user/account")
                .post(formBody)
                .header("Authorization", loggedUser.getToken())
                .build();
        serverRequest(client, request);
    }

    private String serverRequest(OkHttpClient client, Request request) {
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
        return null;
    }

    private void serverRequestAttivita(OkHttpClient client, Request request) {
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
                        JSONObject json = null;
                        try {
                            json = new JSONObject(myResponse);
                            Attivita attivita = new Attivita(homeActivity, json.getInt("idRistorante"), json.getString("nome"), json.getString("numeroTelefono"), json.getString("indirizzo"), json.getString("nomeImmagine"), json.getInt("idProprietario"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });
    }

}
