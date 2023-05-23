
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
import java.awt.event.ActionEvent;

/**
 * @author federicoruiz 10 may 2023 15:59:57
 */
public class VistaError extends JFrame {

	private JPanel contentPane;
	private Controlador miControlador;
	private Modelo miModelo;
	private VistaLogin login;
	private VistaRegistro registro;
	private VistaUsuarios usuarios;
	private VistaError error;
	private VistaPerfil perfil;


	private JLabel lblErrores;
	private JButton btnVolver;
	private JTextField txtErrores;

	/**
	 * Create the frame.
	 */
	public VistaError() {
		
		setTitle("Error");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		lblErrores = new JLabel("Numero de errores: ");
		lblErrores.setBounds(71, 108, 125, 16);
		getContentPane().add(lblErrores);

		txtErrores = new JTextField();
		txtErrores.setBounds(200, 103, 25, 26);
		getContentPane().add(txtErrores);
		txtErrores.setEditable(false);
		txtErrores.setColumns(10);

		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miControlador.mostrarLogin();
			}
		});
		btnVolver.setBounds(71, 210, 117, 29);
		getContentPane().add(btnVolver);

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

	public void setError(int err) {
		String num = Integer.toString(err);
		txtErrores.setText(num);
	}
}
