/*@author: Raul Enamorado Serratosa */

package dominio.turno;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import dominio.doctor.Doctor;

public class TurnoAsignado extends Turno {

	protected GregorianCalendar fecha;
	protected String tipo;
	ArrayList<Doctor> doctoresAsignados;

	// CONSTRUCTORAS

	@Override
	public String toString() {
		return "TurnoAsignado [fecha=" + fecha + ", tipo=" + tipo + ", plus="
				+ plus + ", doctoresAsignados=" + doctoresAsignados + "]";
	}

	// pre: -
	// post: Crea un turno asignado vacio
	public TurnoAsignado() {
		fecha = new GregorianCalendar();
		tipo = new String();
		doctoresAsignados = new ArrayList<Doctor>();
	}

	/*
	 * pre: - el parametro String tipo debe ser Diurno o Nocturno el parametro
	 * double plus debe pertenecer al intervalo [1.0,2.0)
	 */
	// post: Se crea una instancia de TurnoAsignado con atributos
	public TurnoAsignado(GregorianCalendar fecha, String tipo,
			ArrayList<Doctor> doctoresAsignados) {
		this.fecha = fecha;
		if (comprobarTipo(tipo))
			this.tipo = tipo;
		if (tipo.equals("Nocturno"))
			this.plus = plusNocturno;
		else if (tipo.equals("Diurno"))
			this.plus = plusDiurno;
		this.doctoresAsignados = doctoresAsignados;
	}

	// CONSULTORAS

	// pre: el turno asignado debe existir
	// post: devuelve la fecha del turnoAsignado
	@Override
	public GregorianCalendar getFecha() {
		return fecha;
	}

	// pre: el turno asignado debe existir
	// post: devuelve el tipo del turnoAsignado
	@Override
	public String getTipo() {
		return tipo;
	}

	// pre: el turno asignado debe existir
	// post: devuelve el plus del turnoAsignado
	@Override
	public int getPlus() {
		return plus;
	}

	// pre: el turno asignado debe existir
	// post: devuelve el arrayList de doctores asignados al turno
	public ArrayList<Doctor> getDoctoresAsignados() {
		return doctoresAsignados;
	}

	// pre: el turno asignado debe existir
	// post: devuelve el doctor asignado con el dni introducido si existe,
	public Doctor getDoctorPorDni(String dni) {
		int posicion = buscarPosicionDoctorPorDni(dni);
		if (posicion > -1) {
			return this.doctoresAsignados.get(posicion);
		} else
			return null;
	}

	// MODIFICADORAS

	// pre: el turno asignado debe existir
	// post: modifica la fecha del turno a la fecha pasada por parametro
	@Override
	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	/*
	 * pre: el turno asignado debe existir el tipo debe ser Nocturno o Diurno
	 * post: devuelve cierto si se ha podido modificar al tipo que se ha pasado
	 * por parametro y falso en caso contrario
	 */
	@Override
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

	/*
	 * pre: el turno asignado debe existir post: a�ade el doctor con el dni
	 * pasado por parametro al array de doctores asignados al turno
	 */
	public void addDoctor(Doctor doctor) {
		doctoresAsignados.add(doctor);
	}

	/*
	 * pre: el turno asignado debe existir post: elimina el doctor con el dni
	 * pasado por parametro del array de doctores asignados al turno Devuelve la
	 * posici�n del doctor eliminado o -1 si no existe
	 */
	public int borrarDoctorPorDni(String dni) {
		int posicion = buscarPosicionDoctorPorDni(dni);
		if (posicion > -1) {
			this.doctoresAsignados.remove(posicion);
		}
		return posicion;
	}

	// pre: -
	// post: pasa un turno a string en formato dd/mm;tipo;DNI1,DNI2,...,DNIN;"
	public String parseTurnoAsignadoToString() {
		String s = new String();
		String f = parseCalendar2String(fecha);
		s = (f + "-" + tipo + "-");
		for (int i = 0; i < doctoresAsignados.size(); ++i) {
			if (i == 0)
				s += doctoresAsignados.get(i).getDni();
			else
				s += ":" + doctoresAsignados.get(i).getDni();
		}

		return s;
	}

	// pre: El parametro s debe ser en formato dd/mm;tipo;DNI1,DNI2,...,DNIN;
	// post: pasa un string a un turno y devuelve ese turno
	/*
	 * public static TurnoAsignado parseStringToTurnoAsignado(String s) throws
	 * ParseException{ String[] turno = s.split(";"); GregorianCalendar fecha1 =
	 * parseString2Calendar(turno[0]); String tipo1 = turno[1]; String doctores
	 * = turno[2]; String[] d = doctores.split(","); ArrayList<Doctor>
	 * docAsignados = new ArrayList<Doctor>(); for (int i = 0; i <
	 * d.length;++i){ Doctor doc = new Doctor(); doc.setDni(d[i]);
	 * docAsignados.add(doc); } TurnoAsignado t = new TurnoAsignado(fecha1,
	 * tipo1, docAsignados); return t; }
	 */

	// PRIVATE FUNCTIONS

	// pre: -
	// post: devuelve cierto si el tipo es Nocturno o Diurno
	private boolean comprobarTipo(String tipo) {
		if (tipo.equals("Nocturno") || tipo.equals("Diurno"))
			return true;
		else
			return false;
	}

	// pre: -
	// post: devuelve la posici�n del doctor con DNI pasado por parametro
	// o -1 si el doctor no existe
	private int buscarPosicionDoctorPorDni(String DniDoc) {
		int pos = -1;
		for (int i = 0; i < this.doctoresAsignados.size(); i++) {
			if (this.doctoresAsignados.get(i).getDni().compareTo(DniDoc) == 0) {
				pos = i;
				break;
			}
		}
		return pos;
	}
}
