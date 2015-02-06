package dominio.ctrlAlgoritmo;

import java.text.ParseException;
import java.util.ArrayList;

import dominio.asignaciones.Asignacion;
import dominio.doctor.Doctor;
import dominio.graph.FordFulkerson;
import dominio.graph.Graph;
import dominio.graph.MaxFlow;
import dominio.graph.MaxFlowMincost;
import dominio.hospital.Hospital;
import dominio.turno.Turno;
import dominio.turno.TurnoAsignado;

/**
 * @author marti.ribalta
 * @author oscar.carod
 * @author cristian.barrientos
 * @author eric.alvarez.chinchilla
 * @author raul.enamorado
 *
 */
public class CtrlAlgoritmo {

	public boolean asignarSinCoste(Hospital h, String s) throws ParseException {
		GeneradorGrafo gf = new GeneradorGrafo();
		gf.generarGrafo(h.getConjuntoDoctores(), h.getCalendario(),
				h.getConjuntoEspecialidad());
		MaxFlow mf = new MaxFlow(gf.getGrafoGenerado());
		int x = mf.run();
		Graph rg = mf.getResidualGraph();
		interpretarGrafoR(h, rg, s);
		return x == gf.getMaxFlow();
	}

	public boolean asignarSinCosteDfs(Hospital h, String s)
			throws ParseException {
		GeneradorGrafo gf = new GeneradorGrafo();
		gf.generarGrafo(h.getConjuntoDoctores(), h.getCalendario(),
				h.getConjuntoEspecialidad());
		FordFulkerson ff = new FordFulkerson(gf.getGrafoGenerado());
		int x = ff.run();
		Graph rg = ff.getResidualGraph();
		interpretarGrafoR(h, rg, s);
		return x == gf.getMaxFlow();
	}

	public boolean asignarConCoste(Hospital h, String s) throws ParseException {
		GeneradorGrafo gf = new GeneradorGrafo();
		gf.generarGrafo(h.getConjuntoDoctores(), h.getCalendario(),
				h.getConjuntoEspecialidad());
		MaxFlowMincost mfmc = new MaxFlowMincost(gf.getGrafoGenerado());
		int x = mfmc.run();
		Graph rg = mfmc.getResidualGraph();
		interpretarGrafoR(h, rg, s);
		return x == gf.getMaxFlow();
	}

	private void interpretarGrafoR(Hospital h, Graph rg, String s)
			throws ParseException {
		Asignacion as = new Asignacion();
		ArrayList<String> vectorDiaTurno = new ArrayList<String>();
		ArrayList<String> vectorTurnoDni = new ArrayList<String>();
		ArrayList<String> vectorDiaTurnoDni = new ArrayList<String>();

		for (int i = 0; i < rg.getNodeCount(); i++) {
			for (int j = 0; j < rg.getNodeCount(); j++) {
				if (rg.isEdge(i, j) && rg.isEdge(j, i)) {

					if (!(rg.getNodeName(i).compareTo("sink") == 0 || rg
							.getNodeName(i).compareTo("source") == 0)) {
						// rg.getNodeName(j));
						if (rg.getNodeName(i).compareTo("Diurno") == 0
								|| rg.getNodeName(i).compareTo("Nocturno") == 0)
							vectorDiaTurno.add(rg.getNodeName(i) + "-"
									+ rg.getNodeName(j));
						else
							vectorTurnoDni.add(rg.getNodeName(i) + "-"
									+ rg.getNodeName(j));
					}
					rg.removeEdge(i, j);

					// if(rg.getNodeName(i)=="Diurno")
				}
			}

		}
		for (int i = 0; i < vectorDiaTurno.size(); i++) {
			String[] ca = vectorDiaTurno.get(i).split("-");
			for (int j = 0; j < vectorTurnoDni.size(); j++) {
				String[] cb = vectorTurnoDni.get(j).split("-");
				if (ca[1].compareTo(cb[0]) == 0) {
					String[] a = ca[1].split(";");
					// 1-Recogemos el minimo de doctores por
					// especialidad/dia/turno
					// 2-contamos cuantas veces aparece en el arraylist el
					// turno/dia/especialiad
					// 3-comprobamos si aun quedan doctores por rellenar en ese
					// turno
					// 4-comprobamos si se habia introducido antes la asignacion
					// de ese doctor por la ma�ana.
					if (h.getEspecialidadPorNombre(a[1]).getMinimoDoctores() > contarMax(
							vectorDiaTurnoDni, vectorDiaTurno.get(i))
							&& !vectorDiaTurnoDni.contains("Diurno-" + ca[1]
									+ "-" + cb[1]))
						vectorDiaTurnoDni.add(vectorDiaTurno.get(i) + "-"
								+ cb[1]);
				}
			}
		}
		if (!vectorDiaTurnoDni.isEmpty()) {
			String[] nodos = vectorDiaTurnoDni.get(0).split("-");// nodos[0] =
																	// tipo
			String[] fecha = nodos[1].split(";");
			String[] dni = nodos[2].split(";");
			String turno = nodos[0] + fecha[0];
			String turnoAnt = turno;
			int i = 0;
			ArrayList<Doctor> doctores = new ArrayList<Doctor>();
			while (i < vectorDiaTurnoDni.size()) {
				if (turno.equals(turnoAnt)) {
					turnoAnt = turno;
					nodos = vectorDiaTurnoDni.get(i).split("-");// nodos[0] =
																// tipo
					fecha = nodos[1].split(";");
					dni = nodos[2].split(";");
					doctores.add(h.getDoctorPorDni(dni[0]));
					++i;

					if (i == vectorDiaTurnoDni.size())
						break;
					String[] nodos2 = vectorDiaTurnoDni.get(i).split("-");// nodos[0]
																			// =
																			// tipo
					String[] fecha2 = nodos2[1].split(";");
					turno = nodos2[0] + fecha2[0];
				} else {
					turnoAnt = turno;
					TurnoAsignado ta = new TurnoAsignado(
							Turno.parseString2Calendar(fecha[0]), nodos[0],
							doctores);
					as.addTurnoAsignado(ta);
					doctores = new ArrayList<Doctor>();
				}
			}
			TurnoAsignado ta = new TurnoAsignado(
					Turno.parseString2Calendar(fecha[0]), nodos[0], doctores);
			as.addTurnoAsignado(ta);
			as.setNombre(s);
			as.setId(h.getAsignaciones().size());
			h.addAsignacion(as);
		}
	}

	private static int contarMax(ArrayList<String> vec, String string) {
		int cont = 0;
		for (int i = 0; i < vec.size(); i++) {
			String[] b = vec.get(i).split("-");
			String cadena2 = b[0] + "-" + b[1];
			if (cadena2.compareTo(string) == 0)
				cont++;
		}
		return cont;
	}
}
