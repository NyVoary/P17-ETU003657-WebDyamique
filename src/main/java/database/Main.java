// Classe Main pour tester la connecion avec la base de donnees

package database;

import java.sql.Connection;
import java.sql.SQLException;

import entities.User;

public class Main {
    public static void main(String[] args) {
        try {
            // Test de la connexion
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                System.out.println("✅ Connexion réussie !");

                User user = new User();

                try {
                    // Récupérer l'utilisateur par defaut de la base de données
                    User defaultUser = user.getDefaultUser();
                
                    // Vérifier si un utilisateur a été trouvé
                    if (defaultUser != null) {
                        System.out.println("METY");
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); // Afficher l'erreur dans la console (utile pour le debug)
                    System.out.println("TSY METY");
                }

                conn.close();
            } else {
                System.out.println("❌ Erreur de connexion !");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
}
