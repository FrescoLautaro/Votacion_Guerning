

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/elecciones"; // el nombre de la base de datos y el puerto se mantienen
    private static final String DB_USER = "root";  // Por defecto el nombre es root
    private static final String DB_PASS = "";  //rellenar con tu contraseña

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
