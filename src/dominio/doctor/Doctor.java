/*@author: Oscar Carod Iglesias */
package dominio.doctor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dominio.ctrlAlgoritmo.Restriccion;
import dominio.ctrlAlgoritmo.RestriccionNot;

public class Doctor {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyy");
	private String nombre;
	private String apellidos;
	private String dni;
	private Date nacimiento;
	private String direccion;
	private int experiencia;

	private String especialidad;
	private double sueldo;
	private String idHospital;
	private ArrayList<Restriccion> restricciones;

	// constructora por defecto
	public Doctor() {
		this.restricciones = new ArrayList<Restriccion>();
	}

	// constructora con dni(usada para buscar con la funcion Contains())
	public Doctor(String dni) {
		super();
		this.restricciones = new ArrayList<Restriccion>();
		this.dni = dni;

	}

	// pre: -
	// post: Arraylist de restricciones
	public ArrayList<Restriccion> getRestricciones() {
		return restricciones;
	}

	// pre: un arraylist de restriccion
	// post: -
	public void setRestricciones(ArrayList<Restriccion> restricciones) {
		this.restricciones = restricciones;
	}

	// pre: un objeto restriccion
	// post: -
	public void addRestriccion(Restriccion rest) {
		if (rest != null) {
			if (rest instanceof RestriccionNot)
				restricciones.add(rest);
			else
				restricciones.add(0, rest);
		}
	}

	// pre: posicion es la posicion de la restriccion en el arraylist de
	// restricciones
	// post: elimina la restriccion que esta en la posicion "posicion"
	public void borrarRestriccionPorPosicion(int posicion) {
		if (posicion > -1) {
			this.restricciones.remove(posicion);

		}
	}

	// pre: un id de restriccion
	// post: devuelve la posicion de la restriccion antes del borrado o -1 si no
	// lo ha encontrado
	public int borrarRestriccionPorId(String id) {
		int posicion = buscarPosicionRestriccionPorId(id);

		if (posicion > -1) {
			this.restricciones.remove(posicion);

		}
		return posicion;

	}

	// pre: un id de restriccion
	// post: devuelve la posicion de la restriccion si la ha encontrado o -1 si
	// no lo ha encontrado
	private int buscarPosicionRestriccionPorId(String id) {

		int pos = -1;
		for (int i = 0; i < this.restricciones.size(); i++) {
			if (Integer.toString(this.restricciones.get(i).getId()).compareTo(
					id) == 0) {
				pos = i;
				break;
			}

		}

		return pos;

	}

	// pre: un id de restriccion
	// post: devuelve un objeto del tipo restriccion si lo ha encontrado, null
	// si no
	public Restriccion getRestriccionPorId(String id) {
		int posicion = buscarPosicionRestriccionPorId(id);

		if (posicion > -1) {

			return this.restricciones.get(posicion);
		} else
			return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Doctor other = (Doctor) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		return true;
	}

	// pre: -
	// post:devuelve una string con el nombre del doctor
	public String getNombre() {
		return nombre;
	}

	// pre: una string
	// post:devolvera true si se ha a�adido correctamente o false si
	// introducen una string vacia o el formato no es el correcto
	public boolean setNombre(String nombre) {

		String test = nombre.replaceAll(" ", "");
		if (test.matches("[a-zA-Z]+") && test.length() > 0) {
			this.nombre = nombre;
			return true;
		} else
			return false;
	}

	// pre: -
	// post:devuelve una string con los apellidos
	public String getApellidos() {
		return apellidos;
	}

	// pre: una string
	// post:devolvera true si se ha a�adido correctamente o false si
	// introducen una string vacia o el formato no es el correcto
	public boolean setApellidos(String apellidos) {
		String test = apellidos.replaceAll(" ", "");
		if (test.matches("[a-zA-Z]+") && test.length() > 0) {
			this.apellidos = apellidos;
			return true;
		} else
			return false;
	}

	// pre: -
	// post:devuelve una string con el dni
	public String getDni() {
		return dni;
	}

	// pre: una string
	// post:devuelve true si se ha introducido, false si el formato de la string
	// no es el correcto
	public boolean setDni(String dni) {

		if (dni.length() != 9)
			return false;
		else if (dni.substring(0, 8).matches("[0-9]+")
				&& Character.toString(dni.charAt(8)).matches("[a-zA-Z]+")) {
			this.dni = dni;
			return true;

		} else
			return false;

	}

	// pre: -
	// post:devuelve una date con el nacimiento
	public Date getNacimiento() {
		return nacimiento;
	}

	// pre: un objeto date
	// post:-
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	// pre: -
	// post:devuelve una string con la direccion
	public String getDireccion() {
		return direccion;
	}

	// pre: una sttring con la direccion del doctor
	// post:-
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	// pre: -
	// post:devuelve la experiencia del doctor
	public int getExperiencia() {
		return experiencia;
	}

	// pre: numero positivo
	// post:devuelve la experiencia del doctor
	public boolean setExperiencia(int experiencia) {
		if (experiencia < 0)
			return false;
		else {
			this.experiencia = experiencia;
			return true;
		}
	}

	// pre: -
	// post:devuelve un string con el nombre de la especialidad
	public String getEspecialidad() {
		return especialidad;
	}

	// pre: una string
	// post:devolvera true si se ha a�adido correctamente o false si
	// introducen una string vacia o el formato no es el correcto
	public boolean setEspecialidad(String especialidad) {
		if (especialidad.matches("[a-zA-Z]+") && especialidad.length() > 0) {
			this.especialidad = especialidad;
			return true;

		} else
			return false;
	}

	// pre: -
	// post:devuelve el sueldo del doctor

	public double getSueldo() {
		return sueldo * (1 + (experiencia) / 100);
	}

	@Override
	public String toString() {
		return "Doctor [nombre=" + nombre + ", apellidos=" + apellidos
				+ ", dni=" + dni + ", nacimiento=" + nacimiento
				+ ", direccion=" + direccion + ", experiencia=" + experiencia
				+ ", especialidad=" + especialidad + ", sueldo=" + sueldo
				+ ", idHospital=" + idHospital + "]";
	}

	// pre: sueldo >0
	// post:devolvera true si se ha a�adido correctamente o false si el valor
	// no es el correcto

	public boolean setSueldo(double sueldo) {
		if (sueldo > 0) {
			this.sueldo = sueldo;
			return true;
		} else
			return false;
	}

	// pre: -
	// post:devuelve una string con el id del hospital

	public String getIdHospital() {
		return idHospital;
	}

	// pre: una string
	// post:true si se ha a�adido, false si el formato es incorrecto
	public boolean setIdHospital(String idHospital) {
		if (idHospital.matches("[0-9]+") && idHospital.length() > 0) {
			this.idHospital = idHospital;
			return true;

		} else
			return false;
	}

	// pre: -
	// post:devuelve una string formateada para guardar en fichero
	public String generarCadenaGuardado() {
		String cadenaDatos = new String();

		cadenaDatos = idHospital + ";" + nombre + ";" + apellidos + ";" + dni
				+ ";" + dateFormat.format(nacimiento) + ";" + direccion + ";"
				+ experiencia + ";" + especialidad + ";" + sueldo + ";";

		return cadenaDatos;
	}

}