/*@author: Raul Enamorado Serratosa */

package dominio.especialidad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Especialidad {

	// ATRIBUTOS

	private String nombre;
	private int minimoDoctores;
	private double coeficiente = 1.0;

	// METODOS

	// pre: -
	// post: Se crea una instancia de Especialidad vacia. El atributo
	// coeficiente es 1.0 por defecto
	public Especialidad() {
	}

	/*
	 * pre: el parametro String nombre debe contener solo letras el parametro
	 * int minimoDoctores debe ser positivo o 0 el parametro double coef debe
	 * pertenecer al intervalo [1.0,2.0)
	 */
	// post: Se crea una instancia de Especialidad con atributos
	public Especialidad(String nombre, int minimoDoctores, double coeficiente) {
		this.nombre = nombre;
		this.minimoDoctores = minimoDoctores;
		this.coeficiente = coeficiente;
	}

	public Especialidad(String nombre) {
		super();
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Especialidad other = (Especialidad) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	// pre: el parametro String nombre debe contener solo letras
	// post: Devuelve cierto si se ha podido modificar el atributo nombre y
	// falso en caso contrario
	public boolean setNombre(String nombre) throws Exception {
		if (nombre == null)
			return false;
		if (comprobarString(nombre)) {
			this.nombre = nombre;
			return true;
		}
		return false;
	}

	// pre: el parametro int minimoDoctores debe ser positivo o 0
	// post: Devuelve cierto si se ha podido modificar el atributo
	// minimoDoctores y falso en caso contrario
	public boolean setMinimoDoctores(int min) {
		if (comprobarInt(min) && ((Integer) min) != null) {
			minimoDoctores = min;
			return true;
		}
		return false;
	}

	// pre: el parametro double coef debe pertenecer al intervalo [1.0,2.0)
	// post: Devuelve cierto si se ha podido modificar el atributo coeficiente y
	// falso en caso contrario
	public boolean setCoeficiente(double coeff) {
		if (comprobarDouble(coeff)) {
			coeficiente = coeff;
			return true;
		}
		return false;
	}

	// pre: -
	// post: Devuelve el nombre de la Especialidad
	public String getNombre() {
		return nombre;
	}

	// pre: -
	// post: Devuelve el minimo de doctores de la Especialidad
	public int getMinimoDoctores() {
		return minimoDoctores;
	}

	// pre: -
	// post: Devuelve el coeficientee de la Especialidad
	public double getCoeficiente() {
		return coeficiente;
	}

	// pre: -
	// post: pasa una especialidad a string en formato "
	public StringBuilder pasarEspecialidadToString() {
		StringBuilder s = new StringBuilder();
		s.append(nombre + ";" + minimoDoctores + ";" + coeficiente + ";");
		return s;
	}

	// PRIVATE METHODS

	// pre : -
	// post: Devuelve cierto si el String introducido contiene solo letras
	private boolean comprobarString(String s) {
		Pattern patron = Pattern.compile("[^A-Za-z]");
		Matcher encaja = patron.matcher(s);
		if (!encaja.find()) {
			return true;
		} else
			return false;
	}

	// pre: -
	// post: Devuelve cierto si el entero introducido es positivo o 0
	private boolean comprobarInt(int min) {
		if (min >= 0)
			return true;
		else
			return false;
	}

	// pre: -
	// post: Devuelve cierto si el double introducido pertenece al intervalo
	// [1.0,2.0)
	private boolean comprobarDouble(double coeff) {
		if (coeff >= 1 && coeff < 2) {
			return true;
		} else
			return false;
	}

	public String generarCadenaGuardado() {

		return nombre + ";" + minimoDoctores + ";" + coeficiente + ";";

	}
}