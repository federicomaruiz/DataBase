package login;

/**
 * 
 */

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author federicoruiz 10 may 2023 12:36:56
 */
public class Principal {

	public static void main(String[] args) {

		/*
		 * Creo en mi clase principal un modelo, un controlador y las cinco vistas que
		 * voy a usar
		 */

		
		VistaLogin login = new VistaLogin();
		VistaUsuarios usuarios = new VistaUsuarios();
		VistaRegistro registro = new VistaRegistro();
		VistaError error = new VistaError();
		VistaPerfil perfil = new VistaPerfil();
		VistaConfiguracion configuracion = new VistaConfiguracion();
		VistaNoConnection noConnection =  new VistaNoConnection();
		Controlador miControlador = new Controlador();
		Modelo miModelo = new Modelo();
		
		
		/* Creo un array de la interfaz Vistas añado todas las vistas y luego con los metodos que implenta de la interfaz
		 * hago set controlador y modelo.
		 * */
		
		ArrayList<Vistas> vistas = new ArrayList<>();
		vistas.add(login);
		vistas.add(usuarios);
		vistas.add(registro);
		vistas.add(error);
		vistas.add(perfil);
		vistas.add(configuracion);
		vistas.add(noConnection);
		
		for (Vistas vista : vistas) {
			vista.setControlador(miControlador);
			vista.setModelo(miModelo);
		}
		/*
		 * Mediante los metodos Setters hago que el controlador "conozca" todas las
		 * vistas y al modelo. Hago lo mismo para que las vistas conozca al controlador
		 * y al modelo Luego para que el modelo conozca las vistas.
		 */
		miControlador.setModelo(miModelo);
		miControlador.setError(error);
		miControlador.setLogin(login);
		miControlador.setUsuarios(usuarios);
		miControlador.setRegistro(registro);
		miControlador.setPerfil(perfil);
		miControlador.setConfiguracion(configuracion);
		miControlador.setErrorFatal(noConnection);
		
		miModelo.setError(error);
		miModelo.setLogin(login);
		miModelo.setRegistro(registro);
		miModelo.setUsuarios(usuarios);
		miModelo.setPerfil(perfil);
		miModelo.setConfiguracion(configuracion);
		miModelo.setErrorFatal(noConnection);
		miModelo.cargarBd();
		
		
	}
}
