package com.example.ratatouille_android.models;

import com.example.ratatouille_android.views.LoginActivity;

import java.io.Serializable;
import java.util.Observable;

public class User extends Observable implements Serializable {
    private int id_utente;
    private String nome, cognome, data_nascita, email, password, ruolo;
    private boolean isFirstAccess;
    private int aggiunto_da;
    private String data_aggiunta;
    private int id_ristorante;
    private String token;
    private String tk_expiration_timestamp;

    public User(){}

    public User(LoginActivity loginActivity, int id, String nome, String cognome, String dataNascita, String email, String password, String ruolo, boolean isFirstAccess, int aggiuntoDa, String dataAggiunta, int idRistorante, String token, String tk_expiration_timestamp){
        this.id_utente = id;
        this.cognome = cognome;
        this.nome = nome;
        this.data_nascita = dataNascita;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        this.isFirstAccess = isFirstAccess;
        this.aggiunto_da = aggiuntoDa;
        this.data_aggiunta = dataAggiunta;
        this.id_ristorante = idRistorante;
        this.token = token;
        this.tk_expiration_timestamp = tk_expiration_timestamp;
        addObserver(loginActivity);
        setChanged();
        notifyObservers();
    }

    public int getIdRistorante() {
        return id_ristorante;
    }

    public String getToken() {
        return token;
    }
}


