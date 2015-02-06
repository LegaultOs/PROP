package dominio.ctrlAlgoritmo;

import java.util.ArrayList;

import dominio.doctor.Doctor;
import dominio.graph.Graph;

/**
 * @author marti.ribalta
 *
 */
public class RestriccionMaxDia extends RestriccionMax {

	int maxDias;

	// CONSTRUCTORA

	// PRE: maxDias debe ser un entero positivo
	// POST: crea una restriccion de tipo Max con un maximo de dias maxDias
	public RestriccionMaxDia(int maxDias) {
		this.maxDias = maxDias;
	}

	// CONSULTORA

	// PRE: -
	// POST: devuelve el maximo de dias de la restriccion que estamos tratando
	public int getMaxDias() {
		return maxDias;
	}

	// MODIFICADORAS

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
	// POST: modifica el maxflow de la arista que va del nodo Source al nodo
	// doctor
	@Override
	public void construir(Doctor d, Graph g, ArrayList<String> nodosFecha,
			double coste) {
		int nodeId = g.getNodeId(d.getDni());
		int sourceId = g.getSource();
		g.setEdgeMaxFlow(sourceId, nodeId, maxDias);
	}

}
