/*@
 * authors:
 * eric.alvarez.chinchilla
 * cristian.barrientos
 * oscar.carod.iglesias
 * raul.enamorado.serratosa
 * marti.ribalta
 * */

package dominio.hospital;

import gestionFicheros.GestionFicherosExtended;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import dominio.asignaciones.Asignacion;
import dominio.calendario.PeriodoVacacional;
import dominio.ctrlAlgoritmo.CtrlAlgoritmo;
import dominio.ctrlAlgoritmo.Restriccion;
import dominio.ctrlAlgoritmo.RestriccionMaxDia;
import dominio.ctrlAlgoritmo.RestriccionMaxMes;
import dominio.ctrlAlgoritmo.RestriccionMaxPeriodo;
import dominio.ctrlAlgoritmo.RestriccionNotDia;
import dominio.ctrlAlgoritmo.RestriccionNotDiaSemanal;
import dominio.ctrlAlgoritmo.RestriccionNotMes;
import dominio.ctrlAlgoritmo.RestriccionNotPeriodoVacacional;
import dominio.ctrlAlgoritmo.RestriccionXorDia;
import dominio.doctor.Doctor;
import dominio.especialidad.Especialidad;
import dominio.exportar.Exportar;
import dominio.turno.Turno;
import dominio.turno.TurnoAsignado;

public class ControladorHospital {

	private static Hospital hospital;
	private static GestionFicherosExtended gfe;
	private static CtrlAlgoritmo ca;

	public ControladorHospital(String idHospital, String nombre)
			throws Exception {
		hospital = new Hospital(idHospital);
		gfe = new GestionFicherosExtended();
		ca = new CtrlAlgoritmo();
		hospital.setNombre(nombre.substring(1, nombre.length()));
		cargarHospital(idHospital);

	}

	public void cargarHospital(String idHospital) throws Exception {
		// se cargarï¿½ del fichero toda la jerarquia: info de hospital, sus
		// doctores, especialidades, calendario, restricciones...
		// testing
		cargarEspecialidades(idHospital);
		cargarDoctores(idHospital);
		cargarAsignaciones(idHospital);
		cargarTurnos();
		cargarPeriodos();
		cargarRestriccionesTodos(idHospital);
	}

	private void cargarEspecialidades(String idHospital) throws Exception {
		if (!gfe.existeFichero("data/especialidad" + idHospital + ".txt"))
			gfe.guardarDatos("data/especialidad" + idHospital + ".txt",
					new ArrayList<String>());
		ArrayList<String> data = gfe.cargarDatos("data/especialidad"
				+ idHospital + ".txt");
		hospital.getConjuntoEspecialidad().clear();
		for (int i = 0; i < data.size(); i++) {
			String ln = data.get(i);
			String[] l = ln.split(";");
			hospital.insertarEspecialidad(l[0], l[1], l[2]);

		}

	}

	private void cargarEspecialidades(String idHospital, String path,
			Hospital aux) throws Exception {

		ArrayList<String> data = gfe.cargarDatos(path);
		for (int i = 0; i < data.size(); i++) {
			String ln = data.get(i);
			String[] l = ln.split(";");
			aux.insertarEspecialidad(l[0], l[1], l[2]);

		}

	}

	private void cargarDoctores(String idHospital) throws Exception {
		if (!gfe.existeFichero("data/doctores" + idHospital + ".txt"))
			gfe.guardarDatos("data/doctores" + idHospital + ".txt",
					new ArrayList<String>());
		ArrayList<String> data = gfe.cargarDatos("data/doctores" + idHospital
				+ ".txt");
		hospital.getConjuntoDoctores().clear();
		for (int i = 0; i < data.size(); i++) {
			String ln = data.get(i);
			String[] l = ln.split(";");
			hospital.insertarDoctor(l[1], l[2], l[3], l[4], l[5], l[6], l[7],
					l[8]);

		}
		// addDoctoresAFichero();
	}

	private void cargarDoctores(String idHospital, String path, Hospital aux)
			throws Exception {

		ArrayList<String> data = gfe.cargarDatos(path);
		for (int i = 0; i < data.size(); i++) {
			String ln = data.get(i);
			String[] l = ln.split(";");
			aux.insertarDoctor(l[1], l[2], l[3], l[4], l[5], l[6], l[7], l[8]);

		}
	}

	public static Hospital getHospital() {
		return hospital;
	}

	public String[] getEspecialidades() {
		String[] listaEsp = null;
		listaEsp = hospital.getEspecialidades();
		return listaEsp;
	}

	public Object[][] getInfoDoctors() throws Exception {
		Object[][] lista = null;
		cargarDoctores(hospital.getIdHospital());
		lista = hospital.getRowListDoctor();

		return lista;
	}

	public void addDoctor(String[] infoDoc) throws Exception {
		hospital.insertarDoctor(infoDoc[0], infoDoc[1], infoDoc[2], infoDoc[3],
				infoDoc[4], infoDoc[5], infoDoc[6], infoDoc[7]);
		appendDoctorAFichero(hospital.getDoctorPorDni(infoDoc[2]));

	}

	private void appendDoctorAFichero(Doctor doctor) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String cadenaLogin = doctor.getDni() + ";"
				+ dateFormat.format(doctor.getNacimiento()).replace("/", "")
				+ ";";
		gfe.addDatos("data/doctores" + hospital.getIdHospital() + ".txt",
				doctor.generarCadenaGuardado());
		gfe.addDatos("data/login" + hospital.getIdHospital() + ".txt",
				cadenaLogin);

	}

	private void addDoctoresAFichero() throws IOException {
		ArrayList<String> datos = new ArrayList<String>();

		for (Doctor d : hospital.getConjuntoDoctores()) {

			datos.add(d.generarCadenaGuardado());
			// logins.add(d.getDni()+";"+dateFormat.format(d.getNacimiento()).replace("/","")+";");
		}

		gfe.guardarDatos("data/doctores" + hospital.getIdHospital() + ".txt",
				datos);
		// gfe.guardarDatos("data/login"+hospital.getIdHospital()+".txt",
		// logins);

	}

	private void addDoctoresAFicheroImport() throws IOException {
		ArrayList<String> datos = new ArrayList<String>();
		ArrayList<String> logins = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		for (Doctor d : hospital.getConjuntoDoctores()) {

			datos.add(d.generarCadenaGuardado());
			logins.add(d.getDni() + ";"
					+ dateFormat.format(d.getNacimiento()).replace("/", "")
					+ ";");
		}

		gfe.guardarDatos("data/doctores" + hospital.getIdHospital() + ".txt",
				datos);
		gfe.guardarDatos("data/login" + hospital.getIdHospital() + ".txt",
				logins);

	}

	public void modifDoctor(String[] infoDoc) throws Exception {
		hospital.modificarDoctor(hospital.getDoctorPorDni(infoDoc[2]),
				infoDoc[0], infoDoc[1], infoDoc[3], infoDoc[4], infoDoc[5],
				infoDoc[6], infoDoc[7]);
		addDoctoresAFichero();
		// Si se modifica, faltaria reescribir el fichero de doctores
	}

	public int elimDoctor(String infoDoc) throws Exception {
		for (Asignacion a : hospital.getAsignaciones()) {
			for (TurnoAsignado ta : a.getTurnosAsignados()) {

				if (ta.getDoctoresAsignados().contains(new Doctor(infoDoc)))
					throw new Exception(
							"No puedes borrar doctores que estan en alguna asignacion");

			}

		}

		int aux = hospital.borrarDoctorPorDni(infoDoc);
		if (aux > -1)
			addDoctoresAFichero();
		removeLogin(infoDoc);

		gfe.eliminarDatos("data/restricciones" + infoDoc + ".txt");
		// Si se borra, faltaria reescribir el fichero de doctores
		return aux;
	}

	@SuppressWarnings("unchecked")
	public void clearDoctor() throws Exception {
		ArrayList<Doctor> aux = new ArrayList<Doctor>();
		aux = (ArrayList<Doctor>) hospital.getConjuntoDoctores().clone();
		for (Doctor d : aux) {
			elimDoctor(d.getDni());

		}

	}

	private void removeLogin(String dni) throws FileNotFoundException,
			IOException {
		ArrayList<String> data = gfe.cargarDatos("data/login"
				+ hospital.getIdHospital() + ".txt");
		for (int i = 0; i < data.size(); i++) {
			String ln = data.get(i);
			String[] l = ln.split(";");
			if (dni.compareTo(l[0]) == 0)
				data.remove(i);
		}
		gfe.guardarDatos("data/login" + hospital.getIdHospital() + ".txt", data);

	}

	// ESPECIALIDADES

	public Object[][] getInfoEspecialidades() {
		Object[][] lista = null;
		lista = hospital.getRowListEspecialidades();
		return lista;
	}

	public void addEspecialidad(String[] infoDoc) throws Exception {
		hospital.insertarEspecialidad(infoDoc[0], infoDoc[1], infoDoc[2]);
		gfe.addDatos("data/especialidad" + hospital.getIdHospital() + ".txt",
				hospital.getEspecialidadPorNombre(infoDoc[0])
						.generarCadenaGuardado());

	}

	public void modifEsp(String[] infoDoc) throws Exception {
		hospital.modificarEspecialidad(
				hospital.getEspecialidadPorNombre(infoDoc[0]), infoDoc[1],
				infoDoc[2]);
		addEspecialidadesAFichero();
	}

	public int elimEsp(String infoDoc) throws IOException {
		int pos = hospital.borrarEspecialidadPorNombre(infoDoc);
		addEspecialidadesAFichero();
		return pos;
	}

	private void addEspecialidadesAFichero() throws IOException {
		ArrayList<String> datos = new ArrayList<String>();

		for (Especialidad e : hospital.getConjuntoEspecialidad()) {

			datos.add(e.generarCadenaGuardado());

		}

		gfe.guardarDatos("data/especialidad" + hospital.getIdHospital()
				+ ".txt", datos);

	}

	public void cargarAsignaciones(String idHospital)
			throws FileNotFoundException, IOException, ParseException {
		if (!gfe.existeFichero("data/asignaciones" + idHospital + ".txt"))
			gfe.guardarDatos("data/asignaciones" + idHospital + ".txt",
					new ArrayList<String>());
		ArrayList<String> data = gfe.cargarDatos("data/asignaciones"
				+ idHospital + ".txt");
		for (int i = 0; i < data.size(); i++) { // asignaciones
			String ln = data.get(i); // asignacion(i)
			String[] l = ln.split(";");

			Asignacion asig = new Asignacion();
			asig.setId(Integer.parseInt(l[0])); // id
			asig.setNombre(l[1]); // nombre

			String[] t = l[2].split(",");
			for (int j = 0; j < t.length; ++j) { // turnosAsignados
				String ta = t[j]; // turno(j)
				String[] tj = ta.split("-");

				TurnoAsignado turnoA = new TurnoAsignado();
				turnoA.setFecha(parseString2Calendar(tj[0])); // fecha
				turnoA.setTipo(tj[1]); // tipo
				if (tj.length > 2) {
					String[] d = tj[2].split(":");
					for (int k = 0; k < d.length; ++k) { // doctores
						String dni = d[k]; // doctor(k)

						Doctor doc = new Doctor(dni); // dni
						turnoA.addDoctor(doc);
					}
				}
				asig.addTurnoAsignado(turnoA);

			}
			hospital.addAsignacion(asig);
		}
	}

	public ArrayList<String> getInfoAsignaciones() {
		ArrayList<String> asigs = new ArrayList<String>();

		for (int i = 0; i < hospital.getAsignaciones().size(); ++i) {
			Asignacion asig = hospital.getAsignaciones().get(i);
			String s;
			s = asig.getId() + ";" + asig.getNombre() + ";";

			ArrayList<TurnoAsignado> ta = asig.getTurnosAsignados();
			// pasar los turnos a string
			for (int j = 0; j < ta.size(); ++j) {
				String sAux = ta.get(j).parseTurnoAsignadoToString() + ",";
				s += sAux;
			}
			asigs.add(s);
		}
		return asigs;
	}

	static public GregorianCalendar parseString2Calendar(String f)
			throws ParseException {
		GregorianCalendar d = new GregorianCalendar();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		d.setTime(dateFormat.parse(f + "/" + d.get(Calendar.YEAR)));

		return d;
	}

	public String[] getInfoDoc(String dni) {
		String[] doctor = new String[4];
		Doctor doc = hospital.getDoctorPorDni(dni);
		if (doc != null) {
			doctor[0] = doc.getNombre();
			doctor[1] = doc.getApellidos();
			doctor[2] = doc.getDni();
			doctor[3] = doc.getEspecialidad();
			return doctor;
		} else {
			return null;
		}
	}

	public int getMinDoctoresEspecialidadPorNombre(String especialidad) {
		return hospital.getEspecialidadPorNombre(especialidad)
				.getMinimoDoctores();
	}

	public void importarDoctores(String path) throws Exception {

		Hospital aux = new Hospital();
		aux = hospital;
		aux.getConjuntoDoctores().clear();
		addDoctoresAFicheroImport();// limpia fichero para el import
		cargarDoctores(hospital.getIdHospital(), path, aux);
		hospital = aux;
		addDoctoresAFicheroImport();// aï¿½ade los importados
	}

	public void importarEspecialidades(String path) throws Exception {
		Hospital aux = new Hospital();
		aux = hospital;
		aux.getConjuntoEspecialidad().clear();
		addEspecialidadesAFichero();// limpia el fichero para import
		cargarEspecialidades(hospital.getIdHospital(), path, aux);
		hospital = aux;
		addEspecialidadesAFichero();// aï¿½ade los importados
	}

	public ArrayList<String> importarAsignaciones(String path)
			throws FileNotFoundException, IOException, ParseException {
		ArrayList<String> asigs = new ArrayList<String>();
		asigs = gfe.importarDatos(path);
		hospital.getAsignaciones().clear();

		for (int i = 0; i < asigs.size(); i++) { // asignaciones
			String ln = asigs.get(i); // asignacion(i)
			String[] l = ln.split(";");

			Asignacion asig = new Asignacion();
			asig.setId(Integer.parseInt(l[0])); // id
			asig.setNombre(l[1]); // nombre

			String[] t = l[2].split(",");
			for (int j = 0; j < t.length; ++j) { // turnosAsignados
				String ta = t[j]; // turno(j)
				String[] tj = ta.split("-");

				TurnoAsignado turnoA = new TurnoAsignado();
				turnoA.setFecha(parseString2Calendar(tj[0])); // fecha
				turnoA.setTipo(tj[1]); // tipo

				String[] d = tj[2].split(":");
				for (int k = 0; k < d.length; ++k) { // doctores
					String dni = d[k]; // doctor(k)

					Doctor doc = new Doctor(dni); // dni
					turnoA.addDoctor(doc);
				}
				asig.addTurnoAsignado(turnoA);

			}
			hospital.addAsignacion(asig);
		}
		return asigs;

	}

	public void guardarAsignaciones(String path, ArrayList<String> lista)
			throws IOException {
		gfe.guardarDatos(path, lista);
	}

	// cristian.barrientos
	public ArrayList<String> getLoginTxt(String idHospital)
			throws FileNotFoundException, IOException {
		ArrayList<String> loginTxt = new ArrayList<>();
		if (!gfe.existeFichero("data/login" + idHospital + ".txt"))
			gfe.guardarDatos("data/login" + idHospital + ".txt",
					new ArrayList<String>());
		loginTxt = gfe.cargarDatos("data/login" + idHospital + ".txt");

		return loginTxt;
	}

	// cristian.barrientos
	public ArrayList<String> getDoctoresTxt(String idHospital)
			throws FileNotFoundException, IOException {
		ArrayList<String> doctoresTxt = new ArrayList<>();
		;
		if (!gfe.existeFichero("data/doctores" + idHospital + ".txt"))
			gfe.guardarDatos("data/doctores" + idHospital + ".txt",
					new ArrayList<String>());
		doctoresTxt = gfe.cargarDatos("data/doctores" + idHospital + ".txt");

		return doctoresTxt;
	}

	// cristian.barrientos
	public ArrayList<String> getPeriodosTxt(String idHospital)
			throws FileNotFoundException, IOException {
		ArrayList<String> periodosTxt = new ArrayList<>();
		;
		if (!gfe.existeFichero("data/periodos" + idHospital + ".txt"))
			gfe.guardarDatos("data/periodos" + idHospital + ".txt",
					new ArrayList<String>());
		periodosTxt = gfe.cargarDatos("data/periodos" + idHospital + ".txt");

		return periodosTxt;
	}

	// cristian.barrientos
	public ArrayList<String> getAsignacionesTxt(String idHospital)
			throws FileNotFoundException, IOException {
		ArrayList<String> asignacionesTxt = new ArrayList<>();
		;
		if (!gfe.existeFichero("data/asignaciones" + idHospital + ".txt"))
			gfe.guardarDatos("data/asignaciones" + idHospital + ".txt",
					new ArrayList<String>());
		asignacionesTxt = gfe.cargarDatos("data/asignaciones" + idHospital
				+ ".txt");

		return asignacionesTxt;
	}

	// cristian.barrientos
	public ArrayList<String> getRestriccionesTxt(String dni)
			throws FileNotFoundException, IOException {
		ArrayList<String> restriccionesTxt = new ArrayList<>();

		if (!gfe.existeFichero("data/restricciones" + dni + ".txt"))
			gfe.guardarDatos("data/restricciones" + dni + ".txt",
					new ArrayList<String>());
		restriccionesTxt = gfe.cargarDatos("data/restricciones" + dni + ".txt");

		return restriccionesTxt;
	}

	// cristian.barrientos
	public void cargarRestriccionesTodos(String idHospital)
			throws FileNotFoundException, IOException, ParseException {
		for (Doctor d : hospital.getConjuntoDoctores())
			cargarRestricciones(d.getDni());
	}

	// cristian.barrientos
	public void cargarRestricciones(String dni) throws FileNotFoundException,
			IOException, ParseException {
		for (String s : getRestriccionesTxt(dni))
			cargarRestriccion(s, dni);
	}

	// cristian.barrientos
	public void guardarNuevoLoginTxt(String idHospital,
			ArrayList<String> nuevoLogin) throws IOException {
		gfe.guardarDatos("data/login" + idHospital + ".txt", nuevoLogin);

	}

	public void addDoctorAsignacion(String idAsig, String fechaTurno,
			String tipoTurno, String dni) throws ParseException {
		Asignacion asig = hospital.getAsignacionPorId(idAsig);
		TurnoAsignado turnoAux = new TurnoAsignado();

		// para todos los turnosAsignados de la asignacion con id=idAsig
		for (int i = 0; i < asig.getTurnosAsignados().size(); ++i) {
			turnoAux = asig.getTurnosAsignados().get(i);
			GregorianCalendar fecha = Turno.parseString2Calendar(fechaTurno);

			// Si el turno tiene la misma fecha y mismo tipo añadimos el doctor
			if (turnoAux.getFecha().equals(fecha)
					&& turnoAux.getTipo().equals(tipoTurno)) {
				Doctor doctor = hospital.getDoctorPorDni(dni);
				turnoAux.addDoctor(doctor);
				// borrar el turno antiguo y añaadir el turno con el nuevo
				// doctor
				hospital.getAsignaciones().get(Integer.parseInt(idAsig))
						.getTurnosAsignados().remove(i);
				hospital.getAsignaciones().get(Integer.parseInt(idAsig))
						.getTurnosAsignados().add(turnoAux);

				break;
			}
		}
	}

	public boolean eliminarDoctorAsignacion(String idAsig, String fechaTurno,
			String tipoTurno, String dni) throws ParseException {
		Asignacion asig = hospital.getAsignacionPorId(idAsig);
		TurnoAsignado turnoAux = new TurnoAsignado();

		// para todos los turnosAsignados de la asignacion con id=idAsig
		for (int i = 0; i < asig.getTurnosAsignados().size(); ++i) {
			turnoAux = asig.getTurnosAsignados().get(i);
			GregorianCalendar fecha = Turno.parseString2Calendar(fechaTurno);

			// Si el turno tiene la misma fecha y mismo tipo eliminamos el
			// doctor
			if (turnoAux.getFecha().equals(fecha)
					&& turnoAux.getTipo().equals(tipoTurno)) {

				// Si se borra el doctor devolvemos cierto
				if (turnoAux.borrarDoctorPorDni(dni) > -1) {
					// borrar el turno antiguo y aï¿½adir el turno con el nuevo
					// doctor
					hospital.getAsignaciones().get(Integer.parseInt(idAsig))
							.getTurnosAsignados().remove(i);
					hospital.getAsignaciones().get(Integer.parseInt(idAsig))
							.getTurnosAsignados().add(turnoAux);
					return true;
				}
				// Si no se borra el doctor devolvemos falso
				else {
					return false;
				}
			}
		}
		return false;
	}

	// cristian.barrientos
	public boolean addRestriccion(String restriccion, String dni)
			throws ParseException, FileNotFoundException, IOException {
		String[] split = restriccion.split(";");
		if (!gfe.existeFichero("data/restricciones" + dni + ".txt"))
			gfe.guardarDatos("data/restricciones" + dni + ".txt",
					new ArrayList<String>());
		ArrayList<String> rest = gfe.cargarDatos("data/restricciones" + dni
				+ ".txt");
		if (isNuevaRestriccion(restriccion, dni)
				&& noSolapaRestriccion(restriccion, dni)) {
			if (split[0].equals("NOT")) {
				if (split[1].equals("dia")) {
					String[] split1 = split[2].split("/");
					hospital.getDoctorPorDni(dni).addRestriccion(
							new RestriccionNotDia(Turno
									.parseString2Calendar(split1[0] + "/"
											+ split1[1])));
					rest.add(restriccion);
					gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
				}

				else if (split[1].equals("mes")) {
					hospital.getDoctorPorDni(dni).addRestriccion(
							new RestriccionNotMes(split[2]));
					rest.add(restriccion);
					gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
				}

				else if (split[1].equals("pv")) {
					hospital.getDoctorPorDni(dni).addRestriccion(
							new RestriccionNotPeriodoVacacional(hospital
									.getCalendario().getPeriodo(split[2])));
					rest.add(restriccion);
					gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
				} else {
					hospital.getDoctorPorDni(dni).addRestriccion(
							new RestriccionNotDiaSemanal(split[2]));
					rest.add(restriccion);
					gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
				}
			} else if (split[0].equals("MAX")) {
				if (split[1].equals("dia")) {
					hospital.getDoctorPorDni(dni).addRestriccion(
							new RestriccionMaxDia(Integer.parseInt(split[2])));
					rest.add(restriccion);
					gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
				} else if (split[1].equals("mes")) {
					hospital.getDoctorPorDni(dni).addRestriccion(
							new RestriccionMaxMes(split[2], Integer
									.parseInt(split[3])));
					rest.add(restriccion);
					gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
				} else {
					hospital.getDoctorPorDni(dni).addRestriccion(
							new RestriccionMaxPeriodo(hospital.getCalendario()
									.getPeriodo(split[2]), Integer
									.parseInt(split[3])));
					rest.add(restriccion);
					gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
				}
			} else {
				hospital.getDoctorPorDni(dni).addRestriccion(
						new RestriccionXorDia(Turno
								.parseString2Calendar(split[2]), Turno
								.parseString2Calendar(split[3])));
				rest.add(restriccion);
				gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
			}
			return true;
		} else
			return false;
	}

	// cristian.barrientos
	public void cargarRestriccion(String restriccion, String dni)
			throws FileNotFoundException, IOException, ParseException {

		String[] split = restriccion.split(";");
		if (!gfe.existeFichero("data/restricciones" + dni + ".txt"))
			gfe.guardarDatos("data/restricciones" + dni + ".txt",
					new ArrayList<String>());
		if (split[0].equals("NOT")) {
			if (split[1].equals("dia")) {
				String[] split1 = split[2].split("/");
				hospital.getDoctorPorDni(dni).addRestriccion(
						new RestriccionNotDia(Turno
								.parseString2Calendar(split1[0] + "/"
										+ split1[1])));
			}

			else if (split[1].equals("mes"))
				hospital.getDoctorPorDni(dni).addRestriccion(
						new RestriccionNotMes(split[2]));

			else if (split[1].equals("pv"))
				hospital.getDoctorPorDni(dni).addRestriccion(
						new RestriccionNotPeriodoVacacional(hospital
								.getCalendario().getPeriodo(split[2])));

			else
				hospital.getDoctorPorDni(dni).addRestriccion(
						new RestriccionNotDiaSemanal(split[2]));

		} else if (split[0].equals("MAX")) {
			if (split[1].equals("dia"))
				hospital.getDoctorPorDni(dni).addRestriccion(
						new RestriccionMaxDia(Integer.parseInt(split[2])));
			else if (split[1].equals("mes"))
				hospital.getDoctorPorDni(dni).addRestriccion(
						new RestriccionMaxMes(split[2], Integer
								.parseInt(split[3])));
			else
				hospital.getDoctorPorDni(dni).addRestriccion(
						new RestriccionMaxPeriodo(hospital.getCalendario()
								.getPeriodo(split[2]), Integer
								.parseInt(split[3])));
		} else
			hospital.getDoctorPorDni(dni).addRestriccion(
					new RestriccionXorDia(Turno.parseString2Calendar(split[2]),
							Turno.parseString2Calendar(split[3])));
	}

	// cristian.barrientos
	public void removeRestriccion(String restriccion, String dni)
			throws ParseException, FileNotFoundException, IOException {

		String[] split = restriccion.split(";");

		ArrayList<String> rest = gfe.cargarDatos("data/restricciones" + dni
				+ ".txt");
		if (split[0].equals("NOT")) {
			if (split[1].equals("dia")) {
				String[] split1 = split[2].split("/");
				ArrayList<Restriccion> restricciones = hospital
						.getDoctorPorDni(dni).getRestricciones();
				for (int i = 0; i < restricciones.size(); i++) {
					if (restricciones.get(i) instanceof RestriccionNotDia) {
						RestriccionNotDia rnd = (RestriccionNotDia) restricciones
								.get(i);
						String fecha = split1[0] + "/" + split1[1];
						if (fecha.equals(Turno.parseCalendar2String(rnd
								.getDia()))) {
							hospital.getDoctorPorDni(dni)
									.borrarRestriccionPorPosicion(i);
							break;
						}
					}
				}
				for (int i = 0; i < rest.size(); i++) {
					if (rest.get(i).equals(restriccion)) {
						rest.remove(i);
						gfe.guardarDatos("data/restricciones" + dni + ".txt",
								rest);
						break;
					}
				}
			} else if (split[1].equals("mes")) {
				ArrayList<Restriccion> restricciones = hospital
						.getDoctorPorDni(dni).getRestricciones();
				for (int i = 0; i < restricciones.size(); i++) {
					if (restricciones.get(i) instanceof RestriccionNotMes) {
						RestriccionNotMes rnm = (RestriccionNotMes) restricciones
								.get(i);
						if (split[2].equals(rnm.getMes())) {
							hospital.getDoctorPorDni(dni)
									.borrarRestriccionPorPosicion(i);
							break;
						}
					}
				}
				for (int i = 0; i < rest.size(); i++) {
					if (rest.get(i).equals(restriccion)) {
						rest.remove(i);
						gfe.guardarDatos("data/restricciones" + dni + ".txt",
								rest);
						break;
					}
				}
			}

			else if (split[1].equals("pv")) {
				ArrayList<Restriccion> restricciones = hospital
						.getDoctorPorDni(dni).getRestricciones();
				for (int i = 0; i < restricciones.size(); i++) {
					if (restricciones.get(i) instanceof RestriccionNotPeriodoVacacional) {
						RestriccionNotPeriodoVacacional rnv = (RestriccionNotPeriodoVacacional) restricciones
								.get(i);
						if (split[2].equals(rnv.getPeriodo()
								.getPeriodoVacacional())) {
							hospital.getDoctorPorDni(dni)
									.borrarRestriccionPorPosicion(i);
							break;
						}
					}
				}
				for (int i = 0; i < rest.size(); i++) {
					if (rest.get(i).equals(restriccion)) {
						rest.remove(i);
						gfe.guardarDatos("data/restricciones" + dni + ".txt",
								rest);
						break;
					}
				}
			} else {
				ArrayList<Restriccion> restricciones = hospital
						.getDoctorPorDni(dni).getRestricciones();
				for (int i = 0; i < restricciones.size(); i++) {
					if (restricciones.get(i) instanceof RestriccionNotDiaSemanal) {
						RestriccionNotDiaSemanal rnds = (RestriccionNotDiaSemanal) restricciones
								.get(i);
						if (split[2].equals(rnds.getDiaSemanal())) {
							hospital.getDoctorPorDni(dni)
									.borrarRestriccionPorPosicion(i);
							break;
						}
					}
				}
				for (int i = 0; i < rest.size(); i++) {
					if (rest.get(i).equals(restriccion)) {
						rest.remove(i);
						gfe.guardarDatos("data/restricciones" + dni + ".txt",
								rest);
						break;
					}
				}
			}
		} else if (split[0].equals("MAX")) {
			if (split[1].equals("dia")) {
				ArrayList<Restriccion> restricciones = hospital
						.getDoctorPorDni(dni).getRestricciones();
				for (int i = 0; i < restricciones.size(); i++) {
					if (restricciones.get(i) instanceof RestriccionMaxDia) {
						RestriccionMaxDia rmd = (RestriccionMaxDia) restricciones
								.get(i);
						if (split[2].equals(rmd.getMaxDias())) {
							hospital.getDoctorPorDni(dni)
									.borrarRestriccionPorPosicion(i);
							break;
						}
					}
				}
				for (int i = 0; i < rest.size(); i++) {
					if (rest.get(i).equals(restriccion)) {
						rest.remove(i);
						gfe.guardarDatos("data/restricciones" + dni + ".txt",
								rest);
						break;
					}
				}
			} else if (split[1].equals("mes")) {
				ArrayList<Restriccion> restricciones = hospital
						.getDoctorPorDni(dni).getRestricciones();
				for (int i = 0; i < restricciones.size(); i++) {
					if (restricciones.get(i) instanceof RestriccionMaxMes) {
						RestriccionMaxMes rmm = (RestriccionMaxMes) restricciones
								.get(i);
						if (split[3].equals(rmm.getMaxDias())
								&& split[2].equals(rmm.getMes())) {
							hospital.getDoctorPorDni(dni)
									.borrarRestriccionPorPosicion(i);
							break;
						}
					}
				}
				for (int i = 0; i < rest.size(); i++) {
					if (rest.get(i).equals(restriccion)) {
						rest.remove(i);
						gfe.guardarDatos("data/restricciones" + dni + ".txt",
								rest);
						break;
					}
				}
			} else {
				ArrayList<Restriccion> restricciones = hospital
						.getDoctorPorDni(dni).getRestricciones();
				for (int i = 0; i < restricciones.size(); i++) {
					if (restricciones.get(i) instanceof RestriccionMaxPeriodo) {
						RestriccionMaxPeriodo rmp = (RestriccionMaxPeriodo) restricciones
								.get(i);
						if (split[3].equals(rmp.getMaxDias())
								&& split[2].equals(rmp.getPeriodo()
										.getPeriodoVacacional())) {
							hospital.getDoctorPorDni(dni)
									.borrarRestriccionPorPosicion(i);
							break;
						}
					}
				}
				for (int i = 0; i < rest.size(); i++) {
					if (rest.get(i).equals(restriccion)) {
						rest.remove(i);
						gfe.guardarDatos("data/restricciones" + dni + ".txt",
								rest);
						break;
					}
				}
			}
		} else {
			ArrayList<Restriccion> restricciones = hospital
					.getDoctorPorDni(dni).getRestricciones();
			for (int i = 0; i < restricciones.size(); i++) {
				if (restricciones.get(i) instanceof RestriccionXorDia) {
					RestriccionXorDia rxd = (RestriccionXorDia) restricciones
							.get(i);
					if (split[2].equals(Turno.parseCalendar2String(rxd
							.getDia1()))
							&& split[3].equals(Turno.parseCalendar2String(rxd
									.getDia2()))) {
						hospital.getDoctorPorDni(dni)
								.borrarRestriccionPorPosicion(i);
						break;
					}
				}
			}
			for (int i = 0; i < rest.size(); i++) {
				if (rest.get(i).equals(restriccion)) {
					rest.remove(i);
					gfe.guardarDatos("data/restricciones" + dni + ".txt", rest);
					break;
				}
			}
		}
	}

	// cristian.barrientos
	private boolean isNuevaRestriccion(String restriccion, String dni)
			throws FileNotFoundException, IOException {
		for (String r : gfe.cargarDatos("data/restricciones" + dni + ".txt"))
			if (r.equals(restriccion))
				return false;
		return true;
	}

	// cristian.barrientos
	private boolean noSolapaRestriccion(String restriccion, String dni)
			throws FileNotFoundException, IOException {
		String[] split1 = restriccion.split(";");
		for (String r : gfe.cargarDatos("data/restricciones" + dni + ".txt")) {
			String[] split = r.split(";");
			if (split1[0].equals("MAX")) { // Si soy MAX
				if (split[0].equals("MAX")) { // Si soy MAX y me encuentro una
												// MAX
					if (split1[1].equals("dia") && split[1].equals("dia"))
						return false; // Si soy MAXdia y ya hay una restriccion
										// MAXdia, no puedo meter mas
					else if (split1[1].equals("mes")) { // Si soy MaxMes, tengo
														// que tener en cuenta
														// que:
						if (split[1].equals("mes")) // Si me encuentro una
													// MaxMes
							if (split1[2].equals(split[2]))
								return false; // Si soy una MaxMes con mes Junio
												// y hay otra MaxMes con mes
												// Junio, no puedo meter mas
							else if (split[1].equals("pv")) { // Si me encuentro
																// una MaxPv,
																// que comprenda
																// el mes que
																// intento poner
																// como Max, no
																// puedo
								String[] fecha1 = Turno.parseCalendar2String(
										hospital.getCalendario()
												.getPeriodo(split[2])
												.getFechaIni()).split("/");
								String[] fecha2 = Turno.parseCalendar2String(
										hospital.getCalendario()
												.getPeriodo(split[2])
												.getFechaFin()).split("/");
								int mesRestriccion = Integer
										.parseInt(split1[2]);
								int mesPvIni = Integer.parseInt(fecha1[1]);
								int mesPvFin = Integer.parseInt(fecha2[1]);
								if (mesRestriccion >= mesPvIni
										&& mesRestriccion <= mesPvFin)
									return false; // Los paso a int por
													// comodidad en el
													// condicional
							}
					} else if (split1[1].equals("pv")) { // Si soy una MaxPV,
															// tngo que tener en
															// cuenta que:
						String[] fecha1 = Turno.parseCalendar2String(
								hospital.getCalendario().getPeriodo(split1[2])
										.getFechaIni()).split("/");
						String[] fecha2 = Turno.parseCalendar2String(
								hospital.getCalendario().getPeriodo(split1[2])
										.getFechaFin()).split("/");
						int mesPvIni = Integer.parseInt(fecha1[1]);
						int mesPvFin = Integer.parseInt(fecha2[1]);
						if (split[1].equals("mes")) { // Si me encuentro una
														// MaxMes
							int mesMax = Integer.parseInt(split[2]);
							if (mesMax >= mesPvIni && mesMax <= mesPvFin)
								return false; // SU mes no puede estar dentro de
												// MI MaxPeriodo
						} else if (split[1].equals("pv")) {// Si me encuentro
															// una MaxPV
							String[] fecha12 = Turno
									.parseCalendar2String(
											hospital.getCalendario()
													.getPeriodo(split[2])
													.getFechaIni()).split("/");
							String[] fecha22 = Turno
									.parseCalendar2String(
											hospital.getCalendario()
													.getPeriodo(split[2])
													.getFechaIni()).split("/");
							int mesPvIni2 = Integer.parseInt(fecha12[1]);
							int mesPvFin2 = Integer.parseInt(fecha22[1]);
							if (mesPvFin < mesPvIni2)
								return true; // Si MI intervalo esta antes de SU
												// intervalo, es valido
							else if (mesPvIni > mesPvFin2)
								return true; // Si MI intervalo esta despues de
												// SU intervalo, es valido
							else
								return false; // Sino es invalido
						}
					}
				} else if (split[0].equals("XOR")) { // Si soy MAX y me
														// encuentro con XOR
					if (split1[1].equals("mes")) { // Si soy MaxMes tengo que
													// tener en cuenta que:
						String[] fecha1 = split[2].split("/");
						String[] fecha2 = split[3].split("/");
						if (split1[2].equals(fecha1[1])
								|| split1[2].equals(fecha2[1]))
							return false; // MI mes no puede estar en una de las
											// fechas de la XOR
					} else if (split1[1].equals("pv")) { // Si soy MaxPV tengo
															// que tener en
															// cuenta que:
						String[] fecha1 = Turno.parseCalendar2String(
								hospital.getCalendario().getPeriodo(split1[2])
										.getFechaIni()).split("/");
						String[] fecha2 = Turno.parseCalendar2String(
								hospital.getCalendario().getPeriodo(split1[2])
										.getFechaFin()).split("/");
						int mesPvIni = Integer.parseInt(fecha1[1]);
						int mesPvFin = Integer.parseInt(fecha2[1]);
						if (split[0].equals("XOR")) { // Si alguna fecha de la
														// XOR tiene un mes
														// dentro de MI periodo,
														// no puedo.
							String[] fecha1xor = split[2].split("/");
							String[] fecha2xor = split[3].split("/");
							int mes1xor = Integer.parseInt(fecha1xor[1]);
							int mes2xor = Integer.parseInt(fecha2xor[1]);
							if (mes1xor >= mesPvIni && mes1xor <= mesPvFin)
								return false;
							else if (mes2xor >= mesPvIni && mes2xor <= mesPvFin)
								return false;
						}
					}
				} else if (split[0].equals("NOT")) { // Si soy MAX y me
														// encuentro con NOT
					if (split1[1].equals("mes")) { // Si soy MaxMes tengo que
													// tener en cuenta que:
						if (split[1].equals("mes")) // Si me encuentro una
													// NotMes
							if (split1[2].equals(split[2]))
								return false; // Si SU mes es igual que el de MI
												// mes, no puedo
						/*
						 * else if (split[1].equals("pv")) { //Si me encuentro
						 * con una NotPv //CASO COMPLICAO QUE DE MOMENTO LO
						 * PERMITO }
						 */
					} else if (split1[1].equals("pv")) { // Si soy MaxPv tengo
															// que tener en
															// cuenta que:
						if (split[1].equals("pv")) // Si me encuentro una NotPv
							if (split1[2].equals(split[2]))
								return false; // Si SU periodo es igual que MI
												// periodo, no puedo
						/*
						 * else if (split[1].equals("mes")) { //Si me encuentro
						 * con una NotPv //CASO COMPLICAO QUE DE MOMENTO LO
						 * PERMITO }
						 */
					}
				}
			} else if (split1[0].equals("XOR")) { // Si soy una XOR tengo que
													// tener en cuenta que:
				String[] fecha1 = split1[2].split("/");
				String[] fecha2 = split1[3].split("/");
				int mes1xor = Integer.parseInt(fecha1[1]);
				int mes2xor = Integer.parseInt(fecha2[1]);
				if (split[0].equals("MAX")) { // Si me encuentro una MAX
					if (split[1].equals("mes")) // Si es una MaxMes
						if (fecha1[1].equals(split[2])
								|| fecha2[1].equals(split[2]))
							return false; // MIS fechas no pueden tener SU mes
						else if (split[1].equals("pv")) { // Si es una MaxPv
							String[] fecha12 = Turno
									.parseCalendar2String(
											hospital.getCalendario()
													.getPeriodo(split[2])
													.getFechaIni()).split("/");
							String[] fecha22 = Turno
									.parseCalendar2String(
											hospital.getCalendario()
													.getPeriodo(split[2])
													.getFechaFin()).split("/");
							int mesPvIni = Integer.parseInt(fecha12[1]);
							int mesPvFin = Integer.parseInt(fecha22[1]);
							if ((mes1xor >= mesPvIni && mes1xor <= mesPvFin)
									|| (mes2xor >= mesPvIni && mes2xor <= mesPvFin))
								return false; // Si SU periodo contiene el mes
												// de alguna de MIS fechas, no
												// puedo
						}
				} else if (split[0].equals("XOR")
						&& (split1[2].equals(split[2])
								|| split1[2].equals(split[3]) // Si me encuentro
																// una XOR y
																// coincide
																// alguna fecha
								|| split1[3].equals(split[2]) || split1[3]
									.equals(split[3])))
					return false;

				else if (split[0].equals("NOT")) { // Si me encuentro una NOT
					if (split[1].equals("dia")) // Si es una NotDia
						if (split1[2].equals(split[2])
								|| split1[3].equals(split[2]))
							return false; // SU fecha no puede ser una de MIS
											// fechas
						else if (split[1].equals("mes")) // Si es una NotMes
							if (fecha1[1].equals(split[2])
									|| fecha2[1].equals(split[2]))
								return false; // SU mes no puede ser el mismo
												// mes que de una de MIS fechas
					/*
					 * else if (split[1].equals("pv")) { //Si es una NotPv //MIS
					 * fechas no pueden estar dentro de SU periodo //Coger los
					 * gregoriancalendar de este periodo, coger
					 * gregoriancalendar de las xor, y mirar q no esten dentro
					 * (compareTo para fechas?) }
					 */
				}
			} else if (split1[0].equals("NOT")) { // Si soy una NOT
				if (split1[1].equals("dia")) { // Si soy una NotDia
					String[] fecha = split1[2].split("/");
					if (split[0].equals("XOR")) // Si me encuentro una XOR
						if (split1[2].equals(split[2])
								|| split1[2].equals(split[3]))
							return false; // Si MI fecha esta en una de SUS
											// fechas, no puedo
						else if (split[0].equals("NOT")) // Si me encuentro con
															// otra NOT
							if (split[1].equals("mes")) // Si me encuentro con
														// una NotMes
								if (fecha[1].equals(split[2]))
									return false; // Si MI fecha es de SU mes,
													// no puedo
				} else if (split1[1].equals("mes")) { // Si soy una NotMes tengo
														// que tener en cuenta
														// que:
					if (split[0].equals("MAX")) { // Si me encuentro una MAX
						if (split[1].equals("mes")) // Si me encuentro una
													// MaxMes
							if (split1[2].equals(split[2]))
								return false; // Si MI mes es SU mes, no puedo
					} else if (split[0].equals("XOR")) { // Si me encuentro una
															// XOR
						String[] fecha1 = split[2].split("/");
						String[] fecha2 = split[3].split("/");
						if (split1[2].equals(fecha1[1])
								|| split1[2].equals(fecha2[1]))
							return false; // Si MI mes esta en una de SUS
											// fechas, no puedo
					} else if (split[0].equals("NOT")) { // Si me encuentro una
															// NOT
						if (split[1].equals("dia")) { // Si me encuentro una
														// NotDia
							String[] fecha = split[2].split("/");
							if (split1[2].equals(fecha[1]))
								return false; // Si MI mes esta en SU fecha, no
												// puedo
						}
					}
				} else if (split1[1].equals("pv")) { // Si soy una NotPv tengo
														// que tener en cuenta
														// que:
					if (split[0].equals("MAX")) { // Si me encuentro una MAX
						// if (split[1].equals("mes")) //Si me encuentro una
						// MaxMes
						if (split[1].equals("pv")) // Si me encuentro una MaxPv
							if (split1[2].equals(split[2]))
								return false; // Si MI periodo es SU periodo, no
												// puedo
					}
					/*
					 * else if (split[0].equals("XOR")) { //Si me encuentro una
					 * XOR String[] fecha1 = split[2].split("/"); String[]
					 * fecha2 = split[3].split("/"); //SUS fechas no pueden
					 * estar dentro de MI periodo //Coger los gregoriancalendar
					 * de este periodo, coger gregoriancalendar de las xor, y
					 * mirar q no esten dentro (compareTo para fechas?) }
					 */
				}
				/*
				 * else if (split1[1].equals("ds")) { //Si soy una NotDs tengo
				 * que tener en cuenta que:
				 * 
				 * }
				 */
			}
		}
		return true;
	}

	// cristian.barrientos
	private boolean noSolapaRestriccionImport(String restriccion, String path,
			int j) throws FileNotFoundException, IOException {
		String[] split1 = restriccion.split(";");
		ArrayList<String> restriccionesAImportar = gfe.cargarDatos(path);
		String[] split = restriccionesAImportar.get(j).split(";");
		if (split1[0].equals("MAX")) { // Si soy MAX
			if (split[0].equals("MAX")) { // Si soy MAX y me encuentro una MAX
				if (split1[1].equals("dia") && split[1].equals("dia"))
					return false; // Si soy MAXdia y ya hay una restriccion
									// MAXdia, no puedo meter mas
				else if (split1[1].equals("mes")) { // Si soy MaxMes, tengo que
													// tener en cuenta que:
					if (split[1].equals("mes")) // Si me encuentro una MaxMes
						if (split1[2].equals(split[2]))
							return false; // Si soy una MaxMes con mes Junio y
											// hay otra MaxMes con mes Junio, no
											// puedo meter mas
						else if (split[1].equals("pv")) { // Si me encuentro una
															// MaxPv, que
															// comprenda el mes
															// que intento poner
															// como Max, no
															// puedo
							String[] fecha1 = Turno
									.parseCalendar2String(
											hospital.getCalendario()
													.getPeriodo(split[2])
													.getFechaIni()).split("/");
							String[] fecha2 = Turno
									.parseCalendar2String(
											hospital.getCalendario()
													.getPeriodo(split[2])
													.getFechaFin()).split("/");
							int mesRestriccion = Integer.parseInt(split1[2]);
							int mesPvIni = Integer.parseInt(fecha1[1]);
							int mesPvFin = Integer.parseInt(fecha2[1]);
							if (mesRestriccion >= mesPvIni
									&& mesRestriccion <= mesPvFin)
								return false; // Los paso a int por comodidad en
												// el condicional
						}
				} else if (split1[1].equals("pv")) { // Si soy una MaxPV, tngo
														// que tener en cuenta
														// que:
					String[] fecha1 = Turno.parseCalendar2String(
							hospital.getCalendario().getPeriodo(split1[2])
									.getFechaIni()).split("/");
					String[] fecha2 = Turno.parseCalendar2String(
							hospital.getCalendario().getPeriodo(split1[2])
									.getFechaFin()).split("/");
					int mesPvIni = Integer.parseInt(fecha1[1]);
					int mesPvFin = Integer.parseInt(fecha2[1]);
					if (split[1].equals("mes")) { // Si me encuentro una MaxMes
						int mesMax = Integer.parseInt(split[2]);
						if (mesMax >= mesPvIni && mesMax <= mesPvFin)
							return false; // SU mes no puede estar dentro de MI
											// MaxPeriodo
					} else if (split[1].equals("pv")) {// Si me encuentro una
														// MaxPV
						String[] fecha12 = Turno.parseCalendar2String(
								hospital.getCalendario().getPeriodo(split[2])
										.getFechaIni()).split("/");
						String[] fecha22 = Turno.parseCalendar2String(
								hospital.getCalendario().getPeriodo(split[2])
										.getFechaIni()).split("/");
						int mesPvIni2 = Integer.parseInt(fecha12[1]);
						int mesPvFin2 = Integer.parseInt(fecha22[1]);
						if (mesPvFin < mesPvIni2)
							return true; // Si MI intervalo esta antes de SU
											// intervalo, es valido
						else if (mesPvIni > mesPvFin2)
							return true; // Si MI intervalo esta despues de SU
											// intervalo, es valido
						else
							return false; // Sino es invalido
					}
				}
			} else if (split[0].equals("XOR")) { // Si soy MAX y me encuentro
													// con XOR
				if (split1[1].equals("mes")) { // Si soy MaxMes tengo que tener
												// en cuenta que:
					String[] fecha1 = split[2].split("/");
					String[] fecha2 = split[3].split("/");
					if (split1[2].equals(fecha1[1])
							|| split1[2].equals(fecha2[1]))
						return false; // MI mes no puede estar en una de las
										// fechas de la XOR
				} else if (split1[1].equals("pv")) { // Si soy MaxPV tengo que
														// tener en cuenta que:
					String[] fecha1 = Turno.parseCalendar2String(
							hospital.getCalendario().getPeriodo(split1[2])
									.getFechaIni()).split("/");
					String[] fecha2 = Turno.parseCalendar2String(
							hospital.getCalendario().getPeriodo(split1[2])
									.getFechaFin()).split("/");
					int mesPvIni = Integer.parseInt(fecha1[1]);
					int mesPvFin = Integer.parseInt(fecha2[1]);
					if (split[0].equals("XOR")) { // Si alguna fecha de la XOR
													// tiene un mes dentro de MI
													// periodo, no puedo.
						String[] fecha1xor = split[2].split("/");
						String[] fecha2xor = split[3].split("/");
						int mes1xor = Integer.parseInt(fecha1xor[1]);
						int mes2xor = Integer.parseInt(fecha2xor[1]);
						if (mes1xor >= mesPvIni && mes1xor <= mesPvFin)
							return false;
						else if (mes2xor >= mesPvIni && mes2xor <= mesPvFin)
							return false;
					}
				}
			} else if (split[0].equals("NOT")) { // Si soy MAX y me encuentro
													// con NOT
				if (split1[1].equals("mes")) { // Si soy MaxMes tengo que tener
												// en cuenta que:
					if (split[1].equals("mes")) // Si me encuentro una NotMes
						if (split1[2].equals(split[2]))
							return false; // Si SU mes es igual que el de MI
											// mes, no puedo
					/*
					 * else if (split[1].equals("pv")) { //Si me encuentro con
					 * una NotPv //CASO COMPLICAO QUE DE MOMENTO LO PERMITO }
					 */
				} else if (split1[1].equals("pv")) { // Si soy MaxPv tengo que
														// tener en cuenta que:
					if (split[1].equals("pv")) // Si me encuentro una NotPv
						if (split1[2].equals(split[2]))
							return false; // Si SU periodo es igual que MI
											// periodo, no puedo
					/*
					 * else if (split[1].equals("mes")) { //Si me encuentro con
					 * una NotPv //CASO COMPLICAO QUE DE MOMENTO LO PERMITO }
					 */
				}
			}
		} else if (split1[0].equals("XOR")) { // Si soy una XOR tengo que tener
												// en cuenta que:
			String[] fecha1 = split1[2].split("/");
			String[] fecha2 = split1[3].split("/");
			int mes1xor = Integer.parseInt(fecha1[1]);
			int mes2xor = Integer.parseInt(fecha2[1]);
			if (split[0].equals("MAX")) { // Si me encuentro una MAX
				if (split[1].equals("mes")) // Si es una MaxMes
					if (fecha1[1].equals(split[2])
							|| fecha2[1].equals(split[2]))
						return false; // MIS fechas no pueden tener SU mes
					else if (split[1].equals("pv")) { // Si es una MaxPv
						String[] fecha12 = Turno.parseCalendar2String(
								hospital.getCalendario().getPeriodo(split[2])
										.getFechaIni()).split("/");
						String[] fecha22 = Turno.parseCalendar2String(
								hospital.getCalendario().getPeriodo(split[2])
										.getFechaFin()).split("/");
						int mesPvIni = Integer.parseInt(fecha12[1]);
						int mesPvFin = Integer.parseInt(fecha22[1]);
						if ((mes1xor >= mesPvIni && mes1xor <= mesPvFin)
								|| (mes2xor >= mesPvIni && mes2xor <= mesPvFin))
							return false; // Si SU periodo contiene el mes de
											// alguna de MIS fechas, no puedo
					}
			} else if (split[0].equals("XOR")
					&& (split1[2].equals(split[2])
							|| split1[2].equals(split[3]) // Si me encuentro una
															// XOR y coincide
															// alguna fecha
							|| split1[3].equals(split[2]) || split1[3]
								.equals(split[3])))
				return false;

			else if (split[0].equals("NOT")) { // Si me encuentro una NOT
				if (split[1].equals("dia")) // Si es una NotDia
					if (split1[2].equals(split[2])
							|| split1[3].equals(split[2]))
						return false; // SU fecha no puede ser una de MIS fechas
					else if (split[1].equals("mes")) // Si es una NotMes
						if (fecha1[1].equals(split[2])
								|| fecha2[1].equals(split[2]))
							return false; // SU mes no puede ser el mismo mes
											// que de una de MIS fechas
				/*
				 * else if (split[1].equals("pv")) { //Si es una NotPv //MIS
				 * fechas no pueden estar dentro de SU periodo //Coger los
				 * gregoriancalendar de este periodo, coger gregoriancalendar de
				 * las xor, y mirar q no esten dentro (compareTo para fechas?) }
				 */
			}
		} else if (split1[0].equals("NOT")) { // Si soy una NOT
			if (split1[1].equals("dia")) { // Si soy una NotDia
				String[] fecha = split1[2].split("/");
				if (split[0].equals("XOR")) // Si me encuentro una XOR
					if (split1[2].equals(split[2])
							|| split1[2].equals(split[3]))
						return false; // Si MI fecha esta en una de SUS fechas,
										// no puedo
					else if (split[0].equals("NOT")) // Si me encuentro con otra
														// NOT
						if (split[1].equals("mes")) // Si me encuentro con una
													// NotMes
							if (fecha[1].equals(split[2]))
								return false; // Si MI fecha es de SU mes, no
												// puedo
			} else if (split1[1].equals("mes")) { // Si soy una NotMes tengo que
													// tener en cuenta que:
				if (split[0].equals("MAX")) { // Si me encuentro una MAX
					if (split[1].equals("mes")) // Si me encuentro una MaxMes
						if (split1[2].equals(split[2]))
							return false; // Si MI mes es SU mes, no puedo
				} else if (split[0].equals("XOR")) { // Si me encuentro una XOR
					String[] fecha1 = split[2].split("/");
					String[] fecha2 = split[3].split("/");
					if (split1[2].equals(fecha1[1])
							|| split1[2].equals(fecha2[1]))
						return false; // Si MI mes esta en una de SUS fechas, no
										// puedo
				} else if (split[0].equals("NOT")) { // Si me encuentro una NOT
					if (split[1].equals("dia")) { // Si me encuentro una NotDia
						String[] fecha = split[2].split("/");
						if (split1[2].equals(fecha[1]))
							return false; // Si MI mes esta en SU fecha, no
											// puedo
					}
				}
			} else if (split1[1].equals("pv")) { // Si soy una NotPv tengo que
													// tener en cuenta que:
				if (split[0].equals("MAX")) { // Si me encuentro una MAX
					// if (split[1].equals("mes")) //Si me encuentro una MaxMes
					if (split[1].equals("pv")) // Si me encuentro una MaxPv
						if (split1[2].equals(split[2]))
							return false; // Si MI periodo es SU periodo, no
											// puedo
				}
				/*
				 * else if (split[0].equals("XOR")) { //Si me encuentro una XOR
				 * String[] fecha1 = split[2].split("/"); String[] fecha2 =
				 * split[3].split("/"); //SUS fechas no pueden estar dentro de
				 * MI periodo //Coger los gregoriancalendar de este periodo,
				 * coger gregoriancalendar de las xor, y mirar q no esten dentro
				 * (compareTo para fechas?) }
				 */
			}
			/*
			 * else if (split1[1].equals("ds")) { //Si soy una NotDs tengo que
			 * tener en cuenta que:
			 * 
			 * }
			 */
		}
		return true;
	}

	// cristian.barrientos
	public boolean importarRestriccionesTxt(String path, String dni)
			throws ParseException, FileNotFoundException, IOException {
		int numRestricciones = gfe.cargarDatos(path).size();
		ArrayList<String> importRestricciones = gfe.cargarDatos(path);
		for (int i = 0; i < numRestricciones; ++i) {
			if (!isFestivoImport(importRestricciones.get(i)))
				return false;
			for (int j = i + 1; j < numRestricciones; ++j) {
				if (importRestricciones.get(i).equals(
						importRestricciones.get(j)))
					return false;
				if (!noSolapaRestriccionImport(importRestricciones.get(i),
						path, j))
					return false;
			}
		}
		gfe.guardarDatos("data/restricciones" + dni + ".txt",
				gfe.cargarDatos(path));
		hospital.getDoctorPorDni(dni).getRestricciones().clear();
		for (String r : gfe.cargarDatos(path))
			cargarRestriccion(r, dni);
		return true;
	}

	// cristian.barrientos
	public boolean isFestivoImport(String restriccion) {
		ArrayList<String> turnosFestivos = getTurnos();
		String[] split = restriccion.split(";");
		if (split[0].equals("NOT") && split[1].equals("dia")) {
			for (String t : turnosFestivos) {
				String[] split1 = t.split(";");
				if (split[2].equals(split1[0]))
					return true;
			}
			return false;
		} else if (split[0].equals("XOR") && split[1].equals("dia")) {
			boolean festivo1 = false;
			boolean festivo2 = false;
			for (String t : turnosFestivos) {
				String[] split1 = t.split(";");
				if (split[2].equals(split1[0]))
					festivo1 = true;
				if (split[3].equals(split1[0]))
					festivo2 = true;
				if (festivo1 && festivo2)
					return true;
			}
			return false;
		}
		return true;
	}

	public void addTurno(String t) throws IOException, ParseException {
		String[] tr = t.split(";");
		if (tr.length > 1)
			hospital.getCalendario().addTurno(
					new Turno(Turno.parseString2Calendar(tr[0]), tr[1]));
		else
			hospital.getCalendario()
					.addFecha(Turno.parseString2Calendar(tr[0]));
		guardarTurnos();

	}

	private void guardarTurnos() throws IOException {
		ArrayList<String> turnos = new ArrayList<String>();
		for (Turno t : hospital.getCalendario().getFestivos()) {
			turnos.add(Turno.parseCalendar2String(t.getFecha()) + ";"
					+ t.getTipo());
		}
		gfe.guardarDatos("data/turnos" + hospital.getIdHospital() + ".txt",
				turnos);
	}

	public void cargarTurnos() throws IOException, ParseException {
		if (!gfe.existeFichero("data/turnos" + hospital.getIdHospital()
				+ ".txt"))
			gfe.guardarDatos("data/turnos" + hospital.getIdHospital() + ".txt",
					new ArrayList<String>());
		ArrayList<String> data = gfe.cargarDatos("data/turnos"
				+ hospital.getIdHospital() + ".txt");
		hospital.getCalendario().getFestivos().clear();
		for (String s : data) {
			hospital.getCalendario().addTurno(Turno.parseStringToTurno(s));
		}
	}

	public ArrayList<String> getTurnos() {
		ArrayList<String> turnos = new ArrayList<String>();
		for (Turno t : hospital.getCalendario().getFestivos()) {
			turnos.add(Turno.parseCalendar2String(t.getFecha()) + ";"
					+ t.getTipo());
		}
		return turnos;
	}

	public void eliminarTurno(String t) throws IOException, ParseException {
		hospital.getCalendario().eliminarTurno(Turno.parseStringToTurno(t));
		gfe.guardarDatos("data/turnos" + hospital.getIdHospital() + ".txt",
				getTurnos());
	}

	public void definirPeriodo(String n, GregorianCalendar d1,
			GregorianCalendar d2) throws IOException {
		hospital.getCalendario().addPeriodo(n, d1, d2);
		guardarPeriodos();
	}

	public void cargarPeriodos() throws FileNotFoundException, IOException,
			ParseException {
		if (!gfe.existeFichero("data/periodos" + hospital.getIdHospital()
				+ ".txt"))
			gfe.guardarDatos("data/periodos" + hospital.getIdHospital()
					+ ".txt", new ArrayList<String>());
		ArrayList<String> data = gfe.cargarDatos("data/periodos"
				+ hospital.getIdHospital() + ".txt");
		hospital.getCalendario().getPeriodos().clear();
		for (String s : data) {
			String[] periodo = s.split(";");
			hospital.getCalendario().addPeriodo(periodo[0],
					Turno.parseString2Calendar(periodo[1]),
					Turno.parseString2Calendar(periodo[2]));
		}
	}

	private void guardarPeriodos() throws IOException {
		ArrayList<String> periodos = new ArrayList<String>();
		for (PeriodoVacacional t : hospital.getCalendario().getPeriodos()) {
			periodos.add(t.getPeriodoVacacional() + ";"
					+ Turno.parseCalendar2String(t.getFechaIni()) + ";"
					+ Turno.parseCalendar2String(t.getFechaFin()));
		}
		gfe.guardarDatos("data/periodos" + hospital.getIdHospital() + ".txt",
				periodos);
	}

	public void eliminarPeriodo(String t) throws IOException {
		hospital.getCalendario().eliminarPeriodo(t);
		guardarPeriodos();
	}

	public boolean existePeriodo(String p) {
		boolean existe = false;
		for (PeriodoVacacional pv : hospital.getCalendario().getPeriodos()) {
			if (pv.getPeriodoVacacional().equals(p))
				existe = true;
		}
		return existe;
	}

	public void importarCalendario(String path) throws FileNotFoundException,
			IOException, ParseException {
		gfe.guardarDatos("data/turnos" + hospital.getIdHospital() + ".txt",
				gfe.cargarDatos(path));
		hospital.getCalendario().getFestivos().clear();
		cargarTurnos();
	}

	public boolean asignarBFS(String s) throws ParseException, IOException {
		cargarRestriccionesTodos(hospital.getIdHospital());
		boolean b = ca.asignarSinCoste(hospital, s);
		return b;
	}

	public boolean asignarDFS(String s) throws ParseException, IOException {
		cargarRestriccionesTodos(hospital.getIdHospital());
		boolean b = ca.asignarSinCosteDfs(hospital, s);
		return b;
	}

	public boolean asignarCoste(String s) throws ParseException, IOException {
		cargarRestriccionesTodos(hospital.getIdHospital());
		boolean b = ca.asignarConCoste(hospital, s);
		return b;
	}

	public void guardarAsig() throws IOException {
		gfe.guardarDatos("data/asignaciones" + hospital.getIdHospital()
				+ ".txt", getInfoAsignaciones());
	}

	public boolean existeAsignacion(String s) {
		boolean b = false;
		for (Asignacion a : hospital.getAsignaciones()) {
			if (a.getNombre().equals(s))
				b = true;
		}
		return b;
	}

	public boolean tieneRestPeriodo() throws FileNotFoundException, IOException {
		boolean b = false;
		for (Doctor d : hospital.getConjuntoDoctores()) {
			for (String s : getRestriccionesTxt(d.getDni())) {
				if (s.split(";")[1].equals("pv")) {
					b = true;
					break;
				}
			}
		}
		return b;
	}

	public String getAsigs() {
		return Integer.toString(hospital.getAsignaciones().size() - 1);
	}

	public int eliminarAsignacion(String id) {
		return hospital.borrarAsignacionPorId(id);
	}

	public void eliminarAsigDoc(String dni) throws IOException {
		for (Asignacion a : hospital.getAsignaciones()) {
			for (TurnoAsignado ta : a.getTurnosAsignados()) {// turnosAsignados
				for (Doctor d : ta.getDoctoresAsignados()) {
					if (d.getDni().equals(dni))
						ta.borrarDoctorPorDni(dni);
				}
			}
		}
		guardarAsig();
	}

	public void exportarAbonito(String path) throws FileNotFoundException,
			IOException {
		Exportar exp = new Exportar();
		path = "exports/" + hospital.getNombre() + "/" + path;

		exp.exportar(hospital.getIdHospital(), path);

	}
}
