package vista.doctor.perfil;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ConsultarFestivos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	/**
	 * Create the frame.
	 */
	public ConsultarFestivos(ArrayList<String> festivos,
			ArrayList<String> periodos) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 497, 406);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 153, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 25, 340, 139);
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
		for (String p : festivos) {
			String[] split = p.split(";");
			model.addRow(new Object[] { split[0], split[1] });
		}

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(55, 199, 340, 131);
		contentPane.add(scrollPane_1);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Periodo Vacacional", "Fecha de inicio", "Fecha de fin" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_1.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(table_1);

		DefaultTableModel model1 = (DefaultTableModel) table_1.getModel();
		model1.setRowCount(0);
		for (String p : periodos) {
			String[] split = p.split(";");
			model1.addRow(new Object[] { split[0], split[1], split[2] });
		}
	}
}
