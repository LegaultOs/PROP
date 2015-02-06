/*@author: Oscar Carod Iglesias */
package dominio.conjuntohospitales;

import java.util.LinkedHashMap;

public class ConjuntoHospitales {

	private LinkedHashMap<String, Integer> hospitales;

	public ConjuntoHospitales() {
		hospitales = new LinkedHashMap<String, Integer>();
	}

	// pre: -
	// post: Arraylist de Hospital
	public LinkedHashMap<String, Integer> getHospitales() {
		return hospitales;
	}

	// pre: un array de hospitales
	// post: -
	public void setHospitales(LinkedHashMap<String, Integer> Hospitales) {
		this.hospitales = Hospitales;
	}

	// pre: un objeto hospital
	// post: devuelve true si se ha añadido correctamente, false si ya existia
	// en el sistema
	public boolean addHospital(String hosp, Integer id) {
		if (hosp.replace(" ", "").matches("[a-zA-Z0-9]+")) {
			if (!this.hospitales.containsKey(hosp)) {
				this.hospitales.put(hosp, id);
				return true;
			} else
				return false;
		} else
			return false;

	}

	// pre: un nombre de hospital
	// post: devuelve la posicion del hospital antes del borrado o -1 si no lo
	// ha encontrado
	public boolean borrarHospitalPorNombre(String nom) {
		Integer val = hospitales.remove(nom);
		if (val != null)
			return true;
		else
			return false;
	}
}
