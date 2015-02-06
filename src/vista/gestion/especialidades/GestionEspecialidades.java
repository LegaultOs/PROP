/*@author: Oscar Carod Iglesias */
package vista.gestion.especialidades;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
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

public class GestionEspecialidades extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	private ControladorVista cVista = new ControladorVista();

	private String[] columnNames = { "Nombre", "Minimo numero de doctores",
			"Coeficiente" };
	private String[] columnNamesDoctor = { "Nombre", "Apellidos", "DNI" };
	private JTable table2;
	private DefaultTableModel model2 = new DefaultTableModel(columnNamesDoctor,
			0) {
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
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JButton btnR;
	private JLabel lblGestinDeEspecialidades;

	private Object[][] listaEsp;
	private JButton btnAadir;
	private JButton btnModificar;
	private JButton btnEliminar;

	public GestionEspecialidades() {
		setBackground(new Color(255, 153, 0));
		setLayout(null);

		textField = new JTextField();
		textField.setBounds(148, 321, 86, 20);
		add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(148, 352, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(148, 383, 86, 20);
		add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(77, 324, 46, 14);
		add(lblNombre);

		JLabel lblNDoc = new JLabel("N\u00BA Doc");
		lblNDoc.setBounds(77, 355, 46, 14);
		add(lblNDoc);

		JLabel label = new JLabel("Coeficiente");
		label.setBounds(65, 386, 73, 14);
		add(label);

		table = new JTable(model);
		table.setRowSelectionAllowed(true);
		table2 = new JTable(model2);
		table2.setRowSelectionAllowed(true);

		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		cellSelectionModel
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		cellSelectionModel
				.addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						String[] info = new String[3];

						int[] selectedRow = table.getSelectedRows();

						if (selectedRow.length > 0) {

							for (int j = 0; j < 3; j++) {
								info[j] = (String) table.getValueAt(
										selectedRow[0], j);
							}

							textField.setText(info[0]);
							textField_1.setText(info[1]);
							textField_2.setText(info[2]);
							textField.setEditable(false);
							btnAadir.setEnabled(false);
							btnModificar.setEnabled(true);
							btnEliminar.setEnabled(true);
							llenarDoctores(info[0]);
						}
					}

				});

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				refreshInfo();
				busquedaEspecialidad();
			}
		});
		btnBuscar.setBounds(214, 41, 91, 23);
		add(btnBuscar);

		// IMPORTAR
		JButton btnImportar = new JButton("Importar");
		btnImportar.setBounds(455, 41, 100, 23);
		btnImportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				importarEspecialidades();
			}

		});
		add(btnImportar);

		textField_3 = new JTextField();
		textField_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					refreshInfo();
					busquedaEspecialidad();
				}
			}
		});
		textField_3.setBounds(102, 42, 102, 20);
		add(textField_3);
		textField_3.setColumns(10);

		JLabel lblDni_1 = new JLabel("Nombre");
		lblDni_1.setBounds(34, 48, 58, 14);
		add(lblDni_1);

		btnR = new JButton("Restablecer");
		btnR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshInfo();
			}
		});
		btnR.setBounds(316, 41, 119, 23);
		add(btnR);

		lblGestinDeEspecialidades = new JLabel("Gesti\u00F3n de especialidades");
		lblGestinDeEspecialidades.setBounds(10, 11, 154, 14);
		add(lblGestinDeEspecialidades);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(34, 73, 812, 120);

		add(scrollPane);

		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(34, 200, 812, 100);

		add(scrollPane2);

		btnAadir = new JButton("A\u00F1adir");
		btnAadir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addEspecialidad();
			}
		});
		btnAadir.setBounds(143, 444, 91, 23);
		add(btnAadir);

		btnModificar = new JButton("Modificar");
		btnModificar.setEnabled(false);
		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifEspecialidad();

			}
		});
		btnModificar.setBounds(284, 444, 91, 23);
		add(btnModificar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					elimEspecialidad();
				} catch (HeadlessException e1) {
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El código que depende de un teclado, la pantalla, o el ratón se llama en un entorno que no es compatible con un teclado, la pantalla, o el ratón.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEliminar.setBounds(422, 444, 91, 23);
		add(btnEliminar);

		iniData();

	}

	protected void importarEspecialidades() {

		int respuesta = JOptionPane
				.showConfirmDialog(
						null,
						"Esto borrará todos los doctores también ya que se pueden quedar colgados. Quieres continuar?",
						"Advertencia", JOptionPane.YES_NO_OPTION);
		if (respuesta == JOptionPane.YES_OPTION) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter type = new FileNameExtensionFilter(
					"TXT File", "txt");
			chooser.setFileFilter(type);

			int returnValue = chooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();

				String path = selectedFile.getAbsolutePath();

				String motivo = cVista.addEspFichero(path);
				if (motivo != null) {
					JOptionPane.showMessageDialog(new JFrame(), motivo,
							"Error", JOptionPane.ERROR_MESSAGE);

				} else {
					String motivo2 = cVista.clearDoctores();
					if (motivo2 != null) {
						JOptionPane.showMessageDialog(new JFrame(), motivo2,
								"Error", JOptionPane.ERROR_MESSAGE);

					}
				}
			}
		}

		refreshInfo();

	}

	private void refreshInfo() {

		model.setRowCount(0);
		model2.setRowCount(0);
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField.setEditable(true);
		btnAadir.setEnabled(true);
		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);

		iniData();

	}

	private void iniData() {
		// llamaremos al controlador que nos devolverá un Object[][]data con la
		// info de cada doctor

		listaEsp = cVista.getInfoEspecialidades();
		if (listaEsp != null) {
			for (Object[] i : listaEsp)
				model.addRow(i);
		}

	}

	private void busquedaEspecialidad() {
		String nom = textField_3.getText();
		model.setRowCount(0);
		if (listaEsp != null) {
			for (Object[] i : listaEsp) {
				if (((String) i[0]).contains(nom)) {
					model.addRow(i);
				}

			}
		}

	}

	private void llenarDoctores(String string) {

		model2.setRowCount(0);
		Object[][] listaDoc = cVista.getDoctores();
		if (listaDoc != null) {
			for (Object[] i : listaDoc) {

				if (string.compareTo(((String) i[4])) == 0)
					model2.addRow(i);

			}
		}

	}

	// /////////////////////////////////////////////////////////////////CRUD

	private void addEspecialidad() {
		String[] infoDoc = new String[3];

		infoDoc[0] = textField.getText();// nombre
		infoDoc[1] = textField_1.getText();// ndoc
		infoDoc[2] = textField_2.getText();// coeficiente

		try {
			cVista.addEsp(infoDoc);
			JOptionPane.showMessageDialog(new JFrame(),
					"Añadida correctamente", "Dabuti!",
					JOptionPane.INFORMATION_MESSAGE);
			refreshInfo();

		} catch (Exception e) {
			String excep = e.getMessage();
			JOptionPane.showMessageDialog(new JFrame(), excep, "Oops..",
					JOptionPane.ERROR_MESSAGE);
			refreshInfo();
		}
	}

	private void modifEspecialidad() {
		String[] infoDoc = new String[8];

		infoDoc[0] = textField.getText();// nombre
		infoDoc[1] = textField_1.getText();// ndoc
		infoDoc[2] = textField_2.getText();// coeficiente

		try {
			cVista.modifEsp(infoDoc);
			JOptionPane.showMessageDialog(new JFrame(),
					"Modificada correctamente", "Dabuti!",
					JOptionPane.INFORMATION_MESSAGE);
			refreshInfo();

		} catch (Exception e) {
			String excep = e.getMessage();
			JOptionPane.showMessageDialog(new JFrame(), excep, "Oops..",
					JOptionPane.ERROR_MESSAGE);
			refreshInfo();
		}
	}

	private void elimEspecialidad() throws HeadlessException, IOException {
		Object[][] listaDoc = cVista.getDoctores();
		boolean hay = false;
		String nom = textField.getText();
		ArrayList<String> aux = new ArrayList<String>();
		if (listaDoc != null) {
			for (Object[] i : listaDoc) {

				if (nom.compareTo(((String) i[4])) == 0) {

					aux.add((String) i[2]);
					hay = true;
				}

			}
		}

		if (!hay) {

			if (cVista.elimEsp(nom)) {

				JOptionPane.showMessageDialog(new JFrame(),
						"Eliminada correctamente", "",
						JOptionPane.INFORMATION_MESSAGE);
				refreshInfo();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Algo ha ido mal",
						"Oops..", JOptionPane.ERROR_MESSAGE);
				refreshInfo();
			}
		} else {
			int respuesta = JOptionPane
					.showConfirmDialog(
							null,
							"Hay doctores asociados a esta especialidad, quieres borrarla igualmente?\n (Se borrarán los doctores también)",
							"Advertencia", JOptionPane.YES_NO_OPTION);
			if (respuesta == JOptionPane.YES_OPTION) {
				for (String dni : aux) {
					cVista.elimDoc(dni);
				}

				if (cVista.elimEsp(nom)) {

					JOptionPane.showMessageDialog(new JFrame(),
							"Eliminada correctamente", "",
							JOptionPane.INFORMATION_MESSAGE);
					refreshInfo();
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Algo ha ido mal", "Oops..",
							JOptionPane.ERROR_MESSAGE);
					refreshInfo();
				}
			}
			refreshInfo();
		}
	}
}
