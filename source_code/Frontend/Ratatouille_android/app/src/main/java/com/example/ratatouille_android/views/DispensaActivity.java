package com.example.ratatouille_android.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.DispensaController;
import com.example.ratatouille_android.models.Prodotto;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.dialogs.FilterTableDialog;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DispensaActivity extends AppCompatActivity implements Observer {
    private TableLayout tableLayout;
    private DispensaController dispensaController;
    private User loggedUser;
    private ArrayList<Prodotto> dispensa = new ArrayList<>();
    private String selectedFilter = "Nome";
    private TextView filterShower;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensa);

        EditText searchBar = findViewById(R.id.search_field);
        ImageView filter_icon = findViewById(R.id.filter_icon);
        ImageView back_button = findViewById(R.id.back_button);
        ImageView piu_button = findViewById(R.id.piu_button2);
        filterShower = findViewById(R.id.filter_type);
        tableLayout = findViewById(R.id.table);


        loggedUser = (User) getIntent().getSerializableExtra("loggedUser");
        dispensaController = new DispensaController(this, loggedUser);
        dispensaController.getProductFromServer();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cleanTable(tableLayout);
                if(selectedFilter.equals("Nome"))
                    dispensaController.getProductByNameFromServer(searchBar.getText().toString());
                else
                    dispensaController.getProductByCategoriaFromServer(searchBar.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterTableDialog dialog = new FilterTableDialog(DispensaActivity.this);
                dialog.show(getSupportFragmentManager(), "");
            }
        };

        View.OnClickListener backOnMenuListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispensaController.goToMenuActivity();
            }
        };

        View.OnClickListener piuOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispensaController.goToCreaProdottoActivity();
            }
        };

        piu_button.setOnClickListener(piuOnClickListener);
        back_button.setOnClickListener(backOnMenuListener);
        searchBar.addTextChangedListener(textWatcher);
        filter_icon.setOnClickListener(onClickListener);
    }

    private void cleanTable(TableLayout table) {
        int childCount = table.getChildCount();
        if (childCount > 1) {
            table.removeViews(1, childCount - 1);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Prodotto){
            dispensa.add((Prodotto) o);
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            TextView t = creaTextViewNomeProdotto((Prodotto) o);
            TextView t1 = creaTextViewCategoria((Prodotto) o);
            ProgressBar progressBar = creaProgressBarStatoProdotto((Prodotto) o);

            tr.addView(t);
            tr.addView(t1);
            tr.addView(progressBar);
            tableLayout.addView(tr);
        }

    }


    @NonNull
    private ProgressBar creaProgressBarStatoProdotto(Prodotto o) {
        ProgressBar progressBar = new ProgressBar(this,null, android.R.attr.progressBarStyleHorizontal);
        Integer value = 0;
        if(o.getQuantita().intValue() > 100)
            value = 100;
        else
            value = o.getQuantita().intValue();
        progressBar.setProgress(value);
        progressBar.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            progressBar.setMinHeight(200);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            progressBar.setMaxWidth(150);
        }
        progressBar.setPadding(20, 10, 10, 10);
        return progressBar;
    }

    @NonNull
    private TextView creaTextViewCategoria(Prodotto o) {
        TextView t1 = new TextView(this);
        t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        t1.setText(o.getCategoria());
        t1.setMinHeight(200);
        t1.setMaxWidth(150);
        //t1.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.table_cell, null));
        t1.setPadding(20, 10, 10, 10);
        t1.setGravity(Gravity.CENTER);
        return t1;
    }

    @NonNull
    private TextView creaTextViewNomeProdotto(Prodotto o) {
        TextView t = new TextView(this);
        t.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        SpannableString stringSpan = new SpannableString(o.getNome());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                System.out.print("Cliccato");
                dispensaController.goToModificaProdottoActivity(o.getNome());
            }
        };
        t.setMovementMethod(LinkMovementMethod.getInstance());
        stringSpan.setSpan(clickableSpan, 0, o.getNome().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        t.setText(stringSpan);
        t.setMinHeight(200);
        t.setMaxWidth(150);
        t.setGravity(Gravity.CENTER);
        //t.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.table_cell, null));
        t.setPadding(20, 10, 10, 10);
        return t;
    }

    public void setSelectedFilter(String selectedFilter) {
        this.selectedFilter = selectedFilter;
    }

    public void setFilterShower(String filter) {
        this.filterShower.setText(filter);
    }
}