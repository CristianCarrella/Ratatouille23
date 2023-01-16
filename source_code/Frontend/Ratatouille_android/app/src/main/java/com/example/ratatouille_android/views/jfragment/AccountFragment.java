package com.example.ratatouille_android.views.jfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ratatouille_android.R;

public class AccountFragment extends Fragment {

    private String nome, cognome;

    public static AccountFragment newInstance(String nome, String cognome) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(String.valueOf(R.id.nomeField), fragment.nome);
        args.putString(String.valueOf(R.id.cognomeField), fragment.cognome);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nome = getArguments().getString(String.valueOf(R.id.nomeField));
            cognome = getArguments().getString(String.valueOf(R.id.cognomeField));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }
}