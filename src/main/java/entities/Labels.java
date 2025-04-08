package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Labels {
    int label_id;
    String name;

    public Labels(int label_id, String name) {
        this.label_id = label_id;
        this.name = name;
    }

    public Labels() {
    }

    public int getId_Label() {
        return label_id;
    }

    public void setId_Label(int label_id) {
        this.label_id = label_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int findId(String name) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT label_id FROM labels WHERE name = ?";
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(name);
            }
            return -1;
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la récupération du label: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }
    }
                
    // Méthode utilitaire pour fermer les ressources
    private static void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture du ResultSet: " + e.getMessage());
        }
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture du PreparedStatement: " + e.getMessage());
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la fermeture de la connexion: " + e.getMessage());
        }
    }

    public int insert() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "INSERT INTO labels (name) VALUES (?)";
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, this.name);
            stmt.executeUpdate();
    
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.label_id = rs.getInt(1);
                return this.label_id;
            }
            throw new Exception("Label créé mais ID non récupéré.");
        } catch (SQLException e) {
            throw new Exception("Erreur lors de l'insertion du label: " + e.getMessage());
        } finally {
            closeResources(rs, stmt, conn);
        }
    }
}
