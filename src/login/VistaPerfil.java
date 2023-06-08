/**
 * 
 */
package login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;

/**
 * @author federicoruiz 10 may 2023 17:04:58
 */
public class VistaPerfil extends JFrame implements Vistas{

	private Controlador miControlador;
	private Modelo miModelo;
	private VistaLogin login;
	private VistaRegistro registro;
	private VistaUsuarios usuarios;
	private VistaError error;
	private VistaPerfil perfil;

	private JLabel lblNuevaContraseña;
	private JLabel lblContraseña;
	private JButton btnVolver;
	private JButton btnCambiar;
	private JPasswordField txtRepiteContraseña;
	private JPasswordField txtContraseña;
	private JLabel lblContraseñaDistinta;
	private JLabel lblOk;
	private JLabel lblNo;

	/**
	 * Create the frame.
	 */
	public VistaPerfil() {
		setTitle("Perfil");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		lblNuevaContraseña = new JLabel("Repite:");
		lblNuevaContraseña.setBounds(73, 127, 72, 16);
		getContentPane().add(lblNuevaContraseña);

		lblContraseña = new JLabel("Contraseña: ");
		lblContraseña.setBounds(73, 87, 98, 16);
		getContentPane().add(lblContraseña);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.mostrarLogin();
				txtRepiteContraseña.setText(null);
				txtContraseña.setText(null);
				lblContraseñaDistinta.setVisible(false);
				lblOk.setVisible(false);
				lblNo.setVisible(false);
			}
		});
		btnVolver.setBounds(73, 221, 117, 29);
		getContentPane().add(btnVolver);

		btnCambiar = new JButton("Cambiar");
		btnCambiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblOk.setVisible(false);
				lblNo.setVisible(false);
				miControlador.clickCambiar(txtRepiteContraseña, txtContraseña, lblContraseñaDistinta);
			}
		});
		btnCambiar.setEnabled(false);
		btnCambiar.setBounds(231, 221, 117, 29);
		getContentPane().add(btnCambiar);

		txtRepiteContraseña = new JPasswordField();
		txtRepiteContraseña.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				mostrarBtn();
			}
		});
		txtRepiteContraseña.setBounds(192, 127, 127, 16);
		getContentPane().add(txtRepiteContraseña);

		txtContraseña = new JPasswordField();
		txtContraseña.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				mostrarBtn();
			}
		});
		txtContraseña.setBounds(192, 87, 127, 16);
		getContentPane().add(txtContraseña);

		lblContraseñaDistinta = new JLabel("Las contraseñas no coinciden");
		lblContraseñaDistinta.setForeground(new Color(233, 0, 14));
		lblContraseñaDistinta.setVisible(false);
		lblContraseñaDistinta.setBounds(73, 172, 201, 16);
		getContentPane().add(lblContraseñaDistinta);
		
		lblOk = new JLabel("Operacion realizada con exito");
		lblOk.setVisible(false);
		lblOk.setBounds(83, 200, 201, 16);
		getContentPane().add(lblOk);
		
		lblNo = new JLabel("Fallo operacion");
		lblNo.setVisible(false);
		lblNo.setForeground(Color.RED);
		lblNo.setBounds(84, 200, 190, 16);
		getContentPane().add(lblNo);
	}

	@Override
	public void setControlador(Controlador miControlador) {
		this.miControlador = miControlador;
		
	}

	@Override
	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
		
	}

	public void mostrarBtn() {
		if (!String.valueOf(txtContraseña.getPassword()).isEmpty()
				&& !String.valueOf(txtRepiteContraseña.getPassword()).isEmpty()) {
			btnCambiar.setEnabled(true);
		} else {
			btnCambiar.setEnabled(false);
		}
	}

	/**
	 * @return the lblOk
	 */
	public JLabel getLblOk() {
		return lblOk;
	}

	/**
	 * @return the lblNo
	 */
	public JLabel getLblNo() {
		return lblNo;
	}

	
}
