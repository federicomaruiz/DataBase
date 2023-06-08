package login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputFilter.Config;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.mysql.cj.xdevapi.Statement;

/**
 * 
 */

/**
 * @author federicoruiz 10 may 2023 12:36:23
 */
public class Modelo {

	private String bd;
	private String loginDB;
	private String pwd;
	private String url;
	private String driver;
	private Connection conexion;
	private String sqlTabla1;
	String querySelect = "SELECT * FROM users WHERE user=?";
	private String respuesta;
	String queryInsert = "INSERT INTO users (user,passwd) VALUES (?,?)";
	String queryUser = "SELECT * FROM users WHERE  user=? AND passwd=?";
	private String usr = "";
	boolean cambioAdmin = false;

	private VistaLogin login;
	private VistaRegistro registro;
	private VistaUsuarios usuarios;
	private VistaError error;
	private VistaPerfil perfil;
	private VistaConfiguracion configuracion;
	private VistaNoConnection noConnection;

	private final String FILE = "config.ini";
	private File miFichero;
	private Properties config;
	private InputStream entrada;
	private OutputStream salida;
	private String resp;
	private HashMap<String, String> entradaBd = new HashMap<>();
	String userViejo;
	String passwdVieja;

	private DefaultTableModel miTabla;
	boolean cambiarAdmin = false;
	final String passwd = "1234";
	final String admin = "admin";
	HashMap<String, String> miHashMap;
	protected String resultado;
	protected int errores = 0;
	protected String usuarioCambioContraseña; // Lo voy a utilizar para cuando vaya a la ventana Perfil saber a quien
												// cambiarle la passwd
	DefaultTableModel lista; // la voy a usar para ir agregando usuarios a lista

	public void cargarBd() {
		try {
			miFichero = new File(FILE);
			if (miFichero.exists()) {
				crearFichero();
				cargarCampos();
				Class.forName(driver); // nombre del driver
				conexion = DriverManager.getConnection(url, loginDB, pwd); // Hago la conexion
				System.out.println("-> Conexi�n con MySQL establecida");
				sqlTabla1 = "Select * from users";
				login.mostrarLogin();
				vaciarCampos();
			}else {
				crearFichero();
				configuracion.mostrarVentana();	
			}
		} catch (ClassNotFoundException e) {
			noConnection.error("Driver JDBC No encontrado");
			e.printStackTrace();
		} catch (SQLException e) {
			noConnection.error("Error al conectarse a la BD");

			e.printStackTrace();
		} catch (Exception e) {
			noConnection.error("Error general de Conexi�n");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void crearFichero() {
		config = new Properties();
		try {
			miFichero = new File(FILE);
			if (miFichero.exists()) {
				entrada = new FileInputStream(miFichero);
				config.load(entrada);
			} else {
				System.err.println("Fichero no encontrado");
				// System.exit(1);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void cargarCampos() {

		loginDB = config.getProperty("usr");
		pwd = config.getProperty("pwd");
		bd = config.getProperty("bd");
		url = config.getProperty("url");
		driver = config.getProperty("driver");
		

		configuracion.getTxtUsuario().setText(loginDB);
		configuracion.getTxtContraseña().setText(pwd);
		configuracion.getTextBD().setText(bd);
		configuracion.getTextURL().setText(url);
		configuracion.getTxtDriver().setText(driver);

	}

	public void guardar() {

		String usr = configuracion.getTxtUsuario().getText();
		String pwd = configuracion.getTxtContraseña().getText();
		String url = configuracion.getTextURL().getText();
		String bd = configuracion.getTextBD().getText();
		String driver = configuracion.getTxtDriver().getText();

		try {
			config.setProperty("usr", usr);
			config.setProperty("pwd", pwd);
			config.setProperty("url", url);
			config.setProperty("bd", bd);
			config.setProperty("driver", driver);
			salida = new FileOutputStream(FILE);
			config.store(salida, "Ultima operacion: Guardado");
			resp = "Guardado";
			vaciarCampos();
			configuracion.getLblOk().setVisible(true);

		} catch (IOException e) {
			e.printStackTrace();
			configuracion.getLblErr().setVisible(true);
		}
	}

	/**
	 * Cargo la tabla que tengo en la base de datos
	 */
	public void cargarTabla() {
		miTabla = new DefaultTableModel();
		int numColumnas = getNumColumnas(sqlTabla1);
		Object[] contenido = new Object[numColumnas];
		PreparedStatement pstmt;
		try {
			pstmt = conexion.prepareStatement(sqlTabla1);
			ResultSet rset = pstmt.executeQuery();
			java.sql.ResultSetMetaData rsmd = rset.getMetaData();
			for (int i = 0; i < numColumnas; i++) {
				miTabla.addColumn(rsmd.getColumnName(i + 1));
			}
			while (rset.next()) {
				for (int col = 1; col <= numColumnas; col++) {
					contenido[col - 1] = rset.getString(col);
				}
				miTabla.addRow(contenido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		usuarios.actualizoLista(miTabla);
	}

	private int getNumColumnas(String sql) {
		int num = 0;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			java.sql.ResultSetMetaData rsmd = rset.getMetaData();
			num = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	public DefaultTableModel getTabla() {
		return miTabla;
	}

	/**
	 * @param user
	 * @param contraseña En este metodo voy a comprobar si dentro de mi HashMap ya
	 *                   esta registrado el usuario Si esta registrado y no es el
	 *                   admin, devuelve "usuario" y almaceno en la var
	 *                   cambioContraseña user (el valor para si luego quiere
	 *                   modificar la contraseña en la vista perfil se a quien se la
	 *                   voy a cambiar) Si es el admin devuelvo "admin" Sino
	 *                   devuelvo Error
	 */
	public String clickLogin(String user, String contraseña) {
		consultaLogin(queryUser, user, contraseña);
		if (usr.equals(admin)) {
			resultado = admin;
			errores = 0;
			usr = "";
			return resultado;
		} else if (!usr.equals("")) {
			resultado = "usuario";
			usuarioCambioContraseña = user;
			errores = 0;
			usr = "";
			return resultado;
		} else {
			errores++;
			error.setError(errores);
			resultado = "error";
			return resultado;
		}
	}

	/**
	 * @param user
	 * @param passwd Añado el nuevo usuario a mi lista. Añado el usuario a mi lista,
	 *               llamo al metodo pintarUsuarios para pintar el usuario en la
	 *               tabla, errores a 0.
	 */

	public void añadirUsuario(JTable tabla, DefaultTableModel modelo, String user, String passwd) {
		consultaExisteUsuario(querySelect, user);
		int resultado = 0;
		if (usr.equals("")) {
			resultado = insertarUsuario(user, passwd);
			pintarTabla(user, passwd);
			usuarios.userExiste(false);
			errores = 0;
		} else {
			usuarios.userExiste(true);
		}

		if (resultado > 0) {
			usuarios.getLblOk().setVisible(true);
		} else {
			usuarios.getLblNo().setVisible(true);
		}

	}

	/**
	 * @param user
	 * @param passwd2
	 */
	private void pintarTabla(String user, String passwd2) {
		miTabla.addRow(new String[] { user, passwd2 });
	}

	/**
	 * @param passwd Modifico la contrsaeña al usuario indicado que lo tengo
	 *               guardado en usuarioCambioContraseña luego llamo al metodo
	 *               cargarTabla para actualizar los datos
	 */
	public void modificarContraseña(String passwd) {
		int resultado;
		resultado = updateContraseña(passwd, usuarioCambioContraseña);
		System.out.println(resultado);
		if (resultado > 0) {
			login.getLblOk().setVisible(true);
		}
	}

	/**
	 * @param user
	 * @param passwd
	 */
	public void borrarUsuario(String user, String passwd) {
		int resultado;
		resultado = eliminarUsuario(user);
		if (resultado > 0) {
			usuarios.getLblOk().setVisible(true);
		} else {
			usuarios.getLblNo().setVisible(true);
		}
	}

	/**
	 * @param user
	 * @param passwd Modifico el usuario si el que se esta modificando es el Admin,
	 *               solo dejo que modifique la contraseña le voy a volver a poner
	 *               el nombre de Admin y le aviso con un lbl.
	 */
	public int cambiarUsuario(String user, String passwd) {
		int resultado = 0;
		try {
			String query = "UPDATE users SET user = ?,passwd = ? WHERE user='" + userViejo + "' AND passwd='"
					+ passwdVieja + "'";
			PreparedStatement pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, user);
			pstmt.setString(2, passwd);
			resultado = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return resultado;

	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(VistaLogin login) {
		this.login = login;
	}

	/**
	 * @param registro the registro to se
	 */
	public void setRegistro(VistaRegistro registro) {
		this.registro = registro;
	}

	/**
	 * @param usuarios the usuarios to set
	 */
	public void setUsuarios(VistaUsuarios usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(VistaError error) {
		this.error = error;
	}

	/**
	 * @param perfil2
	 */
	public void setPerfil(VistaPerfil perfil2) {
		this.perfil = perfil2;

	}

	/**
	 * @param user
	 * @param passwd2
	 * @param lblUserExiste Luego de llamar al metodo consultaSelect , puedo saber
	 *                      si el usuario existe o no, Sino existe lo agrego , si
	 *                      existe muestro cartel.
	 */
	public void comprobarUsuario(String user, String passwd, JLabel lblUserExiste) {
		consultaSelectUser(querySelect, user, 1);
		if (respuesta == null) {
			insertarUsuario(user, passwd);
			lblUserExiste.setVisible(false);
			respuesta = null;
			login.mostrarVista();
			login.mostrarEtiqueta();
		} else {
			lblUserExiste.setVisible(true);
			respuesta = null;
		}
	}

	/*
	 * Consulta SQL compruebo en mi BD si el usuario existe, le doy el valor del
	 * nombre a la variable resultado
	 * 
	 */

	public void consultaSelectUser(String query, String user, int columna) {
		try {
			PreparedStatement pstmt = conexion.prepareStatement(query); // Creo la sentencia de tipo prepare y le paso
																		// la query
			pstmt.setString(1, user); // Le paso el nombre del usuario
			ResultSet rset = pstmt.executeQuery();// Guardo el resultado en la variable rset
			while (rset.next())
				setRespuesta((rset.getString(columna))); // Le doy valor a la variable resultado con el nombre sino lo
															// encuentra null
			rset.close();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}

	}

	public int insertarUsuario(String usr, String pwd) {
		int resultado = 0;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(queryInsert);
			pstmt.setString(1, usr);
			pstmt.setString(2, pwd);
			resultado = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return resultado;
	}

	/*
	 * */

	public void consultaLogin(String query, String user, String passwd) {
		try {
			PreparedStatement pstmt = conexion.prepareStatement(query); // Creo la sentencia de tipo prepare y le paso
																		// la query
			pstmt.setString(1, user);
			pstmt.setString(2, passwd);
			ResultSet rset = pstmt.executeQuery(); // Guardo el resultado en la variable rset
			while (rset.next())
				usr = rset.getString(1);
			rset.close();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}

	}

	public int updateContraseña(String pwd, String usuarioCambioContraseña) {
		int resultado = 0;
		try {
			String query = "UPDATE users SET passwd = ? WHERE user='" + usuarioCambioContraseña + "'";
			PreparedStatement pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, pwd);
			resultado = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return resultado;
	}

	public void consultaExisteUsuario(String query, String user) {
		try {
			PreparedStatement pstmt = conexion.prepareStatement(query); // Creo la sentencia de tipo prepare y le paso
																		// la query
			pstmt.setString(1, user);
			ResultSet rset = pstmt.executeQuery(); // Guardo el resultado en la variable rset
			while (rset.next())
				usr = rset.getString(1);
			rset.close();
			pstmt.close();
		} catch (SQLException s) {
			s.printStackTrace();
		}
	}

	public int eliminarUsuario(String user) {
		int resultado = 0;
		try {
			String query = "DELETE FROM users WHERE user=? ";
			PreparedStatement pstmt = conexion.prepareStatement(query);
			pstmt.setString(1, user);
			resultado = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return resultado;
	}

	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	/**
	 * @return the miTabla
	 */
	public DefaultTableModel getMiTabla() {
		return miTabla;
	}

	/**
	 * @param miTabla the miTabla to set
	 */
	public void setMiTabla(DefaultTableModel miTabla) {
		this.miTabla = miTabla;
	}

	/**
	 * @return the userViejo
	 */
	public String getUserViejo() {
		return userViejo;
	}

	/**
	 * @param userViejo the userViejo to set
	 */
	public void setUserViejo(String userViejo) {
		this.userViejo = userViejo;
	}

	/**
	 * @return the passwdVieja
	 */
	public String getPasswdVieja() {
		return passwdVieja;
	}

	/**
	 * @param passwdVieja the passwdVieja to set
	 */
	public void setPasswdVieja(String passwdVieja) {
		this.passwdVieja = passwdVieja;
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
	 * @return the db
	 */
	public String getDb() {
		return bd;
	}

	/**
	 * @return the loginDB
	 */
	public String getLoginDB() {
		return loginDB;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	public void vaciarCampos() {

		configuracion.getTxtUsuario().setText("");
		configuracion.getTxtContraseña().setText("");
		configuracion.getTextBD().setText("");
		configuracion.getTextURL().setText("");
		configuracion.getTxtDriver().setText("");

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

}
