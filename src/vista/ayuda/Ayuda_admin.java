/*@author eric.alvarez.chinchilla*/
package vista.ayuda;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;

public class Ayuda_admin extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ayuda_admin() {
		setBackground(new Color(255, 153, 0));

		JTextPane txtpnManualDe = new JTextPane();
		txtpnManualDe.setBackground(new Color(255, 153, 0));
		txtpnManualDe.setText("Manual del administrador");

		JScrollPane scrollPane = new JScrollPane();

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(30)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																scrollPane,
																GroupLayout.PREFERRED_SIZE,
																808,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																txtpnManualDe,
																GroupLayout.PREFERRED_SIZE,
																150,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(881, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(27)
						.addComponent(txtpnManualDe,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(27)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE,
								434, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(197, Short.MAX_VALUE)));

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setAutoscrolls(false);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		textArea.setBorder(BorderFactory.createCompoundBorder(border,
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		try {
			URL is = getClass().getClassLoader().getResource("help_admin.txt");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					is.openStream()));
			textArea.read(br, "");
			br.close();
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}
		scrollPane.setViewportView(textArea);
		setLayout(groupLayout);
	}
}
