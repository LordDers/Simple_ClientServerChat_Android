
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class HandleCliente implements Runnable {
    BufferedReader reader;
    Socket socket;
    
    String ipCliente;
    String puertoCliente;
    
    // Recoger mensaje y datos del cliente
    public HandleCliente(Socket clientSocket) {
        try {
            socket = clientSocket;
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(isReader);
            
            ipCliente = socket.getInetAddress().getHostAddress();
            puertoCliente = "" + socket.getPort();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Leer mensajes y avisar a todos los clientes
    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                ChatServer.notificarClientes(message);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
