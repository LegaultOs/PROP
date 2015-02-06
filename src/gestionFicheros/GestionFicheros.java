/*@author eric.alvarez.chinchilla*/

package gestionFicheros;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestionFicheros {

	public GestionFicheros() {
	}

	// PRE: path != null, el path tiene que apuntar a algun filetext de
	// extensión txt dentro de la carpeta del proyecto
	// POST: Retorna una lista de Strings con el fichero de texto parseado de
	// líneas a elementos de la lista.
	public ArrayList<String> cargarDatos(String path)
			throws FileNotFoundException, IOException {
		ArrayList<String> list = new ArrayList<>();
		Path pathAbs = Paths.get(path).toAbsolutePath();
		String ps = pathAbs.toString();
		BufferedReader reader = new BufferedReader(new FileReader(ps));
		String line;
		while ((line = reader.readLine()) != null)
			list.add(line);
		reader.close();
		return list;
	}

	// PRE: path != null, los directorios indicados en el path han de existir
	// previamente
	// POST: Se guardan los elementos de la list por filas en el fichero de
	// datos filename en el directorio path.
	public void guardarDatos(String path, ArrayList<String> lista)
			throws IOException {
		Path pathAbs = Paths.get(path).toAbsolutePath();
		String ps = pathAbs.toString();
		FileWriter w = new FileWriter(ps);
		for (int i = 0; i < lista.size(); i++) {
			w.write(lista.get(i));
			if (i != (lista.size() - 1))
				w.write("\n");
		}
		w.close();
	}
}
