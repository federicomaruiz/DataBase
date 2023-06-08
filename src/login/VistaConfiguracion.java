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
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.Properties;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * @author federicoruiz 27 may 2023 20:00:10
 */
public class VistaConfiguracion extends JFrame implements Vistas{

	private JPanel contentPane;
	private Modelo miModelo;
	private Controlador miControlador;
	private VistaLogin login;
	private JLabel lblNewLabel;
	private JLabel lblUsuario;
	private JLabel lblContraseña;
	private JLabel lblBd;
	private JButton btnGuardar;
	private JButton btnVolver;
	private JTextField txtUsuario;
	private JTextField txtContraseña;
	private JTextField textURL;
	private JTextField textBD;
	private JLabel lblOk;
	private JLabel lblErr;
	private JLabel lblNoConnection;
	private JTextField txtDriver;
	private JLabel lblDriver;

	/**
	 * @return the miModelo
	 */
	public Modelo getMiModelo() {
		return miModelo;
	}

	/**
	 * @return the miControlador
	 */
	public Controlador getMiControlador() {
		return miControlador;
	}

	
	/**
	 * @return the login
	 */
	public VistaLogin getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(VistaLogin login) {
		this.login = login;
	}

	/**
	 * Create the frame.
	 */
	public VistaConfiguracion() {
		setTitle("Configuraciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("URL:");
		lblNewLabel.setBounds(31, 127, 61, 16);
		contentPane.add(lblNewLabel);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(31, 47, 61, 16);
		contentPane.add(lblUsuario);

		lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(31, 87, 106, 16);
		contentPane.add(lblContraseña);

		lblBd = new JLabel("BD:");
		lblBd.setBounds(31, 167, 61, 16);
		contentPane.add(lblBd);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(146, 42, 232, 26);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		

		txtContraseña = new JTextField();
		txtContraseña.setColumns(10);
		txtContraseña.setBounds(146, 82, 232, 26);
		contentPane.add(txtContraseña);
	

		textURL = new JTextField();
		textURL.setColumns(10);
		textURL.setBounds(146, 122, 232, 26);
		contentPane.add(textURL);
		

		textBD = new JTextField();
		textBD.setColumns(10);
		textBD.setBounds(146, 157, 232, 26);
		contentPane.add(textBD);
		

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.guardar();
				lblNoConnection.setVisible(false);
			}
		});
		btnGuardar.setBounds(286, 226, 92, 29);
		contentPane.add(btnGuardar);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miModelo.cargarBd();
				lblOk.setVisible(false);
				lblErr.setVisible(false);
				
			}
		});
		btnVolver.setBounds(146, 226, 92, 29);
		contentPane.add(btnVolver);
		
		lblOk = new JLabel("Fichero guardado con exito");
		lblOk.setVisible(false);
		lblOk.setBounds(31, 211, 207, 16);
		contentPane.add(lblOk);
		
		lblErr = new JLabel("Error de operacion");
		lblErr.setForeground(Color.RED);
		lblErr.setVisible(false);
		lblErr.setBounds(31, 211, 207, 16);
		contentPane.add(lblErr);
		
		lblNoConnection = new JLabel("Imposible conectar con BD, comprueba configuracion");
		lblNoConnection.setVisible(false);
		lblNoConnection.setForeground(Color.RED);
		lblNoConnection.setBounds(31, 6, 368, 29);
		contentPane.add(lblNoConnection);
		
		txtDriver = new JTextField();
		txtDriver.setColumns(10);
		txtDriver.setBounds(146, 188, 232, 26);
		contentPane.add(txtDriver);
		
		lblDriver = new JLabel("Driver:");
		lblDriver.setBounds(31, 195, 61, 16);
		contentPane.add(lblDriver);

		
	}
	

	/**
	 * @return the txtUsuario
	 */
	public JTextField getTxtUsuario() {
		return txtUsuario;
	}

	
	/**
	 * @return the txtContrsaseña
	 */
	public JTextField getTxtContraseña() {
		return txtContraseña;
	}

	

	/**
	 * @return the textURL
	 */
	public JTextField getTextURL() {
		return textURL;
	}


	/**
	 * @return the textBD
	 */
	public JTextField getTextBD() {
		return textBD;
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
	 * @return the lblOk
	 */
	public JLabel getLblOk() {
		return lblOk;
	}

	/**
	 * @return the lblErr
	 */
	public JLabel getLblErr() {
		return lblErr;
	}
	
	public void mostrarVentana() {
		miControlador.mostrarConfiguracion();
	}

	/**
	 * @return the lblNoConnection
	 */
	public JLabel getLblNoConnection() {
		return lblNoConnection;
	}

	/**
	 * @param lblNoConnection the lblNoConnection to set
	 */
	public void setLblNoConnection(JLabel lblNoConnection) {
		this.lblNoConnection = lblNoConnection;
	}

	/**
	 * @param txtUsuario the txtUsuario to set
	 */
	public void setTxtUsuario(JTextField txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	/**
	 * @param txtContraseña the txtContraseña to set
	 */
	public void setTxtContraseña(JTextField txtContraseña) {
		this.txtContraseña = txtContraseña;
	}

	/**
	 * @param textURL the textURL to set
	 */
	public void setTextURL(JTextField textURL) {
		this.textURL = textURL;
	}

	/**
	 * @param textBD the textBD to set
	 */
	public void setTextBD(JTextField textBD) {
		this.textBD = textBD;
	}

	/**
	 * @return the txtDriver
	 */
	public JTextField getTxtDriver() {
		return txtDriver;
	}

	/**
	 * @param txtDriver the txtDriver to set
	 */
	public void setTxtDriver(JTextField txtDriver) {
		this.txtDriver = txtDriver;
	}

	/**
	 * @return the lblDriver
	 */
	public JLabel getLblDriver() {
		return lblDriver;
	}

	/**
	 * @param lblDriver the lblDriver to set
	 */
	public void setLblDriver(JLabel lblDriver) {
		this.lblDriver = lblDriver;
	}
	
	
}
