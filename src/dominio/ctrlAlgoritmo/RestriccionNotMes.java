package dominio.ctrlAlgoritmo;

import java.util.ArrayList;

import dominio.doctor.Doctor;
import dominio.graph.Graph;

/**
 * @author cristian.barrientos
 *
 */
public class RestriccionNotMes extends RestriccionNot {

	private String mes;

	// CONSTRUCTORA

	// PRE: m debe ser un mes entre 1 y 12
	// POST: crea una restriccion de tipo Not para un mes
	public RestriccionNotMes(String m) {
		mes = m;
	}

	// CONSULTORA

	// PRE: -
	// POST: devuelve el mes de la restriccion que estamos tratando
	public String getMes() {
		return mes;
	}

	// MODIFICADORAS

	// PRE: m debe ser un mes entre 1 y 12
	// POST: modifica el mes de la restriccion que estamos tratando
	public void setMes(String mes) {
		this.mes = mes;
	}

	// PRE: d debe ser un doctor valido y debe estar en la lista de doctores del
	// hospital,
	// g debe ser un grafo valido,
	// nodosFecha debe ser un arraylist de string valido (fecha;especialidad),
	// coste debe ser un double valido
	// POST: elimina la arista que va del doctor a una fecha que contenga el mes
	// en el que NO va a trabajar
	@Override
	public void construir(Doctor d, Graph g, ArrayList<String> nodosFecha,
			double coste) { // Controlador Algoritmo llama a Apply para
							// a�adir/eliminar las aristas y nodos pertinentes
							// en el grafo (NOT)
		for (String s1 : nodosFecha) { // Recorro la lista de nodos Fecha
			String[] nodoFecha = s1.split(";");
			String[] nodoMes = nodoFecha[0].split("/"); // Me quedo solo con el
														// mes en nodoMes[1]

			if (mes.equals(nodoMes[1])) { // Si el mes que me pasan esta en un
											// nodo de nodo Fecha...
				int nodoMesId = g.getNodeId(s1); // Cojo la Id de este nodo y se
													// la paso a Eliminar la
													// Arista
				eliminarArista(d, g, nodoMesId);
			}
		}
	}
}