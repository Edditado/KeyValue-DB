import generado.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import java.util.*;

public class Client {

  public static void main(String [] args) {
    String ip;
    int port;

    if(args.length == 2){
      try {
        ip = args[0];
        port = Integer.parseInt(args[1]);

        TTransport transport;

        transport = new TSocket(ip, port);
        transport.open();

        TProtocol protocol = new  TBinaryProtocol(transport);
        KeyValueDB.Client client = new KeyValueDB.Client(protocol);

        perform(client);

        transport.close();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else{
      System.out.println("\nDebe ingresar la ip y el puerto del servidor como argumentos.\nTerminando programa...\n");
    }
  }

  private static void perform(KeyValueDB.Client client) throws TException{
    String entradaTeclado = "";
    Scanner entradaEscaner = new Scanner (System.in);
    boolean exit = false;

    while(!exit){
      System.out.print("\nkvDB> ");
      entradaTeclado = entradaEscaner.nextLine();
      entradaTeclado = entradaTeclado.trim();

      String[] parts = entradaTeclado.split(" +",2);
      String command = parts[0].toLowerCase();

      //System.out.println( Arrays.toString(parts) );
      if (!entradaTeclado.isEmpty()) {
        switch(command){
          case "get":
            if( parts.length > 1)
              System.out.println( client.kvGet( parts[1] ) );
            else
              System.out.println("Debe ingresar la clave como argumento.");

            break;

          case "set":
            if( parts.length > 1){
                String parts_trim = parts[1].trim();
                String[] parts1 = parts_trim.split(" +",2);

                if( parts1.length > 1)
                  System.out.println( client.kvSet( parts1[0], parts1[1] ) );
                else
                  System.out.println("Debe ingresar la clave y el valor como argumentos.");
            } 
            else
              System.out.println("Debe ingresar la clave como argumento.");

            break;

          case "del":
            if( parts.length > 1)
              client.kvDel( parts[1] );
            else
              System.out.println("Debe ingresar la clave como argumento.");

            break;

          case "list":
            if( parts.length == 1 ){
              List<String> lista = client.kvList();

              for(String key : lista ){
                System.out.println("- "+key);
              }
            }
            else
              System.out.println("Argumentos no son requeridos.");
            
            break;

          case "exit":
            if( parts.length == 1 )
              exit = true;
            else
              System.out.println("Argumentos no son requeridos.");
            
            break;

          case "help":
            if( parts.length == 1 ){
              System.out.println("Listado de comandos permitidos:\n");
              System.out.println("set [arg1] [arg2]       Crea/modifica claves con su respectivo valor.\n\t\t\tLos argumentos [arg1] y [arg2], representan clave y\n\t\t\tvalor,respectivamente.\n");
              System.out.println("get [arg1]              Obtiene el valor asociado a una clave.\n\t\t\tEl argumento [arg1] representa la clave.\n");
              System.out.println("del [arg1]              Elimina la clave y el valor asociado a dicha clave.\n\t\t\tEl argumento [arg1] representa la clave.\n");
              System.out.println("list                    Lista todas las claves almacenadas en memoria.\n");
              System.out.println("help                    Provee información sobre los comandos de kvDB.");
              System.out.println("exit                    Termina la conexión entre el cliente y el servidor.");
            }
            else
              System.out.println("Argumentos no son requeridos.");
            
            break;

          default:
            System.out.println("Comando incorrecto.");
            break;
        }
      }
    }
    
  }
}