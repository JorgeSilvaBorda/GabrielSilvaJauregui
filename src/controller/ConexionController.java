package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionController {

	//Datos para la conexión a MySql
	private static final String SERVER = "localhost"; //Servidor de BD.
	private static final int PORT = 3306; //Puerto.
	private static final String DB = "ventas"; //Nombre de la base de datos.
	private static final String USER = "root"; //Usuario de la base de datos.
	private static final String PASSWORD = "password"; //Password del usuario de la base de datos.
	
	//String de conexión para llegar a la base de datos.
	private static final String URL = "jdbc:mysql://" + SERVER + ":" + Integer.toString(PORT) + "/" + DB;
	//private static final String URL = "jdbc:mysql://" + SERVER + ":" + Integer.toString(PORT) + "/" + DB + "?allowPublicKeyRetrieval=true&useSSL=false";
	
	//Nombre de clase del driver de conexión a Base de datos.
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static Connection con; //Este es el objeto de conexión que se usa para realizar querys.

	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD); //Se instancia un nuevo objeto para conectar a la base de datos con la info de conexión.
		} catch (Exception ex) {
			System.out.println("No se pudo conectar a la base de datos");
			System.out.println(ex);
			ex.printStackTrace();
		}

		return con; //Se devuelve el objeto de conexión a base de datos
	}
}
