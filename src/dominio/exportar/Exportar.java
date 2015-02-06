/*@author eric.alvarez.chinchilla*/

package dominio.exportar;

import gestionFicheros.GestionFicherosExtended;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Asignacion {
	int id;
	String nombre;
	ArrayList<Turno> turnos;

	public void sort() {
		Collections.sort(turnos, new Comparator<Turno>() {
			private int compareTurno(Turno a, Turno b) {
				return (a.tipo.equals("Diurno")) ? -1 : 1;
			}

			private int compareFechas(Turno a, Turno b) {
				String[] fechaA = a.fecha.split("/");
				String[] fechaB = b.fecha.split("/");
				int mesA = Integer.parseInt(fechaA[1]);
				int mesB = Integer.parseInt(fechaB[1]);

				if (mesA == mesB) {
					int diaA = Integer.parseInt(fechaA[0]);
					int diaB = Integer.parseInt(fechaB[0]);

					if (diaA == diaB)
						return 0;
					else {
						return diaA < diaB ? -1 : 1;
					}
				}
				return mesA < mesB ? -1 : 1;
			}

			@Override
			public int compare(Turno a, Turno b) {
				int fechas = compareFechas(a, b);
				if (fechas == 0) {
					return compareTurno(a, b);
				} else {
					return fechas;
				}
			}
		});
	}

	@Override
	public String toString() {
		String result = Integer.toString(id) + " - " + nombre + "\n";
		for (int i = 0; i < turnos.size(); i++) {
			result += '\n' + turnos.get(i).toString();
		}
		result += '\n';
		return result;
	}
}

class Turno {
	String fecha;
	String tipo;
	String[] doctores;

	public static String join(String r[], String d) {
		if (r.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		int i;
		for (i = 0; i < r.length - 1; i++)
			sb.append(r[i] + d);
		return sb.toString() + r[i];
	}

	@Override
	public String toString() {
		return String.format("%-15s%-15s %-15s", fecha, tipo,
				join(doctores, " - "));
	}

}

public class Exportar {

	private GestionFicherosExtended gfe;

	public Exportar() {
		gfe = new GestionFicherosExtended();
	}

	public void exportar(String idHospital, String path)
			throws FileNotFoundException, IOException {
		ArrayList<String> data = gfe.cargarDatos("data/asignaciones"
				+ idHospital + ".txt");
		String[] aux = path.split("/");
		ArrayList<String> dataAux = new ArrayList<String>();
		for (String s : data) {
			String[] sAux = s.split(";");
			if (sAux[1].compareTo(aux[2]) == 0)
				dataAux.add(s);
		}

		ArrayList<String> r = exportarAssignaciones(dataAux);
		gfe.exportarDatos(r, path);
	}

	private static ArrayList<String> exportarAssignaciones(
			ArrayList<String> turnosSerializados) {
		ArrayList<Asignacion> asignaciones = deserializarAsignaciones(turnosSerializados);
		ordenaAsignaciones(asignaciones);
		ArrayList<String> r = new ArrayList<String>();
		for (int i = 0; i < asignaciones.size(); i++) {
			r.add(asignaciones.get(i).toString());
		}
		return r;
	}

	private static void ordenaAsignaciones(ArrayList<Asignacion> asignaciones) {
		for (int i = 0; i < asignaciones.size(); i++) {
			asignaciones.get(i).sort();
		}

	}

	private static ArrayList<Asignacion> deserializarAsignaciones(
			ArrayList<String> asignacionesSerializadas) {
		ArrayList<Asignacion> asignaciones = new ArrayList<Asignacion>();
		for (int i = 0; i < asignacionesSerializadas.size(); i++) {
			asignaciones.add(deserializarAsignacion(asignacionesSerializadas
					.get(i)));
		}
		return asignaciones;
	}

	private static Asignacion deserializarAsignacion(
			String asignacionSerializada) {
		Asignacion asignacion = new Asignacion();
		String[] partes = asignacionSerializada.split(";");

		asignacion.id = Integer.parseInt(partes[0]);
		asignacion.nombre = partes[1];
		asignacion.turnos = new ArrayList<Turno>();
		String[] turnosSerializados = partes[2].split(",");
		for (int i = 0; i < turnosSerializados.length; i++) {
			asignacion.turnos.add(deserializarTurno(turnosSerializados[i]));
		}

		return asignacion;
	}

	private static Turno deserializarTurno(String turnoSerializado) {
		Turno turno = new Turno();
		String[] partes = turnoSerializado.split("-");

		turno.fecha = partes[0];
		turno.tipo = partes[1];
		turno.doctores = partes[2].split(":");

		return turno;
	}
}
