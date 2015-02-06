/*@author: Oscar Carod Iglesias */
package vista.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

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

import vista.ayuda.Ayuda_doctor;
import vista.doctor.perfil.Perfil;

import java.awt.Font;

import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.border.LineBorder;

public class PantallaDoctor extends JFrame {

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
					PantallaDoctor frame = new PantallaDoctor();
					frame.setVisible(true);
				} catch (Exception e) {
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
	public PantallaDoctor() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		setTitle("Perfil de doctor en: " + cVista.getNombreHospital());

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

			JOptionPane.showMessageDialog(new JFrame(),
					"Ha ocurrido un problema cargando la pantalla", " ",
					JOptionPane.ERROR_MESSAGE);
		}

		setIconImage(after);
		contentPane.setLayout(null);
		JLabel picLabel = new JLabel(new ImageIcon(after));
		picLabel.setBounds(173, 133, 260, 306);
		getContentPane().add(picLabel);

		JLabel lblBenvingutsAlMeravellos = new JLabel("HOSPITAL "
				+ cVista.getNombreHospital().toUpperCase());
		lblBenvingutsAlMeravellos.setBounds(461, 164, 333, 88);
		lblBenvingutsAlMeravellos.setHorizontalAlignment(SwingConstants.CENTER);
		lblBenvingutsAlMeravellos.setVerticalAlignment(SwingConstants.CENTER);
		contentPane.add(lblBenvingutsAlMeravellos);
		JLabel labal = new JLabel("Has entrado como Doctor");
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

		JMenuItem mntmPerfil = new JMenuItem("Perfil");
		mntmPerfil.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmPerfil.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mntmPerfil.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmPerfil.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					cambiarPantalla(new Perfil());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		menuBar.add(mntmPerfil);

		JMenuItem mntmAyuda = new JMenuItem("Ayuda");
		mntmAyuda.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmAyuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		mntmAyuda.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmAyuda.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				cambiarPantalla(new Ayuda_doctor());

			}
		});
		menuBar.add(mntmAyuda);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		mntmLogout.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		mntmLogout.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				logOut();
			}
		});
		menuBar.add(mntmLogout);

	}

	public PantallaDoctor(JFrame login) {
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