/*author: eric.alvarez.chinchilla*/
package dominio.conjuntohospitales;

import gestionFicheros.GestionFicherosExtended;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class ControladorLogin {

	private static ConjuntoHospitales cnjh;
	private static GestionFicherosExtended gfe;

	public ControladorLogin() throws FileNotFoundException, IOException {
		cnjh = new ConjuntoHospitales();
		gfe = new GestionFicherosExtended();
		cargarDatos();
	}

	public void cargarDatos() throws FileNotFoundException, IOException {
		if (!gfe.existeFichero("data/cnjHospitales.txt"))
			gfe.guardarDatos("data/cnjHospitales.txt", new ArrayList<String>());
		ArrayList<String> data = gfe.cargarDatos("data/cnjHospitales.txt");
		for (int i = 0; i < data.size(); i++) {
			String ln = data.get(i);
			String[] l = ln.split(";");
			cnjh.addHospital(l[0], Integer.parseInt(l[1]));
		}
	}

	public boolean checkUserPass(String user, String pass, String idHosp)
			throws FileNotFoundException, IOException {
		if (user.compareTo("admin") == 0 && pass.compareTo("admin") == 0)
			return true;
		ArrayList<String> data = gfe
				.cargarDatos("data/login" + idHosp + ".txt");
		for (int i = 0; i < data.size(); i++) {
			String ln = data.get(i);
			String[] l = ln.split(";");
			if ((user.compareTo(l[0]) == 0 && pass.compareTo(l[1]) == 0))
				return true;
		}
		return false;
	}

	public LinkedHashMap<String, Integer> getHospitales()
			throws FileNotFoundException, IOException {
		cnjh.getHospitales().clear();
		cargarDatos();
		return cnjh.getHospitales();
	}

	public boolean addHosp(String text) throws IOException {
		ArrayList<String> cnjhosp = new ArrayList<String>();
		ArrayList<String> aux = new ArrayList<String>();
		if (text.replace(" ", "").matches("[a-zA-Z0-9]+")) {
			if (cnjh.getHospitales().containsKey(text))
				return false;
			Set<String> keys = cnjh.getHospitales().keySet();
			Iterator<String> it = keys.iterator();
			Integer maxId = 0;
			while (it.hasNext()) {
				String key = it.next();
				if (maxId < cnjh.getHospitales().get(key))
					maxId = cnjh.getHospitales().get(key);
				cnjhosp.add(key + ";" + cnjh.getHospitales().get(key) + ";");
			}
			maxId++;
			cnjhosp.add(text + ";" + maxId + ";");
			gfe.guardarDatos("data/cnjHospitales.txt", cnjhosp);
			gfe.guardarDatos("data/login" + maxId + ".txt", aux);
			gfe.guardarDatos("data/doctores" + maxId + ".txt", aux);
			gfe.guardarDatos("data/especialidad" + maxId + ".txt", aux);
			gfe.guardarDatos("data/turnos" + maxId + ".txt", aux);
			gfe.guardarDatos("data/asignaciones" + maxId + ".txt", aux);
			gfe.guardarDatos("data/periodos" + maxId + ".txt", aux);
			return true;
		} else
			return false;
	}

	public void eliminarHospital(String id) throws IOException {

		ArrayList<String> cnjhosp = new ArrayList<String>();
		Set<String> keys = cnjh.getHospitales().keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (cnjh.getHospitales().get(key).compareTo(Integer.parseInt(id)) != 0)
				cnjhosp.add(key + ";" + cnjh.getHospitales().get(key) + ";");

		}

		ArrayList<String> aux = gfe.cargarDatos("data/doctores" + id + ".txt");

		for (String s : aux) {
			String[] divis = s.split(";");
			gfe.eliminarDatos("data/restricciones" + divis[3] + ".txt");

		}

		gfe.guardarDatos("data/cnjHospitales.txt", cnjhosp);
		gfe.eliminarDatos("data/login" + id + ".txt");
		gfe.eliminarDatos("data/doctores" + id + ".txt");
		gfe.eliminarDatos("data/especialidad" + id + ".txt");
		gfe.eliminarDatos("data/turnos" + id + ".txt");
		gfe.eliminarDatos("data/asignaciones" + id + ".txt");
		gfe.eliminarDatos("data/periodos" + id + ".txt");

	}

}
