import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class TCPServer {
    private static final Map<String, Cliente> usuarios = new HashMap<>();
    public static final int PORT = 3400;
    private ServerSocket listener;
    private Socket serverSideSocket;
    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    public TCPServer() {
        System.out.println("Echo TCP server is running on port: " + PORT);
    }

    public void init() throws Exception {
        listener = new ServerSocket(PORT);
        while (true) {
            serverSideSocket = listener.accept();
            createStreams(serverSideSocket);
            protocol(serverSideSocket);

        }
    }

    public void protocol(Socket socket) throws Exception {
        String message = fromNetwork.readLine();
        if (message.startsWith("REGISTRAR")){
            String user = message.substring(10);

            if(usuarios.containsKey(user)){
                String answer = "Usuario ya existe";
                toNetwork.println(answer);
            }else{
                

                usuarios.put(user,new Cliente(user));

                for (Map.Entry<String, Cliente> entry : usuarios.entrySet()) {
                    String clave = entry.getKey();
                    String valor = entry.getValue().getNombre();
                    toNetwork.println(clave+" "+valor);
                }
                toNetwork.println("Usuario creado con exito");
            }
        }





        System.out.println("[Server] From client: " + message);
    }

    private void createStreams(Socket socket) throws Exception {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


    public static void main(String args[]) throws Exception {
        TCPServer es = new TCPServer();
        es.init();
    }
}
