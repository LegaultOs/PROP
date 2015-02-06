package dominio.ctrlAlgoritmo;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import dominio.doctor.Doctor;
import dominio.graph.Graph;
import dominio.turno.Turno;

/**
 * @author marti.ribalta
 *
 */
public class RestriccionXorDia extends RestriccionXor {

	private GregorianCalendar dia1;
	private GregorianCalendar dia2;

	// CONSTRUCTORA

	// PRE: dia1 y dia2 deben ser fechas validas
	// POST: crea una restriccion de tipo Xor para una pareja de fechas dia1 y
	// dia2
	public RestriccionXorDia(GregorianCalendar dia1, GregorianCalendar dia2) {
		this.dia1 = dia1;
		this.dia2 = dia2;
	}

	// CONSULTORA

	// PRE: -
	// POST: devuelve la primera fecha de la restriccion que estamos tratando
	public GregorianCalendar getDia1() {
		return dia1;
	}

	// PRE: -
	// POST: devuelve la segunda fecha de la restriccion que estamos tratando
	public GregorianCalendar getDia2() {
		return dia2;
	}

	// MODIFICADORAS

	// PRE: dia1 debe ser una fecha valida
	// POST: modifica la primera fecha de la restriccion que estamos tratando
	public void setDia1(GregorianCalendar dia1) {
		this.dia1 = dia1;
	}

	// PRE: dia2 debe ser una fecha valida
	// POST: modifica la segunda fecha de la restriccion que estamos tratando
	public void setDia2(GregorianCalendar dia2) {
		this.dia2 = dia2;
	}

	// PRE: d debe ser un doctor valido y debe estar en la lista de doctores del
	// hospital,
	// g debe ser un grafo valido,
	// nodosFecha debe ser un arraylist de string valido (fecha;especialidad),
	// coste debe ser un double valido
	// POST: crea un nodo xor (dni;xor), crea una arista con maxflow=1 de doctor
	// a dicho nodo y crea dos aristas, una a cada una de sus fechas con
	// maxflow=1
	@Override
	public void construir(Doctor d, Graph g, ArrayList<String> nodosFecha,
			double coste) { // Controlador Algoritmo llama a Apply para
							// a�adir/eliminar las aristas y nodos pertinentes
							// en el grafo (NOT)
		String dni = d.getDni();
		g.addNode(dni + ";xor"); // creo el node intermedio mes
		int xorId = g.getNodeId(dni + ";xor");
		int docId = g.getNodeId(dni);
		g.addEdge(0, 1, 0, docId, xorId);
		int dia1Id = g.getNodeId(Turno.parseCalendar2String(dia1) + ";"
				+ d.getEspecialidad());
		g.addEdge(0, 1, coste, xorId, dia1Id);
		int dia2Id = g.getNodeId(Turno.parseCalendar2String(dia2) + ";"
				+ d.getEspecialidad());
		g.addEdge(0, 1, coste, xorId, dia2Id);
	}
}
