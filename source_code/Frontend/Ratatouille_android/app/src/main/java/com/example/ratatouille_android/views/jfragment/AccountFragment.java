package com.example.ratatouille_android.views.jfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.HomeController;

public class AccountFragment extends Fragment {

    private String nome, cognome;
    HomeController homeController;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        EditText nomeField = rootView.findViewById(R.id.nomeField);
        String nome = String.valueOf(nomeField.getText());
        EditText cognomeField = rootView.findViewById(R.id.cognomeField);
        String cognome = String.valueOf(nomeField.getText());
        EditText dateField = rootView.findViewById(R.id.dataField);
        String dataNascita = String.valueOf(nomeField.getText());
        homeController.setNome(nomeField);
        homeController.setCognome(cognomeField);
        homeController.setCognome(dateField);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}