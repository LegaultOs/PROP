package dominio.ctrlAlgoritmo;

import java.util.ArrayList;

import dominio.doctor.Doctor;
import dominio.graph.Graph;

/**
 * @author marti.ribalta
 *
 */
public class RestriccionMaxMes extends RestriccionMax {
	String mes;
	int maxDias;

	// CONSTRUCTORA

	// PRE: mes debe ser un mes entre 1 y 12 y maxDias debe ser un entero
	// positivo
	// POST: crea una restriccion de tipo Max para un mes
	public RestriccionMaxMes(String mes, int maxDias) {
		super();
		this.mes = mes;
		this.maxDias = maxDias;
	}

	// CONSULTORAS

	// PRE: -
	// POST: devuelve el mes de la restriccion que estamos tratando
	public String getMes() {
		return mes;
	}

	// PRE: -
	// POST: devuelve el maximo de dias de la restriccion que estamos tratando
	public int getMaxDias() {
		return maxDias;
	}

	// MODIFICADORAS

	// PRE: mes debe ser un mes entre 1 y 12
	// POST: modifica el mes de la restriccion que estamos tratando
	public void setMes(String mes) {
		this.mes = mes;
	}

	// PRE: maxDias debe ser un entero positivo
	// POST: modifica el maximo de dias de la restriccion que estamos tratando
	public void setMaxDias(int maxDias) {
		this.maxDias = maxDias;
	}

	// PRE: d debe ser un doctor valido y debe estar en la lista de doctores del
	// hospital,
	// g debe ser un grafo valido,
	// nodosFecha debe ser un arraylist de string valido (fecha;especialidad),
	// coste debe ser un double valido
	// POST: crea el nodo mes (dni;mes), crea arista del doctor a dicho nodo con
	// maxflow=maxDias
	// y crea una arista desde dicho nada hasta cada fecha perteneciente al
	// mismo mes y especialidad del doctor
	@Override
	public void construir(Doctor d, Graph g, ArrayList<String> nodosFecha,
			double coste) {
		String dni = d.getDni();
		g.addNode(dni + ";" + mes); // creo el node intermedio mes
		int mesId = g.getNodeId(dni + ";" + mes);
		int docId = g.getNodeId(dni);
		g.addEdge(0, maxDias, 0, docId, mesId);
		int diaId;

		for (String s : nodosFecha) {
			String[] nodoFecha = s.split(";");
			String[] nodoMes = nodoFecha[0].split("/");
			if (mes.equals(nodoMes[1])
					&& nodoFecha[1].equals(d.getEspecialidad())) {
				diaId = g.getNodeId(s);
				g.addEdge(0, 1, coste, mesId, diaId);
			}
		}
	}

}
