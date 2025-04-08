package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class User {
    int user_id;
    String username;
    String password;

    public User(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }
    
    public User() {
    }

    public int getId_User() {
        return user_id;
    }

    public void setId_User(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Méthode pour récupérer l'utilisateur par defaut de la base de données
    public static User getDefaultUser() throws SQLException {
        String query = "SELECT user_id, username, password FROM user LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
                return user;
            }
        }
        return null; // Retourne null si aucun utilisateur n'est trouvé
    }

    // Vérification des informations de connexion
    public static User checkUserCredentials(String username, String password) throws SQLException {
        String query = "SELECT user_id, username, password FROM user WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
                    return user;
                }
            }
        }
        return null; // Retourne null si l'utilisateur n'est pas trouvé
    }
}
