/*Author: Ra�l Enamorado Serratosa */
package vista.gestion.asignaciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import vista.main.ControladorVista;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDayChooser;
import com.toedter.calendar.JMonthChooser;

public class GestionAsignaciones extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ControladorVista cVista = new ControladorVista();
	private ActionListener asignaciones;
	private JTable table;
	private JComboBox<String> comboBoxAsignaciones;
	private JRadioButton rdbtnDiurno = new JRadioButton("Diurno");
	private JRadioButton rdbtnNocturno = new JRadioButton("Nocturno");
	private ButtonGroup turnosRadius = new ButtonGroup();
	private JComboBox<String> comboBoxDoctors = new JComboBox<String>();
	private JCalendar calendar = new JCalendar();
	final JTextPane DiaSeleccionado = new JTextPane();
	private ArrayList<String> asigs = new ArrayList<String>(); // Asignaciones
	private ArrayList<String> idTurnoAsig = new ArrayList<String>(); // En cada
																		// posicion
																		// hay
																		// los
																		// turnos
																		// de la
																		// Asignaci�n
																		// con
																		// ese
																		// id
	private ArrayList<ArrayList<String>> mes = new ArrayList<ArrayList<String>>(); // cada
																					// posici�n
																					// es
																					// el
																					// mes,
																					// en
																					// cada
																					// posici�n
																					// hay
																					// los
																					// turnoAsignados
																					// de
																					// ese
																					// mes
	private ArrayList<String> enero = new ArrayList<String>();
	private ArrayList<String> febrero = new ArrayList<String>();
	private ArrayList<String> marzo = new ArrayList<String>();
	private ArrayList<String> abril = new ArrayList<String>();
	private ArrayList<String> mayo = new ArrayList<String>();
	private ArrayList<String> junio = new ArrayList<String>();
	private ArrayList<String> julio = new ArrayList<String>();
	private ArrayList<String> agosto = new ArrayList<String>();
	private ArrayList<String> septiembre = new ArrayList<String>();
	private ArrayList<String> octubre = new ArrayList<String>();
	private ArrayList<String> noviembre = new ArrayList<String>();
	private ArrayList<String> diciembre = new ArrayList<String>();

	public GestionAsignaciones() {
		setBackground(new Color(255, 153, 0));

		JTextPane txtpnGestinAsignaciones = new JTextPane();
		txtpnGestinAsignaciones.setFocusable(false);
		txtpnGestinAsignaciones.setEditable(false);
		txtpnGestinAsignaciones.setBounds(10, 11, 177, 20);
		txtpnGestinAsignaciones.setBackground(new Color(255, 153, 0));
		txtpnGestinAsignaciones.setText("Gesti\u00F3n de asignaciones");

		JTextPane txtpnAsignacion = new JTextPane();
		txtpnAsignacion.setFocusable(false);
		txtpnAsignacion.setEditable(false);
		txtpnAsignacion.setBounds(80, 52, 65, 20);
		txtpnAsignacion.setBackground(new Color(255, 153, 0));
		txtpnAsignacion.setText("Asignaci\u00F3n");

		// ASIGNACIONES
		comboBoxAsignaciones = new JComboBox<String>();
		comboBoxAsignaciones.setBounds(80, 88, 182, 20);
		obtenerAsignaciones();
		obtenerTurnosMes();
		recargarIdTurnos();

		// Al cambiar de assignaci�n, recargar todo
		asignaciones = new ActionListener() {
			// oldAsig sirve para saber si cambiamos de asignaci�n pero le
			// damos
			// a la misma
			Object oldAsig = comboBoxAsignaciones.getSelectedItem();

			@Override
			public void actionPerformed(ActionEvent e) {
				cambioAsignaciones();
				pintarJdayChooser();
				DiaSeleccionado.setText("");

				turnosRadius.clearSelection();
				// Si cambiamos a otra asignaci�n que no sea la actual
				// deshabilitamos los botones (hasta que clickemos un dia)
				if (oldAsig != comboBoxAsignaciones.getSelectedItem()) {

					rdbtnDiurno.setEnabled(false);
					rdbtnNocturno.setEnabled(false);
				} else
					actualizarRadioButton();

				// actualizar textPanel DiaSeleccionado
				updateDiaSeleccionadoAndRadioButtonAlCambio();

				// limpiar la el combobox de doctores y recargarla, solo si la
				// tabla no esta vacia
				comboBoxDoctors.removeAllItems();
				if (table.getRowCount() != 0) {
					cargarDoctoresCombobox();
				}

				oldAsig = comboBoxAsignaciones.getSelectedItem();
			}
		};
		comboBoxAsignaciones.addActionListener(asignaciones);

		// IMPORTAR
		JButton btnImportar = new JButton("Importar");
		btnImportar.setBounds(162, 49, 100, 23);
		btnImportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				comboBoxAsignaciones.removeActionListener(asignaciones);

				importarAsignaciones();

				updateDiaSeleccionadoAndRadioButtonAlCambio();

				comboBoxAsignaciones.addActionListener(asignaciones);
			}

		});

		// CALENDARIO

		// pintar el JDayChooser
		pintarJdayChooser();
		calendar.getYearChooser().getSpinner().setEnabled(false);

		calendar.setBounds(80, 169, 191, 203);
		calendar.getYearChooser().setStartYear(
				calendar.getYearChooser().getYear());
		calendar.getYearChooser().setEndYear(
				calendar.getYearChooser().getYear());
		add(calendar);

		JMonthChooser monthChooser = calendar.getMonthChooser();
		// Listener al cambio de mes
		monthChooser.addPropertyChangeListener("month",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						// repintar el daychooser al cambiar de mes
						pintarJdayChooser();

						// Radio button
						actualizarRadioButton();

						// actualizar textPanel DiaSeleccionado
						updateDiaSeleccionadoAndRadioButtonAlCambio();
					}
				});

		DiaSeleccionado.setEditable(false);
		DiaSeleccionado.setBounds(206, 400, 49, 20);
		add(DiaSeleccionado);

		JDayChooser dayChooser = calendar.getDayChooser();

		// Listener al cambio de dia
		dayChooser.addPropertyChangeListener("day",
				new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						// repintar para que no se vaya el color
						pintarJdayChooser();

						// escribir la fecha seleccionada en el textpanel
						DiaSeleccionado.setText(Integer.toString((int) e
								.getNewValue())
								+ " / "
								+ (calendar.getMonthChooser().getMonth() + 1));

						// limpiar la tabla al clickar
						DefaultTableModel model = (DefaultTableModel) table
								.getModel();
						model.setRowCount(0);

						actualizarRadioButton();

					}
				});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(346, 169, 476, 289);

		rdbtnDiurno.setBounds(80, 458, 89, 23);
		rdbtnDiurno.setBackground(new Color(255, 153, 0));
		rdbtnDiurno.setEnabled(false);

		rdbtnNocturno.setBounds(171, 458, 100, 23);
		rdbtnNocturno.setBackground(new Color(255, 153, 0));
		rdbtnNocturno.setEnabled(false);

		// RadioButton group
		turnosRadius.add(rdbtnDiurno);
		turnosRadius.add(rdbtnNocturno);

		JTextPane txtpnSeleccionarTurno = new JTextPane();
		txtpnSeleccionarTurno.setFocusable(false);
		txtpnSeleccionarTurno.setEditable(false);
		txtpnSeleccionarTurno.setBounds(80, 431, 116, 20);
		txtpnSeleccionarTurno.setBackground(new Color(255, 153, 0));
		txtpnSeleccionarTurno.setText("Seleccionar turno");

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Nombre", "Apellidos", "DNI", "Especialidad" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false,
					false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getTableHeader().setReorderingAllowed(false);
		// posibilidad de cambiar el tama�o si p.e. un nombre es muy grande y
		// no
		// cabe
		// table.getTableHeader().setResizingAllowed(false);

		JButton btnAddDoctor = new JButton("A\u00F1adir doctor");
		btnAddDoctor.setBounds(346, 87, 135, 23);
		btnAddDoctor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addDoctor();
				} catch (ParseException e1) {
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Ha habido un error inesperado en la conversion de un tipo.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JButton btnEliminarDoctor = new JButton("Eliminar doctor");
		btnEliminarDoctor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// OBTENER EL MINIMO DE DOCTORES DE LA ESPECIALIDAD
				int[] selectedRow = table.getSelectedRows();
				String especialidad = new String();
				if (selectedRow.length > 0) {
					especialidad = (String) table.getValueAt(selectedRow[0], 3);
				}

				boolean isSelected = false;
				for (int i = 0; i < table.getRowCount(); ++i) {
					if (table.isRowSelected(i))
						isSelected = true;
				}

				if (isSelected) {
					int minimoDoctores = cVista
							.getMinDoctoresEspecialidadPorNombre(especialidad);

					// OBTENER EL COUNT DE DOCTORES DE LA ESPECIALIDAD EN LA
					// TABLA
					int countEspecialidad = 0;
					for (int i = 0; i < table.getRowCount(); ++i) {
						if (especialidad.equals(table.getValueAt(i, 3))) {
							++countEspecialidad; // incrementamos en 1 por cada
													// doctor de la especialidad
													// en
													// la tabla
						}
					}
					--countEspecialidad;

					if (countEspecialidad == minimoDoctores - 1) {
						int respuesta = JOptionPane
								.showConfirmDialog(null,
										"Si borras este doctor rebasar�s el m�nimo de doctores de "
												+ especialidad
												+ ".\n�Est� seguro?", "",
										JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							try {
								eliminarDoctor();
							} catch (ParseException e1) {
								e1.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						}
					} else if (countEspecialidad < minimoDoctores - 1) {
						int respuesta = JOptionPane
								.showConfirmDialog(
										null,
										"El l�mite de la especialidad "
												+ especialidad
												+ " ya ha sido rebasado con anterioridad.\n�Est� seguro que quiere borrar otro m�s?",
										"", JOptionPane.YES_NO_OPTION);
						if (respuesta == JOptionPane.YES_OPTION) {
							try {
								eliminarDoctor();
							} catch (ParseException e1) {
								e1.printStackTrace();

								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
						}
					} else {
						try {
							eliminarDoctor();
						} catch (ParseException e1) {
							e1.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				cargarDoctoresCombobox();
			}
		});
		btnEliminarDoctor.setBounds(651, 87, 135, 23);
		scrollPane.setViewportView(table);
		setLayout(null);
		add(txtpnGestinAsignaciones);
		add(rdbtnDiurno);
		add(rdbtnNocturno);
		add(txtpnAsignacion);
		add(btnImportar);
		add(comboBoxAsignaciones);
		add(txtpnSeleccionarTurno);
		add(scrollPane);
		add(btnAddDoctor);
		add(btnEliminarDoctor);

		JTextPane txtpnDoctoresAsignados = new JTextPane();
		txtpnDoctoresAsignados.setFocusable(false);
		txtpnDoctoresAsignados.setEditable(false);
		txtpnDoctoresAsignados.setBackground(new Color(255, 153, 0));
		txtpnDoctoresAsignados.setText("Doctores asignados");
		txtpnDoctoresAsignados.setBounds(346, 49, 135, 20);
		add(txtpnDoctoresAsignados);

		JTextPane txtpnDiaSeleccionado = new JTextPane();
		txtpnDiaSeleccionado.setText("Dia seleccionado:");
		txtpnDiaSeleccionado.setFocusable(false);
		txtpnDiaSeleccionado.setEditable(false);
		txtpnDiaSeleccionado.setBackground(new Color(255, 153, 0));
		txtpnDiaSeleccionado.setBounds(80, 400, 116, 20);
		add(txtpnDiaSeleccionado);

		comboBoxDoctors.setBounds(346, 121, 259, 20);
		add(comboBoxDoctors);

		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (comboBoxAsignaciones.getSelectedItem() != null) {
					if (comboBoxAsignaciones.getSelectedItem().toString() != null) {
						String motiv = exportarAbonito(comboBoxAsignaciones
								.getSelectedItem().toString());
						if (motiv != null) {
							JOptionPane.showMessageDialog(new JFrame(), motiv,
									"Error", JOptionPane.ERROR_MESSAGE);

						} else {
							JOptionPane.showMessageDialog(new JFrame(),
									"Assignaci�n exportada correctamente",
									"", JOptionPane.INFORMATION_MESSAGE);

						}
					} else {
						JOptionPane.showMessageDialog(new JFrame(),
								"Escoge una asignaci�n primero", "Error",
								JOptionPane.ERROR_MESSAGE);

					}
				}

			}
		});
		btnExportar.setBounds(538, 503, 100, 23);
		add(btnExportar);

		JButton btnEliminarAsignacion = new JButton("Eliminar Asignacion");
		btnEliminarAsignacion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				comboBoxAsignaciones.removeActionListener(asignaciones);
				eliminarAsignacion();
				comboBoxAsignaciones.addActionListener(asignaciones);
			}
		});
		btnEliminarAsignacion.setBounds(80, 120, 180, 23);
		add(btnEliminarAsignacion);

	}

	protected String exportarAbonito(String assig) {
		String[] aux = assig.split("-");
		String motivo = cVista.exportarAbonito(aux[1]);
		return motivo;

	}

	// obtiene las asignaciones
	public void obtenerAsignaciones() {
		asigs = cVista.getInfoAsignaciones();
		recargarIdTurnos();
	}

	// importa las asignaciones
	public void importarAsignaciones() {
		idTurnoAsig.clear();
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter type = new FileNameExtensionFilter("TXT File",
				"txt");
		chooser.setFileFilter(type);

		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {

			File selectedFile = chooser.getSelectedFile();

			asigs = new ArrayList<String>();

			String path = selectedFile.getAbsolutePath();

			try {
				asigs = cVista.importarAsignaciones(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();

				JOptionPane.showMessageDialog(new JFrame(),
						"No se ha podido encontrar el fichero a importar",
						"Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();

				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"No se ha podido leer/escribir el fichero, revisar los permisos del fichero",
								"Error", JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e) {
				e.printStackTrace();

				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"No se ha podido parsear un String a una fecha, revisar el formato de las fechas dd/mm",
								"Error", JOptionPane.ERROR_MESSAGE);
			}

			// REFRESH INFO
			recargarIdTurnos();
			ActualizarMes();
			pintarJdayChooser();

			// guardar asignaciones en fichero
			try {
				cVista.guardarAsignaciones(
						"data/asignaciones" + cVista.getIdHospitalActual()
								+ ".txt", asigs);
			} catch (FileNotFoundException e) {
				e.printStackTrace();

				JOptionPane.showMessageDialog(new JFrame(),
						"No se ha podido encontrar el fichero donde guardar",
						"Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();

				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"No se ha podido leer/escribir el fichero, revisar los permisos del fichero",
								"Error", JOptionPane.ERROR_MESSAGE);
			}

			// limpiar pantalla
			limpiarPantalla();
		}
	}

	// calcula el vector mes
	public void obtenerTurnosMes() {
		// obtenemos los turnos de la asig actual

		String selectedItem = (String) comboBoxAsignaciones.getSelectedItem();
		if (selectedItem != null) {
			String[] selected = selectedItem.split("-");
			int pos = Integer.parseInt(selected[0]);
			String turnosAsigPos = idTurnoAsig.get(pos);
			String[] turnoAP = turnosAsigPos.split(",");
			// para cada turno, lo guardamos en el mes que toca
			for (int i = 0; i < turnoAP.length; ++i) {
				// obtener fecha para asignar a cada mes
				String[] fecha = turnoAP[i].split("-");
				String f = fecha[0];

				String[] m = f.split("/");
				int me = Integer.parseInt(m[1]) - 1; // la posicion es el mes-1
				addTurnoAlMes(me, turnoAP[i]);
			}
		}
		mes.add(enero);
		mes.add(febrero);
		mes.add(marzo);
		mes.add(abril);
		mes.add(mayo);
		mes.add(junio);
		mes.add(julio);
		mes.add(agosto);
		mes.add(septiembre);
		mes.add(octubre);
		mes.add(noviembre);
		mes.add(diciembre);
	}

	// a�ade un turno al mes correspondiente
	public void addTurnoAlMes(int me, String turno) {
		if (me == 0) {
			enero.add(turno);
		}
		if (me == 1) {
			febrero.add(turno);
		}
		if (me == 2) {
			marzo.add(turno);
		}
		if (me == 3) {
			abril.add(turno);
		}
		if (me == 4) {
			mayo.add(turno);
		}
		if (me == 5) {
			junio.add(turno);
		}
		if (me == 6) {
			julio.add(turno);
		}
		if (me == 7) {
			agosto.add(turno);
		}
		if (me == 8) {
			septiembre.add(turno);
		}
		if (me == 9) {
			octubre.add(turno);
		}
		if (me == 10) {
			noviembre.add(turno);
		}
		if (me == 11) {
			diciembre.add(turno);
		}
	}

	public void cambioAsignaciones() {
		// limpiar el vector mes y volver a cargarlo
		ActualizarMes();
		// limpiar la tabla cuando clickamos un dia
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
	}

	// Actualiza el vector mes al cambiar de asignaci�n
	public void ActualizarMes() {
		// limpiamos todos los meses
		enero = new ArrayList<String>();
		febrero = new ArrayList<String>();
		marzo = new ArrayList<String>();
		abril = new ArrayList<String>();
		mayo = new ArrayList<String>();
		junio = new ArrayList<String>();
		julio = new ArrayList<String>();
		agosto = new ArrayList<String>();
		septiembre = new ArrayList<String>();
		octubre = new ArrayList<String>();
		noviembre = new ArrayList<String>();
		diciembre = new ArrayList<String>();

		// limpiamos el vector de meses
		mes = new ArrayList<ArrayList<String>>();

		obtenerTurnosMes();
	}

	// pinta el JdayChooser
	public void pintarJdayChooser() {
		JDayChooser dayChooser = calendar.getDayChooser();
		JPanel jPanel1 = dayChooser.getDayPanel();
		Component component[] = jPanel1.getComponents();

		int m = calendar.getMonthChooser().getMonth();

		int offset = calcularOffset();

		// obtenemos los turnos del mes actual
		ArrayList<String> month = mes.get(m);

		// deshabilitamos todos los dias y los pintamos de rojo
		for (int i = 7; i < component.length; ++i) {
			component[i].setBackground(null);
			component[i].setEnabled(false);
		}

		// Si en un dia hay un turnoAsignado, lo habilitamos
		for (int i = 0; i < month.size(); ++i) {
			// obtenemos la fecha dd/mm
			String[] f = month.get(i).split("-");
			// obtenemos el dia
			String fecha[] = f[0].split("/");
			int dia = Integer.parseInt(fecha[0]);

			// pintamos y activamos los dias con turnosAsignados
			component[dia + offset].setBackground(Color.cyan);
			component[dia + offset].setEnabled(true);
		}

	}

	// Calcula el offset para pintar el JDayChooser
	public int calcularOffset() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(calendar.getDate());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat d = new SimpleDateFormat("u");
		int posicion = Integer.parseInt(d.format(cal.getTime())) + 5;
		return posicion;
	}

	public void actualizarRadioButton() {
		turnosRadius.clearSelection();
		rdbtnDiurno.setEnabled(false);
		rdbtnNocturno.setEnabled(false);
		comboBoxDoctors.removeAllItems();
		// limpiar la tabla
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		// volver a llenar el textPanel diaSeleccionado
		DiaSeleccionado.setText(calendar.getDayChooser().getDay() + " / "
				+ (calendar.getMonthChooser().getMonth() + 1));
		// cojemos el mes actual
		int m = calendar.getMonthChooser().getMonth();

		ArrayList<String> turno = new ArrayList<String>();

		// para todos los turnos del mes actual
		for (int i = 0; i < mes.get(m).size(); ++i) {
			// cojemos el dia
			String[] data = mes.get(m).get(i).split("-");
			String[] dia = data[0].split("/");
			int day = Integer.parseInt(dia[0]);

			// Si el dia es el dia seleccionado lo metemos en el vector turnos
			if (day == calendar.getDayChooser().getDay()) {
				turno.add(mes.get(m).get(i));
			}
		}

		// Si solo hay un turno al dia, marcar el radius adecuado (diurno o
		// nocturno)
		if (turno.size() == 1) {
			String[] data = turno.get(0).split("-");

			// si es diurno marcamos el rdbtnDiurno
			if (data[1].equals("Diurno")) {
				rdbtnDiurno.setSelected(true);
				
				mostrarDoctores(turno);
			}
			// si es nocturno marcamos el rdbtnNocturno
			else if (data[1].equals("Nocturno")) {
				
				rdbtnNocturno.setSelected(true);
				mostrarDoctores(turno);
			}
		}
		// Si hay dos turnos ese dia (diurno y nocturno)
		else if (turno.size() == 2) {
			
			turnosRadius.clearSelection();
			rdbtnDiurno.setEnabled(true);
			rdbtnNocturno.setEnabled(true);
			mostrarDoctores(turno);
		}

	}

	public void mostrarDoctores(final ArrayList<String> turno) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		// limpiar la tabla
		model.setRowCount(0);
		
		for( ActionListener al : rdbtnDiurno.getActionListeners() ) {
			rdbtnDiurno.removeActionListener( al );
	    }
		for( ActionListener al : rdbtnNocturno.getActionListeners() ) {
			rdbtnNocturno.removeActionListener( al );
	    }
		
		

		// si solo hay un turno
		if (turno.size() == 1) {
			// insertar los doctores de ese turno
			String[] d = turno.get(0).split("-");
			if (d.length > 2) {
				String[] doctores = d[2].split(":");
				for (int i = 0; i < doctores.length; ++i) {
					String dni = doctores[i];
					String[] doc = getInfoDoctorPorDni(dni);
					if (getInfoDoctorPorDni(dni) != null)
						
						model.addRow(new Object[] { doc[0], doc[1], doc[2],
								doc[3] });
				}

			}
			cargarDoctoresCombobox();
		}

		// Si hay dos turnos (diurno y nocturno)
		else if (turno.size() == 2) {
			final String[] t = new String[2];

			// ponemos el diurno en t[0] y el nocturno en t[1]
			for (int i = 0; i < 2; ++i) {
				String[] data = turno.get(i).split("-");
				if (data[1].equals("Diurno"))
					t[0] = turno.get(i); // en t[0] est� el turno diurno
				else if (data[1].equals("Nocturno"))
					t[1] = turno.get(i); // en t[1] est� el turno nocturno
			}

			// Si clickamos Diurno
			
			ActionListener rdDia = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent b) {
					DefaultTableModel model = (DefaultTableModel) table
							.getModel();
					model.setRowCount(0);
					String[] d = t[0].split("-"); // t[0] <- diurno
					String[] doctores = d[2].split(":");
					
					for (int i = 0; i < doctores.length; ++i) {
						String dni = doctores[i];
						String[] doc = getInfoDoctorPorDni(dni);
						model.addRow(new Object[] { doc[0], doc[1], doc[2],
								doc[3] });
						

					}
					// cargamos los doctores, que no est�n en la tabla, en el
					// combobox
					cargarDoctoresCombobox();
				}
			};
			
			ActionListener rdNoct = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent b) {
					DefaultTableModel model = (DefaultTableModel) table
							.getModel();
					model.setRowCount(0);
					String[] d = t[1].split("-"); // t[1] <- nocturno
					String[] doctores = d[2].split(":");
					
					for (int i = 0; i < doctores.length; ++i) {
						String dni = doctores[i];

						String[] doc = getInfoDoctorPorDni(dni);
						model.addRow(new Object[] { doc[0], doc[1], doc[2],
								doc[3] });
						

					}
					// cargamos los doctores, que no est�n en la tabla, en el
					// combobox
					cargarDoctoresCombobox();
				}
			};
			
			
			
			rdbtnDiurno.addActionListener(rdDia);
			
			// Si clickamos Nocturno
			rdbtnNocturno.addActionListener(rdNoct);
			

		}
	}

	public String[] getInfoDoctorPorDni(String dni) {
		String[] doctor = new String[4];
		doctor = cVista.getInfoDoctorDni(dni);
		return doctor;
	}

	public void updateDiaSeleccionadoAndRadioButtonAlCambio() {
		JDayChooser dayChooser = calendar.getDayChooser();
		JPanel jPanel1 = dayChooser.getDayPanel();
		Component component[] = jPanel1.getComponents();
		int dia = calendar.getDayChooser().getDay();
		int offset = calcularOffset();
		// si al cambiar de mes el componente est� habilitado, quiere decir
		// que
		// tiene turnos y debe escribirse el turno y actualizar el RadioButton
		if (component[dia + offset].isEnabled()) {
			
			
			
			DiaSeleccionado.setText(calendar.getDayChooser().getDay() + " / "
					+ (calendar.getMonthChooser().getMonth() + 1));
			actualizarRadioButton();
			
			
		} else
			DiaSeleccionado.setText("");
	}

	public void recargarIdTurnos() {

		comboBoxAsignaciones.removeAllItems();
		idTurnoAsig.clear();
		for (int i = 0; i < asigs.size(); ++i) {
			String a = asigs.get(i);
			String[] asig = a.split(";");
			String s = asig[0] + "-" + asig[1];
			comboBoxAsignaciones.addItem(s);

			idTurnoAsig.add(asig[2]);
		}

	}

	public void cargarDoctoresCombobox() {
		comboBoxDoctors.removeAllItems();
		Object[][] docs = cVista.getDoctores();
		ArrayList<String> tableDocs = new ArrayList<String>();

		// coger los dnis de los doctores
		for (int count = 0; count < table.getRowCount(); count++) {
			tableDocs.add(table.getValueAt(count, 2).toString());
		}
		// insertar los doctores que no est�n ya en la tabla
		for (int i = 0; i < docs.length; ++i) {

			if (!tableDocs.contains(docs[i][2])) {
				comboBoxDoctors.addItem(docs[i][2] + ": " + docs[i][0] + " "
						+ docs[i][1] + " - " + docs[i][4]);
			}
		}

	}

	public void addDoctor() throws ParseException {
		String doc = ((String) comboBoxDoctors.getSelectedItem());
		// Si hay alguien en el combobox
		if (doc != null) {
			// buscar el doctor de DNI=dni, cargarlo en la tabla y modificar el
			// combomboxDoctores
			String[] dni = doc.split(":");
			String[] doctor = cVista.getInfoDoctorDni(dni[0]);
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.addRow(new Object[] { doctor[0], doctor[1], doctor[2],
					doctor[3] });
			cargarDoctoresCombobox();

			// Coger Asignacion, turnoAsignado y dni del doctor a a�adir
			String selected = (String) comboBoxAsignaciones.getSelectedItem();
			String[] selec = selected.split("-");
			String idAsig = selec[0]; // idAsig
			String diaTurno = Integer.toString(calendar.getDayChooser()
					.getDay());
			String idMesTurno = Integer.toString(calendar.getMonthChooser()
					.getMonth() + 1);
			String fechaTurno = diaTurno + "/" + idMesTurno; // fechaTurno
			String tipoTurno = new String(); // tipoTurno
			if (rdbtnDiurno.isSelected())
				tipoTurno = "Diurno";
			else if (rdbtnNocturno.isSelected())
				tipoTurno = "Nocturno";
			String dniDoc = dni[0]; // dniDoc

			// adDoctor de la asignacion actual

			cVista.addDoctorAsignacion(idAsig, fechaTurno, tipoTurno, dniDoc);

			// releer el hospital y guardar en disco lo modificado
			// desactivar el listener para que no salte antes de recargar el
			// vector IdTurnos
			comboBoxAsignaciones.removeActionListener(asignaciones);

			asigs = cVista.getInfoAsignaciones();
			// recargar idTurnosAsig
			idTurnoAsig.clear();
			for (int i = 0; i < asigs.size(); ++i) {
				String a = asigs.get(i);
				String[] asig = a.split(";");
				idTurnoAsig.add(asig[2]);
			}

			ActualizarMes();
			pintarJdayChooser();
			try {

				cVista.guardarAsignaciones(
						"data/asignaciones" + cVista.getIdHospitalActual()
								+ ".txt", asigs);
			} catch (FileNotFoundException e) {
				e.printStackTrace();

				JOptionPane.showMessageDialog(new JFrame(),
						"No se ha podido encontrar el fichero a guardar",
						"Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();

				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"No se ha podido leer/escribir el fichero, revisar los permisos del fichero",
								"Error", JOptionPane.ERROR_MESSAGE);
			}
			// volver a activar el listener
			comboBoxAsignaciones.addActionListener(asignaciones);
		}

	}

	public void eliminarDoctor() throws ParseException {
		// coger el dni del doctor seleccionado de la tabla
		int[] selectedRow = table.getSelectedRows();
		String info = new String();
		if (selectedRow.length > 0) {
			info = (String) table.getValueAt(selectedRow[0], 2);
		}

		// eliminar la fila seleccionada recargando la tabla
		cargarDoctoresCombobox();

		// Coger Asignacion, turnoAsignado y dni del doctor a eliminar
		String selected = (String) comboBoxAsignaciones.getSelectedItem();
		String[] selec = selected.split("-");
		String idAsig = selec[0]; // idAsig
		String diaTurno = Integer.toString(calendar.getDayChooser().getDay());
		String idMesTurno = Integer.toString(calendar.getMonthChooser()
				.getMonth() + 1);
		String fechaTurno = diaTurno + "/" + idMesTurno; // fechaTurno
		String tipoTurno = new String(); // tipoTurno
		if (rdbtnDiurno.isSelected())
			tipoTurno = "Diurno";
		else if (rdbtnNocturno.isSelected())
			tipoTurno = "Nocturno";
		String dniDoc = info; // dniDoc

		// eliminar doctor de la asignaci�n actual, si se puede eliminar,
		boolean b = cVista.eliminarDoctorAsignacion(idAsig, fechaTurno,
				tipoTurno, dniDoc);
		if (b) {
			// releer el hospital y guardar en disco lo modificado
			// desactivar el listener para que no salte antes de recargar el
			// vector IdTurnos
			comboBoxAsignaciones.removeActionListener(asignaciones);

			asigs = cVista.getInfoAsignaciones();
			idTurnoAsig.clear();
			for (int i = 0; i < asigs.size(); ++i) {
				String a = asigs.get(i);
				String[] asig = a.split(";");
				idTurnoAsig.add(asig[2]);
			}

			ActualizarMes();
			pintarJdayChooser();

			actualizarRadioButton(); // ///////////////////////////////////////////////////////////
			try {

				cVista.guardarAsignaciones(
						"data/asignaciones" + cVista.getIdHospitalActual()
								+ ".txt", asigs);
			} catch (FileNotFoundException e) {
				e.printStackTrace();

				JOptionPane.showMessageDialog(new JFrame(),
						"No se ha podido encontrar el fichero a guardar",
						"Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();

				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"No se ha podido leer/escribir el fichero, revisar los permisos del fichero",
								"Error", JOptionPane.ERROR_MESSAGE);
			}
			// volver a activar el listener
			comboBoxAsignaciones.addActionListener(asignaciones);
		}
	}

	public void eliminarAsignacion() {
		String a = (String) comboBoxAsignaciones.getSelectedItem();

		if (a != null) {
			String[] as = a.split("-");
			String id = as[0];

			int respuesta = JOptionPane.showConfirmDialog(null,
					"Estas seguro que quieres borrar la asignaci�n " + as[1]
							+ "?", "", JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				// elimina la asignacion y guarda en fichero
				if (cVista.eliminarAsignacion(id) > -1) {

					// recargar asigs y refresh
					asigs = cVista.getInfoAsignaciones();

					recargarIdTurnos();
					ActualizarMes();
					pintarJdayChooser();

					// guardar asignaciones en fichero
					try {
						cVista.guardarAsignaciones(
								"data/asignaciones"
										+ cVista.getIdHospitalActual() + ".txt",
								asigs);
					} catch (FileNotFoundException e) {
						e.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"No se ha podido encontrar el fichero donde guardar",
										"Error", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e) {
						e.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"No se ha podido leer/escribir el fichero, revisar los permisos del fichero",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			actualizarRadioButton();

		}
	}

	public void limpiarPantalla() {
		// diaSeleccionado
		DiaSeleccionado.setText("");
		// radiobuttons
		turnosRadius.clearSelection();
		// tabla
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		// comboboxDoctores
		comboBoxDoctors.removeAllItems();
	}
}