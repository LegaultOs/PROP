package dominio.calendario;

import java.util.GregorianCalendar;

/**
 * @author cristian.barrientos
 * @others marti.ribalta
 */
public class PeriodoVacacional {
	String periodoVacacional;
	GregorianCalendar fechaIni = new GregorianCalendar();
	GregorianCalendar fechaFin = new GregorianCalendar();

	public PeriodoVacacional() {
		fechaIni = new GregorianCalendar();
		fechaFin = new GregorianCalendar();
	}

	public PeriodoVacacional(String periodoVacacional,
			GregorianCalendar fechaIni, GregorianCalendar fechaFin) {
		this.periodoVacacional = periodoVacacional;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
	}

	public String getPeriodoVacacional() {
		return periodoVacacional;
	}

	public void setPeriodoVacacional(String periodoVacacional) {
		this.periodoVacacional = periodoVacacional;
	}

	public GregorianCalendar getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(GregorianCalendar fechaIni) {
		this.fechaIni = fechaIni;
	}

	public GregorianCalendar getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(GregorianCalendar fechaFin) {
		this.fechaFin = fechaFin;
	}

}
