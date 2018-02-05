package com.example.usuario6i.chatandroidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Usuario6i on 16/01/2018.
 */

public class Chat extends Activity {

    private TextView textViewMsgs;
    private EditText editTextMsg;
    private Button btnSendMsg;

    private String nick;

    public static final String EXTRA_MESSAGE_NICK = "Ruben";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        textViewMsgs = findViewById(R.id.textViewMensajes);
        editTextMsg = findViewById(R.id.editTextMsg);
        btnSendMsg = findViewById(R.id.btnEnviarMsg);

        recogerNick();

        // Conectar y Leer Mensajes del servidor
        conectarLeerMsgs();

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EnviarMensaje
                enviarMensajeAsyncTask(editTextMsg.getText().toString());
            }
        });
    }

    // Recoger Nick
    private void recogerNick() {
        Intent intent = getIntent();
        nick = intent.getStringExtra(Salas.EXTRA_MESSAGE_SALAS);
    }

    // Conectar con el servidor
    // Leer mensajes del servidor
    private void conectarLeerMsgs() {
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Socket socket = null;
                InetSocketAddress address = null;
                String messageFromServer = "";
                try {
                    socket = new Socket();
                    address = new InetSocketAddress(Conexion.IP, Conexion.PUERTO);
                    socket.connect(address);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

                    messageFromServer = in.readLine();

                    while (messageFromServer != null) {
                        mostrarMensajePantalla(messageFromServer);
                        messageFromServer = in.readLine();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        // esperar respuesta del servidor
        handler.sendMessage(handler.obtainMessage());
    }

    // Mostrar mensajes en Activity
    private void mostrarMensajePantalla(final String messageFromServer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewMsgs.append(messageFromServer + "\n");
            }
        });
    }

    // Enviar mensaje
    private void enviarMensajeAsyncTask(String mensaje) {
        new EnviarMensaje(this, mensaje, nick).execute();
    }

    // Al pulsar el boton atras, volver a las Salas
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Salas.class);
        intent.putExtra(EXTRA_MESSAGE_NICK, nick);
        Chat.this.finish();
        startActivity(intent);
    }
}
