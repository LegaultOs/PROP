package dominio.turno;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author cristian.barrientos
 *
 */
public class Turno {

	protected GregorianCalendar fecha;
	protected String tipo;
	protected int plus;
	protected int plusDiurno = 0;
	protected int plusNocturno = 60;

	@Override
	public String toString() {
		return "Turno [fecha=" + fecha + ", tipo=" + tipo + ", plus=" + plus
				+ "]";
	}

	// CONSTRUCTORAS
	public Turno() {
	}

	public Turno(GregorianCalendar fecha, String tipo) {
		this.fecha = fecha;
		if (comprobarTipo(tipo))
			this.tipo = tipo;
		if (tipo.equals("Nocturno"))
			this.plus = plusNocturno;
		else if (tipo.equals("Diurno"))
			this.plus = plusDiurno;

	}

	// CONSULTORAS
	// PRE: -
	// POST: Retorna el valor decimal referente al plus de un turno
	public int getPlus() {
		return plus;
	}

	// PRE: -
	// POST: Retorna el valor decimal referente al plus Diurno
	public int getPlusDiurno() {
		return plusDiurno;
	}

	// PRE: -
	// POST: Retorna el valor decimal referente al plus Nocturno
	public int getPlusNocturno() {
		return plusNocturno;
	}

	// PRE: -
	// POST: Retorna la fecha referente a un turno
	public GregorianCalendar getFecha() {
		return fecha;
	}

	public String getTipo() {
		return tipo;
	}

	// MODIFICADORAS

	// PRE: El plus debe ser un plus valido
	// POST: Modifica el plus de un turno
	public boolean setPlus(int plus) {
		if (comprobarPlus(plus)) {
			this.plus = plus;
			return true;
		} else
			return false;
	}

	// PRE: El plus debe ser un plus valido
	// POST: Modifica el plus Diurno de los turnos
	public void setPlusDiurno(int plusDiurno) {
		this.plusDiurno = plusDiurno;
	}

	// PRE: El plus debe ser un plus valido
	// POST: Modifica el plus Nocturno de los turnos
	public void setPlusNocturno(int plusNocturno) {
		this.plusNocturno = plusNocturno;
	}

	// PRE: La fecha debe ser valida
	// POST: Modifica la fecha de un turno
	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	// PRE: El tipo debe ser valido
	// POST: Modifica el tipo de un turno
	public boolean setTipo(String tipo) {
		if (comprobarTipo(tipo)) {
			this.tipo = tipo;
			if (this.tipo.equals("Nocturno"))
				this.plus = plusNocturno;
			else if (this.tipo.equals("Diurno"))
				this.plus = plusDiurno;
			return true;
		} else
			return false;
	}

	public String parseTurnoToString() {
		String f = parseCalendar2String(fecha);
		return (f + ";" + tipo + ";" + plus + ";");
	}

	public static Turno parseStringToTurno(String s) throws ParseException {

		String[] turno = s.split(";");
		GregorianCalendar fecha1 = parseString2Calendar(turno[0]);
		String tipo1 = turno[1];
		Turno t = new Turno(fecha1, tipo1);
		return t;
	}

	// PRIVATE FUNCTIONS

	private boolean comprobarPlus(int plus) {
		if (plus >= 0)
			return true;
		else
			return false;
	}

	private boolean comprobarTipo(String tipo) {
		if (tipo.equals("Nocturno") || tipo.equals("Diurno"))
			return true;
		else
			return false;
	}

	static public String parseCalendar2String(GregorianCalendar d) {
		return d.get(Calendar.DAY_OF_MONTH) + "/" + (d.get(Calendar.MONTH) + 1);
	}

	static public GregorianCalendar parseString2Calendar(String f)
			throws ParseException {
		GregorianCalendar d = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		d.setTime(dateFormat.parse(f + "/" + d.get(Calendar.YEAR)));
		return d;
	}
}
