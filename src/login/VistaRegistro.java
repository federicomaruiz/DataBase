/**
 * 
 */
package login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * @author federicoruiz 10 may 2023 16:02:13
 */
public class VistaRegistro extends JFrame {

	private Controlador miControlador;
	private Modelo miModelo;
	private VistaLogin login;
	private VistaRegistro registro;
	private VistaUsuarios usuarios;
	private VistaError error;
	private VistaPerfil perfil;

	private JLabel lblContraseña;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JButton btnVolver;
	private JButton btnNuevoUsuario;
	private JPasswordField txtContraseña;
	private JLabel lblUserExiste;
	private JLabel lblCampoVacio;
	/**
	 * Create the frame.
	 */
	public VistaRegistro() {
		setTitle("Registro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		lblContraseña = new JLabel("Contraseña");
		lblContraseña.setBounds(73, 128, 98, 16);
		getContentPane().add(lblContraseña);

		lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(73, 84, 61, 16);
		getContentPane().add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				mostrarBtn();
			}
		});
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(175, 79, 130, 26);
		getContentPane().add(txtUsuario);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.mostrarLogin();
				txtUsuario.setText(null);
				txtContraseña.setText(null);
				lblUserExiste.setVisible(false);
				lblCampoVacio.setVisible(false);
			}
		});
		btnVolver.setBounds(73, 214, 117, 29);
		getContentPane().add(btnVolver);

		btnNuevoUsuario = new JButton("Nuevo Usuario");
		btnNuevoUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = txtUsuario.getText();
				String passwd = String.valueOf(txtContraseña.getPassword());
				if(user.equals("") || passwd.equals("")) {
					lblCampoVacio.setVisible(true);
				}else {
					miModelo.comprobarUsuario(user,passwd,lblUserExiste);
					txtUsuario.setText(null);
					txtContraseña.setText(null);
					lblCampoVacio.setVisible(false);
				}
			}
		});
		btnNuevoUsuario.setEnabled(false);
		btnNuevoUsuario.setBounds(220, 214, 117, 29);
		getContentPane().add(btnNuevoUsuario);

		txtContraseña = new JPasswordField();
		txtContraseña.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				mostrarBtn();
			}
		});
		txtContraseña.setBounds(175, 123, 130, 16);
		getContentPane().add(txtContraseña);
		
		lblUserExiste = new JLabel("Ya existe un usuario con ese nombre");
		lblUserExiste.setForeground(Color.RED);
		lblUserExiste.setVisible(false);
		lblUserExiste.setBounds(73, 169, 264, 16);
		getContentPane().add(lblUserExiste);
		
		lblCampoVacio = new JLabel("Debe completar los dos campos");
		lblCampoVacio.setVisible(false);
		lblCampoVacio.setForeground(Color.RED);
		lblCampoVacio.setBounds(73, 169, 276, 16);
		getContentPane().add(lblCampoVacio);
	}

	public void setControlador(Controlador miControlador) {
		this.miControlador = miControlador;
	}

	/**
	 * @param miModelo the miModelo to set
	 */
	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

	public void mostrarBtn() {
		if (!txtUsuario.getText().isEmpty() && !String.valueOf(txtContraseña.getPassword()).isEmpty()) {
			btnNuevoUsuario.setEnabled(true);
		} else {
			btnNuevoUsuario.setEnabled(false);
		}
	}
}
