package vista.doctor.perfil;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import vista.main.ControladorVista;

/**
 * @author cristian.barrientos
 *
 */
public class ConsultarRestricciones extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	private JButton btnEliminar;
	private ControladorVista cv = new ControladorVista();
	DefaultTableModel model;

	/**
	 * Create the frame.
	 */
	public ConsultarRestricciones(ArrayList<String> restriccionesAux,
			final boolean eliminar, final String dni) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 719, 412);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 153, 0));
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 11, 636, 295);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Tipo de Restriccion", "Informacion" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			boolean[] columnEditables = new boolean[] { false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		});
		table.getTableHeader().setReorderingAllowed(false);

		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel
				.addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						ListSelectionModel lsm = (ListSelectionModel) e
								.getSource();
						btnEliminar.setEnabled(!lsm.isSelectionEmpty());
					}
				});

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String restTipo = (String) table.getValueAt(
						table.getSelectedRow(), 0);
				String restInfo = (String) table.getValueAt(
						table.getSelectedRow(), 1);
				model.removeRow(table.getSelectedRow());
				String restriccion = null;
				String[] split = restInfo.split(" ");
				String mes;
				if (restTipo.charAt(0) == 'N') { // NOT
					if (restTipo.charAt(4) == 'p') { // PERIODO
						restriccion = "NOT;pv;";
						for (int i = 4; i < split.length; i++)
							restriccion += split[i];
						
						try {
							cv.removeRestriccion(restriccion, dni);
						} catch (FileNotFoundException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else if (restTipo.length() > 12) { // Dia Semanal
						restriccion = "NOT;ds;";
						restriccion += split[4];
						try {
							cv.removeRestriccion(restriccion, dni);
						} catch (FileNotFoundException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else if (restTipo.charAt(4) == 'd') { // DIA
						restriccion = "NOT;dia;";
						restriccion += split[4];
						try {
							cv.removeRestriccion(restriccion, dni);
						} catch (FileNotFoundException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else if (restTipo.charAt(4) == 'm') { // MES
						restriccion = "NOT;mes;";
						if (split[4].equals("enero"))
							mes = "1";
						else if (split[4].equals("febrero"))
							mes = "2";
						else if (split[4].equals("marzo"))
							mes = "3";
						else if (split[4].equals("abril"))
							mes = "4";
						else if (split[4].equals("mayo"))
							mes = "5";
						else if (split[4].equals("junio"))
							mes = "6";
						else if (split[4].equals("julio"))
							mes = "7";
						else if (split[4].equals("agosto"))
							mes = "8";
						else if (split[4].equals("septiembre"))
							mes = "9";
						else if (split[4].equals("octubre"))
							mes = "10";
						else if (split[4].equals("noviembre"))
							mes = "11";
						else
							mes = "12";
						restriccion += mes;
						try {
							cv.removeRestriccion(restriccion, dni);
						} catch (FileNotFoundException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else if (restTipo.charAt(0) == 'M') { // MAX
					if (restTipo.charAt(4) == 'd') { // DIA
						restriccion = "MAX;dia;";
						restriccion += split[4];
						try {
							cv.removeRestriccion(restriccion, dni);
						} catch (FileNotFoundException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else if (restTipo.charAt(4) == 'm') { // MES
						restriccion = "MAX;mes;";
						if (split[7].equals("enero"))
							mes = "1";
						else if (split[7].equals("febrero"))
							mes = "2";
						else if (split[7].equals("marzo"))
							mes = "3";
						else if (split[7].equals("abril"))
							mes = "4";
						else if (split[7].equals("mayo"))
							mes = "5";
						else if (split[7].equals("junio"))
							mes = "6";
						else if (split[7].equals("julio"))
							mes = "7";
						else if (split[7].equals("agosto"))
							mes = "8";
						else if (split[7].equals("septiembre"))
							mes = "9";
						else if (split[7].equals("octubre"))
							mes = "10";
						else if (split[7].equals("noviembre"))
							mes = "11";
						else
							mes = "12";
						restriccion += mes + ";";
						restriccion += split[4];
						try {
							cv.removeRestriccion(restriccion, dni);
						} catch (FileNotFoundException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else if (restTipo.charAt(4) == 'p') { // PERIODO
						restriccion = "MAX;pv;";
						for (int i = 7; i < split.length; i++) {
							restriccion += split[i];
							if (i < split.length - 1)
								restriccion += " ";
						}
						restriccion += ";" + split[4];
						try {
							cv.removeRestriccion(restriccion, dni);
						} catch (FileNotFoundException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Ha habido un error inesperado en la conversion de un tipo.",
											"Error", JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} else { // XOR
					restriccion = "XOR;dia;";
					restriccion += split[3] + ";" + split[7];
					try {
						cv.removeRestriccion(restriccion, dni);
					} catch (FileNotFoundException e) {
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
										"Error", JOptionPane.ERROR_MESSAGE);
					} catch (ParseException e) {
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: Ha habido un error inesperado en la conversion de un tipo.",
										"Error", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e) {
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		btnEliminar.setEnabled(false);
		if (!eliminar)
			btnEliminar.setVisible(false);
		btnEliminar.setBounds(292, 340, 135, 23);
		contentPane.add(btnEliminar);

		table.getColumnModel().getColumn(0).setPreferredWidth(138);
		table.getColumnModel().getColumn(1).setPreferredWidth(130);

		model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		String mes;
		Collections.sort(restriccionesAux);
		for (String r : restriccionesAux) {
			String[] split = r.split(";");
			if (split[0].equals("NOT")) {
				if (split[1].equals("dia")) {
					model.addRow(new Object[] { "NOT dia",
							"No quiero trabajar el " + split[2] });
				}

				else if (split[1].equals("mes")) {
					if (split[2].equals("1"))
						mes = "enero";
					else if (split[2].equals("2"))
						mes = "febrero";
					else if (split[2].equals("3"))
						mes = "marzo";
					else if (split[2].equals("4"))
						mes = "abril";
					else if (split[2].equals("5"))
						mes = "mayo";
					else if (split[2].equals("6"))
						mes = "junio";
					else if (split[2].equals("7"))
						mes = "julio";
					else if (split[2].equals("8"))
						mes = "agosto";
					else if (split[2].equals("9"))
						mes = "septiembre";
					else if (split[2].equals("10"))
						mes = "octubre";
					else if (split[2].equals("11"))
						mes = "noviembre";
					else
						mes = "diciembre";

					model.addRow(new Object[] { "NOT mes",
							"No quiero trabajar en " + mes });

				}

				else if (split[1].equals("pv")) {
					model.addRow(new Object[] { "NOT periodo vacacional",
							"No quiero trabajar en " + split[2] });
				} else {
					model.addRow(new Object[] { "NOT dia semanal",
							"No quiero trabajar ningún " + split[2] });
				}
			} else if (split[0].equals("MAX")) {
				if (split[1].equals("dia")) {
					model.addRow(new Object[] {
							"MAX dia",
							"Quiero trabajar como máximo " + split[2]
									+ " días este año" });
				} else if (split[1].equals("mes")) {
					if (split[2].equals("1"))
						mes = "enero";
					else if (split[2].equals("2"))
						mes = "febrero";
					else if (split[2].equals("3"))
						mes = "marzo";
					else if (split[2].equals("4"))
						mes = "abril";
					else if (split[2].equals("5"))
						mes = "mayo";
					else if (split[2].equals("6"))
						mes = "junio";
					else if (split[2].equals("7"))
						mes = "julio";
					else if (split[2].equals("8"))
						mes = "agosto";
					else if (split[2].equals("9"))
						mes = "septiembre";
					else if (split[2].equals("10"))
						mes = "octubre";
					else if (split[2].equals("11"))
						mes = "noviembre";
					else
						mes = "diciembre";
					model.addRow(new Object[] {
							"MAX mes",
							"Quiero trabajar como máximo " + split[3]
									+ " días en " + mes });
				} else {
					model.addRow(new Object[] {
							"MAX periodo vacacional",
							"Quiero trabajar como máximo " + split[3]
									+ " días en " + split[2] });
				}
			} else {
				model.addRow(new Object[] {
						"XOR dia",
						"O trabajo el " + split[2] + " o trabajo el "
								+ split[3] });
			}
		}

	}
}
