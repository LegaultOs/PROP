/*@author eric.alvarez.chinchilla*/

package vista.gestion.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import vista.main.ControladorVista;
import vista.main.PantallaAdmin;
import vista.main.PantallaDoctor;
import dominio.conjuntohospitales.ControladorLogin;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	private ControladorLogin cLogin;
	private LinkedHashMap<String, Integer> listaHosp;
	private JComboBox<String> comboBox;
	private JTextField textField_1;

	public static String userName; // PRUEBAS BARRI
	public static String id; // PRUEBAS BARRI

	/**
	 * Create the panel.
	 */
	public Login() {
		getContentPane().setBackground(new Color(255, 153, 0));
		setResizable(false);
		setMinimumSize(new Dimension(400, 400));
		getContentPane().setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		setTitle("Login");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		comboBox = new JComboBox<String>();
		comboBox.setBounds(122, 104, 168, 22);

		getContentPane().add(comboBox);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(173, 55, 46, 14);
		getContentPane().add(lblLogin);

		textField = new JTextField();
		textField.setBounds(122, 151, 168, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER) {
					String pass = new String(passwordField.getPassword());
					String key = (String) comboBox.getSelectedItem();
					String id = getId(key);
					boolean autentificado = false;
					try {
						autentificado = cLogin.checkUserPass(
								textField.getText(), pass, id);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
										"Error", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e1) {
						e1.printStackTrace();

						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
										"Error", JOptionPane.ERROR_MESSAGE);
					}

					if (comboBox.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Tienes que escoger un hospital.", " ",
								JOptionPane.INFORMATION_MESSAGE);
					} else if (!autentificado) {
						JOptionPane.showMessageDialog(new JFrame(),
								"Usuario y/o contraseña incorrectos.", " ",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						userName = textField.getText(); // PRUEBAS BARRI
						AbrirPrograma();
					}
				}
			}
		});
		passwordField.setBounds(122, 197, 168, 22);
		getContentPane().add(passwordField);

		JLabel lblHospital = new JLabel("Hospital");
		lblHospital.setBounds(66, 108, 46, 14);
		getContentPane().add(lblHospital);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(66, 154, 46, 14);
		getContentPane().add(lblUsuario);

		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(42, 201, 70, 14);
		getContentPane().add(lblContrasea);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pass = new String(passwordField.getPassword());
				String key = (String) comboBox.getSelectedItem();
				String id = getId(key);
				
				// Integer id = listaHosp.get(key);
				boolean autentificado = false;
				try {
					autentificado = cLogin.checkUserPass(textField.getText(),
							pass, id);
				} catch (FileNotFoundException e) {
					e.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
									"Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();

					JOptionPane
							.showMessageDialog(
									new JFrame(),
									"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}

				if (comboBox.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Tienes que escoger un hospital.", " ",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (!autentificado) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Usuario y/o contraseña incorrectos.", " ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					userName = textField.getText(); // PRUEBAS BARRI
					AbrirPrograma();
				}
			}
		});
		btnEntrar.setBounds(159, 230, 91, 23);
		getContentPane().add(btnEntrar);

		JButton btnBorrar = new JButton("Eliminar hospital");
		btnBorrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String user = textField.getText();
				String pass = new String(passwordField.getPassword());
				String key = (String) comboBox.getSelectedItem();
				String id = getId(key);

				if (user.compareTo("admin") == 0
						&& pass.compareTo("admin") == 0
						&& comboBox.getSelectedIndex() != 0) {

					int respuesta = JOptionPane
							.showConfirmDialog(
									null,
									"Esto borrará todos los datos del hospital. Quieres continuar?",
									"Advertencia", JOptionPane.YES_NO_OPTION);
					if (respuesta == JOptionPane.YES_OPTION) {

						try {
							cLogin.eliminarHospital(id);
							JOptionPane.showMessageDialog(new JFrame(),
									"Hospital borrado correctamente", " ",
									JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e) {
							e.printStackTrace();

							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
											"Error", JOptionPane.ERROR_MESSAGE);
						}
						refreshData();
					}
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							"Solo puede eliminar el Administrador", " ",
							JOptionPane.ERROR_MESSAGE);

				}

			}
		});
		btnBorrar.setBounds(142, 264, 131, 23);
		getContentPane().add(btnBorrar);

		textField_1 = new JTextField();
		textField_1.setBounds(122, 344, 115, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton = new JButton("A\u00F1adir hospital");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addHospital();
			}
		});
		btnNewButton.setBounds(247, 343, 137, 23);
		getContentPane().add(btnNewButton);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(66, 347, 46, 14);
		getContentPane().add(lblNombre);

		try {
			cLogin = new ControladorLogin();
			iniData();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addHospital() {
		boolean todoOk = false;
		String user = textField.getText();
		String pass = new String(passwordField.getPassword());
		if (user.compareTo("admin") == 0 && pass.compareTo("admin") == 0) {
			try {
				todoOk = cLogin.addHosp(textField_1.getText());
			} catch (IOException e) {
				e.printStackTrace();

				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
								"Error", JOptionPane.ERROR_MESSAGE);
			}
			if (!todoOk)
				JOptionPane.showMessageDialog(new JFrame(), "El hospital "
						+ textField_1.getText()
						+ " tiene un formato incorrecto o ya existe", " ",
						JOptionPane.INFORMATION_MESSAGE);
			else {
				JOptionPane.showMessageDialog(new JFrame(),
						"Se ha añadido correctamente", "Nice",
						JOptionPane.INFORMATION_MESSAGE);
				textField_1.setText("");
				refreshData();
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(),
					"Solo puede añadir el Administrador", " ",
					JOptionPane.ERROR_MESSAGE);

		}
	}

	private void refreshData() {
		textField.setText("");
		passwordField.setText("");
		comboBox.removeAllItems();
		iniData();
	}

	private void iniData() {
		try {
			listaHosp = cLogin.getHospitales();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: El intento de abrir el archivo denotado por una ruta de acceso especificada ha fallado.",
							"Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}
		Set<String> keys = listaHosp.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String option = listaHosp.get(key) + " - " + key;
			comboBox.addItem(option);
		}
		comboBox.insertItemAt("", 0);
		comboBox.setSelectedIndex(0);
	}

	private String getId(String key) {
		String[] l1 = key.split("-");
		String l2 = l1[0].replaceAll("\\s", "");
		return l2;
	}

	private void AbrirPrograma() {
		String key = (String) comboBox.getSelectedItem();
		String[] aux = key.split("-");
		id = getId(key);
		try {
			ControladorVista.cambiarHospital(id.toString(), aux[1]);
			if (textField.getText().contentEquals("admin")) {
				PantallaAdmin PA = new PantallaAdmin(this);
				PA.setVisible(true);
				this.setVisible(false);
			} else {
				PantallaDoctor PU = new PantallaDoctor(this);
				PU.setVisible(true);
				this.setVisible(false);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), " ",
					JOptionPane.ERROR_MESSAGE);
		}

		refreshData();
	}

	public static String getUserName() {
		return userName;
	}

	public static String getId() {
		return id;
	}
}
