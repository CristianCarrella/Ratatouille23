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

    private TextView nomeAttivita;
    private HomeController homeController;
    private HomeActivity homeActivity;

    public HomeFragment(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        nomeAttivita = (TextView) view.findViewById(R.id.nomeAttivitaHome);
        nomeAttivita.setText(homeActivity.getNomeRistorante());

        return view;
    }

    public TextView getNomeAttivita() {
        return nomeAttivita;
    }
}