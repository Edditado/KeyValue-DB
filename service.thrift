namespace java generado
namespace c_glib generado

service KeyValueDB {

	string kvGet(1:string key),

	string kvSet(1:string key, 2:string value),

	void kvDel(1:string key),

	list<string> kvList()

}
