package dominio.ctrlAlgoritmo;

import java.util.ArrayList;

import dominio.doctor.Doctor;
import dominio.graph.Graph;

/**
 * @author cristian.barrientos
 *
 */
public abstract class RestriccionNot extends Restriccion {

	// MODIFICADORA

	// PRE: d debe ser un doctor valido y debe estar en la lista de doctores del
	// hospital,
	// g debe ser un grafo valido,
	// nodeFecha debe ser un id valido de un nodo (fecha;especialidad)
	// POST: elimina una arista por la que el doctor NO va a pasar
	public void eliminarArista(Doctor d, Graph g, int nodeFecha) {
		String s1 = g.getNodeName(nodeFecha);
		ArrayList<Integer> nodosAnteriores = g.getReverseNeighbours(s1);
		for (Integer s : nodosAnteriores) {
			String nodoActual = g.getNodeName(s);
			int nodeDocId;
			if (nodoActual.length() == 9) {// Si el nodo tiene 9 caraceteres es
											// un nodo de un doctor
				if (d.getDni().equals(nodoActual)) {
					nodeDocId = g.getNodeId(nodoActual);
					if (g.isEdge(nodeDocId, nodeFecha))
						g.removeEdge(nodeDocId, nodeFecha);
				}
			} else { // Estoy en un nodo de condicion (Mes, Xor...) formato:
						// dni;condicion
				String[] dniDoc = nodoActual.split(";");
				if (d.getDni().equals(dniDoc[0])) {
					nodeDocId = g.getNodeId(dniDoc[0] + ";" + dniDoc[1]);
					if (g.isEdge(nodeDocId, nodeFecha))
						g.removeEdge(nodeDocId, nodeFecha);
				}
			}
		}
	}
}
