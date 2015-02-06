package dominio.ctrlAlgoritmo;

import java.text.ParseException;
import java.util.ArrayList;

import dominio.doctor.Doctor;
import dominio.graph.Graph;

/**
 * @author cristian.barrientos
 *
 */

public abstract class Restriccion {
	private int id;

	// PRE: -
	// POST: Devuelve el id de la restriccion
	public int getId() {
		return id;
	}

	// PRE: id debe ser una id valida de una restriccion
	// POST: Modifica el id de la restriccion
	public void setId(int id) {
		this.id = id;
	}

	// PRE: d debe ser un doctor valido y debe estar en la lista de doctores del
	// hospital,
	// g debe ser un grafo valido,
	// nodosFecha debe ser un arraylist de string valido (fecha;especialidad),
	// coste debe ser un double valido
	// POST: a�ade/elimina tanto aristas como nodos segun que restriccion la
	// llame.
	public abstract void construir(Doctor d, Graph g,
			ArrayList<String> nodosFecha, double coste) throws ParseException;

}
