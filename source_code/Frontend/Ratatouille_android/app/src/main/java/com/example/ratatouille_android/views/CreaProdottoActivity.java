package com.example.ratatouille_android.views;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.ratatouille_android.controllers.CreaProdottoController;
import com.example.ratatouille_android.models.User;

public class CreaProdottoActivity extends AppCompatActivity {
    String categorie [] = {"Frutta", "Verdura", "Carne", "Pesce", "Uova", "LatteDerivati", "CerealiDerivati", "Legumi", "Altro"};
    CreaProdottoController creaProdottoController;
    User loggedUser;
    TextView errorLableAutoCompilation, errorLable;
    EditText descrizioneInput, nomeInput2, quantitaInput, costoInput, nomeInput;
    ToggleButton kg_or_lt;
    Spinner categoriaSpinner;
    Button auto_button;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_prodotto);

        nomeInput = findViewById(R.id.nome_input);
        nomeInput2  = findViewById(R.id.nome_input2);
        descrizioneInput = findViewById(R.id.descrizione_input);
        costoInput = findViewById(R.id.costo_input);
        quantitaInput = findViewById(R.id.quantita_input);
        kg_or_lt = findViewById(R.id.kg_or_lt);
        categoriaSpinner = findViewById(R.id.spinner_categoria);

        ImageView back_button = findViewById(R.id.back_button2);
        Button ok_button = findViewById(R.id.ok_button);
        errorLable = findViewById(R.id.error_lable);
        errorLableAutoCompilation = findViewById(R.id.error_lable2);
        auto_button = findViewById(R.id.automaticamente_button);

        loggedUser = (User) getIntent().getSerializableExtra("loggedUser");
        creaProdottoController = new CreaProdottoController(this, loggedUser);

        setUpSpinner(categoriaSpinner);


        View.OnClickListener backOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creaProdottoController.goToDispensaActivity();
            }
        };

        View.OnClickListener autoCompilazioneOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLable.setText("");
                String nomeProdotto = nomeInput.getText().toString();
                if(auto_button.getText().toString().equals("Non è quello che cercavo"))
                    creaProdottoController.setResultIndex(creaProdottoController.getResultIndex() + 1);

                if(!nomeProdotto.equals(""))
                    creaProdottoController.getInfoProdotto(nomeProdotto);
                else
                    setErrorLableAutoCompilationOnErrorEmpty();
            }
        };

        View.OnClickListener applicaOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorLableAutoCompilation.setText("");
                String nomeProdotto = nomeInput2.getText().toString();
                String descrizione = descrizioneInput.getText().toString();
                String costo = costoInput.getText().toString();
                String quantita = quantitaInput.getText().toString();
                String kgOrLt = kg_or_lt.getText().toString();
                if(nomeProdotto.equals("") || descrizione.equals("") ||costo.equals("") || quantita.equals(""))
                    setErrorLableOnErrorFieldNotAllCompiled();
                else
                    creaProdottoController.salvaProdotto(nomeProdotto, descrizione, costo, quantita, CreaProdottoActivity.this.categoria, kgOrLt);
            }
        };

        ok_button.setOnClickListener(applicaOnClickListener);
        auto_button.setOnClickListener(autoCompilazioneOnClickListener);
        back_button.setOnClickListener(backOnClickListener);

    }

    private void setUpSpinner(Spinner categoriaSpinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, categorie);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaSpinner.setAdapter(adapter);
    }

    private void setErrorLableAutoCompilationOnErrorEmpty(){
        errorLableAutoCompilation.setText("Campo nome non compilato");
        errorLableAutoCompilation.setTextColor(Color.RED);
    }

    public void setErrorLableAutoCompilationOnSuccess(){
        errorLableAutoCompilation.setText("Prodotto aggiunto con sucesso, alcune informazioni potrebbero non essere al completo");
        errorLableAutoCompilation.setTextColor(Color.GREEN);
        auto_button.setText("Non è quello che cercavo");
    }


    public void setNomeInput(String nome){
        nomeInput2.setText(nome);
    }

    public void setDescrizioneInputInput(String descrizione){
        descrizioneInput.setText(descrizione);
    }

    public void setKg_or_ltInput(String KgOrLt){
        if(KgOrLt.equals("lt"))
            kg_or_lt.setChecked(true);
        else
            kg_or_lt.setChecked(false);
    }

    public void setCategoriaInput(String categoria) {
        this.categoria = categoria;
        switch (categoria) {
            case "frutta":
                categoriaSpinner.setSelection(0);
                break;
            case "verdura":
                categoriaSpinner.setSelection(1);
                break;
            case "carne":
                categoriaSpinner.setSelection(2);
                break;
            case "pesce":
                categoriaSpinner.setSelection(3);
                break;
            case "uova":
                categoriaSpinner.setSelection(4);
                break;
            case "latte_e_derivati":
                categoriaSpinner.setSelection(5);
                break;
            case "cereali_e_derivati":
                categoriaSpinner.setSelection(6);
                break;
            case "legumi":
                categoriaSpinner.setSelection(7);
                break;
            default:
                categoriaSpinner.setSelection(8);
                break;
        }
    }

    public void setErrorLableOnError(){
        errorLable.setText("Errore nell'aggiunta del prodotto");
        errorLable.setTextColor(Color.RED);
    }
    private void setErrorLableOnErrorFieldNotAllCompiled(){
        errorLable.setText("Compilare tutti i campi");
        errorLable.setTextColor(Color.RED);
    }

    public void setErrorLableOnSuccess(){
        errorLable.setText("Prodotto aggiunto con successo");
        errorLable.setTextColor(Color.GREEN);
    }

    public void resetField(){
        nomeInput2.setText("");
        descrizioneInput.setText("");
        costoInput.setText("");
        quantitaInput.setText("");
        categoriaSpinner.setSelection(0);
        nomeInput.setText("");
        auto_button.setText("Compila automaticamente");
    }

}