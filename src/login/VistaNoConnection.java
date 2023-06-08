/**
 * 
 */
package login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author federicoruiz
 * 30 may 2023 11:18:55
 */
public class VistaNoConnection extends JFrame implements Vistas {

	private JPanel contentPane;
	private Modelo modelo;
	private Controlador controlador;
	private JLabel lblError;
	private JButton btnVolver;
	

	/**
	 * Create the frame.
	 */
	public VistaNoConnection() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setBounds(31, 88, 385, 68);
		contentPane.add(lblError);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlador.noConexion();
				controlador.mostrarConfiguracion();	
			}
		});
		btnVolver.setBounds(150, 225, 117, 29);
		contentPane.add(btnVolver);

		
	}


	@Override
	public void setControlador(Controlador miControlador) {
		this.controlador = miControlador;
		
	}

	@Override
	public void setModelo(Modelo miModelo) {
		this.modelo = miModelo;
		
	}

	/**
	 * @param string
	 */
	public void error(String err) {
		lblError.setText(err);
		controlador.mostrarNoConnection();
		
	}
}
