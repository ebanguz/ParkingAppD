package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private static Connection con = null;

	public static Connection getConnection1() {
		try {
			if (con == null) {

				String driver = "com.mysql.cj.jdbc.Driver";
				String base = "ITLA";
				String url = "jdbc:mysql://localhost:3306/" + base;
				String user = "root";
				String password = "";
				Class.forName(driver);
				con = (Connection) DriverManager.getConnection(url, user, password);

			}

		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		return con;
	}

}
