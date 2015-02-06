package vista.gestion.hospital;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import vista.main.ControladorVista;

/**
 * @author marti.ribalta
 *
 */
public class AnadirTurno extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String turno;
	private ControladorVista ctrlVista = new ControladorVista();

	/**
	 * Create the dialog.
	 * 
	 * @param turno
	 */
	public AnadirTurno(String turno, boolean a) {

		this.turno = turno;
		setBounds(700, 400, 500, 150);
		getContentPane().setLayout(null);

		JButton btnOk = new JButton("OK");
		btnOk.setBounds(135, 90, 117, 25);
		getContentPane().add(btnOk);

		JButton btnCan = new JButton("Cancel");
		btnCan.setBounds(260, 90, 117, 25);
		getContentPane().add(btnCan);
		btnCan.addActionListener(new MyActionListener());

		if (a) {
			btnOk.addActionListener(new MyActionListener2());

			JLabel lblNewLabel = new JLabel(
					"Seguro que quieres añadir el turno: " + turno + "?");
			lblNewLabel.setBounds(25, 12, 449, 51);
			getContentPane().add(lblNewLabel);
		} else {
			btnOk.addActionListener(new MyActionListener3());
			JLabel lblNewLabel = new JLabel(
					"Seguro que quieres eliminar el turno: " + turno + "?");
			lblNewLabel.setBounds(25, 12, 449, 51);
			getContentPane().add(lblNewLabel);

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
			try {
				try {
					ctrlVista.anadirTurno(turno);
				} catch (ParseException e1) {
					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Ha habido un error inesperado en la conversion de un tipo.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
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

	class MyActionListener3 implements ActionListener {

		// close and dispose of the window.
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				ctrlVista.eliminarTurno(turno);
			} catch (IOException e2) {
				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
								"Error", JOptionPane.ERROR_MESSAGE);
			} catch (ParseException e2) {
				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"Error: Ha habido un error inesperado en la conversion de un tipo.",
								"Error", JOptionPane.ERROR_MESSAGE);
			}

			setVisible(false);
			dispose();
			try {
				Robot robot1 = new Robot();
				robot1.keyPress(KeyEvent.VK_F2);
				robot1.keyRelease(KeyEvent.VK_F2);
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
