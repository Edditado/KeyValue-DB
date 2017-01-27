import generado.*;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

import java.util.Scanner;

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
      System.out.print("kvDB> ");
      entradaTeclado = entradaEscaner.nextLine();

      switch(entradaTeclado){
        case "ping":
          System.out.println( client.ping() );
          break;

        case "exit":
          exit = true;
          break;

        default:
          System.out.println("Comando incorrecto.");
          break;
      }
    }
    
  }
}