package vista.gestion.hospital;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import vista.main.ControladorVista;

import com.toedter.calendar.JDateChooser;

public class DefinirPeriodo extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private ControladorVista ctrlVista = new ControladorVista();
	private JTextField textField;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;

	/**
	 * Create the dialog.
	 */
	public DefinirPeriodo() {
		setBounds(100, 100, 233, 232);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		dateChooser = new JDateChooser();
		dateChooser.getDateEditor().setEnabled(false);
		dateChooser.setBounds(97, 88, 114, 19);
		contentPanel.add(dateChooser);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.getDateEditor().setEnabled(false);
		dateChooser_1.setBounds(97, 112, 114, 19);
		contentPanel.add(dateChooser_1);

		JLabel lblDataDinici = new JLabel("Fecha inicio:");
		lblDataDinici.setBounds(12, 92, 79, 15);
		contentPanel.add(lblDataDinici);

		JLabel lblDataFi = new JLabel("Fecha fin:");
		lblDataFi.setBounds(12, 116, 78, 15);
		contentPanel.add(lblDataFi);

		textField = new JTextField();
		textField.setBounds(97, 57, 114, 19);
		contentPanel.add(textField);
		textField.setColumns(10);

		JLabel lblNom = new JLabel("Nom:");
		lblNom.setBounds(12, 59, 70, 15);
		contentPanel.add(lblNom);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new MyActionListener2());
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new MyActionListener());
			}
		}
	}

	class MyActionListener implements ActionListener {

		// close and dispose of the window.
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			dispose();
		}
	}

	class MyActionListener2 implements ActionListener {

		// close and dispose of the window.
		@Override
		public void actionPerformed(ActionEvent e) {
			if (dateChooser.getDate() == null
					|| dateChooser_1.getDate() == null
					|| textField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(new JFrame(),
						"No puede haber campos vacios", " ",
						JOptionPane.ERROR_MESSAGE);

			} else if (dateChooser_1.getDate().compareTo(dateChooser.getDate()) == -1) {
				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"La fecha de inicio debe ser inferior o igual a la fecha fin",
								" ", JOptionPane.ERROR_MESSAGE);
			} else if (ctrlVista.existePeriodo(textField.getText())) {
				JOptionPane.showMessageDialog(new JFrame(),
						"Ya existe un periodo vacacional con este nombre", " ",
						JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					ctrlVista.definirPeriodo(textField.getText(),
							dateChooser.getDate(), dateChooser_1.getDate());
				} catch (IOException e1) {
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
				setVisible(false);
				dispose();
			}
		}
	}
}
