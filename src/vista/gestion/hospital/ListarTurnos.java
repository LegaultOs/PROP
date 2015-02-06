package vista.gestion.hospital;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import vista.main.ControladorVista;

public class ListarTurnos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;
	private ControladorVista ctrlVista = new ControladorVista();

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public ListarTurnos(ArrayList<String> turnos) {
		setDefaultCloseOperation(ListarTurnos.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 719, 430);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 153, 0));
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 51, 636, 295);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Fecha", "Tipo", "Especialidad", "Doctores necesarios" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			boolean[] columnEditables = new boolean[] { false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}

		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new MyActionListener());
		btnCancelar.setBounds(556, 358, 117, 25);
		contentPane.add(btnCancelar);

		JButton btnGuardarAsignacion = new JButton("Guardar asignacion");
		btnGuardarAsignacion.addActionListener(new MyActionListener3());
		btnGuardarAsignacion.setBounds(371, 358, 173, 25);
		contentPane.add(btnGuardarAsignacion);

		JLabel lblNewLabel = new JLabel(
				"Los siguientes turnos no han sido completados:");
		lblNewLabel.setBounds(37, 24, 636, 15);
		contentPane.add(lblNewLabel);
		table.getTableHeader().setReorderingAllowed(false);
		model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		for (String s : turnos) {
			String[] split = s.split(";");
			model.addRow(new Object[] { split[0], split[1], split[2], split[3] });
		}

	}

	class MyActionListener implements ActionListener {

		// close and dispose of the window.
		public void actionPerformed(ActionEvent e) {
			ctrlVista.eliminarAsignacion(ctrlVista.getAsigs());
			setVisible(false);
			dispose();
		}
	}

	class MyActionListener3 implements ActionListener {

		// close and dispose of the window.
		public void actionPerformed(ActionEvent e) {
			try {
				ctrlVista.guardarAsig();
			} catch (IOException e2) {
				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"Error: Se ha producido un error de E/S de alg�n tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
								"Error", JOptionPane.ERROR_MESSAGE);
			}
			setVisible(false);
			dispose();
			try {
				Robot robot1 = new Robot();
				robot1.keyPress(KeyEvent.VK_F4);
				robot1.keyRelease(KeyEvent.VK_F4);
			} catch (AWTException e1) {
				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"Error: Se ha producido un error con Abstract Window Toolkit.",
								"Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
