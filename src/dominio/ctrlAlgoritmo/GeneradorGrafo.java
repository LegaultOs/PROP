package dominio.ctrlAlgoritmo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import dominio.calendario.Calendario;
import dominio.doctor.Doctor;
import dominio.especialidad.Especialidad;
import dominio.graph.Graph;
import dominio.turno.Turno;

/**
 * @author marti.ribalta
 * @others cristian.barrientos
 */
public class GeneradorGrafo {
	private Graph grafoGenerado;
	private int maxFlow;
	private int maximoDias;
	private ArrayList<String> nodosFecha = new ArrayList<String>();

	// CONSTRUCTORA
	public GeneradorGrafo() {
		grafoGenerado = new Graph();
		nodosFecha = new ArrayList<String>();
	}

	// CONSULTORAS
	public Graph getGrafoGenerado() {
		return grafoGenerado;
	}

	public void setGrafoGenerado(Graph grafoGenerado) {
		this.grafoGenerado = grafoGenerado;
	}

	public int getMaxFlow() {
		return maxFlow;
	}

	public int getMaximoDias() {
		return maximoDias;
	}

	// MODIFICADORAS
	public void setMaxFlow(int maxFlow) {
		this.maxFlow = maxFlow;
	}

	public void setMaximoDias(int maximoDias) {
		this.maximoDias = maximoDias;
	}

	// Genera un grafo con todas las FECHAS unidas por aristas a los nodos
	// diurno y nocturno y estos los uno a sink
	public void generarParteSinkGrafo(Calendario turnos,
			ArrayList<Especialidad> espec) {
		grafoGenerado.addNode("sink"); // creo el node sink
		int sinkId = grafoGenerado.getNodeId("sink");
		grafoGenerado.setSinkNode(sinkId);
		grafoGenerado.addNode("Diurno"); // creo el node diurno
		int diurnoId = grafoGenerado.getNodeId("Diurno");
		grafoGenerado.addNode("Nocturno"); // creo el node nocturno
		int nocturnoId = grafoGenerado.getNodeId("Nocturno");
		maxFlow = 0;
		String nodeName = "none";
		String[] parse;
		for (Turno t2 : turnos.getFestivos()) {
			parse = t2.parseTurnoToString().split(";");
			for (Especialidad e : espec) {
				maxFlow += e.getMinimoDoctores(); // maxflow deberia ser =
													// minimodoctores*especialidades*turnos
				String esp = e.getNombre();
				nodeName = parse[0] + ";" + esp;
				if (!grafoGenerado.isNode(nodeName)) {
					grafoGenerado.addNode(nodeName); // creo un nodo
														// fecha;especialidad
														// que no sea igual al
														// anterior para
														// diferenciar diurno de
														// nocturno,
					nodosFecha.add(nodeName); // solo a�ado un nodo por fecha.
				}
				int diaId = grafoGenerado.getNodeId(nodeName);
				int tipoId;
				if (t2.getTipo().equals("Diurno"))
					tipoId = diurnoId;
				else
					tipoId = nocturnoId;
				grafoGenerado.addEdge(0, e.getMinimoDoctores(), 0, diaId,
						tipoId); // a�ado una arista del nodo
									// fecha;especialidad al nodo del tipo que
									// sea el turno con maxflow minimo de
									// doctores por turno de esa especialidad.
			}
		}
		grafoGenerado.addEdge(0, maxFlow, 0, diurnoId, sinkId); // a�ado la
																// arista
																// diurno-sink
																// con el flujo
																// maximo.
		grafoGenerado.addEdge(0, maxFlow, 0, nocturnoId, sinkId); // a�ado la
																	// arista
																	// nocturno-sink
																	// con el
																	// flujo
																	// maximo
	}

	public ArrayList<String> getNodosFecha() {
		return nodosFecha;
	}

	public void setNodosFecha(ArrayList<String> nodosFechas) {
		nodosFecha = nodosFechas;
	}

	public void generarGrafo(ArrayList<Doctor> doctors, Calendario turnos,
			ArrayList<Especialidad> espec) throws ParseException {
		generarParteSinkGrafo(turnos, espec);
		grafoGenerado.addNode("source"); // creo el node source
		int sourceId = grafoGenerado.getNodeId("source");
		grafoGenerado.setSourceNode(sourceId);
		ArrayList<Doctor> docs = doctors;
		Collections.shuffle(docs);
		for (Doctor d : docs) {
			double coste = calcularCoste(d, espec);
			String dni = d.getDni();
			grafoGenerado.addNode(dni); // creo un node de doctor con su dni,
			int doctorId = grafoGenerado.getNodeId(dni);
			grafoGenerado.addEdge(0, maxFlow, 0, sourceId, doctorId); // creo
																		// una
																		// arista
																		// de
																		// source
																		// al
																		// doctor
																		// con
																		// maxflow=0,
																		// las
																		// restricciones
																		// ya la
																		// modificaran
			boolean primer = true;
			if (!d.getRestricciones().isEmpty()) {
				boolean fnot = false;
				if (d.getRestricciones().get(0) instanceof RestriccionNot)
					fnot = true;
				for (int i = 0; i < d.getRestricciones().size(); ++i) {
					if (!fnot)
						d.getRestricciones().get(i)
								.construir(d, grafoGenerado, nodosFecha, coste); // esta
																					// funcion
																					// se
																					// ocupara
																					// de
																					// completar
																					// el
																					// grafo
																					// restriccion
																					// a
																					// restriccion.
					if (primer
							&& (i == (d.getRestricciones().size() - 1) || (d
									.getRestricciones().get(i + 1) instanceof RestriccionNot))) {
						primer = false;
						for (String s : nodosFecha) {
							int iter = grafoGenerado.getNodeId(s);
							String[] fechesp = grafoGenerado.getNodeName(iter)
									.split(";");
							if (fechesp[1].equals(d.getEspecialidad())) {
								boolean esta = false;
								for (int it : grafoGenerado
										.getReverseNeighbours(s)) {
									String[] docDni = grafoGenerado
											.getNodeName(it).split(";");
									if (docDni[0].equals(d.getDni())) {
										esta = true;
										break;
									}
								}
								if (!esta)
									grafoGenerado.addEdge(0, 1, coste,
											d.getDni(), s);
							}
						}
					}
					if (fnot)
						d.getRestricciones().get(i)
								.construir(d, grafoGenerado, nodosFecha, coste);
				}
			} else {
				for (String s : nodosFecha) {
					int iter = grafoGenerado.getNodeId(s);
					String[] fechesp = grafoGenerado.getNodeName(iter).split(
							";");
					if (fechesp[1].equals(d.getEspecialidad())) {
						boolean esta = false;
						for (int it : grafoGenerado.getReverseNeighbours(s)) {
							String[] docDni = grafoGenerado.getNodeName(it)
									.split(";");
							if (docDni[0].equals(d.getDni())) {
								esta = true;
								break;
							}
						}
						if (!esta)
							grafoGenerado.addEdge(0, 1, coste, d.getDni(), s);
					}
				}
			}
		}
	}

	private double calcularCoste(Doctor d, ArrayList<Especialidad> espec) {
		double exp = d.getExperiencia() / 100 + 1;
		double esp = 0;
		for (Especialidad e : espec) {
			if (d.getEspecialidad().equals(e.getNombre())) {
				esp = e.getCoeficiente();
				break;
			}
		}
		return esp * exp * d.getSueldo();
	}
}
