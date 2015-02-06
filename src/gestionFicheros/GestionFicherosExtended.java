/*@author eric.alvarez.chinchilla*/

package gestionFicheros;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestionFicherosExtended extends GestionFicheros {

	public GestionFicherosExtended() {
	}

	public ArrayList<String> importarDatos(String path)
			throws FileNotFoundException, IOException {
		String ps, line;
		ArrayList<String> list = new ArrayList<>();
		Path pathAbs = Paths.get(path).toAbsolutePath();
		ps = pathAbs.toString();
		BufferedReader reader = new BufferedReader(new FileReader(ps));
		while ((line = reader.readLine()) != null)
			list.add(line);
		reader.close();
		return list;
	}

	public void exportarDatos(ArrayList<String> lista, String path)
			throws IOException {
		Path pathAbs = Paths.get(path).toAbsolutePath();
		String ps = pathAbs.toString();
		File file = new File(ps);
		file.getParentFile().mkdirs();
		FileWriter w = new FileWriter(file);

		for (int i = 0; i < lista.size(); i++) {
			w.write(lista.get(i));
			if (i != (lista.size() - 1))
				w.write("\n");
		}
		w.close();
	}

	@Override
	public void guardarDatos(String path, ArrayList<String> lista)
			throws IOException {
		Path pathAbs = Paths.get(path).toAbsolutePath();
		String ps = pathAbs.toString();
		FileWriter w = new FileWriter(ps);
		for (int i = 0; i < lista.size(); i++) {
			w.write(lista.get(i) + "\n");

		}
		w.close();
	}

	public boolean existeFichero(String path) {
		Path pathAbs = Paths.get(path).toAbsolutePath();
		String ps = pathAbs.toString();
		File file = new File(ps);
		file.getParentFile().mkdirs();
		if (file.exists())
			return true;
		else
			return false;
	}

	public boolean eliminarDatos(String path) {
		Path pathAbs = Paths.get(path).toAbsolutePath();
		String ps = pathAbs.toString();
		File file = new File(ps);
		return file.delete();
	}

	public void addDatos(String path, String lista) throws IOException {
		Path pathAbs = Paths.get(path).toAbsolutePath();
		String ps = pathAbs.toString();
		FileWriter w = new FileWriter(ps, true);
		w.write(lista);
		w.write("\n");
		w.close();
	}

}
