/*@author: Oscar Carod Iglesias */
package vista.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import vista.ayuda.Ayuda_admin;
import vista.gestion.asignaciones.GestionAsignaciones;
import vista.gestion.doctores.GestionDoctores;
import vista.gestion.especialidades.GestionEspecialidades;
import vista.gestion.hospital.GestionHospital;

import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.io.IOException;
import javax.swing.border.LineBorder;

public class PantallaAdmin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame padere;
	private ControladorVista cVista = new ControladorVista();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					PantallaAdmin frame = new PantallaAdmin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();

					JOptionPane.showMessageDialog(new JFrame(),
							"Ha ocurrido un problema cargando la pantalla",
							" ", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PantallaAdmin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		setTitle("Gestionando: " + cVista.getNombreHospital());

		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 153, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		BufferedImage after = null;
		try {
			BufferedImage myPicture = ImageIO.read(getClass().getClassLoader()
					.getResource("sierpe.png"));
			int w = myPicture.getWidth();
			int h = myPicture.getHeight();
			after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(0.5, 0.5);
			at.translate(270, 200);
			AffineTransformOp scaleOp = new AffineTransformOp(at,
					AffineTransformOp.TYPE_BILINEAR);
			after = scaleOp.filter(myPicture, after);
		} catch (IOException e1) {
			e1.printStackTrace();

			JOptionPane
					.showMessageDialog(
							new JFrame(),
							"Error: Se ha producido un error de E/S de algún tipo. Estos errores suelen ser producidos por las operaciones de E/S fallidas o interrumpidas.",
							"Error", JOptionPane.ERROR_MESSAGE);
		}

		contentPane.setLayout(null);
		setIconImage(after);
		JLabel picLabel = new JLabel(new ImageIcon(after));
		picLabel.setBounds(173, 133, 260, 306);
		getContentPane().add(picLabel);

		JLabel lblBenvingutsAlMeravellos = new JLabel("HOSPITAL "
				+ cVista.getNombreHospital().toUpperCase());
		lblBenvingutsAlMeravellos.setBounds(461, 164, 333, 88);
		lblBenvingutsAlMeravellos.setHorizontalAlignment(SwingConstants.CENTER);
		lblBenvingutsAlMeravellos.setVerticalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBenvingutsAlMeravellos);
		JLabel labal = new JLabel("Has entrado como Administrador");
		labal.setBounds(461, 263, 333, 88);
		labal.setHorizontalAlignment(SwingConstants.CENTER);
		labal.setVerticalAlignment(SwingConstants.CENTER);
		contentPane.add(labal);

		/*
		 * JPanel panel = new JPanel(); panel.setBackground(new Color(255, 153,
		 * 0)); contentPane.add(panel, BorderLayout.CENTER);
		 */

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenuItem mntmGestiondoctores = new JMenuItem("Gestión de Doctores");
		mntmGestiondoctores.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmGestiondoctores.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmGestiondoctores.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F1, 0));
		mntmGestiondoctores.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cambiarPantalla(new GestionDoctores());

			}
		});
		menuBar.add(mntmGestiondoctores);

		JMenuItem mntmGestionhospitales = new JMenuItem("Gestión de Hospital");
		mntmGestionhospitales.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmGestionhospitales.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmGestionhospitales.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F2, 0));
		mntmGestionhospitales.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cambiarPantalla(new GestionHospital());

			}
		});
		menuBar.add(mntmGestionhospitales);

		JMenuItem mntmGestionespecialidades = new JMenuItem(
				"Gestión de Especialidades");
		mntmGestionespecialidades.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmGestionespecialidades.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmGestionespecialidades.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F3, 0));
		mntmGestionespecialidades.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cambiarPantalla(new GestionEspecialidades());

			}
		});
		menuBar.add(mntmGestionespecialidades);

		JMenuItem mntmGestionAsignaciones = new JMenuItem(
				"Gestión de Asignaciones");
		mntmGestionAsignaciones.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmGestionAsignaciones.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmGestionAsignaciones.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_F4, 0));
		mntmGestionAsignaciones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cambiarPantalla(new GestionAsignaciones());

			}
		});
		menuBar.add(mntmGestionAsignaciones);

		JMenuItem mntmAyuda = new JMenuItem("Ayuda");
		mntmAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmAyuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		mntmAyuda.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmAyuda.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cambiarPantalla(new Ayuda_admin());

			}
		});
		menuBar.add(mntmAyuda);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmLogout.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		mntmLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				logOut();

			}
		});
		menuBar.add(mntmLogout);

	}

	public PantallaAdmin(JFrame login) {
		this();
		padere = login;
	}

	private void logOut() {
		padere.setVisible(true);
		this.dispose();

	}

	private void cambiarPantalla(JPanel pant) {
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.removeAll();
		contentPane.add(pant);
		contentPane.validate();
		contentPane.repaint();

	}

}
