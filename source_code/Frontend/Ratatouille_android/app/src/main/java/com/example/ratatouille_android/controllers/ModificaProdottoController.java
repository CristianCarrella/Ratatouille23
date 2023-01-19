package com.example.ratatouille_android.controllers;

import android.content.Intent;
import android.util.Log;

import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.DispensaActivity;
import com.example.ratatouille_android.views.ModificaProdottoActivity;


import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ModificaProdottoController {
    User loggedUser;
    ModificaProdottoActivity modificaProdottoActivity;
    String[] categorie = {"Frutta", "Verdura", "Carne", "Pesce", "Uova", "LatteDerivati", "CerealiDerivati", "Legumi", "Altro"};
    String url = "http://192.168.1.5:8080/dispensa";

    public ModificaProdottoController(ModificaProdottoActivity modificaProdottoActivity, User loggedUser) {
        this.loggedUser = loggedUser;
        this.modificaProdottoActivity = modificaProdottoActivity;
    }

    public void goToDispensaActivity() {
        Intent switchActivityIntent = new Intent(modificaProdottoActivity, DispensaActivity.class);
        switchActivityIntent.putExtra("loggedUser", loggedUser);
        modificaProdottoActivity.startActivity(switchActivityIntent);
        modificaProdottoActivity.finish();
    }

    public void modificaServerPiattoInfo(Integer idProdotto, String nome, String descrizione, String costo, String quantita, String kg_or_lt, String categoria) {
        Float stato = Float.parseFloat(String.valueOf(quantita));
        Integer stato2 = stato.intValue();
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("idProdotto", idProdotto.toString())
                .add("nome", nome)
                .add("stato", stato2.toString())
                .add("descrizione", descrizione)
                .add("prezzo", costo)
                .add("quantita", quantita)
                .add("unitaMisura", kg_or_lt.toLowerCase(Locale.ROOT))
                .add("categoria", changeCategoriaSignature(categoria))
                .build();
        Request request = new Request.Builder()
                .url(url + "/modifyProduct")
                .post(formBody)
                .header("Authorization", loggedUser.getToken())
                .build();

        Log.v("Prova", loggedUser.getToken() + " " + idProdotto.toString() + " " + nome + " " + quantita.toString() + " " + descrizione + " " + costo.toString() + " " + kg_or_lt + " " + categoria);

        serverRequest(client, request);
    }

    private String changeCategoriaSignature(String categoria){
        if(categorie[0].equals(categoria))
            return "frutta";
        if(categorie[1].equals(categoria))
            return "verdura";
        if(categorie[2].equals(categoria))
            return "carne";
        if(categorie[3].equals(categoria))
            return "pesce";
        if(categorie[4].equals(categoria))
            return "uova";
        if(categorie[5].equals(categoria))
            return "latte_e_derivati";
        if(categorie[6].equals(categoria))
            return "cereali_e_derivati";
        if(categorie[7].equals(categoria))
            return "legumi";

        return "altro";
    }

    private void serverRequest(OkHttpClient client, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                modificaProdottoActivity.setErrorLableOnError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse = response.body().string();
                if(!myResponse.contains("status")) {
                    modificaProdottoActivity.setErrorLableOnSuccess();
                }

            }
        });
    }

    public void eliminaPiattoDalServer(Integer idProdotto) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("idProdotto", idProdotto.toString())
                .build();
        Request request = new Request.Builder()
                .url(url + "/deleteProduct")
                .post(formBody)
                .header("Authorization", loggedUser.getToken())
                .build();

        serverRequest(client, request);
    }
}
