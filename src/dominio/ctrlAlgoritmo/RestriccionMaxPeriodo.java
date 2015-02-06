package dominio.ctrlAlgoritmo;

import java.text.ParseException;
import java.util.ArrayList;

import dominio.calendario.PeriodoVacacional;
import dominio.doctor.Doctor;
import dominio.graph.Graph;
import dominio.turno.Turno;

/**
 * @author marti.ribalta
 *
 */
public class RestriccionMaxPeriodo extends RestriccionMax {
	private PeriodoVacacional periodo;
	private int maxDias;

	// CONSTRUCTORA

	// PRE: periodo debe ser un periodo vacacional de la lista de periodos
	// vacacionales y maxDias debe ser un entero positivo
	// POST: crea una restriccion de tipo Max para un periodo vacacional
	public RestriccionMaxPeriodo(PeriodoVacacional periodo, int maxDias) {
		this.periodo = periodo;
		this.maxDias = maxDias;
	}

	// CONSULTORAS

	// PRE: -
	// POST: devuelve el periodo vacacional de la restriccion que estamos
	// tratando
	public PeriodoVacacional getPeriodo() {
		return periodo;
	}

	// PRE: -
	// POST: devuelve el maximo de dias de la restriccion que estamos tratando
	public int getMaxDias() {
		return maxDias;
	}

	// MODIFICADORAS

	// PRE: periodo debe ser un periodo vacacional de la lista de periodos
	// vacacionales
	// POST: modifica el periodo vacacional de la restriccion que estamos
	// tratando
	public void setPeriodo(PeriodoVacacional periodo) {
		this.periodo = periodo;
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
	// POST: crea el nodo perdiodo vacacional (dni;nombrePeriodoVacacional),
	// crea arista del doctor a dicho nodo con maxflow=maxDias
	// y crea una arista desde dicho nada hasta cada fecha perteneciente al
	// mismo periodo vacacional y especialidad del doctor
	@Override
	public void construir(Doctor d, Graph g, ArrayList<String> nodosFecha,
			double coste) throws ParseException {
		String dni = d.getDni();
		g.addNode(dni + ";" + periodo.getPeriodoVacacional()); // creo el node
																// intermedio
																// mes
		int periodoId = g.getNodeId(dni + ";" + periodo.getPeriodoVacacional());
		int docId = g.getNodeId(dni);
		g.addEdge(0, maxDias, 0, docId, periodoId);
		int diaId;

		for (String s : nodosFecha) {
			String[] nodoFecha = s.split(";");
			int cond1 = Turno.parseString2Calendar(nodoFecha[0]).compareTo(
					periodo.getFechaIni());
			int cond2 = Turno.parseString2Calendar(nodoFecha[0]).compareTo(
					periodo.getFechaFin());
			if ((cond1 == 1 || cond1 == 0) && (cond2 == -1 || cond2 == 0)
					&& d.getEspecialidad().equals(nodoFecha[1])) {
				diaId = g.getNodeId(s);
				g.addEdge(0, 1, coste, periodoId, diaId);
			}
		}
	}
}
