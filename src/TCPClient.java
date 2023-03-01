import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Scanner sc = new Scanner(System.in);

    public static final String SERVER = "localhost";
    public static final int PORT = 3400;
    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;
    private Socket clientSideSocket;

    public TCPClient() {
        System.out.println("Echo TCP client is running ... ");
    }

    public void init() throws Exception {
        clientSideSocket = new Socket(SERVER, PORT);
        createStreams(clientSideSocket);
        protocol(clientSideSocket);
        clientSideSocket.close();
    }


    public void protocol(Socket socket) throws Exception {

        int opcion;

        do {

            System.out.println("MENU");
            System.out.println("Ingrese el numero de la accion que desea hacer");
            System.out.println("1. Registrar una cuenta de banco");
            System.out.println("2. Consultar saldo");
            System.out.println("3. Depositar");
            System.out.println("4. Retirar dinero");
            System.out.println("5. Leer archivo de texto");
            System.out.println("6. Salir");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion){

                case 1:
                    System.out.println("Ingrese su nombre y numero de cedula seguidos de un guion");
                    String fromUser = SCANNER.nextLine();
                    toNetwork.println("REGISTRAR-" + fromUser);
                    String fromServer = fromNetwork.readLine();
                    System.out.println( "[Client] From server: "+ fromServer);
                    break;

                case 2:
                    System.out.println("Ingrese su numero de cedula");
                    fromUser = SCANNER.nextLine();
                    toNetwork.println("CONSULTAR-" + fromUser);
                    fromServer = fromNetwork.readLine();
                    System.out.println( "[Client] From server: "+ fromServer);
                    break;

                case 3:
                    System.out.println("Ingrese su numero de cedula y valor a depositar seguidos por un guion");
                    fromUser = SCANNER.nextLine();
                    toNetwork.println("DEPOSITAR-" + fromUser);
                    fromServer = fromNetwork.readLine();
                    System.out.println( "[Client] From server: "+ fromServer);
                    break;

                case 4:
                    System.out.println("Ingrese su numero de cedula y valor a retirar seguidos por un guion");
                    fromUser = SCANNER.nextLine();
                    toNetwork.println("RETIRAR-" + fromUser);
                    fromServer = fromNetwork.readLine();
                    System.out.println( "[Client] From server: "+ fromServer);
                    break;

                case 5:
                    System.out.println("Ingrese el nombre del archivo a leer");
                    fromUser = SCANNER.nextLine();
                    BufferedReader br = null;
                    try {
                        // Crear un objeto BufferedReader al que se le pasa
                        //   un objeto FileReader con el nombre del fichero
                        br = new BufferedReader(new FileReader(fromUser));
                        String texto;

                        // Leer la primera línea, guardando en un String
                        while((texto = br.readLine()) != null){
                            // Repetir mientras no se llegue al final del fichero

                            toNetwork.println(texto);
                            fromServer = fromNetwork.readLine();
                            System.out.println( "[Client] From server: "+ fromServer);
                        }

                    }
                    // Captura de excepción por fichero no encontrado
                    catch (FileNotFoundException ex) {
                        System.out.println("Error: Fichero no encontrado");
                        ex.printStackTrace();
                    }
                    // Captura de cualquier otra excepción
                    catch(Exception ex) {
                        System.out.println("Error de lectura del fichero");
                        ex.printStackTrace();
                    }
                    // Asegurar el cierre del fichero en cualquier caso
                    finally {
                        try {
                            // Cerrar el fichero si se ha podido abrir
                            if(br != null) {
                                br.close();
                            }
                        }
                        catch (Exception ex) {
                            System.out.println("Error al cerrar el fichero");
                            ex.printStackTrace();
                        }
                    }
                    break;

                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Error de comando");
                    break;


            }
        }while (opcion != 6);
        sc.close();
        SCANNER.close();


        /*
        System.out.print("Ingrese un mensaje: ");
        String fromUser = SCANNER.nextLine();
        toNetwork.println(fromUser);
        String fromServer = fromNetwork.readLine();
        System.out.println( "[Client] From server: "+ fromServer);

         */
    }
    private void createStreams(Socket socket) throws Exception {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public static void main(String args[]) throws Exception {
        TCPClient ec = new TCPClient();
        ec.init();
    }
}

