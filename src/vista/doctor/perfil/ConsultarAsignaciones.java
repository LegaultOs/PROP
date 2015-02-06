package vista.doctor.perfil;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ConsultarAsignaciones extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ConsultarAsignaciones(String asignacionesAux, String dni) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 471, 342);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 153, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 11, 328, 262);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Fecha", "Turno" }) {
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

		scrollPane.setViewportView(table);

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		String[] split = asignacionesAux.split(";");
		String[] turnos = split[2].split(",");
		for (int i = 0; i < turnos.length; ++i) {
			String[] camposTurno = turnos[i].split("-");
			String[] dnis = camposTurno[2].split(":");
			for (int j = 0; j < dnis.length; ++j) {
				if (dnis[j].equals(dni)) {
					model.addRow(new Object[] { camposTurno[0], camposTurno[1] });
					break;
				}
			}
		}
	}
}
