
import java.util.ArrayList;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class ChatServer {

    static ArrayList<PrintWriter> clientOutputStreams;

    public static void main (String[] args) {
        new ChatServer().inicio();
    }

    // Inicio para aceptar conexiones y empezar a escuchar los mensajes
    public void inicio() {
        clientOutputStreams = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(999);
            System.out.println("Servidor iniciado");

            while(true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread hiloEscuchaCliente = new Thread(new HandleCliente(clientSocket));
                hiloEscuchaCliente.start();
                //System.out.println("Cliente conectado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Enviar mensaje a los clientes conectados
    public static void notificarClientes(String message) {
        for (PrintWriter pw : clientOutputStreams) {
            try {
            	pw.println(message);
                pw.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}