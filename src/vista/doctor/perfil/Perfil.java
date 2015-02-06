package vista.doctor.perfil;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import vista.gestion.login.Login;
import vista.main.ControladorVista;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;

/**
 * @author cristian.barrientos
 *
 */
public class Perfil extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField txtDNI;
	private JTextField textNombre;
	private JTextField textApellidos;
	private JTextField textDireccion;
	private JTextField textExperiencia;
	private JTextField textSueldo;
	private JTextField textEspecialidad;
	private JButton btnAadirRestricciones;
	private JButton btnRestriccionNot;
	private JButton btnRestriccionMax;
	private JButton btnRestriccionXor;
	private JButton btnRestriccionDiaNOT;
	private JButton btnRestriccionMes;
	private JButton btnRestriccionPeriodoVacacional;
	private JButton btnRestriccionDiaSemanal;
	private JLabel lblPrimeraFecha;
	private JLabel lblSegundaFecha;
	private JLabel lblFecha;
	private JLabel lblMes;
	private JDateChooser dateChooser_1;
	private JDateChooser dateChooser_3;
	private JDateChooser dateChooser_2;
	private JMonthChooser dateChooser_4;
	private JComboBox<String> comboBox;
	private JComboBox<String> comboBox_1;
	private JLabel lblNumeroDias;
	private JLabel lblNumeroDias_1;
	private JLabel lblNumeroDias_2;
	private JTextField textNacimiento;

	private ArrayList<String> doctoresTxt;
	private ArrayList<String> loginsTxt;
	private ArrayList<String> restriccionesTxt;
	private ArrayList<String> asignacionesTxt;
	private ArrayList<String> periodosTxt;
	private ArrayList<String> festivosTxt;

	private ControladorVista cv = new ControladorVista();

	private String dni;
	private String nombre;
	private String apellidos;
	private String experiencia;
	private String direccion;
	private String especialidad;
	private String nacimiento;
	private String sueldo;
	private JFormattedTextField formattedTextField_1;
	private JFormattedTextField formattedTextField_2;
	private JFormattedTextField formattedTextField;
	private JButton btnConfirmarRestriccion;
	private JButton btnCambiarPassword;

	private JButton btnImportarRestricciones;
	private JLabel lblEscogeLaAsignacion;
	private JComboBox<String> comboBox_2;
	private JLabel lblIntroduceElPassword;
	private JLabel lblIntroduceElNuevo;
	private JLabel lblRepitaElNuevo;
	private JTextField passwordField;
	private JTextField passwordField_1;
	private JTextField passwordField_2;
	private JButton btnConfirmar;
	private JButton btnConsultar;

	ConsultarAsignaciones ca;
	ConsultarRestricciones cr;
	ConsultarFestivos cf;

	public Perfil() throws FileNotFoundException, IOException, ParseException {
		setBackground(new Color(255, 153, 0));
		setLayout(null);

		dni = Login.getUserName();
		doctoresTxt = cv.getDoctoresTxt();
		for (String doc : doctoresTxt) {
			String[] camposDoc = doc.split(";");
			if (camposDoc[3].equalsIgnoreCase(dni)) {
				nombre = camposDoc[1];
				apellidos = camposDoc[2];
				nacimiento = camposDoc[4];
				direccion = camposDoc[5];
				experiencia = camposDoc[6];
				especialidad = camposDoc[7];
				sueldo = camposDoc[8];
			}
		}

		cv.cargarPeriodos();

		restriccionesTxt = cv.getRestriccionesTxt(dni);
		for (String s : restriccionesTxt)
			cv.cargarRestriccion(s, dni);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(40, 11, 46, 14);
		add(lblDni);
		txtDNI = new JTextField();
		txtDNI.setEditable(false);
		txtDNI.setText(dni);
		txtDNI.setBounds(106, 8, 86, 20);
		add(txtDNI);
		txtDNI.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(202, 11, 59, 14);
		add(lblNombre);

		textNombre = new JTextField();
		textNombre.setEditable(false);
		textNombre.setText(nombre);
		textNombre.setBounds(281, 8, 86, 20);
		add(textNombre);
		textNombre.setColumns(10);

		textApellidos = new JTextField();
		textApellidos.setEditable(false);
		textApellidos.setBounds(446, 8, 86, 20);
		textApellidos.setText(apellidos);
		add(textApellidos);
		textApellidos.setColumns(10);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(373, 11, 66, 14);
		add(lblApellidos);

		JLabel lblNacimiento = new JLabel("Nacimiento");
		lblNacimiento.setBounds(373, 36, 72, 14);
		add(lblNacimiento);

		JLabel lblDireccion = new JLabel("Direcci\u00F3n");
		lblDireccion.setBounds(41, 36, 66, 14);
		add(lblDireccion);

		JLabel lblEspecialidad = new JLabel("Especialidad");
		lblEspecialidad.setBounds(202, 36, 79, 14);
		add(lblEspecialidad);

		JLabel lblExperiencia = new JLabel("Experiencia");
		lblExperiencia.setBounds(543, 11, 77, 14);
		add(lblExperiencia);

		JLabel lblSueldo = new JLabel("Sueldo");
		lblSueldo.setBounds(545, 36, 46, 14);
		add(lblSueldo);

		textDireccion = new JTextField();
		textDireccion.setEditable(false);
		textDireccion.setBounds(106, 33, 86, 20);
		textDireccion.setText(direccion);
		add(textDireccion);
		textDireccion.setColumns(10);

		textExperiencia = new JTextField();
		textExperiencia.setEditable(false);
		textExperiencia.setBounds(616, 8, 86, 20);
		textExperiencia.setText(experiencia);
		add(textExperiencia);
		textExperiencia.setColumns(10);

		textSueldo = new JTextField();
		textSueldo.setEditable(false);
		textSueldo.setBounds(616, 33, 86, 20);
		textSueldo.setText(sueldo);
		add(textSueldo);
		textSueldo.setColumns(10);

		JLabel lblAos = new JLabel("a\u00F1os");
		lblAos.setBounds(712, 11, 46, 14);
		add(lblAos);

		JLabel lblEuros = new JLabel("euros");
		lblEuros.setBounds(712, 36, 46, 14);
		add(lblEuros);

		JButton btnConsultarRestricciones = new JButton(
				"Consultar Restricciones");
		btnConsultarRestricciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					restriccionesTxt = cv.getRestriccionesTxt(dni);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
				boolean eliminar = false;
				if (cr != null)
					cr.dispose();
				cr = new ConsultarRestricciones(restriccionesTxt, eliminar, dni);
				cr.setVisible(true);

			}
		});
		btnConsultarRestricciones.setBounds(40, 76, 180, 23);
		add(btnConsultarRestricciones);

		JButton btnEliminarRestricciones = new JButton("Eliminar Restricciones");
		btnEliminarRestricciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					restriccionesTxt = cv.getRestriccionesTxt(dni);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
				boolean eliminar = true;
				if (cr != null)
					cr.dispose();
				cr = new ConsultarRestricciones(restriccionesTxt, eliminar, dni);
				cr.setVisible(true);
			}
		});
		btnEliminarRestricciones.setBounds(410, 76, 172, 23);
		add(btnEliminarRestricciones);

		btnRestriccionNot = new JButton("Restricci\u00F3n Not");
		btnRestriccionNot.setBounds(51, 166, 150, 23);
		btnRestriccionNot.setVisible(false);
		btnRestriccionNot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRestriccionDiaNOT.setVisible(true);
				btnRestriccionMes.setVisible(true);
				btnRestriccionPeriodoVacacional.setVisible(true);
				btnRestriccionDiaSemanal.setVisible(true);

				btnConfirmarRestriccion.setEnabled(false);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblMes.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_1.setVisible(false);
				lblNumeroDias_2.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);
				dateChooser_4.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_1.setVisible(false);
				formattedTextField_2.setVisible(false);

				comboBox.setVisible(false);
				comboBox_1.setVisible(false);
			}
		});
		add(btnRestriccionNot);

		btnRestriccionMax = new JButton("Restricci\u00F3n Max");
		btnRestriccionMax.setBounds(51, 230, 150, 23);
		btnRestriccionMax.setVisible(false);
		btnRestriccionMax.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRestriccionDiaNOT.setVisible(true);
				btnRestriccionMes.setVisible(true);
				btnRestriccionPeriodoVacacional.setVisible(true);
				btnRestriccionDiaSemanal.setVisible(false);

				btnConfirmarRestriccion.setEnabled(false);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblMes.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_1.setVisible(false);
				lblNumeroDias_2.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);
				dateChooser_4.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_1.setVisible(false);
				formattedTextField_2.setVisible(false);

				comboBox.setVisible(false);
				comboBox_1.setVisible(false);
			}
		});
		add(btnRestriccionMax);

		btnRestriccionXor = new JButton("Restricci\u00F3n Xor");
		btnRestriccionXor.setBounds(51, 285, 150, 23);
		btnRestriccionXor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRestriccionDiaNOT.setVisible(true);
				btnRestriccionMes.setVisible(false);
				btnRestriccionPeriodoVacacional.setVisible(false);
				btnRestriccionDiaSemanal.setVisible(false);

				btnConfirmarRestriccion.setEnabled(false);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblMes.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_1.setVisible(false);
				lblNumeroDias_2.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);
				dateChooser_4.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_1.setVisible(false);
				formattedTextField_2.setVisible(false);

				comboBox.setVisible(false);
				comboBox_1.setVisible(false);

			}
		});
		btnRestriccionXor.setVisible(false);
		add(btnRestriccionXor);

		btnRestriccionDiaNOT = new JButton("Restricci\u00F3n Dia");
		btnRestriccionDiaNOT.setBounds(225, 144, 142, 23);
		btnRestriccionDiaNOT.setVisible(false);
		btnRestriccionDiaNOT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnRestriccionPeriodoVacacional.isVisible()
						&& !btnRestriccionDiaSemanal.isVisible()) {
					lblNumeroDias.setVisible(true);
					formattedTextField.setVisible(true);
				} else if (!btnRestriccionPeriodoVacacional.isVisible()) {
					lblPrimeraFecha.setVisible(true);
					dateChooser_1.setVisible(true);
					lblSegundaFecha.setVisible(true);
					dateChooser_2.setVisible(true);
				} else {
					lblFecha.setVisible(true);
					dateChooser_3.setVisible(true);
				}
				btnConfirmarRestriccion.setEnabled(true);

				lblMes.setVisible(false);
				lblNumeroDias_1.setVisible(false);
				lblNumeroDias_2.setVisible(false);

				dateChooser_4.setVisible(false);

				formattedTextField_1.setVisible(false);
				formattedTextField_2.setVisible(false);

				comboBox.setVisible(false);
				comboBox_1.setVisible(false);
			}
		});
		add(btnRestriccionDiaNOT);

		btnRestriccionMes = new JButton("Restricci\u00F3n Mes");
		btnRestriccionMes.setBounds(225, 201, 142, 23);
		btnRestriccionMes.setVisible(false);
		btnRestriccionMes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!btnRestriccionDiaSemanal.isVisible()) {
					lblNumeroDias_1.setVisible(true);
					formattedTextField_1.setVisible(true);
				}
				btnConfirmarRestriccion.setEnabled(true);

				lblMes.setVisible(true);
				dateChooser_4.setVisible(true);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_2.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_2.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);

				comboBox.setVisible(false);
				comboBox_1.setVisible(false);
			}
		});
		add(btnRestriccionMes);

		btnRestriccionDiaSemanal = new JButton("Restricci\u00F3n Dia Semanal");
		btnRestriccionDiaSemanal.setBounds(225, 308, 214, 23);
		btnRestriccionDiaSemanal.setVisible(false);
		btnRestriccionDiaSemanal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnConfirmarRestriccion.setEnabled(true);

				comboBox_1.setVisible(true);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblMes.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_1.setVisible(false);
				lblNumeroDias_2.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);
				dateChooser_4.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_1.setVisible(false);
				formattedTextField_2.setVisible(false);

				comboBox.setVisible(false);
			}
		});
		add(btnRestriccionDiaSemanal);

		btnRestriccionPeriodoVacacional = new JButton(
				"Restricci\u00F3n Periodo Vacacional");
		btnRestriccionPeriodoVacacional.setBounds(225, 255, 214, 23);
		btnRestriccionPeriodoVacacional.setVisible(false);
		btnRestriccionPeriodoVacacional.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!btnRestriccionDiaSemanal.isVisible()) {
					lblNumeroDias_2.setVisible(true);
					formattedTextField_2.setVisible(true);
				}
				btnConfirmarRestriccion.setEnabled(true);

				comboBox.setVisible(true);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblMes.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_1.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);
				dateChooser_4.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_1.setVisible(false);

				comboBox_1.setVisible(false);

			}
		});
		add(btnRestriccionPeriodoVacacional);

		lblPrimeraFecha = new JLabel("Fecha:");
		lblPrimeraFecha.setBounds(391, 144, 46, 14);
		lblPrimeraFecha.setVisible(false);
		add(lblPrimeraFecha);

		lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(391, 153, 48, 14);
		lblFecha.setVisible(false);
		add(lblFecha);

		lblSegundaFecha = new JLabel("Fecha:");
		lblSegundaFecha.setBounds(391, 170, 46, 14);
		lblSegundaFecha.setVisible(false);
		add(lblSegundaFecha);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(437, 138, 95, 20);
		dateChooser_1.setDateFormatString("dd/MM/yyyy");
		dateChooser_1.setVisible(false);
		dateChooser_1.getDateEditor().setEnabled(false);
		add(dateChooser_1);

		dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(437, 164, 95, 20);
		dateChooser_2.setDateFormatString("dd/MM/yyyy");
		dateChooser_2.setVisible(false);
		dateChooser_2.getDateEditor().setEnabled(false);
		add(dateChooser_2);

		dateChooser_3 = new JDateChooser();
		dateChooser_3.setBounds(437, 147, 95, 20);
		dateChooser_3.setDateFormatString("dd/MM/yyyy");
		dateChooser_3.setVisible(false);
		dateChooser_3.getDateEditor().setEnabled(false);

		add(dateChooser_3);

		lblNumeroDias = new JLabel("N\u00FAmero D\u00EDas:");
		lblNumeroDias.setBounds(391, 153, 77, 14);
		lblNumeroDias.setVisible(false);
		add(lblNumeroDias);

		lblMes = new JLabel("Mes:");
		lblMes.setBounds(391, 205, 29, 14);
		lblMes.setVisible(false);
		add(lblMes);

		dateChooser_4 = new JMonthChooser();
		dateChooser_4.setBounds(437, 199, 95, 20);
		dateChooser_4.setVisible(false);
		add(dateChooser_4);

		lblNumeroDias_1 = new JLabel("N\u00FAmero D\u00EDas:");
		lblNumeroDias_1.setBounds(543, 205, 77, 14);
		lblNumeroDias_1.setVisible(false);
		add(lblNumeroDias_1);

		comboBox = new JComboBox<String>();
		periodosTxt = cv.getPeriodosTxt();
		for (String p : periodosTxt) {
			String[] split = p.split(";");
			comboBox.addItem(split[0]);
		}
		comboBox.setBounds(446, 256, 95, 20);
		comboBox.setVisible(false);
		add(comboBox);

		lblNumeroDias_2 = new JLabel("N\u00FAmero D\u00EDas:");
		lblNumeroDias_2.setBounds(545, 259, 77, 14);
		lblNumeroDias_2.setVisible(false);
		add(lblNumeroDias_2);

		comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(448, 309, 95, 20);
		comboBox_1.addItem("Lunes");
		comboBox_1.addItem("Martes");
		comboBox_1.addItem("Miercoles");
		comboBox_1.addItem("Jueves");
		comboBox_1.addItem("Viernes");
		comboBox_1.addItem("Sabado");
		comboBox_1.addItem("Domingo");
		comboBox_1.setVisible(false);
		add(comboBox_1);

		btnAadirRestricciones = new JButton("A\u00F1adir Restricciones");
		btnAadirRestricciones.setBounds(230, 76, 170, 23);
		btnAadirRestricciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnRestriccionNot.setVisible(true);
				btnRestriccionMax.setVisible(true);
				btnRestriccionXor.setVisible(true);

				btnRestriccionDiaNOT.setVisible(false);
				btnRestriccionMes.setVisible(false);
				btnRestriccionPeriodoVacacional.setVisible(false);
				btnRestriccionDiaSemanal.setVisible(false);
				btnConfirmarRestriccion.setVisible(true);

				btnConfirmarRestriccion.setEnabled(false);

				btnImportarRestricciones.setVisible(true);

				btnConfirmar.setVisible(false);
				btnConsultar.setVisible(false);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblMes.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_1.setVisible(false);
				lblNumeroDias_2.setVisible(false);
				lblEscogeLaAsignacion.setVisible(false);
				lblIntroduceElPassword.setVisible(false);
				lblIntroduceElNuevo.setVisible(false);
				lblRepitaElNuevo.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);
				dateChooser_4.setVisible(false);

				passwordField.setVisible(false);
				passwordField_1.setVisible(false);
				passwordField_2.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_1.setVisible(false);
				formattedTextField_2.setVisible(false);

				comboBox.setVisible(false);
				comboBox_1.setVisible(false);
				comboBox_2.setVisible(false);

				passwordField.setText("");
				passwordField_1.setText("");
				passwordField_2.setText("");
			}
		});
		add(btnAadirRestricciones);

		textEspecialidad = new JTextField();
		textEspecialidad.setEditable(false);
		textEspecialidad.setText(especialidad);
		textEspecialidad.setBounds(281, 33, 86, 20);
		add(textEspecialidad);
		textEspecialidad.setColumns(10);

		JButton btnConsultarMisTurnos = new JButton(
				"Consultar mis turnos de guardia");
		btnConsultarMisTurnos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				lblEscogeLaAsignacion.setVisible(true);
				comboBox_2.setVisible(true);
				btnConsultar.setVisible(true);

				btnRestriccionNot.setVisible(false);
				btnRestriccionMax.setVisible(false);
				btnRestriccionXor.setVisible(false);

				btnRestriccionDiaNOT.setVisible(false);
				btnRestriccionMes.setVisible(false);
				btnRestriccionPeriodoVacacional.setVisible(false);
				btnRestriccionDiaSemanal.setVisible(false);
				btnConfirmarRestriccion.setVisible(false);

				btnConfirmarRestriccion.setEnabled(false);

				btnImportarRestricciones.setVisible(false);

				btnConfirmar.setVisible(false);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblMes.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_1.setVisible(false);
				lblNumeroDias_2.setVisible(false);
				lblIntroduceElPassword.setVisible(false);
				lblIntroduceElNuevo.setVisible(false);
				lblRepitaElNuevo.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);
				dateChooser_4.setVisible(false);

				passwordField.setVisible(false);
				passwordField_1.setVisible(false);
				passwordField_2.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_1.setVisible(false);
				formattedTextField_2.setVisible(false);

				comboBox.setVisible(false);
				comboBox_1.setVisible(false);

				passwordField.setText("");
				passwordField_1.setText("");
				passwordField_2.setText("");
			}
		});
		btnConsultarMisTurnos.setBounds(592, 76, 216, 23);
		add(btnConsultarMisTurnos);

		textNacimiento = new JTextField();
		textNacimiento.setEditable(false);
		textNacimiento.setText(nacimiento);
		textNacimiento.setBounds(446, 33, 86, 20);
		add(textNacimiento);
		textNacimiento.setColumns(10);

		btnConfirmarRestriccion = new JButton("Confirmar Restricci\u00F3n");
		btnConfirmarRestriccion.setVisible(false);
		btnConfirmarRestriccion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String restriccion;
				if (btnRestriccionPeriodoVacacional.isVisible()
						&& !btnRestriccionDiaSemanal.isVisible()) { // MAX
					if (formattedTextField.isVisible()) {
						restriccion = "MAX;dia;" + formattedTextField.getText();
						if (!formattedTextField.getText().isEmpty()) {
							try {
								if (cv.addRestriccion(restriccion, dni))
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"La Restriccion ha sido añadida correctamente",
													"Correcto",
													JOptionPane.INFORMATION_MESSAGE);
								else
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: La restricción ya existe o se solapa con alguna otra",
													"Error",
													JOptionPane.ERROR_MESSAGE);
							} catch (HeadlessException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (FileNotFoundException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (ParseException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (IOException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						} else
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Debes introducir almenos un numero",
											"Error", JOptionPane.ERROR_MESSAGE);
					} else if (formattedTextField_1.isVisible()) {
						restriccion = "MAX;mes;"
								+ (dateChooser_4.getMonth() + 1) + ";"
								+ formattedTextField_1.getText();
						if (!formattedTextField_1.getText().isEmpty()) {
							try {
								if (cv.addRestriccion(restriccion, dni))
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"La Restriccion ha sido añadida correctamente",
													"Correcto",
													JOptionPane.INFORMATION_MESSAGE);
								else
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: La restricción ya existe o se solapa con alguna otra",
													"Error",
													JOptionPane.ERROR_MESSAGE);
							} catch (HeadlessException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (FileNotFoundException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (ParseException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (IOException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						} else
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Debes introducir almenos un numero11",
											"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						restriccion = "MAX;pv;" + comboBox.getSelectedItem()
								+ ";" + formattedTextField_2.getText();
						if (comboBox.getSelectedItem() != null
								&& !formattedTextField_2.getText().isEmpty()) {
							try {
								if (cv.addRestriccion(restriccion, dni))
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"La Restriccion ha sido añadida correctamente",
													"Correcto",
													JOptionPane.INFORMATION_MESSAGE);
								else
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: La restricción ya existe o se solapa con alguna otra",
													"Error",
													JOptionPane.ERROR_MESSAGE);
							} catch (HeadlessException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (FileNotFoundException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (ParseException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (IOException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						} else
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Los campos no pueden estar vacios",
									"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (!btnRestriccionPeriodoVacacional.isVisible()) { // XOR
					restriccion = "XOR;dia;"
							+ dateChooser_1.getJCalendar().getDayChooser()
									.getDay()
							+ "/"
							+ (dateChooser_1.getJCalendar().getMonthChooser()
									.getMonth() + 1)
							+ "/"
							+ dateChooser_1.getJCalendar().getYearChooser()
									.getYear()
							+ ";"
							+ dateChooser_2.getJCalendar().getDayChooser()
									.getDay()
							+ "/"
							+ (dateChooser_2.getJCalendar().getMonthChooser()
									.getMonth() + 1)
							+ "/"
							+ dateChooser_2.getJCalendar().getYearChooser()
									.getYear();
					String fecha1 = dateChooser_1.getJCalendar()
							.getDayChooser().getDay()
							+ "/"
							+ (dateChooser_1.getJCalendar().getMonthChooser()
									.getMonth() + 1)
							+ "/"
							+ dateChooser_1.getJCalendar().getYearChooser()
									.getYear();
					String fecha2 = dateChooser_2.getJCalendar()
							.getDayChooser().getDay()
							+ "/"
							+ (dateChooser_2.getJCalendar().getMonthChooser()
									.getMonth() + 1)
							+ "/"
							+ dateChooser_2.getJCalendar().getYearChooser()
									.getYear();
					String fecha1sin = dateChooser_1.getJCalendar()
							.getDayChooser().getDay()
							+ "/"
							+ (dateChooser_1.getJCalendar().getMonthChooser()
									.getMonth() + 1);
					String fecha2sin = dateChooser_2.getJCalendar()
							.getDayChooser().getDay()
							+ "/"
							+ (dateChooser_2.getJCalendar().getMonthChooser()
									.getMonth() + 1);
					if (!isFestiva(fecha1sin) || !isFestiva(fecha2sin))
						JOptionPane.showMessageDialog(new JFrame(),
								"Error: Alguna de las fechas no es festiva",
								"Error", JOptionPane.ERROR_MESSAGE);
					else if (fecha1.equals(fecha2))
						JOptionPane.showMessageDialog(new JFrame(),
								"Error: No pueden ser las dos fechas iguales",
								"Error", JOptionPane.ERROR_MESSAGE);
					else if (dateChooser_1.getDate() != null
							&& dateChooser_2.getDate() != null) {
						try {
							if (cv.addRestriccion(restriccion, dni))
								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"La Restriccion ha sido añadida correctamente",
												"Correcto",
												JOptionPane.INFORMATION_MESSAGE);
							else
								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: La restricción ya existe o se solapa con alguna otra",
												"Error",
												JOptionPane.ERROR_MESSAGE);
						} catch (HeadlessException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (FileNotFoundException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else
						JOptionPane.showMessageDialog(new JFrame(),
								"Error: Los campos no pueden estar vacios",
								"Error", JOptionPane.ERROR_MESSAGE);
				} else { // NOT
					if (dateChooser_3.isVisible()) {
						restriccion = "NOT;dia;"
								+ dateChooser_3.getJCalendar().getDayChooser()
										.getDay()
								+ "/"
								+ (dateChooser_3.getJCalendar()
										.getMonthChooser().getMonth() + 1)
								+ "/"
								+ dateChooser_3.getJCalendar().getYearChooser()
										.getYear();
						String fechasin = dateChooser_3.getJCalendar()
								.getDayChooser().getDay()
								+ "/"
								+ (dateChooser_3.getJCalendar()
										.getMonthChooser().getMonth() + 1);
						if (!isFestiva(fechasin))
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: La fecha no es festiva", "Error",
									JOptionPane.ERROR_MESSAGE);
						else if (dateChooser_3.getDate() != null) {
							try {
								if (cv.addRestriccion(restriccion, dni))
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"La Restriccion ha sido añadida correctamente",
													"Correcto",
													JOptionPane.INFORMATION_MESSAGE);
								else
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: La restricción ya existe o se solapa con alguna otra",
													"Error",
													JOptionPane.ERROR_MESSAGE);
							} catch (HeadlessException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (FileNotFoundException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (ParseException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (IOException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						} else
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Los campos no pueden estar vacios",
									"Error", JOptionPane.ERROR_MESSAGE);
					} else if (dateChooser_4.isVisible()) {
						restriccion = "NOT;mes;"
								+ (dateChooser_4.getMonth() + 1);
						try {
							if (cv.addRestriccion(restriccion, dni))
								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"La Restriccion ha sido añadida correctamente",
												"Correcto",
												JOptionPane.INFORMATION_MESSAGE);
							else
								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: La restricción ya existe o se solapa con alguna otra",
												"Error",
												JOptionPane.ERROR_MESSAGE);
						} catch (HeadlessException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (FileNotFoundException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else if (comboBox.isVisible()) {
						restriccion = "NOT;pv;" + comboBox.getSelectedItem();
						if (comboBox.getSelectedItem() != null) {
							try {
								if (cv.addRestriccion(restriccion, dni))
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"La Restriccion ha sido añadida correctamente",
													"Correcto",
													JOptionPane.INFORMATION_MESSAGE);
								else
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: La restricción ya existe o se solapa con alguna otra",
													"Error",
													JOptionPane.ERROR_MESSAGE);
							} catch (HeadlessException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (FileNotFoundException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (ParseException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (IOException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						} else
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Los campos no pueden estar vacios",
									"Error", JOptionPane.ERROR_MESSAGE);
					} else {
						restriccion = "NOT;ds;" + comboBox_1.getSelectedItem();
						if (!comboBox_1.getSelectedItem().toString().isEmpty()) {
							try {
								if (cv.addRestriccion(restriccion, dni))
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"La Restriccion ha sido añadida correctamente",
													"Correcto",
													JOptionPane.INFORMATION_MESSAGE);
								else
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: La restricción ya existe o se solapa con alguna otra",
													"Error",
													JOptionPane.ERROR_MESSAGE);
							} catch (HeadlessException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (FileNotFoundException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (ParseException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							} catch (IOException e) {
								e.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						} else
							JOptionPane.showMessageDialog(new JFrame(),
									"Error: Los campos no pueden estar vacios",
									"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnConfirmarRestriccion.setEnabled(false);
		btnConfirmarRestriccion.setBounds(343, 393, 172, 23);
		add(btnConfirmarRestriccion);

		formattedTextField = new JFormattedTextField(NumberFormat.getInstance());
		formattedTextField.setVisible(false);
		formattedTextField.setBounds(478, 145, 29, 20);
		add(formattedTextField);

		formattedTextField_1 = new JFormattedTextField(
				NumberFormat.getInstance());
		formattedTextField_1.setVisible(false);
		formattedTextField_1.setBounds(628, 202, 29, 20);
		add(formattedTextField_1);

		formattedTextField_2 = new JFormattedTextField(
				NumberFormat.getInstance());
		formattedTextField_2.setVisible(false);
		formattedTextField_2.setBounds(628, 256, 29, 20);
		add(formattedTextField_2);

		btnCambiarPassword = new JButton("Cambiar Password");
		btnCambiarPassword.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblIntroduceElPassword.setVisible(true);
				lblIntroduceElNuevo.setVisible(true);
				lblRepitaElNuevo.setVisible(true);
				passwordField.setVisible(true);
				passwordField_1.setVisible(true);
				passwordField_2.setVisible(true);
				btnConfirmar.setVisible(true);

				btnRestriccionNot.setVisible(false);
				btnRestriccionMax.setVisible(false);
				btnRestriccionXor.setVisible(false);

				btnRestriccionDiaNOT.setVisible(false);
				btnRestriccionMes.setVisible(false);
				btnRestriccionPeriodoVacacional.setVisible(false);
				btnRestriccionDiaSemanal.setVisible(false);
				btnConfirmarRestriccion.setVisible(false);

				btnConfirmarRestriccion.setEnabled(false);
				btnConsultar.setVisible(false);

				btnImportarRestricciones.setVisible(false);

				lblPrimeraFecha.setVisible(false);
				lblFecha.setVisible(false);
				lblSegundaFecha.setVisible(false);
				lblMes.setVisible(false);
				lblNumeroDias.setVisible(false);
				lblNumeroDias_1.setVisible(false);
				lblNumeroDias_2.setVisible(false);
				lblEscogeLaAsignacion.setVisible(false);

				dateChooser_1.setVisible(false);
				dateChooser_2.setVisible(false);
				dateChooser_3.setVisible(false);
				dateChooser_4.setVisible(false);

				formattedTextField.setVisible(false);
				formattedTextField_1.setVisible(false);
				formattedTextField_2.setVisible(false);

				comboBox.setVisible(false);
				comboBox_1.setVisible(false);
				comboBox_2.setVisible(false);
			}
		});
		btnCambiarPassword.setBounds(636, 458, 172, 23);
		add(btnCambiarPassword);

		btnImportarRestricciones = new JButton("Importar Restricciones");
		btnImportarRestricciones.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter type = new FileNameExtensionFilter(
						"TXT File", "txt");
				chooser.setFileFilter(type);

				int returnValue = chooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					try {
						if (cv.importarRestriccionesTxt(path, dni))
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Se han importado las restricciones correctamente",
											"Correcto",
											JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: No se han importado las restricciones, ya que alguna está repetida, se solapan entre ellas o almenos una de las restricciones no es una restricción válida",
											"Error", JOptionPane.ERROR_MESSAGE);
					} catch (HeadlessException e) {
						e.printStackTrace();
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
										"Error", JOptionPane.ERROR_MESSAGE);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
										"Error", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
										"Error", JOptionPane.ERROR_MESSAGE);
					} catch (ParseException e) {
						e.printStackTrace();
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: Ha habido un error inesperado en la conversion de un tipo.",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnImportarRestricciones.setBounds(51, 393, 169, 23);
		btnImportarRestricciones.setVisible(false);
		add(btnImportarRestricciones);

		lblEscogeLaAsignacion = new JLabel(
				"Escoge la asignaci\u00F3n que quieres consultar:");
		lblEscogeLaAsignacion.setBounds(346, 110, 274, 14);
		lblEscogeLaAsignacion.setVisible(false);
		add(lblEscogeLaAsignacion);

		comboBox_2 = new JComboBox<String>();
		ArrayList<String> asig = cv.getAsignacionesTxt();
		for (String as : asig) {
			String[] camposAsignacion = as.split(";");
			comboBox_2.addItem(camposAsignacion[1]);
		}
		comboBox_2.setBounds(616, 107, 142, 20);
		comboBox_2.setVisible(false);
		add(comboBox_2);

		lblIntroduceElPassword = new JLabel("Introduce el password antiguo:");
		lblIntroduceElPassword.setBounds(512, 312, 177, 14);
		lblIntroduceElPassword.setVisible(false);
		add(lblIntroduceElPassword);

		lblIntroduceElNuevo = new JLabel("Introduce el nuevo password:");
		lblIntroduceElNuevo.setBounds(512, 349, 180, 14);
		lblIntroduceElNuevo.setVisible(false);
		add(lblIntroduceElNuevo);

		lblRepitaElNuevo = new JLabel("Repita el nuevo password:");
		lblRepitaElNuevo.setBounds(512, 382, 180, 14);
		lblRepitaElNuevo.setVisible(false);
		add(lblRepitaElNuevo);

		passwordField = new JPasswordField();
		passwordField.setBounds(692, 309, 86, 20);
		add(passwordField);
		passwordField.setVisible(false);
		passwordField.setColumns(10);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(692, 346, 86, 20);
		add(passwordField_1);
		passwordField_1.setVisible(false);
		passwordField_1.setColumns(10);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(692, 379, 86, 20);
		add(passwordField_2);
		passwordField_2.setVisible(false);
		passwordField_2.setColumns(10);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					loginsTxt = cv.getLoginTxt();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
				int index = 0;
				try {
					for (String log : cv.getLoginTxt()) {
						String[] s = log.split(";");
						if (dni.equals(s[0])) {
							if (s[1].equals(passwordField.getText())
									&& (passwordField_1.getText()
											.equals(passwordField_2.getText()))
									&& !passwordField_1.getText().isEmpty()) {
								loginsTxt.remove(index);
								loginsTxt.add(dni + ";"
										+ passwordField_1.getText() + ";");

								cv.guardarNuevoLoginTxt(loginsTxt);

								lblIntroduceElNuevo.setVisible(false);
								lblIntroduceElPassword.setVisible(false);
								lblRepitaElNuevo.setVisible(false);
								passwordField.setVisible(false);
								passwordField.setText("");
								passwordField_1.setVisible(false);
								passwordField_1.setText("");
								passwordField_2.setVisible(false);
								passwordField_2.setText("");

								btnConfirmar.setVisible(false);

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"El password ha sido cambiado correctamente",
												"Correcto",
												JOptionPane.INFORMATION_MESSAGE);
							} else {
								if (!s[1].equals(passwordField.getText()))
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: Ha escrito mal su antiguo password",
													"Error",
													JOptionPane.ERROR_MESSAGE);
								else if (!passwordField_1.getText().isEmpty())
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: Los campos no pueden estar vacios",
													"Error",
													JOptionPane.ERROR_MESSAGE);
								else {
									JOptionPane
											.showMessageDialog(
													new JFrame(),
													"Error: El nuevo password no coincide en ambos campos",
													"Error",
													JOptionPane.ERROR_MESSAGE);
								}
							}
						}
						++index;
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConfirmar.setBounds(660, 410, 121, 23);
		btnConfirmar.setVisible(false);
		add(btnConfirmar);

		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String asginacionSelected = null;
				try {
					asignacionesTxt = cv.getAsignacionesTxt();
				} catch (FileNotFoundException e1) {
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
				for (String as : asignacionesTxt) {
					String[] split = as.split(";");
					if (split[1].equals(comboBox_2.getSelectedItem()))
						asginacionSelected = as;
				}
				if (ca != null)
					ca.dispose();
				ca = new ConsultarAsignaciones(asginacionSelected, dni);
				ca.setVisible(true);
			}
		});
		btnConsultar.setVisible(false);
		btnConsultar.setBounds(616, 140, 89, 23);
		add(btnConsultar);

		JButton btnNewButton = new JButton("Consultar festivos");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				festivosTxt = cv.getTurnos();
				try {
					periodosTxt = cv.getPeriodosTxt();
				} catch (FileNotFoundException e) {
					e.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
				if (cf != null)
					cf.dispose();
				cf = new ConsultarFestivos(festivosTxt, periodosTxt);
				cf.setVisible(true);
			}
		});
		btnNewButton.setBounds(51, 458, 169, 23);
		add(btnNewButton);

	}

	private boolean isFestiva(String fecha) {
		ArrayList<String> fechasFestivas = cv.getTurnos();
		for (String t : fechasFestivas) {
			String[] split = t.split(";");
			if (fecha.equals(split[0]))
				return true;
		}
		return false;
	}
}