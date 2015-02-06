/*@author: Oscar Carod Iglesias */
package vista.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import dominio.hospital.ControladorHospital;

public class ControladorVista {

	private static String idHospitalActual;
	private static ControladorHospital hospMgr;

	// cristian.barrientos
	public ArrayList<String> getDoctoresTxt() throws FileNotFoundException,
			IOException {
		return hospMgr.getDoctoresTxt(getIdHospitalActual());
	}

	// cristian.barrientos
	public ArrayList<String> getPeriodosTxt() throws FileNotFoundException,
			IOException {
		return hospMgr.getPeriodosTxt(getIdHospitalActual());
	}

	// cristian.barrientos
	public ArrayList<String> getAsignacionesTxt() throws FileNotFoundException,
			IOException {
		return hospMgr.getAsignacionesTxt(getIdHospitalActual());
	}

	// cristian.barrientos
	public ArrayList<String> getLoginTxt() throws FileNotFoundException,
			IOException {
		return hospMgr.getLoginTxt(getIdHospitalActual());
	}

	// cristian.barrientos
	public ArrayList<String> getRestriccionesTxt(String dni)
			throws FileNotFoundException, IOException {
		return hospMgr.getRestriccionesTxt(dni);
	}

	// cristian.barrientos
	public void guardarNuevoLoginTxt(ArrayList<String> nuevoLogins)
			throws IOException {
		hospMgr.guardarNuevoLoginTxt(getIdHospitalActual(), nuevoLogins);
	}

	// cristian.barrientos
	public boolean addRestriccion(String restriccion, String dni)
			throws ParseException, FileNotFoundException, IOException { // BARRI
		return hospMgr.addRestriccion(restriccion, dni);
	}

	// cristian.barrientos
	public void cargarRestriccion(String restriccion, String dni)
			throws ParseException, FileNotFoundException, IOException { // BARRI
		hospMgr.cargarRestriccion(restriccion, dni);
	}

	// cristian.barrientos
	public void removeRestriccion(String restriccion, String dni)
			throws ParseException, FileNotFoundException, IOException { // BARRI
		hospMgr.removeRestriccion(restriccion, dni);
	}

	// cristian.barrientos
	public boolean importarRestriccionesTxt(String path, String dni)
			throws FileNotFoundException, IOException, ParseException {
		return hospMgr.importarRestriccionesTxt(path, dni);
	}

	public String getIdHospitalActual() {
		return idHospitalActual;
	}

	public void setIdHospitalActual(String idHospAct) {
		idHospitalActual = idHospAct;
	}

	public static void cambiarHospital(String id, String nombre)
			throws Exception {
		idHospitalActual = id;// esto habria que recogerlo de la pantalla de
								// login
		hospMgr = new ControladorHospital(idHospitalActual, nombre);
	}

	public Object[][] getDoctores() {
		Object[][] lista = null;
		try {
			lista = hospMgr.getInfoDoctors();
		} catch (Exception e) {
			lista = null;
		}
		return lista;
	}

	public String addDoc(String[] infoDoc) {
		String motivo = null;
		try {
			hospMgr.addDoctor(infoDoc);
		} catch (Exception e) {
			motivo = e.getMessage();
		}
		return motivo;
	}

	public String modifDoc(String[] infoDoc) {
		String motivo = null;
		try {
			hospMgr.modifDoctor(infoDoc);
		} catch (Exception e) {
			motivo = e.getMessage();
		}
		return motivo;
	}

	public String elimDoc(String infoDoc) {
		String motivo = null;
		try {
			if (hospMgr.elimDoctor(infoDoc) < 0)
				motivo = "No se ha borrado";
		} catch (Exception e) {
			motivo = e.getMessage();
		}
		return motivo;
	}

	public String clearDoctores() {
		String motivo = null;
		try {
			hospMgr.clearDoctor();
		} catch (Exception e) {
			motivo = e.getMessage();
		}
		return motivo;

	}

	public String[] getEspecialidades() {
		String[] lista = null;
		lista = hospMgr.getEspecialidades();
		return lista;
	}

	public Object[][] getInfoEspecialidades() {
		Object[][] lista = null;
		lista = hospMgr.getInfoEspecialidades();
		return lista;
	}

	public void addEsp(String[] infoDoc) throws Exception {
		hospMgr.addEspecialidad(infoDoc);
	}

	public void modifEsp(String[] infoDoc) throws Exception {
		hospMgr.modifEsp(infoDoc);
	}

	public boolean elimEsp(String infoDoc) throws IOException {
		if (hospMgr.elimEsp(infoDoc) > -1)
			return true;
		return false;
	}

	public ArrayList<String> getInfoAsignaciones() {
		ArrayList<String> asigs = new ArrayList<String>();
		asigs = hospMgr.getInfoAsignaciones();
		return asigs;
	}

	public String[] getInfoDoctorDni(String dni) {
		String[] doctor = hospMgr.getInfoDoc(dni);
		return doctor;

	}

	public String addDocFichero(String path) {
		String errorMotivo = null;
		try {
			hospMgr.importarDoctores(path);
		} catch (Exception e) {
			errorMotivo = e.getMessage();
		}
		return errorMotivo;
	}

	public String addEspFichero(String path) {
		String errorMotivo = null;
		try {
			hospMgr.importarEspecialidades(path);
		} catch (Exception e) {
			errorMotivo = e.getMessage();
		}
		return errorMotivo;
	}

	public ArrayList<String> importarAsignaciones(String path)
			throws FileNotFoundException, IOException, ParseException {
		return hospMgr.importarAsignaciones(path);
	}

	public void guardarAsignaciones(String path, ArrayList<String> lista)
			throws FileNotFoundException, IOException {
		hospMgr.guardarAsignaciones(path, lista);
	}

	public void addDoctorAsignacion(String idAsig, String fechaTurno,
			String tipoTurno, String dni) throws ParseException {
		hospMgr.addDoctorAsignacion(idAsig, fechaTurno, tipoTurno, dni);
	}

	public boolean eliminarDoctorAsignacion(String idAsig, String fechaTurno,
			String tipoTurno, String dni) throws ParseException {
		return hospMgr.eliminarDoctorAsignacion(idAsig, fechaTurno, tipoTurno,
				dni);
	}

	public int getMinDoctoresEspecialidadPorNombre(String especialidad) {
		return hospMgr.getMinDoctoresEspecialidadPorNombre(especialidad);
	}

	public void anadirTurno(String t) throws IOException, ParseException {
		hospMgr.addTurno(t);
	}

	public ArrayList<String> getTurnos() {
		return hospMgr.getTurnos();
	}

	public void cargarTurnos() throws IOException, ParseException {
		hospMgr.cargarTurnos();
	}

	public void eliminarTurno(String t) throws IOException, ParseException {
		hospMgr.eliminarTurno(t);
	}

	public void definirPeriodo(String n, Date di, Date df) throws IOException {
		GregorianCalendar gci = new GregorianCalendar();
		GregorianCalendar gcf = new GregorianCalendar();
		gci.setTime(di);
		gcf.setTime(df);
		hospMgr.definirPeriodo(n, gci, gcf);

	}

	public void cargarPeriodos() throws FileNotFoundException, IOException,
			ParseException {
		hospMgr.cargarPeriodos();
	}

	public void eliminarPeriodo(String t) throws IOException {
		hospMgr.eliminarPeriodo(t);
	}

	public boolean existePeriodo(String p) {
		return hospMgr.existePeriodo(p);
	}

	public void importarCalendario(String path) throws FileNotFoundException,
			IOException, ParseException {
		hospMgr.importarCalendario(path);
	}

	public boolean asignarBFS(String s) throws ParseException, IOException {
		return hospMgr.asignarBFS(s);
	}

	public boolean asignarDFS(String s) throws ParseException, IOException {
		return hospMgr.asignarDFS(s);
	}

	public boolean asignarCoste(String s) throws ParseException, IOException {
		return hospMgr.asignarCoste(s);
	}

	public boolean existeAsignacion(String s) {
		return hospMgr.existeAsignacion(s);
	}

	public void guardarAsig() throws IOException {
		hospMgr.guardarAsig();
	}

	public boolean tieneRestPeriodo() throws FileNotFoundException, IOException {
		return hospMgr.tieneRestPeriodo();
	}

	public void eliminarAsigDoc(String dni) throws IOException {
		hospMgr.eliminarAsigDoc(dni);
	}

	public String getAsigs() {
		return hospMgr.getAsigs();
	}

	public String exportarAbonito(String path) {
		String motivo = null;
		try {
			hospMgr.exportarAbonito(path);
		} catch (FileNotFoundException e) {
			motivo = "Ha ocurrido un problema creando el archivo";
		} catch (IOException e) {
			motivo = "Ha ocurrido un problema escribiendo el archivo";
		}
		return motivo;
	}

	public String getNombreHospital() {

		String cadena = ControladorHospital.getHospital().getNombre();
		return cadena;
	}

	public int eliminarAsignacion(String id) {
		return hospMgr.eliminarAsignacion(id);
	}
}
