package login;
/**
 * 
 */

/**
 * @author federicoruiz
 * 10 may 2023 12:36:56
 */
public class Principal {

	public static void main(String[] args) {
		
		/* Creo en mi clase principal un modelo, un controlador y las
		 *  cinco vistas que voy a usar */
		
		Controlador miControlador =  new Controlador();
		Modelo miModelo = new Modelo();
		VistaLogin login = new VistaLogin();
		VistaUsuarios usuarios = new VistaUsuarios();
		VistaRegistro registro = new VistaRegistro();
		VistaError error = new VistaError();
		VistaPerfil perfil = new VistaPerfil();
		
		/* Mediante los metodos Setters hago que el controlador "conozca" todas las vistas y al modelo.
		 * Hago lo mismo para que las vistas conozca al controlador y al modelo
		 * Luego para que el modelo conozca las vistas.
		 * */
		miControlador.setModelo(miModelo);
		miControlador.setError(error);
		miControlador.setLogin(login);
		miControlador.setUsuarios(usuarios);
		miControlador.setRegistro(registro);
		miControlador.setPerfil(perfil);
		login.setControlador(miControlador);
		login.setModelo(miModelo);
		usuarios.setControlador(miControlador);
		usuarios.setModelo(miModelo);
		registro.setControlador(miControlador);
		registro.setModelo(miModelo);
		error.setControlador(miControlador);
		error.setModelo(miModelo);
		perfil.setMiControlador(miControlador);
		perfil.setMiModelo(miModelo);
		miModelo.setError(error);
		miModelo.setLogin(login);
		miModelo.setRegistro(registro);
		miModelo.setUsuarios(usuarios);
		miModelo.setPerfil(perfil);
		login.setVisible(true); // Ventana de inicio la de login
	
	}
}
