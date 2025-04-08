package servlets;

import java.io.IOException;
import entities.Movements;
import entities.Labels;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class MovementsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String label = request.getParameter("label");
        String amountStr = request.getParameter("amount");

        try {
            Labels labels = new Labels();
            int label_id = labels.findId(label);

            if (label_id == -1) {
                labels.setName(label);
                label_id = labels.insert();
            }

            double amount = Double.parseDouble(amountStr);

            Movements depense = new Movements(label_id, amount);
            depense.ajouterLigneDepense(label_id, amount); // Exemple méthode

            request.setAttribute("successMessage", "Dépense ajoutée !");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur : " + e.getMessage());
        }

        request.getRequestDispatcher("movements.jsp").forward(request, response);
    }
}