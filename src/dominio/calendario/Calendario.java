package dominio.calendario;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import dominio.turno.Turno;

/**
 * @author cristian.barrientos
 * @others marti.ribalta
 * 
 */
public class Calendario {

	private ArrayList<Turno> festivos;
	private ArrayList<PeriodoVacacional> periodos;

	// CONSTRUCTORAS

	// PRE: -
	// POST: Se crea una instancia de Calendario con una lista de festivos
	// vacia.
	public Calendario() {
		festivos = new ArrayList<Turno>();
		periodos = new ArrayList<PeriodoVacacional>();
	}

	// PRE: festivos debe ser un arraylist de turnos festivos valido
	// POST: Se crea una instancia de Calendario con la lista de festivos que se
	// le pase por parametro.
	public Calendario(ArrayList<Turno> festivos) {
		this.festivos = festivos;
		periodos = new ArrayList<PeriodoVacacional>();
	}

	// MODIFICADORAS

	// PRE: turno d debe ser valido
	// POST: Se a�ade el turno festivo a la lista de festivos si no lo
	// contenia ya
	public void addTurno(Turno d) {
		if (!esFestivo(d)) {
			festivos.add(d);
			Collections.sort(festivos, new Comparator<Turno>() {
				@Override
				public int compare(Turno d1, Turno d2) {
					return d1.getFecha().compareTo(d2.getFecha());
				}
			});
		}
		
	}

	// PRE: La lista de turnos festivos debe ser valida
	// POST: Añade a la lista de turnos festivos otra lista de turnos festivos
	public void addTurnos(ArrayList<Turno> turnos) {
		for (Turno t : turnos)
			addTurno(t);
	}

	// PRE: La fecha d debe ser valida
	// POST: Se a�ade tanto turno Diurno como Nocturno para una fecha
	public void addFecha(GregorianCalendar d) throws ParseException {
		Turno t = Turno.parseStringToTurno(Turno.parseCalendar2String(d)
				+ ";Diurno");
		addTurno(t);
		t = Turno.parseStringToTurno(Turno.parseCalendar2String(d)
				+ ";Nocturno");
		addTurno(t);
	}

	// PRE: La fecha d debe ser valida
	// POST: Se elimina tanto turno Diurno como Nocturno de la fecha d
	public void eliminarFecha(GregorianCalendar d) throws ParseException {
		Turno t = Turno.parseStringToTurno(Turno.parseCalendar2String(d)
				+ ";Diurno");
		eliminarTurno(t);
		t = Turno.parseStringToTurno(Turno.parseCalendar2String(d)
				+ ";Nocturno");
		eliminarTurno(t);
	}

	// PRE: turno d debe ser valido
	// POST: Se elimina el turno a la lista de festivos
	public void eliminarTurno(Turno d) {
		if (esFestivo(d))
			festivos.remove(getIndexFestivo(d));
	}

	// PRE: La lista de turnos festivos debe ser valida
	// POST: Modifica la lista de turnos festivos
	public void setFestivos(ArrayList<Turno> festivos) {
		this.festivos = festivos;
		Collections.sort(festivos, new Comparator<Turno>() {
			@Override
			public int compare(Turno d1, Turno d2) {
				return d1.getFecha().compareTo(d2.getFecha());
			}
		});
	}

	// PRE: La lista de periodos vacacionales debe ser valida
	// POST: Modifica la lista de periodos vacacionales
	public void setPeriodos(ArrayList<PeriodoVacacional> periodos) {
		this.periodos = periodos;
	}

	// CONSULTORAS

	// PRE: -
	// POST: Retorna la lista de festivos
	public ArrayList<Turno> getFestivos() {
		return festivos;
	}

	// En el controlador AVISAR si no hay ningun festivo tal mes
	// PRE: el numero de mes debe estar entre 1 y 12
	// POST: Retorna todos los dias festivos que hay en el mes numero:numMes
	public ArrayList<Turno> getFestivosMes(int numMes) {
		ArrayList<Turno> fMes = new ArrayList<Turno>();
		for (Turno gc : festivos)
			if (numMes == gc.getFecha().get(Calendar.MONTH) + 1)
				fMes.add(gc);
		return fMes;
	}

	// PRE: Domingo es el numero 1, lunes el numero 2, y asi consecutivamente
	// POST: Retorna la lista de los dias festivos que caen en un dia de la
	// semana
	public ArrayList<Turno> getFestivosDiaSemanal(int diaSemana) {
		ArrayList<Turno> fDiaSemanal = new ArrayList<Turno>();
		for (Turno gc : festivos)
			if (diaSemana == gc.getFecha().get(Calendar.DAY_OF_WEEK))
				fDiaSemanal.add(gc);
		return fDiaSemanal;
	}

	// PRE: -
	// POST: Retorna cierto si la fecha esta en la lista de festivos
	public boolean esFestivo(Turno d) {
		for (int i = 0; i < festivos.size(); ++i) {
			if (d.getFecha().compareTo(festivos.get(i).getFecha()) == 0
					&& d.getTipo().compareTo(festivos.get(i).getTipo()) == 0) // si
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
				return true;
		}
		return false;
	}

	// PRE: d existe en la ArrayList festivos
	// POST: Retorna el indice de una fecha:d en la lista de festivos
	public int getIndexFestivo(Turno d) {
		int i;
		for (i = 0; i < festivos.size(); ++i)
			if (d.getFecha().compareTo(festivos.get(i).getFecha()) == 0
					&& d.getTipo().compareTo(festivos.get(i).getTipo()) == 0)
				break;
		return i;
	}

	// PRE: pv es un Periodo Vacacional que existe en la lista de Periodos
	// Vacacionales
	// POST: Retorna el indice de un periodo vacacional en la lista de periodos
	// vacacionales
	public int getIndexPeriodo(String pv) {
		int i;
		for (i = 0; i < periodos.size(); ++i)
			if (pv.compareTo(periodos.get(i).getPeriodoVacacional()) == 0)
				break;
		return i;
	}

	// PRE: namePeriodo es un Periodo Vacacional que existe en la lista de
	// Periodos Vacacionales
	// POST: Retorna el periodo vacacional con el nombre namePeriodo
	public PeriodoVacacional getPeriodo(String namePeriodo) {

		return periodos.get(getIndexPeriodo(namePeriodo));
	}

	// PRE:
	// POST: Retorna el valor entero referente al dia de la semana en el que cae
	// la fecha:d
	public static int getDiaSemana(Turno d) {
		return d.getFecha().get(Calendar.DAY_OF_WEEK);
	}

	// PRE: diaSemana debe ser un entero entre 1 y 7
	// POST: Retorna el dia de la semana al que pertenece un valor
	public static String getDiaTextual(int diaSemana) {
		switch (diaSemana) {
		case 1:
			return "Domingo";
		case 2:
			return "Lunes";
		case 3:
			return "Martes";
		case 4:
			return "Miercoles";
		case 5:
			return "Jueves";
		case 6:
			return "Viernes";
		case 7:
			return "Sabado";
		default:
			return "Dia no valido";
		}
	}

	// PRE: d existe en la ArrayList festivo
	// POST: Retorna el dia de la semana en el que cae la fecha:d
	public static String getDiaTextual(Turno d) {
		int diaSemana = getDiaSemana(d);
		switch (diaSemana) {
		case 1:
			return "Domingo";
		case 2:
			return "Lunes";
		case 3:
			return "Martes";
		case 4:
			return "Miercoles";
		case 5:
			return "Jueves";
		case 6:
			return "Viernes";
		case 7:
			return "Sabado";
		default:
			return "Dia no valido";
		}
	}

	// PRE: -
	// POST: Retorna la lista de periodos vacacionales
	public ArrayList<PeriodoVacacional> getPeriodos() {
		return periodos;
	}

	// PRE: namePeriodo tiene que ser un string,
	// fechaIni < fechaFin y las ambas deben ser fechas validas
	// POST: A�ade un periodo vacacional a la lista de periodos vacacionales
	public void addPeriodo(String namePeriodo, GregorianCalendar fechaIni,
			GregorianCalendar fechaFin) {
		PeriodoVacacional pv = new PeriodoVacacional(namePeriodo, fechaIni,
				fechaFin);
		periodos.add(pv);
	}

	// PRE: namePeriodo debe ser un periodo vacacional de la lista de periodos
	// vacacionales
	// POST: Se elimina el periodo vacacional namePeriodo
	public void eliminarPeriodo(String namePeriodo) {
		periodos.remove(getIndexPeriodo(namePeriodo));
	}

}
