package com.example.ratatouille_android.views.jfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.HomeController;
import com.example.ratatouille_android.views.HomeActivity;

public class HomeFragment extends Fragment {

    TextView nomeAttivita;
    HomeController homeController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        nomeAttivita = (TextView) view.findViewById(R.id.nomeAttivitaHome);



        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public TextView getNomeAttivita() {
        return nomeAttivita;
    }
}