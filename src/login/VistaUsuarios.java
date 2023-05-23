/**
 * 
 */
package login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map.Entry;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * @author federicoruiz 10 may 2023 16:02:46
 */
public class VistaUsuarios extends JFrame {

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
				miControlador.altaUsuario(modelo, txtUsuario, txtContraseña);
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
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPane.setViewportView(tabla);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				tabla.setModel(miModelo.getTabla());
			}
		});
		
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
				miModelo.completarCampos(tabla,txtUsuario, txtContraseña);
			}
		});
		// scrollPane.setViewportView(tabla);

		// Asigno a la variable modelo las columnas y filas para luego poder modificarlas
	
		
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

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(DefaultTableModel modelo) {
		this.modelo = modelo;
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
	 * @param user
	 * Recibo por parametro el nombre del usuario para  mirar en la tabla y en mi lista que no este repetido.
	 * Paso por todas las filas y busco si existe el nombre, sino existe lo pinto, si existe saco cartel.
	 * 
	 */
	public void pintarLista(String user) {
		boolean existe = false;
		int rowCount = modelo.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String nombre = modelo.getValueAt(i, 0).toString();
			if (nombre.equals(user)) {
				existe=true;
			} 
		}
		if(!existe) {
			int j = 0;
			for (Entry<String, String> entry : miModelo.miHashMap.entrySet()) {
				if (entry.getKey() == user.toLowerCase().trim()) {
					modelo.addRow(new String[] { entry.getKey(), entry.getValue() });
					lblUsuarioExiste.setVisible(false);
					j++;
				}
			}
		}else {
			lblUsuarioExiste.setVisible(true);
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
	
	public void usuarioExiste(boolean a) {
		lblUsuarioExiste.setVisible(a);
	}

	/**
	 * @return the tabla
	 */

	/**
	 * @param miTabla the tabla to set
	 */

	
}