package servlets;

import java.io.IOException;
import entities.Budget;
import entities.Labels;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BudgetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Affichage du formulaire
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("budget.jsp").forward(request, response);
    }

    // Traitement du formulaire
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String label = request.getParameter("label");
        String amountStr = request.getParameter("amount");

        try {
            if (label == null || label.isEmpty() || amountStr == null || amountStr.isEmpty()) {
                throw new Exception("Le label et le montant ne doivent pas être vides.");
            }

            // Vérifie si le label existe déjà
            Labels labels = new Labels();
            int label_id = labels.findId(label);

            // Si non, on insère le label
            if (label_id == -1) {
                labels.setName(label);
                label_id = labels.insert();
            }

            double amount = Double.parseDouble(amountStr);

            Budget budget = new Budget(label_id, amount);
            budget.ajouterLigneCredit(label_id, amount);

            request.setAttribute("successMessage", "Ajout effectué avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
        }

        // Toujours rediriger vers budget.jsp à la fin
        request.getRequestDispatcher("budget.jsp").forward(request, response);
    }
}