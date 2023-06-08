/**
 * 
 */
package login;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author federicoruiz 10 may 2023 13:42:20
 */
public class VistaLogin extends JFrame implements Vistas{

	private JPanel contentPane;
	private Controlador miControlador;
	private Modelo miModelo;
	private VistaLogin login;
	private VistaRegistro registro;
	private VistaUsuarios usuarios;
	private VistaError error;
	private VistaPerfil perfil;

	private JLabel lblUsuario;
	private JLabel lblContraseña;
	private JTextField txtUsuario;
	private JPasswordField txtContraseña;
	private JButton btnLogin;
	private JButton btnRegistro;
	private JLabel lblUsuarioCreado;
	private JLabel lblContraseñaModificada;
	private JButton btnConfig;
	private JLabel lblOk;

	/**
	 * Create the frame.
	 */
	public VistaLogin() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(73, 75, 61, 16);
		getContentPane().add(lblUsuario);

		lblContraseña = new JLabel("Contraseña");
		lblContraseña.setBounds(73, 114, 79, 16);
		getContentPane().add(lblContraseña);

		txtUsuario = new JTextField();
		txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				mostrarBtn();
			}
		});
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(206, 70, 130, 26);
		getContentPane().add(txtUsuario);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.clickLogin();
				txtUsuario.setText(null);
				txtContraseña.setText(null);
				lblUsuarioCreado.setVisible(false);
				lblContraseñaModificada.setVisible(false);
				lblOk.setVisible(false);
			}
		});
		btnLogin.setEnabled(false);
		btnLogin.setBounds(219, 180, 117, 29);
		getContentPane().add(btnLogin);

		txtContraseña = new JPasswordField();
		txtContraseña.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				mostrarBtn();
			}
		});
		txtContraseña.setBounds(206, 109, 130, 21);
		getContentPane().add(txtContraseña);

		btnRegistro = new JButton("Registro");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.mostrarRegistro();
				txtUsuario.setText(null);
				txtContraseña.setText(null);
				lblUsuarioCreado.setVisible(false);
				lblContraseñaModificada.setVisible(false);
				lblOk.setVisible(false);
			}
		});
		btnRegistro.setBounds(219, 222, 117, 29);
		getContentPane().add(btnRegistro);

		lblUsuarioCreado = new JLabel("Usuario creado");
		lblUsuarioCreado.setVisible(false);
		lblUsuarioCreado.setForeground(Color.RED);
		lblUsuarioCreado.setBounds(73, 155, 117, 16);
		getContentPane().add(lblUsuarioCreado);

		lblContraseñaModificada = new JLabel("Contraseña modificada");
		lblContraseñaModificada.setVisible(false);
		lblContraseñaModificada.setForeground(Color.RED);
		lblContraseñaModificada.setBounds(73, 155, 158, 16);
		getContentPane().add(lblContraseñaModificada);
		
		
		ImageIcon icono = new ImageIcon(VistaLogin.class.getResource("/img/rueda.png"));
		Image imagenOriginal = icono.getImage();
	    Image imagenRedimensionada = imagenOriginal.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	    ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);
		btnConfig = new JButton(iconoRedimensionado);
		btnConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.mostrarConfiguracion();
				miControlador.rellenarCampos();
			}
		});
		btnConfig.setBounds(58, 180, 94, 56);
		btnConfig.setBorderPainted(false);
	    btnConfig.setFocusPainted(false);
	    btnConfig.setContentAreaFilled(false);
		getContentPane().add(btnConfig);
		
		lblOk = new JLabel("Operacion realizada con exito");
		lblOk.setVisible(false);
		lblOk.setBounds(18, 248, 199, 16);
		getContentPane().add(lblOk);
		 // Ruta de la imagen
       
	}

	@Override
	public void setControlador(Controlador miControlador) {
		this.miControlador = miControlador;
		
	}

	@Override
	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
		
	}

	/**
	 * @return Devuelve el valor que hay en el campo usuario
	 */
	public String getUser() {
		return txtUsuario.getText();
	}

	/**
	 * @return Devuelve el valor que hay en el campo contraseña
	 */
	public String getContraseña() {
		return String.valueOf(txtContraseña.getPassword());
	}

	public void mostrarBtn() {
		if (!txtUsuario.getText().isEmpty() && !String.valueOf(txtContraseña.getPassword()).isEmpty()) {
			btnLogin.setEnabled(true);
			;
		} else {
			btnLogin.setEnabled(false);
		}
	}

	public void mostrarVista() {
		miControlador.mostrarLogin();
	}

	public void mostrarEtiqueta() {
		lblUsuarioCreado.setVisible(true);
	}

	public void mostrarEtiquetaContraseña() {
		lblContraseñaModificada.setVisible(true);
	}

	/**
	 * @return the lblOk
	 */
	public JLabel getLblOk() {
		return lblOk;
	}

	/**
	 * 
	 */
	public void mostrarLogin() {
		miControlador.mostrarLogin();	
	}
	
	

}
