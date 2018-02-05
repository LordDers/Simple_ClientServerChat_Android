package com.example.usuario6i.chatandroidclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Usuario6i on 18/01/2018.
 */

public class Salas extends Activity {

    private ListView listViewSalasDisponibles;
    private FloatingActionButton fab;

    String nick;

    ArrayList<String> lista_salas = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    public static final String EXTRA_MESSAGE_SALAS = "Ruben";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salas_activity);

        listViewSalasDisponibles = findViewById(R.id.listViewSalas);
        fab = findViewById(R.id.fab);

        recogerNick();

        crearSalas();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anyadirSala();
            }
        });
    }



    // Recoger Nick
    private void recogerNick() {
        Intent intent = getIntent();
        nick = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_NICK);
    }

    // Crear Salas
    private void crearSalas() {

        // Crear sala Publica
        String[] salas = new String[] {
                "Publico"
        };

        // Crear lista de elementos
        lista_salas = new ArrayList<String>(Arrays.asList(salas));

        // Anyadir a la lista el usuario conectado
        // EN PRUEBAS *** NECESITA ACTUALIZARSE
        lista_salas.add(nick);

        // Crear ArrayAdapter de List
        arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, lista_salas);

        listViewSalasDisponibles.setAdapter(arrayAdapter);

        listViewSalasDisponibles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // i = 0 --> Sala Publica
                // Para que unicamente se pueda acceder a ella
                if (i == 0) {
                    cargarPantallaChat();
                }
            }
        });
    }

    // Anyadir Sala - Al pulsar boton
    private void anyadirSala() {

        View view = (LayoutInflater.from(Salas.this)).inflate(R.layout.anyadir_grupo_activity, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Salas.this);
        alertBuilder.setView(view);

        final EditText nombreGrupo = view.findViewById(R.id.nombreGrupo);

        alertBuilder.setCancelable(true)
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    lista_salas.add(nombreGrupo.getText().toString());
                    arrayAdapter.notifyDataSetChanged();

                }
            });

        Dialog dialog = alertBuilder.create();
        dialog.setTitle("Nuevo Grupo");
        dialog.show();
    }

    // Llamar a la activity de las salas
    // Finalizar activity
    private void cargarPantallaChat() {
        Intent intent = new Intent(this, Chat.class);
        intent.putExtra(EXTRA_MESSAGE_SALAS, nick);
        Salas.this.finish();
        startActivity(intent);
    }
}