package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Budget {
    int budget_id;
    int label_id;
    double amount;

    public Budget(int label_id, double amount) {
        this.label_id = label_id;
        this.amount = amount;
    }
    public Budget() {
    }

    public int getId_Label() {
        return label_id;
    }

    public void setId_Label(int label_id) {
        this.label_id = label_id;
    }

    public int getId_Budget() {
        return budget_id;
    }

    public void setId_Budget(int budget_id) {
        this.budget_id = budget_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void ajouterLigneCredit(int label_id, double amount)  throws SQLException {
        String query = "INSERT INTO budget (label_id, amount) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, label_id);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        }
    }
}
