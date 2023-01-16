package com.example.ratatouille_android.views.jfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.HomeController;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.HomeActivity;

public class FunctionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_function, container, false);
    }



}