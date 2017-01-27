import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import java.util.Arrays;

// Generated code
import generado.*;

import java.util.HashMap;

public class Server {
  public static KvDB_Handler handler;
  public static KeyValueDB.Processor processor;

  public static void main(String[] args) {
    int port;

    if(args.length == 1){
      try {
        port = Integer.parseInt(args[0]);
        handler = new KvDB_Handler();
        processor = new KeyValueDB.Processor(handler);

        Runnable simple = new Runnable() {
          public void run() {
            simple(processor, port);
          }
        };      

        new Thread(simple).start();

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    else{
      System.out.println("\nDebe ingresar el puerto como argumento.\nTerminando programa...\n");
    }
  }

  public static void simple(KeyValueDB.Processor processor, int port) {
    try {
      TServerTransport serverTransport = new TServerSocket(port);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

      // Use this for a multithreaded server
      // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

      System.out.println("\nServidor iniciado en el puerto "+port+"...\n");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}