package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Movements {
    int movement_id;
    int label_id;
    double amount;

    public Movements(int movement_id, double amount) {
        this.movement_id = movement_id;
        this.amount = amount;
    }

    public int getId_Movement() {
        return movement_id;
    }

    public void setId_Movement(int movement_id) {
        this.movement_id = movement_id;
    }

    public int getId_Label() {
        return label_id;
    }

    public void setId_Label(int label_id) {
        this.label_id = label_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void ajouterLigneDepense(int label_id, double amount)  throws SQLException {
        String query = "INSERT INTO movements label_id, amount) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, label_id);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        }
    }

    // public boolean checkSufficient() throws SQLException {
    //     String query = "";
    // }


}
