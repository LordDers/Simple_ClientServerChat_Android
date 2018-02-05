package com.example.usuario6i.chatandroidclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNick;
    private Button btnConectar;

    public static final String EXTRA_MESSAGE_NICK = "Ruben";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNick = findViewById(R.id.eTextNick);
        btnConectar = findViewById(R.id.btnConnect);

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarPantallaSalas();
            }
        });
    }

    // Llamar a la activity de las salas
    // Finalizar activity
    private void cargarPantallaSalas() {
        Intent intent = new Intent(this, Salas.class);
        intent.putExtra(EXTRA_MESSAGE_NICK, editTextNick.getText().toString());
        MainActivity.this.finish();
        startActivity(intent);
    }
}