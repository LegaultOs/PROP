package vista.gestion.hospital;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import vista.main.ControladorVista;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;

/**
 * @author marti.ribalta
 *
 */
public class GestionHospital extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */

	HashMap<String, Component> componentMap;
	private JCalendar calendar;
	private JTextPane DiaSeleccionado = new JTextPane();
	private Color myColor = new Color(255, 153, 0);
	private ControladorVista ctrlVista = new ControladorVista();
	private JCheckBox chckbxDiurno = new JCheckBox("Diurno");
	private JCheckBox chckbxNocturno = new JCheckBox("Nocturno");
	private JButton anadirButton = new JButton("A\u00F1adir turno");
	private JButton eliminarButton = new JButton("Eliminar turno");
	private JButton btnEliminarDiurno = new JButton("Eliminar Diurno");
	private JButton btnEliminarNocturno = new JButton("Eliminar Nocturno");
	private JTextField textField_1;
	private JComboBox<String> comboBox;
	private int asig;

	public GestionHospital() {
		setVisible(true);

		try {
			ctrlVista.cargarTurnos();
		} catch (IOException e2) {
			e2.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} catch (ParseException e2) {
			e2.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: Ha habido un error inesperado en la conversion de un tipo.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}

		try {
			ctrlVista.cargarPeriodos();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e2) {
			e2.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} catch (ParseException e2) {
			e2.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: Ha habido un error inesperado en la conversion de un tipo.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}
		setBackground(new Color(255, 153, 0));
		setLayout(null);

		calendar = new JCalendar();
		pintarCalendario();
		actualizarCheck();
		actualizarButtons();
		DiaSeleccionado.setText(calendar.getDayChooser().getDay() + " / "
				+ (calendar.getMonthChooser().getMonth() + 1));

		JMonthChooser monthChooser = calendar.getMonthChooser();
		// Listener al cambio de mes
		monthChooser.addPropertyChangeListener("month",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						// repintar el daychooser al cambiar de mes
						pintarCalendario();
						actualizarCheck();
						actualizarButtons();

					}
				});

		calendar.getYearChooser().getSpinner().setEnabled(false);
		calendar.setBounds(63, 40, 480, 280);
		calendar.setBackground(myColor);
		calendar.getYearChooser().setStartYear(
				calendar.getYearChooser().getYear());
		calendar.getYearChooser().setEndYear(
				calendar.getYearChooser().getYear());
		add(calendar);

		DiaSeleccionado.setEditable(false);
		DiaSeleccionado.setBounds(246, 12, 60, 20);
		add(DiaSeleccionado);

		JButton btnAsdf = new JButton("Importar calendario");
		btnAsdf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter type = new FileNameExtensionFilter(
						"TXT File", "txt");
				chooser.setFileFilter(type);

				int returnValue = chooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					// recoger el resultado de importarDatos
					
					String path = selectedFile.getAbsolutePath();
					try {
						ctrlVista.importarCalendario(path);
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
					} catch (ParseException e1) {
						e1.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: Ha habido un error inesperado en la conversion de un tipo.",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
					Robot robot;
					try {
						robot = new Robot();
						robot.keyPress(KeyEvent.VK_F2);
						robot.keyRelease(KeyEvent.VK_F2);
					} catch (AWTException e) {
						e.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: Se ha producido un error con Abstract Window Toolkit.",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnAsdf.setBounds(612, 55, 199, 25);
		add(btnAsdf);

		JButton btnNewassignarConBfs = new JButton("Asignar con BFS");
		btnNewassignarConBfs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				asig = ctrlVista.getInfoAsignaciones().size();
				if (textField_1.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Introduce un nombre válido", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.existeAsignacion(textField_1.getText())) {
					// ya existe
					JOptionPane.showMessageDialog(new JFrame(),
							"Ya existe una asignacion con ese nombre", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.getDoctores().length == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"No hay ningun doctor para asignar", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.getTurnos().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"No hay turnos festivos para asignar", " ",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						if (ctrlVista.asignarBFS(textField_1.getText())) {
							ctrlVista.guardarAsig();
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Se han llenado todos los turnos satisfactoriamente",
											" ",
											JOptionPane.INFORMATION_MESSAGE);
						} else {
							if (asig < ctrlVista.getInfoAsignaciones().size()) {
								ListarTurnos lt = new ListarTurnos(
										turnosVacios());
								lt.setModal(true);
								lt.setResizable(false);
								lt.setVisible(true);
							} else {
								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"No se ha podido asignar a ningun doctor",
												" ",
												JOptionPane.INFORMATION_MESSAGE);
							}
						}
					} catch (HeadlessException e) {
						e.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
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

				}
			}
		});
		btnNewassignarConBfs.setBounds(612, 181, 199, 25);
		add(btnNewassignarConBfs);

		JButton btnNewButton_2 = new JButton("Asignar con DFS");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				asig = ctrlVista.getInfoAsignaciones().size();
				if (textField_1.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Introduce un nombre válido", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.existeAsignacion(textField_1.getText())) {
					// ya existe
					JOptionPane.showMessageDialog(new JFrame(),
							"Ya existe una asignacion con ese nombre", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.getDoctores().length == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"No hay ningun doctor para asignar", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.getTurnos().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"No hay turnos festivos para asignar", " ",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						if (ctrlVista.asignarDFS(textField_1.getText())) {
							ctrlVista.guardarAsig();
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Se han llenado todos los turnos satisfactoriamente",
											" ",
											JOptionPane.INFORMATION_MESSAGE);
						} else {
							if (asig < ctrlVista.getInfoAsignaciones().size()) {
								ListarTurnos lt = new ListarTurnos(
										turnosVacios());
								lt.setModal(true);
								lt.setResizable(false);
								lt.setVisible(true);
							} else {
								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"No se ha podido asignar a ningun doctor",
												" ",
												JOptionPane.INFORMATION_MESSAGE);
							}
						}
					} catch (HeadlessException e) {
						e.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
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
				}
			}
		});
		btnNewButton_2.setBounds(612, 218, 199, 25);
		add(btnNewButton_2);

		JButton btnAsignarConCoste = new JButton("Asignar con coste");
		btnAsignarConCoste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				asig = ctrlVista.getInfoAsignaciones().size();
				if (textField_1.getText().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Introduce un nombre válido", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.existeAsignacion(textField_1.getText())) {
					// ya existe
					JOptionPane.showMessageDialog(new JFrame(),
							"Ya existe una asignacion con ese nombre", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.getDoctores().length == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"No hay ningun doctor para asignar", " ",
							JOptionPane.ERROR_MESSAGE);
				} else if (ctrlVista.getTurnos().isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"No hay turnos festivos para asignar", " ",
							JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						if (ctrlVista.asignarCoste(textField_1.getText())) {
							ctrlVista.guardarAsig();
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Se han llenado todos los turnos satisfactoriamente",
											" ",
											JOptionPane.INFORMATION_MESSAGE);
						} else {
							if (asig < ctrlVista.getInfoAsignaciones().size()) {
								ListarTurnos lt = new ListarTurnos(
										turnosVacios());
								lt.setModal(true);
								lt.setResizable(false);
								lt.setVisible(true);
							} else {
								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"No se ha podido asignar a ningun doctor",
												" ",
												JOptionPane.INFORMATION_MESSAGE);
							}
						}
					} catch (HeadlessException e) {
						e.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
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
				}
			}
		});
		btnAsignarConCoste.setBounds(612, 255, 199, 25);
		add(btnAsignarConCoste);

		JButton btnEliminarPeriodoVacacional = new JButton(
				"Eliminar Periodo vacacional");
		btnEliminarPeriodoVacacional.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Selecciona un periodo", " ",
							JOptionPane.ERROR_MESSAGE);
				} else
					try {
						if (ctrlVista.tieneRestPeriodo()) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Aún hay doctores que tienen restricciones sobre este periodo",
											" ", JOptionPane.ERROR_MESSAGE);
						} else {
							try {
								ctrlVista.eliminarPeriodo(comboBox
										.getSelectedItem().toString()
										.split("_")[1]);
								actualizarCombo();
							} catch (IOException e1) {
								e1.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						}
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
		btnEliminarPeriodoVacacional.setBounds(612, 396, 199, 25);
		add(btnEliminarPeriodoVacacional);

		JDayChooser dayChooser = calendar.getDayChooser();
		chckbxDiurno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarButtons();
			}
		});

		chckbxDiurno.setBounds(177, 328, 129, 23);
		chckbxDiurno.setBackground(myColor);
		add(chckbxDiurno);
		chckbxNocturno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarButtons();
			}
		});

		chckbxNocturno.setBounds(338, 328, 129, 23);
		chckbxNocturno.setBackground(myColor);
		add(chckbxNocturno);
		DiaSeleccionado.setText(calendar.getDayChooser().getDay() + " / "
				+ (calendar.getMonthChooser().getMonth() + 1));

		dayChooser.addPropertyChangeListener("day",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {

						// escribir la fecha seleccionada en el textpanel
						DiaSeleccionado.setText(Integer.toString((int) e
								.getNewValue())
								+ " / "
								+ (calendar.getMonthChooser().getMonth() + 1));
						pintarCalendario();
						actualizarCheck();
						actualizarButtons();

					}
				});
		anadirButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String turno = "";
				if (chckbxDiurno.isSelected()
						&& (!chckbxNocturno.isEnabled() || !chckbxNocturno
								.isSelected()) && chckbxDiurno.isEnabled())
					turno += ";Diurno";
				else if (chckbxNocturno.isSelected()
						&& (!chckbxDiurno.isEnabled() || !chckbxDiurno
								.isSelected()) && chckbxNocturno.isEnabled())
					turno += ";Nocturno";

				if (!chckbxDiurno.isSelected() && !chckbxNocturno.isSelected()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Debes seleccionar al menos un tipo de turno", " ",
							JOptionPane.ERROR_MESSAGE);
				}
				if (!chckbxDiurno.isEnabled() && !chckbxNocturno.isEnabled()) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Este dia ya tiene 2 turnos", " ",
							JOptionPane.ERROR_MESSAGE);
				} else {
					AnadirTurno an = new AnadirTurno(Integer.toString(calendar
							.getDayChooser().getDay())
							+ "/"
							+ Integer.toString(calendar.getMonthChooser()
									.getMonth() + 1) + turno, true);
					an.setModal(true);
					an.setResizable(false);
					an.setVisible(true);
					pintarCalendario();
					actualizarCheck();
					actualizarButtons();
				}
			}
		});

		anadirButton.setBounds(63, 359, 180, 25);
		add(anadirButton);
		eliminarButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String turno = "";
				if (chckbxDiurno.isSelected() && !chckbxDiurno.isEnabled()
						&& chckbxNocturno.isEnabled())
					turno += ";Diurno";
				else if (chckbxNocturno.isSelected()
						&& !chckbxNocturno.isEnabled()
						&& chckbxDiurno.isEnabled())
					turno += ";Nocturno";
				AnadirTurno an = new AnadirTurno(Integer.toString(calendar
						.getDayChooser().getDay())
						+ "/"
						+ Integer.toString(calendar.getMonthChooser()
								.getMonth() + 1) + turno, false);
				an.setModal(true);
				an.setResizable(false);
				an.setVisible(true);
				// recrearCalendario();

				calendar.getDayChooser().getDayPanel().revalidate();
				calendar.getDayChooser().getDayPanel().repaint();
				pintarCalendario();
				actualizarCheck();
				actualizarButtons();
			}
		});

		eliminarButton.setBounds(63, 396, 180, 25);
		add(eliminarButton);

		JButton btnDefinirPeriodoVacacional = new JButton(
				"Definir periodo vacacional");
		btnDefinirPeriodoVacacional.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefinirPeriodo dp = new DefinirPeriodo();
				dp.setModal(true);
				dp.setResizable(false);
				dp.setVisible(true);
				try {
					actualizarCombo();
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
		btnDefinirPeriodoVacacional.setBounds(612, 323, 199, 25);
		add(btnDefinirPeriodoVacacional);

		JLabel lblFechaSeleccionada = new JLabel("Fecha seleccionada");
		lblFechaSeleccionada.setBounds(75, 12, 153, 20);
		add(lblFechaSeleccionada);
		btnEliminarNocturno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnadirTurno an = new AnadirTurno(Integer.toString(calendar
						.getDayChooser().getDay())
						+ "/"
						+ Integer.toString(calendar.getMonthChooser()
								.getMonth() + 1) + ";Nocturno", false);
				an.setModal(true);
				an.setResizable(false);
				an.setVisible(true);
				pintarCalendario();
				actualizarCheck();
				actualizarButtons();
			}
		});

		btnEliminarNocturno.setBounds(255, 396, 180, 25);
		add(btnEliminarNocturno);
		btnEliminarDiurno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AnadirTurno an = new AnadirTurno(Integer.toString(calendar
						.getDayChooser().getDay())
						+ "/"
						+ Integer.toString(calendar.getMonthChooser()
								.getMonth() + 1) + ";Diurno", false);
				an.setModal(true);
				an.setResizable(false);
				an.setVisible(true);
				pintarCalendario();
				actualizarCheck();
				actualizarButtons();
			}
		});

		btnEliminarDiurno.setBounds(255, 359, 180, 25);
		add(btnEliminarDiurno);

		textField_1 = new JTextField();
		textField_1.setBounds(612, 150, 199, 19);
		add(textField_1);
		textField_1.setColumns(10);

		comboBox = new JComboBox<String>();
		try {
			for (String p : ctrlVista.getPeriodosTxt()) {
				String[] split = p.split(";");
				comboBox.addItem(split[1] + " - " + split[2] + "_" + split[0]);
			}
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
		comboBox.setBounds(612, 360, 199, 24);
		add(comboBox);

		JLabel lblNombreAsignacin = new JLabel("Nombre asignaci\u00F3n:");
		lblNombreAsignacin.setBounds(612, 123, 180, 15);
		add(lblNombreAsignacin);
		btnEliminarNocturno.setVisible(false);
		btnEliminarDiurno.setVisible(false);

	}

	public void pintarCalendario() {
		JDayChooser dayChooser = calendar.getDayChooser();
		JPanel jPanel1 = dayChooser.getDayPanel();
		Component component[] = jPanel1.getComponents();
		Calendar cal = Calendar.getInstance();
		cal.setTime(calendar.getDate());

		int month = cal.get(Calendar.MONTH) + 1;
		for (String s : ctrlVista.getTurnos()) {
			String[] turno = s.split(";");
			if (month == Integer.parseInt(turno[0].split("/")[1])) {
				cal.set(Calendar.DAY_OF_MONTH, 1);
				int offset = cal.get(Calendar.DAY_OF_WEEK) - 2;
				if (offset == -1)
					offset = 6;
				component[Integer.parseInt(turno[0].split("/")[0]) + offset + 6]
						.setBackground(Color.cyan);
			}
		}
	}

	public void actualizarCheck() {
		chckbxDiurno.setSelected(false);
		chckbxNocturno.setSelected(false);
		chckbxNocturno.setEnabled(true);
		chckbxDiurno.setEnabled(true);
		for (String s : ctrlVista.getTurnos()) {
			String[] dia = s.split(";");
			if (calendar.getMonthChooser().getMonth() + 1 == Integer
					.parseInt(dia[0].split("/")[1])) {
				if (calendar.getDayChooser().getDay() == Integer
						.parseInt((dia[0].split("/")[0]))) {

					if (dia[1].equals("Diurno")) {
						chckbxDiurno.setSelected(true);
						chckbxDiurno.setEnabled(false);
					}
					if (dia[1].equals("Nocturno")) {
						chckbxNocturno.setSelected(true);
						chckbxNocturno.setEnabled(false);
					}
				}

			}
		}
	}

	public void actualizarButtons() {
		anadirButton.setEnabled(false);
		eliminarButton.setEnabled(false);
		btnEliminarDiurno.setVisible(false);
		btnEliminarNocturno.setVisible(false);
		if (chckbxDiurno.isEnabled() && chckbxDiurno.isSelected()
				|| chckbxNocturno.isEnabled() && chckbxNocturno.isSelected())
			anadirButton.setEnabled(true);
		if (!chckbxDiurno.isEnabled() && chckbxDiurno.isSelected()
				&& chckbxNocturno.isEnabled() || !chckbxNocturno.isEnabled()
				&& chckbxNocturno.isSelected() && chckbxDiurno.isEnabled())
			eliminarButton.setEnabled(true);
		if (!chckbxDiurno.isEnabled() && chckbxDiurno.isSelected()
				&& !chckbxNocturno.isEnabled() && chckbxNocturno.isSelected()) {
			btnEliminarDiurno.setVisible(true);
			btnEliminarNocturno.setVisible(true);
		}
	}

	private ArrayList<String> turnosVacios() {
		ArrayList<String> asdf = new ArrayList<String>();
		ArrayList<String> tr = ctrlVista.getInfoAsignaciones();
		String[] l = tr.get(tr.size() - 1).split(";");
		String[] t = l[2].split(",");
		int sum = 0;
		for (int e = 0; e < ctrlVista.getEspecialidades().length; ++e) {
			sum += ctrlVista.getMinDoctoresEspecialidadPorNombre(ctrlVista
					.getEspecialidades()[e]);
		}
		for (int j = 0; j < t.length; ++j) { // turnosAsignados
			String ta = t[j]; // turno(j)
			String[] tj = ta.split("-");
			String[] d = tj[2].split(":");
			if (d.length < sum) {
				int[] cont = new int[ctrlVista.getEspecialidades().length];
				for (int e = 0; e < ctrlVista.getEspecialidades().length; ++e) {
					cont[e] = ctrlVista
							.getMinDoctoresEspecialidadPorNombre(ctrlVista
									.getEspecialidades()[e]);
					if (d[0] != null) {
						for (int k = 0; k < d.length; ++k) { // doctores
							if (ctrlVista.getEspecialidades()[e]
									.equals(ctrlVista.getInfoDoctorDni(d[k])[3]))
								--cont[e];
						}
					}
					if (cont[e] > 0)
						asdf.add(tj[0] + ";" + tj[1] + ";"
								+ ctrlVista.getEspecialidades()[e] + ";"
								+ cont[e]);
				}
			}
		}
		return asdf;
	}

	public void actualizarCombo() throws FileNotFoundException, IOException {
		comboBox.removeAllItems();
		for (String p : ctrlVista.getPeriodosTxt()) {
			String[] split = p.split(";");
			comboBox.addItem(split[1] + " - " + split[2] + "_" + split[0]);
		}
	}

	public Component getComponentByName(String name) {
		if (componentMap.containsKey(name)) {
			return (Component) componentMap.get(name);
		} else
			return null;
	}

	public String recogerCadena() {

		return ((JLabel) getComponentByName("label")).getText();
	}
}
