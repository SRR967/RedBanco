import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        String message = "";


        while (!message.equalsIgnoreCase("Fin")) {

            message = fromNetwork.readLine();

            String [] subMessage= message.split("-");

            switch (subMessage[0]){

                case "REGISTRAR":
                    String user = subMessage[1];
                    String cc = subMessage[2];

                    if(usuarios.containsKey(cc)){
                        String answer = "Usuario ya existe";
                        toNetwork.println(answer);
                    }else{

                        usuarios.put(cc,new Cliente(user,cc,0));
                        toNetwork.println("Usuario creado con exito");
                    }
                    break;

                case "CONSULTAR":
                    cc= subMessage[1];
                    if (!usuarios.containsKey(cc)){
                        toNetwork.println("Usuario no encontrado");
                    }else {

                        String nombre = usuarios.get(cc).getNombre();
                        String saldo = String.valueOf(usuarios.get(cc).getSaldo());
                        toNetwork.println("Nombre usuario: "+nombre+", Cedula: "+cc+", Su saldo es: "+saldo);
                    }
                    break;

                case "DEPOSITAR":
                    cc = subMessage[1];
                    String deposito = subMessage[2];
                    int depositoUsuario = Integer.parseInt(deposito);
                    if(!usuarios.containsKey(cc)){
                        toNetwork.println("Usuario no encontrado");
                    }else{
                        if(depositoUsuario == 0){
                            toNetwork.println("Valor ingresado igual cero, por favor ingrese un valor");
                        }if(depositoUsuario > 0){
                            int saldo = usuarios.get(cc).getSaldo();
                            int depo = saldo+depositoUsuario;
                            usuarios.get(cc).setSaldo(depo);
                            int nuevo = usuarios.get(cc).getSaldo();
                            toNetwork.println("su saldo es:"+nuevo );
                        }
                    }
                    break;

                case "RETIRAR":

                    cc = subMessage[1];
                    String retiro = subMessage[2];
                    int valorRetirar = Integer.parseInt(retiro);
                    if(!usuarios.containsKey(cc)){
                        toNetwork.println("Usuario no encontrado");
                    }else{
                        if(valorRetirar == 0){
                            toNetwork.println("Valor ingresado igual cero, por favor ingrese un valor");
                        }else{
                            if(usuarios.get(cc).getSaldo() < valorRetirar){
                                toNetwork.println("Saldo insufuciente: el valor que desea retirar es mayor a su saldo");
                            }if(usuarios.get(cc).getSaldo() > valorRetirar){

                                int saldo = usuarios.get(cc).getSaldo();
                                int retirar = saldo-valorRetirar;
                                usuarios.get(cc).setSaldo(retirar);
                                int nuevo = usuarios.get(cc).getSaldo();
                                toNetwork.println("su saldo actualizado es:"+nuevo );

                            }
                        }
                    }
                    break;
            }

            System.out.println("[Server] From client: " + message);
        }
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
