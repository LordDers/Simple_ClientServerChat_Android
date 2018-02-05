package com.example.usuario6i.chatandroidclient;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Usuario6i on 16/01/2018.
 */

public class EnviarMensaje extends AsyncTask<Void, Void, String> {

    String mensaje = "";

    private TextView textViewMensaje;

    public EnviarMensaje(Activity activity, String mensaje, String nick) {
        textViewMensaje = activity.findViewById(R.id.editTextMsg);
        this.mensaje = nick + ": " + mensaje;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Despues de mandar el mensaje, se deja en blanco el textView
        textViewMensaje.setText("");
    }

    @Override
    protected String doInBackground(Void... params) {
        Socket socket = null;
        InetSocketAddress address = null;
        try {
            socket = new Socket();
            address = new InetSocketAddress(Conexion.IP, Conexion.PUERTO);
            socket.connect(address);

            // Chat
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.write(mensaje);

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}