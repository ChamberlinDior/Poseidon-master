// Déclarer le package du projet
package com.openclassroomsprojet.poseidon.controlllers;

// Importer les classes nécessaires
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Ce contrôleur fournit le chemin vers la page d'accueil
 * (This controller provides the path to the home page)
 *
 * @author chamberlin dior
 * @version 1.0
 */
@Controller // Indiquer que cette classe est un contrôleur géré par Spring
public class HomeController {

    /**
     * cette méthode fournit la page d'accueil
     * (this method provides the home page)
     *
     * @param model Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     * (An object that contain the data for rendering into the view
     * A string path of the requested view)
     */
    @RequestMapping("/") // Indiquer que cette méthode gère les requêtes HTTP à l'URL racine
    public String home(Model model) {
        return "home"; // Renvoyer le nom de la vue "home"
    }
}
