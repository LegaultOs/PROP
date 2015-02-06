/*@author: Oscar Carod Iglesias */
package dominio.hospital;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dominio.asignaciones.Asignacion;
import dominio.calendario.Calendario;
import dominio.doctor.Doctor;
import dominio.especialidad.Especialidad;

public class Hospital {

	private String nombre;
	private String idHospital;

	private ArrayList<Doctor> conjuntoDoctores;
	private ArrayList<Especialidad> conjuntoEspecialidad;
	private Calendario calendario;
	private ArrayList<Asignacion> asignaciones;

	// pre: -
	// post:Se crea un hospital con nuevo conjuntoDoctores, conjuntoEspecialidad
	// y calendario vacios.
	public Hospital() {
		this.conjuntoDoctores = new ArrayList<Doctor>();
		this.conjuntoEspecialidad = new ArrayList<Especialidad>();
		this.calendario = new Calendario();
		this.asignaciones = new ArrayList<Asignacion>();
	}

	// pre: -
	// post:Se crea un hospital con nuevo idHospital
	public Hospital(String idHospital) {
		this();
		this.idHospital = idHospital;
	}

	// pre: -
	// post: Arraylist de Asignacion
	public ArrayList<Asignacion> getAsignaciones() {
		return asignaciones;
	}

	// pre: un arraylist de Asignacion
	// post: -
	public void setAsignaciones(ArrayList<Asignacion> asignaciones) {
		this.asignaciones = asignaciones;
	}

	// pre: un objeto asignacion
	// post: true si se ha podido insertar, false si no
	public boolean addAsignacion(Asignacion asig) {
		if (!this.asignaciones.contains(new Asignacion(asig.getNombre()))) {
			this.asignaciones.add(asig);
			return true;
		} else
			return false;

	}

	// pre: un id de restriccion
	// post: devuelve la posicion de la restriccion antes del borrado o -1 si no
	// lo ha encontrado
	public int borrarAsignacionPorId(String id) {
		int posicion = buscarPosicionAsignacionPorId(id);

		if (posicion > -1) {
			this.asignaciones.remove(posicion);

			for (int i = posicion; i < asignaciones.size(); ++i) {
				int idAsig = asignaciones.get(i).getId();
				asignaciones.get(i).setId(idAsig - 1);
			}

		}
		return posicion;

	}

	// pre: un id de asignacion
	// post: devuelve un objeto del tipo asignacion si lo ha encontrado, null si
	// no
	public Asignacion getAsignacionPorId(String id) {
		int posicion = buscarPosicionAsignacionPorId(id);

		if (posicion > -1) {

			return this.asignaciones.get(posicion);
		} else
			return null;
	}

	// pre: un id de asignacion
	// post: devuelve la posicion de la asignacion si la ha encontrado o -1 si
	// no lo ha encontrado
	private int buscarPosicionAsignacionPorId(String id) {

		int pos = -1;
		for (int i = 0; i < this.asignaciones.size(); i++) {
			if (Integer.toString(this.asignaciones.get(i).getId())
					.compareTo(id) == 0) {
				pos = i;
				break;
			}

		}

		return pos;

	}

	// pre: -
	// post:devuelve una String con el nombre del hospital
	public String getNombre() {
		return nombre;
	}

	// pre: Una string con solo letras
	// post: Devuelve true en caso de que el input sea correcto o false en caso
	// de que no sea correcto

	public boolean setNombre(String nombre) {

		if (nombre.replace(" ", "").matches("[a-zA-Z0-9]+")) {
			this.nombre = nombre;
			return true;
		} else
			return false;
	}

	// pre: -
	// post:Devuelve una string con la id del hospital

	public String getIdHospital() {
		return idHospital;
	}

	// pre: Una string con el id del hospital
	// post:-

	public void setIdHospital(String idHospital) {
		this.idHospital = idHospital;
	}

	// pre: -
	// post:Devuelve un arraylist con los doctores del hospital

	public ArrayList<Doctor> getConjuntoDoctores() {
		return conjuntoDoctores;
	}

	// pre: ArrayList de doctores
	// post:-

	public void setConjuntoDoctores(ArrayList<Doctor> conjuntoDoctores) {
		this.conjuntoDoctores = conjuntoDoctores;
	}

	// pre: -
	// post:Devuelve un arraylist de especialidades
	public ArrayList<Especialidad> getConjuntoEspecialidad() {
		return conjuntoEspecialidad;
	}

	// pre: arraylist de especialidad
	// post:-
	public void setConjuntoEspecialidad(
			ArrayList<Especialidad> conjuntoEspecialidad) {
		this.conjuntoEspecialidad = conjuntoEspecialidad;
	}

	// pre: -
	// post:devuelve un objeto calendario
	public Calendario getCalendario() {
		return calendario;
	}

	// pre: objeto calendario
	// post:-
	public void setCalendario(Calendario calendario) {
		this.calendario = calendario;
	}

	// pre: doc=!null
	// post:true si se ha añadido, false si no
	public boolean addDoctor(Doctor doc) {
		if (!this.conjuntoDoctores.contains(new Doctor(doc.getDni()))) {
			this.conjuntoDoctores.add(doc);
			return true;
		} else
			return false;

	}

	// pre: string de 8numeros y 1 letra
	// post:devuelve la posición del doctor antes de ser borrado, devuelve -1 si
	// no lo ha encontrado
	public int borrarDoctorPorDni(String dni) {
		int posicion = buscarPosicionDoctorPorDni(dni);

		if (posicion > -1) {
			this.conjuntoDoctores.remove(posicion);

		}
		return posicion;

	}

	// pre: una string de 8 numeros y una letra
	// post:devuelve un objeto doctor que contenga el DNI pasado por parametro,
	// null si no lo encuentra
	public Doctor getDoctorPorDni(String dni) {
		int posicion = buscarPosicionDoctorPorDni(dni);
		if (posicion > -1) {

			return this.conjuntoDoctores.get(posicion);
		} else
			return null;
	}

	// pre: esp!=null
	// post:devuelve true si se ha añadido correctamente, false si ya existia en
	// el conjunto
	public boolean addEspecialidad(Especialidad esp) {
		if (!this.conjuntoEspecialidad.contains(new Especialidad(esp
				.getNombre()))) {
			this.conjuntoEspecialidad.add(esp);
			return true;
		} else
			return false;
	}

	// pre: una string con un nombre de especialidad
	// post:devuelve la posicion de la especialidad antes de que se haya
	// borrado, -1 si no lo ha encontrado
	public int borrarEspecialidadPorNombre(String esp) {
		int posicion = buscarPosicionEspecialidadPorNombre(esp);

		if (posicion > -1) {
			this.conjuntoEspecialidad.remove(posicion);

		}
		return posicion;

	}

	// pre: una string con un nombre de especialidad
	// post:devuelve la posicion de la especialidad pasada por parametro, -1 si
	// no lo encuentra
	public int buscarPosicionEspecialidadPorNombre(String esp) {

		int pos = -1;
		for (int i = 0; i < this.conjuntoEspecialidad.size(); i++) {
			if (this.conjuntoEspecialidad.get(i).getNombre().compareTo(esp) == 0) {
				pos = i;
				break;
			}

		}

		return pos;
	}

	// pre: un nombre de especialidad
	// post: devuelve un objeto del tipo especialidad si lo ha encontrado, null
	// si no
	public Especialidad getEspecialidadPorNombre(String esp) {
		int posicion = buscarPosicionEspecialidadPorNombre(esp);

		if (posicion > -1) {

			return this.conjuntoEspecialidad.get(posicion);
		} else
			return null;
	}

	// pre: una string de dni
	// post:devuelve la posicion con el dni igual que el parametro, -1 si no lo
	// encuentra
	public int buscarPosicionDoctorPorDni(String DniDoc) {

		int pos = -1;
		for (int i = 0; i < this.conjuntoDoctores.size(); i++) {
			if (this.conjuntoDoctores.get(i).getDni().compareTo(DniDoc) == 0) {
				pos = i;
				break;
			}

		}

		return pos;
	}

	// pre: -
	// post:devuelve una string formateada para guardar en fichero
	public String generarCadenaGuardado() {
		String cadenaGuardado = "";

		cadenaGuardado += this.idHospital + ";" + this.nombre + ";";

		return cadenaGuardado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idHospital == null) ? 0 : idHospital.hashCode());
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
		Hospital other = (Hospital) obj;
		if (idHospital == null) {
			if (other.idHospital != null)
				return false;
		} else if (!idHospital.equals(other.idHospital))
			return false;
		return true;
	}

	public void insertarEspecialidad(String nombre, String minDoctores,
			String coeficiente) throws Exception {
		Especialidad esp = new Especialidad();
		if (nombre.equals("") || minDoctores.equals("")
				|| coeficiente.equals(""))
			throw new Exception("Hay campos vacios");

		if (esp.setNombre(nombre) == false
				|| this.getConjuntoEspecialidad().contains(
						new Especialidad(nombre)) == true)
			throw new Exception("Nombre invalido o ya existe en el sistema");
		esp = modificarEspecialidad(esp, minDoctores, coeficiente);
		this.addEspecialidad(esp);

	}

	public Especialidad modificarEspecialidad(Especialidad esp,
			String minDoctores, String coeficiente) throws Exception {

		if (esp.setMinimoDoctores(Integer.parseInt(minDoctores)) == false)
			throw new Exception("Minimo doctores incorrecto");
		else if (esp.setCoeficiente(Double.parseDouble(coeficiente)) == false)
			throw new Exception("Coeficiente incorrecto(Max 2.0)");

		return esp;

	}

	public void insertarDoctor(String nombre, String apellidos, String dni,
			String nacimiento, String direccion, String experiencia,
			String especialidad, String sueldo) throws Exception {

		if (nombre.equals("") || apellidos.equals("") || dni.equals("")
				|| nacimiento.equals("") || direccion.equals("")
				|| experiencia.equals("") || especialidad.equals("")
				|| sueldo.equals(""))
			throw new Exception("Hay campos vacios");
		Doctor doc = new Doctor();
		if (doc.setDni(dni) == false
				|| this.getConjuntoDoctores().contains(new Doctor(dni)) == true)
			throw new Exception("DNI Incorrecto: " + dni);
		doc = modificarDoctor(doc, nombre, apellidos, nacimiento, direccion,
				experiencia, especialidad, sueldo);
		this.addDoctor(doc);

	}

	public Doctor modificarDoctor(Doctor doc, String nombre, String apellidos,
			String nacimiento, String direccion, String experiencia,
			String especialidad, String sueldo) throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

		int exp = -1;
		try {
			exp = Integer.parseInt(experiencia);
		} catch (Exception e) {
			throw new Exception("Experiencia incorrecta");
		}
		double sueld = -1;
		try {
			sueld = Double.parseDouble(sueldo);
		} catch (Exception e) {
			throw new Exception("Sueldo incorrecto");
		}

		if (doc.setIdHospital(this.getIdHospital()) == false)
			throw new Exception("ID hospital invalida");
		if (doc.setNombre(nombre) == false)
			throw new Exception("Nombre con caracteres invalidos");
		if (doc.setApellidos(apellidos) == false)
			throw new Exception("Apellidos con caracteres invalidos");
		if (doc.setExperiencia(exp) == false)
			throw new Exception("Experiencia incorrecta");
		if ((this.conjuntoEspecialidad.contains(new Especialidad(especialidad)) == false || doc
				.setEspecialidad(especialidad) == false))
			throw new Exception("Especialidad incorrecta");
		if (doc.setSueldo(sueld) == false)
			throw new Exception("Sueldo incorrecto");

		doc.setNacimiento(dateFormat.parse(nacimiento));
		doc.setDireccion(direccion);

		return doc;

	}

	public Object[][] getRowListDoctor() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
		Object[][] data = new Object[conjuntoDoctores.size()][9];
		int i = 0;
		DecimalFormat df = new DecimalFormat("#.00");
		for (Doctor d : conjuntoDoctores) {
			data[i][0] = d.getNombre();
			data[i][1] = d.getApellidos();
			data[i][2] = d.getDni();
			data[i][3] = d.getDireccion();
			data[i][4] = d.getEspecialidad();
			data[i][5] = Integer.toString(d.getExperiencia());
			data[i][6] = dateFormat.format(d.getNacimiento());
			data[i][7] = df.format(d.getSueldo()).replace(",", ".");
			data[i][8] = df
					.format(d.getSueldo()
							* this.getEspecialidadPorNombre(d.getEspecialidad())
									.getCoeficiente()).replace(",", ".");
			i++;

		}
		return data;
	}

	public String[] getEspecialidades() {
		String[] listaEsp = new String[conjuntoEspecialidad.size()];
		int i = 0;
		for (Especialidad e : conjuntoEspecialidad) {
			listaEsp[i] = e.getNombre();
			i++;

		}

		return listaEsp;
	}

	public Object[][] getRowListEspecialidades() {

		Object[][] data = new Object[conjuntoEspecialidad.size()][3];
		int i = 0;
		for (Especialidad e : conjuntoEspecialidad) {
			data[i][0] = e.getNombre();
			data[i][1] = Integer.toString(e.getMinimoDoctores());
			data[i][2] = Double.toString(e.getCoeficiente());

			i++;

		}
		return data;
	}

}
