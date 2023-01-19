package com.example.ratatouille_android.controllers;
import android.content.Intent;
import android.widget.TableLayout;

import com.example.ratatouille_android.models.Prodotto;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.CreaProdottoActivity;
import com.example.ratatouille_android.views.DispensaActivity;
import com.example.ratatouille_android.views.HomeActivity;
import com.example.ratatouille_android.views.ModificaProdottoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DispensaController {
    DispensaActivity dispensaActivitity;
    User loggedUser;
    String url = "http://192.168.1.47:8080/dispensa";
    ArrayList<Prodotto> dispensa = new ArrayList<Prodotto>();

    HomeActivity homeActivity;

    public DispensaController(){ }

    public DispensaController(DispensaActivity dispensaActivity, User loggedUser) {
        this.dispensaActivitity = dispensaActivity;
        this.loggedUser = loggedUser;
    }

    public void getProductFromServer(){
        try {
            run(loggedUser.getIdRistorante(), loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getProductByNameFromServer(String productName){
        try {
            run2(loggedUser.getIdRistorante(), productName , loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  getProductByCategoriaFromServer(String category){
        try {
            run3(loggedUser.getIdRistorante(), category , loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void run(Integer id_ristorante, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "?id_ristorante=" + id_ristorante.toString())
                .header("Authorization", token)
                .build();

        serverRequestWithSavingOfStatus(client, request);
    }

    void run2(Integer id_ristorante, String productName, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/prodotto?id_ristorante=" + id_ristorante.toString() + "&&nomeProdotto="+productName)
                .header("Authorization", token)
                .build();

        serverRequest(client, request);
    }

    void run3(Integer id_ristorante, String category, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/categoria?id_ristorante=" + id_ristorante.toString() + "&&categoria="+category)
                .header("Authorization", token)
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
                dispensaActivitity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray jsonA = null;
                        try {
                            jsonA = new JSONArray(myResponse);
                            for (int i = 0; i < jsonA.length(); i++){
                                JSONObject json = jsonA.getJSONObject(i);
                                Prodotto p = new Prodotto(dispensaActivitity, json.getInt("idProdotto"), json.getInt("idRistorante"), json.getString("nome"), json.getInt("stato"), json.getString("descrizione"), json.getDouble("prezzo"), json.getDouble("quantita"), json.getString("unitaMisura"),  json.getString("categoria"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    private void serverRequestWithSavingOfStatus(OkHttpClient client, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                dispensaActivitity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray jsonA = null;
                        try {
                            jsonA = new JSONArray(myResponse);
                            for (int i = 0; i < jsonA.length(); i++){
                                JSONObject json = jsonA.getJSONObject(i);
                                Prodotto p = new Prodotto(dispensaActivitity, json.getInt("idProdotto"), json.getInt("idRistorante"), json.getString("nome"), json.getInt("stato"), json.getString("descrizione"), json.getDouble("prezzo"), json.getDouble("quantita"), json.getString("unitaMisura"),  json.getString("categoria"));
                                dispensa.add(p);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    public void goToMenuActivity(){
        Intent switchActivityIntent = new Intent(dispensaActivitity, HomeActivity.class);
        switchActivityIntent.putExtra("loggedUser", loggedUser);
        dispensaActivitity.startActivity(switchActivityIntent);
    }

    public void goToModificaProdottoActivity(String nomeProdotto){
        Intent switchActivityIntent = new Intent(dispensaActivitity, ModificaProdottoActivity.class);
        switchActivityIntent.putExtra("loggedUser", loggedUser);
        switchActivityIntent.putExtra("nomeProdotto", nomeProdotto);
        switchActivityIntent.putExtra("dispensa", dispensa);
        dispensaActivitity.startActivity(switchActivityIntent);
    }

    public void goToCreaProdottoActivity() {
        Intent switchActivityIntent = new Intent(dispensaActivitity, CreaProdottoActivity.class);
        switchActivityIntent.putExtra("loggedUser", loggedUser);
        dispensaActivitity.startActivity(switchActivityIntent);
    }
}
