package com.example.ratatouille_android.controllers;

import android.content.Intent;

import android.util.Log;


import com.example.ratatouille_android.models.Avviso;
import com.example.ratatouille_android.models.Prodotto;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.AvvisiNascostiActivity;
import com.example.ratatouille_android.views.DispensaActivity;
import com.example.ratatouille_android.views.HomeActivity;
import com.example.ratatouille_android.views.MainActivity;
import com.example.ratatouille_android.views.jfragment.NoticesFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NoticesController {

    NoticesFragment noticesFragment;
    HomeActivity homeActivity;
    User loggedUser;
    String url = MainActivity.address;
    ArrayList<Avviso> avvisiLetti = new ArrayList<Avviso>();
    ArrayList<Avviso> avvisiNascosti = new ArrayList<Avviso>();

    public NoticesController(HomeActivity homeActivity, User loggedUser) {
        this.homeActivity = homeActivity;
        this.loggedUser = loggedUser;
    }

    public void getNoticeFromServer(){
        try {
            runNotices(loggedUser.getIdRistorante(), loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getHiddenNoticeFromServer(){
        try {
            runHiddenNotices(loggedUser.getIdRistorante(), loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getReadNoticeFromServer(){
        try {
            runReadNotices(loggedUser.getIdRistorante(), loggedUser.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void markAsReadNotice(Integer id_avviso){
        try {
            runMarkNoticeAsRead(id_avviso, loggedUser.getIdRistorante());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void markAsNotReadNotice(Integer id_avviso){
        try {
            runMarkNoticeAsNotRead(id_avviso);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runMarkNoticeAsNotRead(Integer id_avviso) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("id_avviso", String.valueOf(id_avviso))
                .build();
        Request request = new Request.Builder()
                .url(url + "/avviso/segna-come-non-letto/" + id_avviso)
                .post(formBody)
                .header("Authorization", loggedUser.getToken())
                .build();
        serverRequestMarkNotice(client, request);
    }

    void runMarkNoticeAsRead(Integer id_avviso, Integer id_ristorante){
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("id_avviso", String.valueOf(id_avviso))
                .add("id_ristorante", String.valueOf(id_ristorante))
                .build();
        Request request = new Request.Builder()
                .url(url + "/avviso/segna-come-letto/" + id_avviso)
                .post(formBody)
                .header("Authorization",  loggedUser.getToken())
                .build();
        serverRequestMarkNotice(client, request);
    }

    private void serverRequestMarkNotice(OkHttpClient client, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                Log.v("prova", "prova");
            }
        });
    }

    void runNotices(Integer id_ristorante, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/avvisi?id_avviso=" + id_ristorante.toString())
                .header("Authorization", token)
                .build();

        serverRequest(client, request);
    }

    void runReadNotices(Integer id_ristorante, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/avvisi-viewed/" + loggedUser.getIdUtente())
                .header("Authorization", token)
                .build();

        serverRequestReadNotices(client, request);
    }

    void runHiddenNotices(Integer id_ristorante, String token) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url + "/avvisi-hidden/" + loggedUser.getIdUtente())
                .header("Authorization", token)
                .build();

        serverRequestHiddenNotices(client, request);
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
                final JSONArray[] jsonA = {null};
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jsonA[0] = new JSONArray(myResponse);
                            for (int i = 0; i < jsonA[0].length(); i++) {
                                JSONObject json = jsonA[0].getJSONObject(i);
                                Avviso a = new Avviso(homeActivity, json.getInt("idAvviso"), json.getInt("idUtente"), json.getInt("idRistorante"), json.getString("testo"), json.getString("dataOra"), json.getString("autore"), containsIdAvvisoLetti(json), containsIdAvvisoNascosti(json));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void serverRequestReadNotices(OkHttpClient client, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                final JSONArray[] jsonA = {null};
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jsonA[0] = new JSONArray(myResponse);
                            for (int i = 0; i < jsonA[0].length(); i++) {
                                JSONObject json = jsonA[0].getJSONObject(i);
                                Avviso a = new Avviso(homeActivity, json.getInt("idUtente"), json.getInt("idAvviso"));
                                avvisiLetti.add(a);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void serverRequestHiddenNotices(OkHttpClient client, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                final JSONArray[] jsonA = {null};
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jsonA[0] = new JSONArray(myResponse);
                            for (int i = 0; i < jsonA[0].length(); i++) {
                                JSONObject json = jsonA[0].getJSONObject(i);
                                Avviso a = new Avviso(homeActivity, json.getInt("idUtente"), json.getInt("idAvviso"));
                                avvisiNascosti.add(a);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private boolean containsIdAvvisoLetti(JSONObject json) throws JSONException {
        for(Avviso a : avvisiLetti){
            if(a.getIdAvviso() == json.getInt("idAvviso")){
                return true;
            }
        }
        return false;
    }

    private boolean containsIdAvvisoNascosti(JSONObject json) throws JSONException {
        for(Avviso a : avvisiNascosti){
            if(a.getIdAvviso() == json.getInt("idAvviso")){
                return true;
            }
        }
        return false;
    }


}
