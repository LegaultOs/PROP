package dominio.ctrlAlgoritmo;

import java.text.ParseException;
import java.util.ArrayList;

import dominio.calendario.PeriodoVacacional;
import dominio.doctor.Doctor;
import dominio.graph.Graph;
import dominio.turno.Turno;

/**
 * @author cristian.barrientos
 *
 */
public class RestriccionNotPeriodoVacacional extends RestriccionNot {

	private PeriodoVacacional periodo;

	// CONSTRUCTORA

	// PRE: periodo debe ser un periodo vacacional valido
	// POST: crea una restriccion de tipo Not para un periodo vacacional
	public RestriccionNotPeriodoVacacional(PeriodoVacacional periodo) {
		this.periodo = periodo;
	}

	// CONSULTORA

	// PRE: -
	// POST: devuelve el periodo vacacional de la restriccion que estamos
	// tratando
	public PeriodoVacacional getPeriodo() {
		return periodo;
	}

	// MODIFICADORAS

	// PRE: periodo debe ser un periodo vacacional valido
	// POST: modifica el periodo vacacional de la restriccion que estamos
	// tratando
	public void setPeriodo(PeriodoVacacional periodo) {
		this.periodo = periodo;
	}

	// PRE: d debe ser un doctor valido y debe estar en la lista de doctores del
	// hospital,
	// g debe ser un grafo valido,
	// nodosFecha debe ser un arraylist de string valido (fecha;especialidad),
	// coste debe ser un double valido
	// POST: elimina law arista que van del doctor a las fechas que caen en un
	// periodo vacacional que NO va a trabajar
	@Override
	public void construir(Doctor d, Graph g, ArrayList<String> nodosFecha,
			double coste) throws ParseException {
		int nodo;
		for (String s1 : nodosFecha) { // Recorro la lista de nodos Fecha
			String[] nodoFecha = s1.split(";");
			int cond1 = Turno.parseString2Calendar(nodoFecha[0]).compareTo(
					periodo.getFechaIni());
			int cond2 = Turno.parseString2Calendar(nodoFecha[0]).compareTo(
					periodo.getFechaFin());
			if ((cond1 == 1 || cond1 == 0) && (cond2 == -1 || cond2 == 0)) { // si
																				// 0
																				// son
																				// iguales;
																				// -1
																				// si
																				// d
																				// >
																				// festivos.get(i);
																				// 1
																				// si
																				// d
																				// es
																				// menor
				// Si la fecha del nodo esta entre fechaini y fechafin elimino
				// arista
				nodo = g.getNodeId(s1);
				eliminarArista(d, g, nodo);
			}
		}

	}
}
