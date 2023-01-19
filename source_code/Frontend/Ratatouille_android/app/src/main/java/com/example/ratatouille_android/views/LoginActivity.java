package com.example.ratatouille_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.LoginController;
import com.example.ratatouille_android.views.jfragment.HomeFragment;

import java.util.Observable;
import java.util.Observer;


public class LoginActivity extends AppCompatActivity implements Observer {
    TextView error;
    LoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        error = findViewById(R.id.error);

        TextView underLoginButton = findViewById(R.id.login_under_button);
        SpannableString content = new SpannableString("Login");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        underLoginButton.setText(content);

        TextView underSignupButton = findViewById(R.id.sign_up_under_button);
        View.OnClickListener onClickListenerSignup = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignUpActivity();
            }
        };
        underSignupButton.setOnClickListener(onClickListenerSignup);

        Button btn = findViewById(R.id.login_button);
        EditText emailField = findViewById(R.id.email_field);
        EditText passwordField = findViewById(R.id.password_field);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String email = String.valueOf(emailField.getText());
                //String password = String.valueOf(passwordField.getText());
                String email = "teka.freitas@example.com";
                String password = "meister";
                loginController = new LoginController(LoginActivity.this, email, password, error);
                loginController.requestToServer();

            }
        };
        btn.setOnClickListener(onClickListener);
    }


    @Override
    public void update(Observable o, Object arg) {
        /*if (o instanceof User) {

        }*/
    }

    public void goToSignUpActivity(){
        Intent switchActivityIntent = new Intent(this, SignUpActivity.class);
        startActivity(switchActivityIntent);
    }

}
