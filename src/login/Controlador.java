package login;

import javax.swing.JFrame;
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
	private VistaConfiguracion configuracion;
	private VistaNoConnection noConnection;
	final String admin = "admin";
	final String usuario = "usuario";
	final int cantidadErrores = 3;
	boolean cambioAdmin = false;

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
	 * Metodo que va a almacenar en una varible el usuario y en otra la contraseña ,
	 * llamoa al metodo clickLogin de mi modelo que va verificar si la entrada es
	 * correcta y me va devovler una respuesta en RESULTADO (return) Luego con la
	 * variable resultado veo quien se esta logeando y por donde tiene que ir
	 * Perfil, Usuario, Error (+ de 3 errores exit)
	 */
	public void clickLogin() {
		String user = login.getUser();
		String contraseña = login.getContraseña();
		String res = miModelo.clickLogin(user, contraseña);
		if (res.equals(usuario)) {
			mostrarPerfil();
		} else if (res.equals(admin)) {
			mostrarUsuarios();
			miModelo.cargarTabla();
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
		noConnection.setVisible(false);

	}

	public void mostrarError() {
		error.setVisible(true);
		registro.setVisible(false);
		usuarios.setVisible(false);
		login.setVisible(false);
		perfil.setVisible(false);
		noConnection.setVisible(false);
	}

	public void mostrarPerfil() {
		perfil.setVisible(true);
		login.setVisible(false);
		usuarios.setVisible(false);
		error.setVisible(false);
		registro.setVisible(false);
		noConnection.setVisible(false);
	}

	public void mostrarLogin() {
		login.setVisible(true);
		usuarios.setVisible(false);
		error.setVisible(false);
		perfil.setVisible(false);
		registro.setVisible(false);
		noConnection.setVisible(false);
		configuracion.setVisible(false);
	}

	public void mostrarUsuarios() {
		usuarios.setVisible(true);
		login.setVisible(false);
		error.setVisible(false);
		perfil.setVisible(false);
		registro.setVisible(false);
		noConnection.setVisible(false);
	}
	
	public void mostrarConfiguracion() {
		configuracion.setVisible(true);
		usuarios.setVisible(false);
		login.setVisible(false);
		error.setVisible(false);
		perfil.setVisible(false);
		registro.setVisible(false);
		noConnection.setVisible(false);
		
	}

	/**
	 * @param passwd Compruebo que las contraseñas ingresadas sean iguales. LLamo a
	 *               miModelo para actualizar la contraseña le paso la nueva
	 *               contraseña Luego muestro el login y la etiqueta, los otros
	 *               campos a null y etiquetas.
	 */
	public void clickCambiar(JPasswordField txtRepiteContraseña, JPasswordField txtContraseña,
			JLabel lblContraseñaDistinta) {
		String passwd1 = String.valueOf(txtContraseña.getPassword());
		String passwd2 = String.valueOf(txtRepiteContraseña.getPassword());
		if (passwd1.equals(passwd2)) {
			miModelo.modificarContraseña(passwd1);
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

	public void altaUsuario(JTable tabla, DefaultTableModel modelo, JTextField txtUsuario, JTextField txtContraseña) {
		String usr = txtUsuario.getText();
		String passwd = txtContraseña.getText();
		miModelo.añadirUsuario(tabla, modelo, usr, passwd);
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
		int res = 0;
		if (cambioAdmin) {
			if(!txtUsuario.getText().equals(admin)) { // Miro si esta intentando cambiarle el nombre al "admin"
				usuarios.cambioAdmin(true);
			}else {
				usuarios.getLblCambiarAdmin().setVisible(false);
				modelo.setValueAt(admin, fila, 0);
				modelo.setValueAt(txtContraseña.getText(), fila, 1);
				res = miModelo.cambiarUsuario(admin, txtContraseña.getText());	
			}
			
		} else {
			modelo.setValueAt(txtUsuario.getText(), fila, 0);
			modelo.setValueAt(txtContraseña.getText(), fila, 1);
			usuarios.cambioAdmin(false);
			res = miModelo.cambiarUsuario(txtUsuario.getText(), txtContraseña.getText());
		}
		
		if(res>0) {
			usuarios.getLblOk().setVisible(true);
		}else {
			usuarios.getLblNo().setVisible(true);
		}
		txtUsuario.setText(null);
		txtContraseña.setText(null);
	}

	/**
	 * Completo los campos de texto con los datos seleccionados en la tabla,
	 * compruebo si el seleccionado es Admin.
	 */

	public void completarCampos(JTable tabla, DefaultTableModel modelo, JTextField txtUsuario,
			JTextField txtContraseña) {
		int fila = tabla.getSelectedRow();
		String esAdmin = (String) modelo.getValueAt(fila, 0);
		if (esAdmin.equals(admin)) {
			cambioAdmin = true;
		} else {
			cambioAdmin = false;
		}
		String campoUser = (String) modelo.getValueAt(fila, 0);
		String campoPasswd = (String) modelo.getValueAt(fila, 1);
		txtUsuario.setText(campoUser);
		txtContraseña.setText(campoPasswd);
		miModelo.setUserViejo(campoUser); // Guardo los datos viejos para luego poder
											// actualizarlos en la query Update Set
		miModelo.setPasswdVieja(campoPasswd);
	}

	/**
	 * @return the configuracion
	 */
	public VistaConfiguracion getConfiguracion() {
		return configuracion;
	}

	/**
	 * @param configuracion the configuracion to set
	 */
	public void setConfiguracion(VistaConfiguracion configuracion) {
		this.configuracion = configuracion;
	}

	/**
	 * 
	 */
	public void rellenarCampos() {
		
		miModelo.cargarCampos();
		
	}

	/**
	 * @return the errorFatal
	 */
	public VistaNoConnection getErrorFatal() {
		return noConnection;
	}

	/**
	 * @param errorFatal the errorFatal to set
	 */
	public void setErrorFatal(VistaNoConnection errorFatal) {
		this.noConnection = errorFatal;
	}

	/**
	 * 
	 */
	public void guardar() {
		miModelo.guardar();
		
	}

	/**
	 * 
	 */
	public void mostrarNoConnection() {
		noConnection.setVisible(true);
		login.setVisible(false);
		usuarios.setVisible(false);
		error.setVisible(false);
		perfil.setVisible(false);
		registro.setVisible(false);
	}
	
	public void noConexion() {
		configuracion.getLblNoConnection().setVisible(true);
	}

	/**
	 * 
	 */
	/*public void mostrarErrorFatal() {
		VistaNoConnection noConnection =  new VistaNoConnection();
		noConnection.setVisible(true);
		login.setVisible(false);
	}
	*/

	
	
}
