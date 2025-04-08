package servlets;

import java.io.IOException;
import java.sql.SQLException;

import entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    // Méthode pour afficher le formulaire de connexion (GET)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = new User();

        try {
            // Récupérer l'utilisateur par defaut de la base de données
            User defaultUser = user.getDefaultUser();
    
            // Vérifier si un utilisateur a été trouvé
            if (defaultUser != null) {
                request.setAttribute("defaultUsername", defaultUser.getUsername());
                request.setAttribute("defaultPassword", defaultUser.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Afficher l'erreur dans la console (utile pour le debug)
            request.setAttribute("errorMessage", "Erreur lors de la récupération de l'utilisateur.");
        }

        // Afficher le formulaire de connexion (par exemple, en redirigeant vers login.jsp)
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    // Méthode pour traiter la soumission du formulaire de connexion (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        // Récupérer les paramètres du formulaire de connexion
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = null; // Initialisation de l'objet user

        try {
            // Vérifier les informations de connexion
            user = User.checkUserCredentials(username, password); // Appel correct de la méthode statique
        } catch (SQLException e) {
            // Gérer l'exception (par exemple, loguer l'erreur et rediriger l'utilisateur)
            e.printStackTrace(); // Affiche l'exception dans la console (vous pouvez aussi loguer l'erreur)
            request.setAttribute("errorMessage", "Une erreur est survenue lors de la vérification des informations. Veuillez réessayer.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return; // Sortir de la méthode après avoir géré l'exception
        }

        if (user != null) { // Vérifier si l'utilisateur est trouvé
            try {
                int user_id = user.getId_User();

                // Stocker les informations dans la session
                HttpSession session = request.getSession();
                session.setAttribute("user_id", user_id);

                // Connexion réussie : rediriger vers la page de budget
                response.sendRedirect(request.getContextPath() + "/budget");  // Utiliser sendRedirect pour rediriger vers une autre page
            } catch (Exception e) {
                // Gérer l'exception si elle est lancée lors de l'appel à getDepartement()
                e.printStackTrace(); // Afficher l'exception dans la console
                request.setAttribute("errorMessage", "Erreur lors de la récupération de l'Id. Veuillez réessayer." + e.getMessage());
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            // Connexion échouée : renvoyer un message d'erreur et rediriger vers la page de login
            request.setAttribute("errorMessage", "Nom ou mot de passe incorrect. Veuillez réessayer.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
