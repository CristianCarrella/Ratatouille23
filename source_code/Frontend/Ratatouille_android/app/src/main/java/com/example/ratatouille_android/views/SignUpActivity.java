package com.example.ratatouille_android.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.example.ratatouille_android.R;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView underSignUpButton = findViewById(R.id.sign_up_under_button2);
        TextView underLoginButton = findViewById(R.id.login_under_button2);
        SpannableString content = new SpannableString("Account");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        underSignUpButton.setText(content);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        };
        underLoginButton.setOnClickListener(onClickListener);

    }

    public void goToLoginActivity(){
        Intent switchActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(switchActivityIntent);
    }
}