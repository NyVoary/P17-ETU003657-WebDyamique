package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/budget_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @SuppressWarnings("CallToPrintStackTrace")
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données établie avec succès !");
            return connection;

        } catch (ClassNotFoundException e) {
            System.err.println("Erreur: Impossible de charger le pilote JDBC MySQL");
            e.printStackTrace();
            throw new SQLException("Pilote JDBC non trouvé", e);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données");
            e.printStackTrace();
            throw e;
        }
    }
}