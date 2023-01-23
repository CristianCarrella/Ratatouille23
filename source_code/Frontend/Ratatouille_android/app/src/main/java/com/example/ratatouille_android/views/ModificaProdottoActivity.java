package com.example.ratatouille_android.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.ratatouille_android.R;
import com.example.ratatouille_android.controllers.ModificaProdottoController;
import com.example.ratatouille_android.models.Prodotto;
import com.example.ratatouille_android.models.User;
import com.example.ratatouille_android.views.dialogs.DeleteDialog;

import java.util.ArrayList;

public class ModificaProdottoActivity extends AppCompatActivity {
    User loggedUser;
    private ArrayList<Prodotto> dispensa;
    String nomeProdotto;
    ModificaProdottoController modificaProdottoController;
    String categorie [] = {"Frutta", "Verdura", "Carne", "Pesce", "Uova", "LatteDerivati", "CerealiDerivati", "Legumi", "Altro"};
    TextView errorLable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_prodotto);

        loggedUser = (User) getIntent().getSerializableExtra("loggedUser");
        nomeProdotto = (String) getIntent().getSerializableExtra("nomeProdotto");
        setDispensa((ArrayList<Prodotto>) getIntent().getSerializableExtra("dispensa"));

        modificaProdottoController = new ModificaProdottoController(this, loggedUser);

        TextView nomeProdottoSelezionato = findViewById(R.id.nome_prodotto_selezionato);
        EditText descrizioneField = findViewById(R.id.descrizione_input);
        EditText costoField = findViewById(R.id.costo_input);
        EditText quantitaField = findViewById(R.id.quantita_input);
        Spinner categoria = findViewById(R.id.spinner_categoria);
        errorLable = findViewById(R.id.error_lable);
        ToggleButton kgOrlt = findViewById(R.id.kg_or_lt);
        Button applicaButton = findViewById(R.id.ok_button);
        Button piubutton = findViewById(R.id.piu_button);
        Button menobutton = findViewById(R.id.meno_button);
        Button eliminabutton = findViewById(R.id.elimina_button);

        View.OnClickListener piuOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float value = Float.parseFloat(String.valueOf(quantitaField.getText())) + 1;
                quantitaField.setText(value.toString());
            }
        };

        View.OnClickListener menoOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float value = Float.parseFloat(String.valueOf(quantitaField.getText())) - 1;
                quantitaField.setText(value.toString());
            }
        };
        menobutton.setOnClickListener(menoOnClickListener);
        AutocompilazionePagina(nomeProdottoSelezionato, descrizioneField, costoField, quantitaField, categoria);

        ImageView backButton = findViewById(R.id.back_button2);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificaProdottoController.goToDispensaActivity();
            }
        };

        View.OnClickListener applicaListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String descrizione = descrizioneField.getText().toString();
                String costo = costoField.getText().toString();
                String quantita = quantitaField.getText().toString();
                String kg_or_lt = kgOrlt.getText().toString();
                String selectedCategoria = categoria.getSelectedItem().toString();
                int i = findIndexProductInDispensa();
                if (descrizione.equals("") || costo.equals("") || quantita.equals("")) {
                    errorLable.setText("Compilare tutti i campi");
                    errorLable.setTextColor(Color.RED);
                } else {
                    modificaProdottoController.modificaServerPiattoInfo(getDispensa().get(i).getIdProdotto(), nomeProdotto, descrizione, costo, quantita, kg_or_lt, selectedCategoria);
                }
            }
        };

        View.OnClickListener eliminaOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog dialog = new DeleteDialog(ModificaProdottoActivity.this.modificaProdottoController, ModificaProdottoActivity.this);
                dialog.show(getSupportFragmentManager(), "");
            }
        };
        eliminabutton.setOnClickListener(eliminaOnClickListener);
        piubutton.setOnClickListener(piuOnClickListener);
        applicaButton.setOnClickListener(applicaListener);
        backButton.setOnClickListener(onClickListener);
    }

    private void AutocompilazionePagina(TextView nomeProdottoSelezionato, EditText descrizioneField, EditText costoField, EditText quantitaField, Spinner categoria) {
        nomeProdottoSelezionato.setText(nomeProdotto);
        Integer i = findIndexProductInDispensa();

        setUpSpinner(categoria);

        descrizioneField.setText(getDispensa().get(i).getDescrizione());
        costoField.setText(getDispensa().get(i).getPrezzo().toString());
        quantitaField.setText(getDispensa().get(i).getQuantita().toString());
        switch (getDispensa().get(i).getCategoria()){
            case "frutta":
                categoria.setSelection(0);
                break;
            case "verdura":
                categoria.setSelection(1);
                break;
            case "carne":
                categoria.setSelection(2);
                break;
            case "pesce":
                categoria.setSelection(3);
                break;
            case "uova":
                categoria.setSelection(4);
                break;
            case "latte_e_derivati":
                categoria.setSelection(5);
                break;
            case "cereali_e_derivati":
                categoria.setSelection(6);
                break;
            case "legumi":
                categoria.setSelection(7);
                break;
            default:
                categoria.setSelection(8);
                break;
        }


    }

    private void setUpSpinner(Spinner categoria) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, categorie);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoria.setAdapter(adapter);
    }

    public int findIndexProductInDispensa() {
        int i = 0;
        for(Prodotto p : getDispensa()){
            if(p.getNome().equals(nomeProdotto)){
                return i;
            }
            i++;
        }
        return 0;
    }


    public void setErrorLableOnError(){
        errorLable.setText("Errore");
        errorLable.setTextColor(Color.RED);
    }

    public void setErrorLableOnCancel(){
        errorLable.setText("Operazione di eliminazione annullata");
        errorLable.setTextColor(Color.parseColor("#FF4500"));
    }

    public void setErrorLableOnSuccess(){
        errorLable.setText("Aggiornamento avvenuto con successo");
        errorLable.setTextColor(Color.GREEN);
    }

    public void setErrorLableOnMissed(){
        errorLable.setText("Compilare tutti i campi");
        errorLable.setTextColor(Color.RED);
    }

    public ArrayList<Prodotto> getDispensa() {
        return dispensa;
    }

    public void setDispensa(ArrayList<Prodotto> dispensa) {
        this.dispensa = dispensa;
    }
}