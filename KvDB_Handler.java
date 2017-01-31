import org.apache.thrift.TException;

// Generated code
import generado.*;

import java.util.*;

public class KvDB_Handler implements KeyValueDB.Iface {
  private static HashMap<String, String> base = new HashMap<String, String>();

  public String kvGet(String key) {
  	if(base.get(key) != null)
  		return ( "key = "+base.get(key) );
  	else
  		return ("key = ");
  }

  public String kvSet(String key, String value) {
  	base.put(key, value);
  	return ("OK");
  }

  public void kvDel(String key){
  	base.remove(key);
  }

  public List<String> kvList(){
  	List lista = new ArrayList();

  	for(String key : base.keySet() ){
  		lista.add(key);
  	}

  	return lista;
  }


}