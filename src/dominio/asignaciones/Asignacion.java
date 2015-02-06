/*@author: Raul Enamorado Serratosa */
package dominio.asignaciones;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import dominio.turno.TurnoAsignado;

public class Asignacion {
	private int id;
	private String nombre;
	private ArrayList<TurnoAsignado> turnosAsignados;

	public Asignacion() {
		nombre = new String();
		turnosAsignados = new ArrayList<TurnoAsignado>();
	}

	public Asignacion(String nombre) {
		turnosAsignados = new ArrayList<TurnoAsignado>();
		this.nombre = nombre;
	}

	public Asignacion(int id) {
		turnosAsignados = new ArrayList<TurnoAsignado>();
		this.id = id;
	}

	public Asignacion(int id, ArrayList<TurnoAsignado> turnosAsignados) {
		this.id = id;
		this.turnosAsignados = turnosAsignados;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asignacion other = (Asignacion) obj;
		if (nombre != other.nombre)
			return false;
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<TurnoAsignado> getTurnosAsignados() {
		return turnosAsignados;
	}

	public void addTurnoAsignado(TurnoAsignado turnoAsignado) {
		turnosAsignados.add(turnoAsignado);
	}

	public int borrarTurnoAsignadoPorFecha(GregorianCalendar fecha) {
		int posicion = buscarPosicionTurnoPorFecha(fecha);

		if (posicion > -1) {
			this.turnosAsignados.remove(posicion);

		}
		return posicion;
	}

	public int buscarPosicionTurnoPorFecha(GregorianCalendar fecha) {
		int pos = -1;
		for (int i = 0; i < turnosAsignados.size(); i++) {
			if (turnosAsignados.get(i).getFecha().compareTo(fecha) == 0) {
				pos = i;
				break;
			}

		}

		return pos;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
