package com.example.ratatouille_android.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ratatouille_android.controllers.HomeController;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.jfragment.FunctionFragment;
import com.example.ratatouille_android.views.jfragment.HomeFragment;
import com.example.ratatouille_android.views.jfragment.LogoutFragment;
import com.example.ratatouille_android.views.jfragment.NoticesFragment;
import com.example.ratatouille_android.R;
import com.example.ratatouille_android.views.jfragment.AccountFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class HomeActivity extends AppCompatActivity implements Observer {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    AccountFragment accountFragment = new AccountFragment();
    NoticesFragment noticesFragment = new NoticesFragment();
    FunctionFragment functionFragment = new FunctionFragment();
    LogoutFragment logoutFragment = new LogoutFragment();
    User loggedUser;
    HomeController homeController;
    EditText nomeField, cognomeField, dataNascitaField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loggedUser = (User) getIntent().getSerializableExtra("loggedUser");
        homeController = new HomeController(loggedUser, this);

        nomeField = accountFragment.getNome();
        cognomeField = accountFragment.getCognome();
        dataNascitaField = accountFragment.getDataNascita();

        TextView textNomeCognome = findViewById(R.id.textNomeCognome);
        TextView textNomeAttivita = findViewById(R.id.textnomeAttività);
        TextView textRuolo = findViewById(R.id.textRuolo);
        ImageView immagineProfilo = findViewById(R.id.imageView7);

        textNomeCognome.setText("Nome e Cognome");
        textNomeAttivita.setText("Attività");
        textRuolo.setText("Ruolo");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FloatingActionButton floatingActionButton = findViewById(R.id.home);
        bottomNavigationView.getMenu().findItem(R.id.nothing).setChecked(true);
        bottomNavigationView.getMenu().findItem(R.id.nothing).setEnabled(false);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.getMenu().findItem(R.id.nothing).setChecked(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            }
        };

        floatingActionButton.setOnClickListener(onClickListener);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();


        //BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notices);
        //badgeDrawable.setVisible(true);
        //badgeDrawable.setNumber(8);

        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.notices:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, noticesFragment).commit();
                        return true;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, accountFragment).commit();
                        return true;
                    case R.id.logout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, logoutFragment).commit();
                        return true;
                    case R.id.functions:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, functionFragment).commit();
                        return true;
                    case R.id.nothing:
                        return true;
                }

                return false;
            }
        });

        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);

        MaterialShapeDrawable bottomBarBackground = (MaterialShapeDrawable) bottomAppBar.getBackground();
        bottomBarBackground.setShapeAppearanceModel(
                bottomBarBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED,40)
                        .setTopLeftCorner(CornerFamily.ROUNDED,40)
                        .setBottomRightCorner(CornerFamily.ROUNDED,40)
                        .setBottomLeftCorner(CornerFamily.ROUNDED,40)
                        .build());


    }

    public void onClickChangeInfoUser(View v){
        try {
            homeController.run(nomeField.getText().toString(), cognomeField.getText().toString(), dataNascitaField.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickDispensaListener(View v){
        homeController.goToDispensaActivity();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}