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

      String[] parts = entradaTeclado.split(" +",2);
      String command = parts[0].toLowerCase();

      //System.out.println( Arrays.toString(parts) );
      if (!entradaTeclado.trim().isEmpty()) {
        switch(command){
          case "get":
            if( parts.length > 1)
              if( parts[1].length() > 0 ){
                String[] parts1 = parts[1].split(" +");

                if( parts1.length == 1 )
                  System.out.println( client.kvGet( parts1[0] ) );
                else
                  System.out.println("Debe ingresar la clave y el valor como argumentos.");
              }
              else
                System.out.println("Debe ingresar la clave como argumento.");
            else
              System.out.println("Debe ingresar la clave como argumento.");

            break;

          case "set":
            if( parts.length > 1)
              if( parts[1].length() > 0 ){
                String[] parts1 = parts[1].split(" +",2);

                if( parts1.length > 1)
                  if( parts1[1].length() > 0 )
                    System.out.println( client.kvSet( parts1[0], parts1[1] ) );
                  else
                    System.out.println("Debe ingresar la clave y el valor como argumentos.");
                else
                  System.out.println("Debe ingresar la clave y el valor como argumentos.");
              }
              else
                System.out.println("Debe ingresar la clave y el valor como argumentos.");
            else
              System.out.println("Debe ingresar la clave como argumento.");

            break;

          case "del":
            if( parts.length > 1)
              if( parts[1].length() > 0 ){
                String[] parts1 = parts[1].split(" +");

                if( parts1.length == 1 )
                  client.kvDel( parts1[0] );
                else
                  System.out.println("Debe ingresar la clave y el valor como argumentos.");
              }
              else
                System.out.println("Debe ingresar la clave como argumento.");
            else
              System.out.println("Debe ingresar la clave como argumento.");

            break;

          case "list":
            if( parts.length == 1 || parts[1].length() == 0){
              List<String> lista = client.kvList();

              for(String key : lista ){
                System.out.println("- "+key);
              }
            }
            else
              System.out.println("Argumentos no son requeridos.");
            
            break;

          case "exit":
            if( parts.length == 1 || parts[1].length() == 0)
              exit = true;
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