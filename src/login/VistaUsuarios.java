/**
 * 
 */
package login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map.Entry;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author federicoruiz 10 may 2023 16:02:46
 */
public class VistaUsuarios extends JFrame implements Vistas{

	private Controlador miControlador;
	private Modelo miModelo;
	private VistaLogin login;
	private VistaRegistro registro;
	private VistaUsuarios usuarios;
	private VistaError error;
	private VistaPerfil perfil;

	private JButton btnVolver;
	private JButton btnAlta;
	private JButton btnBaja;
	private JButton btnModificar;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private JTable tabla;
	private JTextField txtUsuario;
	private JTextField txtContraseña;
	private JLabel lblUsuarioExiste;
	private JLabel lblBajaAdmin;
	private JLabel lblCambiarAdmin;
	final String admin = "admin";
	final String passwd = "1234";
	private JLabel lblOk;
	private JLabel lblNo;

	/**
	 * Create the frame.
	 */
	public VistaUsuarios() {
		setTitle("Usuarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblOk.setVisible(false);
				lblNo.setVisible(false);
				miControlador.cambiarUsuario(tabla, modelo, txtUsuario, txtContraseña);
				btnModificar.setEnabled(false);
				limpiarPantalla();
			}
		});
		btnModificar.setEnabled(false);
		btnModificar.setBounds(333, 224, 89, 29);
		getContentPane().add(btnModificar);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblOk.setVisible(false);
				lblNo.setVisible(false);
				miControlador.mostrarLogin();
				limpiarPantalla();
				txtUsuario.setText(null);
				txtContraseña.setText(null);
				lblCambiarAdmin.setVisible(false);
			}
		});
		btnVolver.setBounds(30, 224, 89, 29);
		getContentPane().add(btnVolver);

		btnAlta = new JButton("Alta");
		btnAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.altaUsuario(tabla, modelo, txtUsuario, txtContraseña);
				btnAlta.setEnabled(false);
				lblBajaAdmin.setVisible(false);
				lblCambiarAdmin.setVisible(false);
			}
		});
		btnAlta.setEnabled(false);
		btnAlta.setBounds(131, 224, 89, 29);
		getContentPane().add(btnAlta);

		btnBaja = new JButton("Baja");
		btnBaja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblOk.setVisible(false);
				lblNo.setVisible(false);
				miControlador.bajaUsuario(modelo, tabla, txtUsuario, txtContraseña);
				btnBaja.setEnabled(false);
				lblUsuarioExiste.setVisible(false);
				lblCambiarAdmin.setVisible(false);
			}
		});
		btnBaja.setEnabled(false);
		btnBaja.setBounds(232, 224, 89, 29);
		getContentPane().add(btnBaja);

		txtContraseña = new JTextField();
		txtContraseña.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				mostrarBtnAlta();
			}
		});
		txtContraseña.setBounds(232, 197, 130, 26);
		getContentPane().add(txtContraseña);
		txtContraseña.setColumns(10);

		txtUsuario = new JTextField();
		txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				mostrarBtnAlta();
			}
		});
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(79, 197, 130, 26);
		getContentPane().add(txtUsuario);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(79, 6, 283, 157);
		getContentPane().add(scrollPane);

		tabla = new JTable();
		tabla.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnModificar.setEnabled(true);
				btnBaja.setEnabled(true);
			}
		});
		tabla.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				lblOk.setVisible(false);
				lblNo.setVisible(false);
				lblCambiarAdmin.setVisible(false);
				miControlador.completarCampos(tabla, modelo, txtUsuario, txtContraseña);
			}
		});
		scrollPane.setViewportView(tabla);

		// Asigno a la variable modelo las columnas y filas para luego poder
		// modificarlas
		modelo = new DefaultTableModel();
		tabla.setModel(modelo);

		lblUsuarioExiste = new JLabel("El usuario ya existe");
		lblUsuarioExiste.setVisible(false);
		lblUsuarioExiste.setForeground(Color.RED);
		lblUsuarioExiste.setBounds(79, 169, 283, 16);
		getContentPane().add(lblUsuarioExiste);

		lblBajaAdmin = new JLabel("No se puede eliminar el administrador");
		lblBajaAdmin.setVisible(false);
		lblBajaAdmin.setForeground(Color.RED);
		lblBajaAdmin.setBounds(79, 169, 283, 16);
		getContentPane().add(lblBajaAdmin);

		lblCambiarAdmin = new JLabel("No se puede modificar el nombre ADMIN, solo la contraseña");
		lblCambiarAdmin.setVisible(false);
		lblCambiarAdmin.setForeground(Color.RED);
		lblCambiarAdmin.setBounds(20, 169, 424, 16);
		getContentPane().add(lblCambiarAdmin);
		
		lblOk = new JLabel("Operacion realizada con exito");
		lblOk.setVisible(false);
		lblOk.setBounds(126, 250, 221, 16);
		getContentPane().add(lblOk);
		
		lblNo = new JLabel("Error de operacion");
		lblNo.setVisible(false);
		lblNo.setForeground(Color.RED);
		lblNo.setBounds(131, 250, 190, 16);
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

	/**
	 * @param modelo the modelo to set
	 */
	public void setTablaUser(DefaultTableModel modelo) {
		this.modelo = miModelo.getMiTabla();
	}

	/**
	 * @param user
	 * @param passwd
	 */

	public void mostrarBtnAlta() {
		if (!txtUsuario.getText().isEmpty() && !txtContraseña.getText().isEmpty()) {
			btnAlta.setEnabled(true);
		} else {
			btnAlta.setEnabled(false);
			;
		}
	}

	/**
	 * Recorro la tabla busco donde este el nombre , y luego modifico la contraseña
	 * por la actual.
	 */
	public void actualizarContraseña(String nombreUsuario, String passwd) {
		int rowCount = modelo.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String nombre = modelo.getValueAt(i, 0).toString();
			if (nombre.equals(nombreUsuario)) {
				modelo.setValueAt(passwd, i, i);
			}
		}
	}

	public void bajaAdmin() {
		lblBajaAdmin.setVisible(true);
	}

	public void limpiarPantalla() {
		lblUsuarioExiste.setVisible(false);
		lblBajaAdmin.setVisible(false);

	}

	public void cambioAdmin(boolean a) {
		lblCambiarAdmin.setVisible(a);
	}

	public void userExiste(boolean a) {
		lblUsuarioExiste.setVisible(a);
	}

	/**
	 * @return the modelo
	 */
	public DefaultTableModel getModelo() {
		return modelo;
	}

	/**
	 * @return the tabla
	 */
	public JTable getTabla() {
		return tabla;
	}

	public void actualizoLista(DefaultTableModel miTabla) {
		this.modelo = miTabla;
		tabla.setModel(modelo);

	}

	/**
	 * @return the lblOk
	 */
	public JLabel getLblOk() {
		return lblOk;
	}

	/**
	 * @param lblOk the lblOk to set
	 */
	public void setLblOk(JLabel lblOk) {
		this.lblOk = lblOk;
	}

	/**
	 * @return the lblNo
	 */
	public JLabel getLblNo() {
		return lblNo;
	}

	/**
	 * @param lblNo the lblNo to set
	 */
	public void setLblNo(JLabel lblNo) {
		this.lblNo = lblNo;
	}

	/**
	 * @return the lblCambiarAdmin
	 */
	public JLabel getLblCambiarAdmin() {
		return lblCambiarAdmin;
	}
	
	
	



}