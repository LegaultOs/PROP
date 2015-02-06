package dominio.ctrlAlgoritmo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dominio.calendario.Calendario;
import dominio.doctor.Doctor;
import dominio.graph.Graph;
import dominio.turno.Turno;

/**
 * @author cristian.barrientos
 *
 */
public class RestriccionNotDiaSemanal extends RestriccionNot {

	private String diasemanal;

	// CONSTRUCTORA

	// PRE: ds debe ser un dia de la semana valido (Lunes - Domingo)
	// POST: crea una restriccion de tipo Not para todas las instancias de un
	// dia de la semana en todo el a�o (ejemplo: Ningun domigo)
	public RestriccionNotDiaSemanal(String ds) {
		diasemanal = ds;
	}

	// CONSULTORA

	// PRE: -
	// POST: devuelve el dia de la semana de la restriccion que estamos tratando
	public String getDiaSemanal() {
		return diasemanal;
	}

	// MODIFICADORAS

	// PRE: ds debe ser un dia de la semana valido (Lunes - Domingo)
	// POST: modifica el dia de la semana de la restriccion que estamos tratando
	public void setDiaSemanal(String ds) {
		this.diasemanal = ds;
	}

	// PRE: d debe ser un doctor valido y debe estar en la lista de doctores del
	// hospital,
	// g debe ser un grafo valido,
	// nodosFecha debe ser un arraylist de string valido (fecha;especialidad),
	// coste debe ser un double valido
	// POST: elimina las aristas que van del doctor a las fechas en las que
	// coincide con el dia semanal de la restriccion que NO va a trabajar
	@Override
	public void construir(Doctor d, Graph g, ArrayList<String> nodosFecha,
			double coste) throws ParseException { // Controlador Algoritmo llama
													// a Apply para
													// a�adir/eliminar las
													// aristas y nodos
													// pertinentes en el grafo
													// (NOT)
		for (String s : nodosFecha) { // Recorro la lista de nodos Fecha
			String[] nodoFecha = s.split(";");
			GregorianCalendar gc = Turno.parseString2Calendar(nodoFecha[0]);
			int diaSemana = gc.get(Calendar.DAY_OF_WEEK);
			String diaSemana1 = Calendario.getDiaTextual(diaSemana);
			if (diasemanal.equals(diaSemana1)) { // Si el mes que me pasan esta
													// en un nodo de nodo
													// Fecha...
				int nodoSemanaId = g.getNodeId(s); // Cojo la Id de este nodo y
													// se la paso a Eliminar la
													// Arista
				eliminarArista(d, g, nodoSemanaId);
			}
		}
	}
}
