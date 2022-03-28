
package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	public static Connection getConnection() { //static perche solo qui facciamo connessioni
		
		try {
			String url="jdbc:mysql://localhost/iscritticorsi?user=root&password=edo000";
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.err.println("Errore di connessione");
			e.printStackTrace();
			return null;
		}
	}

}
