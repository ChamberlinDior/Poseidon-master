package com.openclassroomsprojet.poseidon.controlllers;


import com.openclassroomsprojet.poseidon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Ce contrôleur gère les requêtes liées à la connexion, aux détails des articles et aux erreurs
 * (This controller handles requests related to login, article details, and errors)
 * @author chamberlin dior
 * @version 1.0
 */
@Controller // Indiquer que cette classe est un contrôleur géré par Spring
@RequestMapping("app") // Indiquer que ce contrôleur gère les requêtes commençant par "app"
public class LoginController {

    @Autowired // Injecter les dépendances automatiquement
    private UserRepository userRepository; // Déclarer le dépôt d'utilisateurs

    /**
     * Cette méthode renvoie la vue de la page de connexion
     * (This method returns the view of the login page)
     * @return Un objet contenant les données du modèle et la vue dans une seule valeur de retour
     * (Model data and view in a single return value)
     */
    @GetMapping("login") // Indiquer que cette méthode gère les requêtes GET à l'URL "app/login"
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView(); // Créer un objet ModelAndView
        mav.setViewName("login"); // Définir le nom de la vue "login"
        return mav; // Renvoyer l'objet ModelAndView
    }

    /**
     * Cette méthode renvoie la vue de la liste des articles des utilisateurs
     * (This method returns the view of the list of user articles)
     * @return Un objet contenant les données du modèle et la vue dans une seule valeur de retour
     * (Model data and view in a single return value)
     */
    @GetMapping("secure/article-details") // Indiquer que cette méthode gère les requêtes GET à l'URL "app/secure/article-details"
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView(); // Créer un objet ModelAndView
        mav.addObject("users", userRepository.findAll()); // Ajouter la liste des utilisateurs au modèle
        mav.setViewName("user/list"); // Définir le nom de la vue "user/list"
        return mav; // Renvoyer l'objet ModelAndView
    }

    /**
     * Cette méthode renvoie la vue de la page d'erreur 403
     * (This method returns the view of the 403 error page)
     * @return Un objet contenant les données du modèle et la vue dans une seule valeur de retour
     * (Model data and view in a single return value)
     */
    @GetMapping("error") // Indiquer que cette méthode gère les requêtes GET à l'URL "app/error"
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView(); // Créer un objet ModelAndView
        String errorMessage = "You are not authorized for the requested data."; // Définir le message d'erreur
        mav.addObject("errorMsg", errorMessage); // Ajouter le message d'erreur au modèle
        mav.setViewName("403"); // Définir le nom de la vue "403"
        return mav; // Renvoyer l'objet ModelAndView
    }
}
