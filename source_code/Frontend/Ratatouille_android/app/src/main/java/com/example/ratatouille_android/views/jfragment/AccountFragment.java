package com.example.ratatouille_android.views.jfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.HomeController;
import com.example.ratatouille_android.views.HomeActivity;

import java.io.IOException;

public class AccountFragment extends Fragment implements View.OnClickListener {

    EditText nomeField, cognomeField, dateField;
    HomeActivity homeActivity;

    public AccountFragment(HomeActivity homeActivity){
        this.homeActivity = homeActivity;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Button btn = view.findViewById(R.id.confermaButton);
        btn.setOnClickListener(this);
        nomeField = view.findViewById(R.id.nomeField);
        cognomeField = view.findViewById(R.id.cognomeField);
        dateField = view.findViewById(R.id.dataField);

        return view;
    }

    @Override
    public void onClick(View v) {
        try {
            homeActivity.getHomeController().run(nomeField.getText().toString(), cognomeField.getText().toString(), dateField.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}