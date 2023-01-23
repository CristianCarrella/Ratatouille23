package com.example.ratatouille_android.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ratatouille_android.R;

public class MainActivity extends AppCompatActivity {
    public static String address = "http://192.168.1.5:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.entra_in_app);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity();
            }
        };
        btn.setOnClickListener(onClickListener);
    }

    private void changeActivity(){
        Intent switchActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(switchActivityIntent);
    }
}