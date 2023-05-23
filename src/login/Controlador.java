package login;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * @author federicoruiz 10 may 2023 12:36:31
 */
public class Controlador {

	private Modelo miModelo;
	private VistaLogin login;
	private VistaRegistro registro;
	private VistaUsuarios usuarios;
	private VistaError error;
	private VistaPerfil perfil;
	final String admin = "admin";
	final String usuario = "usuario";
	final int cantidadErrores = 3;

	/**
	 * @param miModelo the miModelo to set
	 */
	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(VistaLogin login) {
		this.login = login;
	}

	/**
	 * @param registro the registro to set
	 */
	public void setRegistro(VistaRegistro registro) {
		this.registro = registro;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(VistaError error) {
		this.error = error;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(VistaUsuarios usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Metodo que va a almacenar en una varible el usuario y en otra la contraseña , llamoa al metodo clickLogin de mi modelo
	 * que va verificar si la entrada es correcta y me va devovler una respuesta en RESULTADO (return) 
	 * Luego con la variable resultado veo quien se esta logeando y por donde tiene que ir 
	 * Perfil, Usuario, Error (+ de 3 errores exit)
	 */
	public void clickLogin() {
		String user = login.getUser();
		String contraseña = login.getContraseña();
		miModelo.clickLogin(user, contraseña);
		if (miModelo.resultado.equals(usuario)) {
			mostrarPerfil();
		} else if (miModelo.resultado.equals(admin)) {
			mostrarUsuarios();
		} else {
			mostrarError();
			if (miModelo.errores >= cantidadErrores) {
				System.exit(0);
			}
		}
	}

	/**
	 * @param perfil2
	 */
	public void setPerfil(VistaPerfil perfil2) {
		this.perfil = perfil2;

	}

	public void mostrarRegistro() {
		perfil.setVisible(false);
		login.setVisible(false);
		usuarios.setVisible(false);
		error.setVisible(false);
		registro.setVisible(true);

	}

	public void mostrarError() {
		error.setVisible(true);
		registro.setVisible(false);
		usuarios.setVisible(false);
		login.setVisible(false);
		perfil.setVisible(false);
	}

	public void mostrarPerfil() {
		perfil.setVisible(true);
		login.setVisible(false);
		usuarios.setVisible(false);
		error.setVisible(false);
		registro.setVisible(false);
	}

	public void mostrarLogin() {
		login.setVisible(true);
		usuarios.setVisible(false);
		error.setVisible(false);
		perfil.setVisible(false);
		registro.setVisible(false);
	}

	public void mostrarUsuarios() {
		usuarios.setVisible(true);
		login.setVisible(false);
		error.setVisible(false);
		perfil.setVisible(false);
		registro.setVisible(false);
	}

	/**
	 * @param passwd
	 * @param user  
	 *  Compruebo si el usuario existe o no. LLam0 al metodo de miModelo que va a crear el nuevo usuario,
	 *  le paso por parametro el user y la passwd Luego muestro el login y la etiqueta correspondiente
	 *  
	 */

	/**
	 * @param passwd 
	 * Compruebo que las contraseñas ingresadas sean iguales. 
	 * LLamo a miModelo para actualizar la contraseña le paso la nueva contraseña
	 * Luego muestro el login y la etiqueta, los otros campos a null y etiquetas.
	 */
	public void clickCambiar(JPasswordField txtRepiteContraseña, JPasswordField txtContraseña,
			JLabel lblContraseñaDistinta) {
		if (String.valueOf(txtContraseña.getPassword()).equals(String.valueOf(txtRepiteContraseña.getPassword()))) {
			String passwd = String.valueOf(txtContraseña.getPassword());
			miModelo.modificarContraseña(passwd);
			mostrarLogin();
			login.mostrarEtiquetaContraseña();
			txtRepiteContraseña.setText(null);
			txtContraseña.setText(null);
			lblContraseñaDistinta.setVisible(false);
		} else {
			lblContraseñaDistinta.setVisible(true);
		}
	}

	/**
	 * @param modelo
	 * @param txtUsuario
	 * @param txtContraseña
	 */

	public void altaUsuario(DefaultTableModel modelo, JTextField txtUsuario, JTextField txtContraseña) {
		String usr = txtUsuario.getText();
		String passwd = txtContraseña.getText();
		miModelo.añadirUsuario(usr,passwd);
		txtUsuario.setText(null);
		txtContraseña.setText(null);
	}

	/**
	 * @param modelo
	 * @param tabla
	 * @param txtUsuario
	 * @param txtContraseña
	 * @param user
	 * @param passwd
	 */

	public void bajaUsuario(DefaultTableModel modelo, JTable tabla, JTextField txtUsuario, JTextField txtContraseña) {
		int fila = tabla.getSelectedRow();
		String user = (String) modelo.getValueAt(fila, 0);
		String passwd = (String) modelo.getValueAt(fila, 1);
		if (!user.trim().toLowerCase().equals(admin)) {
			modelo.removeRow(tabla.getSelectedRow());
			miModelo.borrarUsuario(user, passwd);
			txtUsuario.setText(null);
			txtContraseña.setText(null);
		} else {
			usuarios.bajaAdmin();
		}

	}

	public void cambiarUsuario(JTable tabla, DefaultTableModel modelo, JTextField txtUsuario,
			JTextField txtContraseña) {
		int fila = tabla.getSelectedRow();
		if(miModelo.cambioAdmin){
			modelo.setValueAt(admin, fila, 0);
			modelo.setValueAt(txtContraseña.getText(), fila, 1);
			usuarios.cambioAdmin(true);
			miModelo.cambiarUsuario(admin, txtContraseña.getText());
		}else {
			modelo.setValueAt(txtUsuario.getText(), fila, 0);
			modelo.setValueAt(txtContraseña.getText(), fila, 1);
			usuarios.cambioAdmin(false);
			miModelo.cambiarUsuario(txtUsuario.getText(), txtContraseña.getText());	
		}
		txtUsuario.setText(null);
		txtContraseña.setText(null);
	}
}
	/**
	 * Completo los campos de texto con los datos seleccionados en la tabla, compruebo si el seleccionado es Admin.
	 * */


