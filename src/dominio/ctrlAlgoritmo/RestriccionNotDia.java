package dominio.ctrlAlgoritmo;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import dominio.doctor.Doctor;
import dominio.graph.Graph;
import dominio.turno.Turno;

/**
 * @author cristian.barrientos
 *
 */
public class RestriccionNotDia extends RestriccionNot {

	private GregorianCalendar dia;

	// CONSTRUCTORA

	// PRE: d debe ser una fecha valida
	// POST: crea una restriccion de tipo Not para una fecha d
	public RestriccionNotDia(GregorianCalendar d) {
		dia = d;
	}

	// CONSULTORA

	// PRE: -
	// POST: devuelve la fecha de la restriccion que estamos tratando
	public GregorianCalendar getDia() {
		return dia;
	}

	// MODIFICADORAS

	// PRE: d debe ser una fecha valida
	// POST: modifica la fecha de la restriccion que estamos tratando
	public void setDia(GregorianCalendar dia) {
		this.dia = dia;
	}

	// PRE: d debe ser un doctor valido y debe estar en la lista de doctores del
	// hospital,
	// g debe ser un grafo valido,
	// nodosFecha debe ser un arraylist de string valido (fecha;especialidad),
	// coste debe ser un double valido
	// POST: elimina la arista que va del doctor a la fecha en la que NO va a
	// trabajar
	@Override
	public void construir(Doctor d, Graph g, ArrayList<String> nodosFecha,
			double coste) { // Controlador Algoritmo llama a Apply para
							// a�adir/eliminar las aristas y nodos pertinentes
							// en el grafo (NOT)
		// String fecha = Turno.parseCalendar2String(dia);
		// String especialidad = d.getEspecialidad();
		String nodo = Turno.parseCalendar2String(dia) + ";"
				+ d.getEspecialidad();
		int nodeFechaId = g.getNodeId(nodo); // Tengo el id del nodo
												// Fecha;Especialidad
		eliminarArista(d, g, nodeFechaId);
	}
}
