import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

// Generated code
import generado.*;

import java.util.HashMap;

public class Server {

  public static KvDB_Handler handler;

  public static KeyValueDB.Processor processor;

  public static void main(String [] args) {
    try {
      handler = new KvDB_Handler();
      processor = new KeyValueDB.Processor(handler);

      Runnable simple = new Runnable() {
        public void run() {
          simple(processor);
        }
      };      

      new Thread(simple).start();

    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  public static void simple(KeyValueDB.Processor processor) {
    try {
      TServerTransport serverTransport = new TServerSocket(9090);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

      // Use this for a multithreaded server
      // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

      System.out.println("\nServidor iniciado...\n");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}