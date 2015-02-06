/*@author: Oscar Carod Iglesias */
package vista.gestion.doctores;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import vista.main.ControladorVista;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class GestionDoctores extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	private ControladorVista cVista = new ControladorVista();
	private JTextField textField;
	private JTextField textField_1;
	private String[] columnNames = { "Nombre", "Apellidos", "DNI", "Direccion",
			"Especialidad", "Experiencia", "Nacimiento", "Sueldo Base",
			"Sueldo Final" };

	private JTable table;
	private DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			// no se podran editar las celdas
			return false;
		}
	};
	private JLabel lblGestinDeDoctores;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
	private JTextField textField_7;
	private JButton btnR;
	private Object[][] listaDoc;
	private String[] listaEsp;
	private JTextField textField_8;
	private JLabel lblSueldo;
	private JLabel lblNewLabel;
	private JButton btnAadirDoctor;
	private JButton btnNewButton;
	private JButton btnEliminarDoctor;
	private JDateChooser dateChooser;
	private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private JComboBox<String> comboBox;

	public GestionDoctores() {
		setBackground(new Color(255, 153, 0));
		setLayout(null);

		textField = new JTextField();
		textField.setBounds(102, 329, 86, 20);
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(102, 356, 120, 20);
		add(textField_1);
		textField_1.setColumns(10);
		table = new JTable(model);
		table.setRowSelectionAllowed(true);

		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		cellSelectionModel
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel
				.addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						String[] info = new String[8];

						int[] selectedRow = table.getSelectedRows();

						if (selectedRow.length > 0) {

							for (int j = 0; j < 8; j++) {
								info[j] = (String) table.getValueAt(
										selectedRow[0], j);
							}

							textField.setText(info[0]);
							textField_1.setText(info[1]);
							textField_2.setText(info[2]);
							textField_3.setText(info[3]);
							comboBox.setSelectedItem(info[4]);
							textField_5.setText(info[5]);
							try {
								dateChooser.setDate(df.parse(info[6]));
							} catch (ParseException e1) {
								JOptionPane
										.showMessageDialog(
												new JFrame(),
												"Error: Ha habido un error inesperado en la conversion de un tipo.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
							}
							textField_8.setText(info[7]);

							btnAadirDoctor.setEnabled(false);
							btnNewButton.setEnabled(true);
							btnEliminarDoctor.setEnabled(true);
							textField_2.setEditable(false);

						}
					}

				});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(34, 73, 812, 190);

		add(scrollPane);

		// IMPORTAR
		JButton btnImportar = new JButton("Importar");
		btnImportar.setBounds(421, 39, 100, 23);
		btnImportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				importarDoctores();
			}

		});
		add(btnImportar);

		lblGestinDeDoctores = new JLabel("Gesti\u00F3n de doctores");
		lblGestinDeDoctores.setBounds(10, 11, 154, 14);
		add(lblGestinDeDoctores);

		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(50, 332, 59, 14);
		add(lblNombre);

		lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(40, 362, 69, 14);
		add(lblApellidos);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(73, 306, 46, 14);
		add(lblDni);

		textField_2 = new JTextField();
		textField_2.setBounds(102, 303, 86, 20);
		add(textField_2);
		textField_2.setColumns(10);

		JLabel lblDireccin = new JLabel("Direcci\u00F3n");
		lblDireccin.setBounds(246, 306, 69, 14);
		add(lblDireccin);

		textField_3 = new JTextField();
		textField_3.setBounds(315, 303, 126, 20);
		add(textField_3);
		textField_3.setColumns(10);

		JLabel lblEspecialidad = new JLabel("Especialidad");
		lblEspecialidad.setBounds(232, 332, 83, 14);
		add(lblEspecialidad);

		JLabel lblExperiencia = new JLabel("Experiencia");
		lblExperiencia.setBounds(232, 359, 74, 14);
		add(lblExperiencia);

		textField_5 = new JTextField();
		textField_5.setBounds(315, 356, 58, 20);
		add(textField_5);
		textField_5.setColumns(10);

		JLabel lblAos = new JLabel("a\u00F1os");
		lblAos.setBounds(383, 359, 46, 14);
		add(lblAos);

		JLabel lblNacimiento = new JLabel("Nacimiento");
		lblNacimiento.setBounds(34, 389, 79, 14);
		add(lblNacimiento);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshInfo();
				busquedaDoctorDni();

			}
		});
		btnBuscar.setBounds(170, 39, 91, 23);
		add(btnBuscar);

		textField_7 = new JTextField();
		textField_7.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					refreshInfo();
					busquedaDoctorDni();
				}
			}
		});
		textField_7.setBounds(58, 42, 102, 20);
		add(textField_7);
		textField_7.setColumns(10);

		JLabel lblDni_1 = new JLabel("DNI");
		lblDni_1.setBounds(34, 48, 29, 14);
		add(lblDni_1);

		btnR = new JButton("Restablecer");
		btnR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshInfo();
			}
		});
		btnR.setBounds(282, 39, 119, 23);
		add(btnR);

		textField_8 = new JTextField();
		textField_8.setBounds(315, 387, 86, 20);
		add(textField_8);
		textField_8.setColumns(10);

		lblSueldo = new JLabel("Sueldo Base");
		lblSueldo.setBounds(232, 389, 86, 14);
		add(lblSueldo);

		lblNewLabel = new JLabel("Euros");
		lblNewLabel.setBounds(411, 392, 46, 14);
		add(lblNewLabel);

		btnAadirDoctor = new JButton("A\u00F1adir Doctor");
		btnAadirDoctor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDoctor();

			}
		});
		btnAadirDoctor.setBounds(102, 431, 146, 23);
		add(btnAadirDoctor);

		btnNewButton = new JButton("Modificar Doctor");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifDoctor();
			}
		});
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(310, 431, 147, 23);
		add(btnNewButton);

		btnEliminarDoctor = new JButton("Eliminar Doctor");
		btnEliminarDoctor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				elimDoctor();
			}
		});
		btnEliminarDoctor.setEnabled(false);
		btnEliminarDoctor.setBounds(517, 431, 146, 23);
		add(btnEliminarDoctor);

		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		dateChooser.setBounds(102, 387, 120, 20);
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.getDateEditor().setEnabled(false);
		((JTextFieldDateEditor) dateChooser.getDateEditor())
				.setDisabledTextColor(Color.darkGray);

		add(dateChooser);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(315, 328, 120, 22);

		add(comboBox);

		iniData();

	}

	private void refreshInfo() {
		model.setRowCount(0);
		comboBox.removeAllItems();
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_2.setEditable(true);
		textField_3.setText("");

		textField_5.setText("");
		((JTextField) dateChooser.getDateEditor()).setText("");

		textField_8.setText("");
		btnAadirDoctor.setEnabled(true);
		btnNewButton.setEnabled(false);
		btnEliminarDoctor.setEnabled(false);

		iniData();

	}

	private void importarDoctores() {

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter type = new FileNameExtensionFilter("TXT File",
				"txt");
		chooser.setFileFilter(type);

		int returnValue = chooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();

			String path = selectedFile.getAbsolutePath();
			String motivo = cVista.addDocFichero(path);
			if (motivo != null) {
				JOptionPane.showMessageDialog(new JFrame(), "Error: \n"
						+ motivo, "Error", JOptionPane.ERROR_MESSAGE);

			}
		}

		refreshInfo();

	}

	private void iniData() {
		// llamaremos al controlador que nos devolverá un Object[][]data con la
		// info de cada doctor

		listaDoc = cVista.getDoctores();
		if (listaDoc != null) {
			for (Object[] i : listaDoc)
				model.addRow(i);
		}

		listaEsp = cVista.getEspecialidades();
		if (listaEsp != null) {
			for (String s : listaEsp)
				comboBox.addItem(s);
		}
		comboBox.insertItemAt("", 0);
		comboBox.setSelectedIndex(0);

	}

	private void addDoctor() {

		String[] infoDoc = new String[8];

		infoDoc[0] = textField.getText();// nombre
		infoDoc[1] = textField_1.getText();// apellidos
		infoDoc[2] = textField_2.getText();// dni
		if (dateChooser.getDate() != null)
			infoDoc[3] = df.format(dateChooser.getDate());// nacimiento
		infoDoc[4] = textField_3.getText();// direccion
		infoDoc[5] = textField_5.getText();// experiencia
		infoDoc[6] = (String) comboBox.getSelectedItem();// especialidad
		infoDoc[7] = textField_8.getText();// sueldo

		String motivo = cVista.addDoc(infoDoc);
		if (motivo == null) {

			JOptionPane.showMessageDialog(new JFrame(), "Todo correcto", "",
					JOptionPane.INFORMATION_MESSAGE);
			refreshInfo();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), motivo, "Oops..",
					JOptionPane.ERROR_MESSAGE);
			// refreshInfo();

		}

	}

	private void modifDoctor() {
		String[] infoDoc = new String[8];

		infoDoc[0] = textField.getText();// nombre
		infoDoc[1] = textField_1.getText();// apellidos
		infoDoc[2] = textField_2.getText();// dni
		infoDoc[3] = df.format(dateChooser.getDate());// nacimiento
		infoDoc[4] = textField_3.getText();// direccion
		infoDoc[5] = textField_5.getText();// experiencia
		infoDoc[6] = (String) comboBox.getSelectedItem();// especialidad
		infoDoc[7] = textField_8.getText();// sueldo

		String motivo = cVista.modifDoc(infoDoc);
		if (motivo == null) {

			JOptionPane.showMessageDialog(new JFrame(), "Todo correcto", "",
					JOptionPane.INFORMATION_MESSAGE);
			refreshInfo();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), motivo, "Oops..",
					JOptionPane.ERROR_MESSAGE);
			// refreshInfo();

		}

	}

	private void elimDoctor() {
		String dni = textField_2.getText();
		String motivo = cVista.elimDoc(dni);
		if (motivo == null) {

			JOptionPane.showMessageDialog(new JFrame(), "Todo correcto", "",
					JOptionPane.INFORMATION_MESSAGE);
			refreshInfo();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), motivo, "Oops..",
					JOptionPane.ERROR_MESSAGE);
			// refreshInfo();

		}

	}

	private void busquedaDoctorDni() {

		String dni = textField_7.getText();
		model.setRowCount(0);
		if (listaDoc != null) {
			for (Object[] i : listaDoc) {
				if (((String) i[2]).contains(dni)) {
					model.addRow(i);
				}

			}
		}

	}

}
