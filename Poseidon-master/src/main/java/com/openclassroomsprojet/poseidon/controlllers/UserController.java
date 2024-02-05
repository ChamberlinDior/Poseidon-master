package com.openclassroomsprojet.poseidon.controlllers;

import com.openclassroomsprojet.poseidon.domain.User;
import com.openclassroomsprojet.poseidon.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Ce contrôleur fournit les opérations CRUD sur l'entité utilisateur
 * This controller provides CRUD operations on the User entity
 *
 * A noter : Assurez-vous que le service IUserService est correctement injecté pour les opérations CRUD.
 * Note: Ensure that the IUserService is correctly injected for CRUD operations.
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * Cette méthode permet d'afficher tous les utilisateurs
     * This method allows displaying all the users
     *
     * @param model Un objet contenant les données à rendre dans la vue
     *              An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        // Ajouter la liste des utilisateurs à l'objet Model pour l'affichage dans la vue
        // Add the list of users to the Model object for display in the view
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }

    /**
     * Cette méthode permet d'accéder au formulaire de création d'un nouvel utilisateur
     * This method allows access to the form for creating a new user
     *
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le sauvegarde s'il n'y a pas d'erreur
     * This method checks the @Valid object and saves it if there is no error
     *
     * @param user          Objet qui doit être validé avant d'être enregistré
     *                      Object that must be validated before being saved
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier s'il y a des erreurs
     *                      Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         Un objet contenant les données à rendre dans la vue
     *                      An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult bindingResult, Model model) {
        // Vérifier s'il y a des erreurs de validation
        // Check if there are any validation errors
        if (!bindingResult.hasErrors()) {
            // Sauvegarder l'utilisateur et mettre à jour la liste des utilisateurs dans le Model
            // Save the user and update the list of users in the Model
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUsers());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Cette méthode permet d'accéder au formulaire de mise à jour d'un utilisateur existant
     * This method allows access to the form for updating an existing user
     *
     * @param id    L'identifiant de l'objet à afficher
     *              The identifier of the object to display
     * @param model Un objet contenant les données à rendre dans la vue
     *              An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Rechercher l'utilisateur par ID et le préparer pour la mise à jour
        // Search for the user by ID and prepare it for update
        Optional<User> optionalUser = userService.findUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Supprimer le mot de passe pour des raisons de sécurité lors de l'affichage dans le formulaire
            // Remove the password for security reasons when displaying in the form
            user.setPassword("");
            model.addAttribute("user", user);
        } else {
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
        return "user/update";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le met à jour s'il n'y a pas d'erreur
     * This method checks the @Valid object and updates it if there is no error
     *
     * @param id            L'identifiant de l'objet à vérifier et à mettre à jour
     *                      The identifier of the object to check and update
     * @param user          Objet qui doit être vérifié avant d'être mis à jour
     *                      Object that must be checked before being updated
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier s'il y a des erreurs
     *                      Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         Un objet contenant les données à rendre dans la vue
     *                      An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult bindingResult, Model model) {
        // Vérifier s'il y a des erreurs de validation
        // Check if there are any validation errors
        if (bindingResult.hasErrors()) {
            return "user/update";
        }
        // Mettre à jour l'utilisateur en utilisant le service et mettre à jour la liste des utilisateurs dans le Model
        // Update the user using the service and update the list of users in the Model
        user.setId(id);
        userService.saveUser(user);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
    }

/**
 * Cette méthode permet de supprimer un utilisateur existant
 * This method allows deleting an existing user
 *
 * @param id    L'identifiant de l'objet à supprimer
 *              The identifier of the object to delete
 * @param model Un objet contenant les données à rendre dans la vue
 *              An object
*/
 @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        Optional<User> optionalUser = userService.findUserById(id);
        if (optionalUser.isPresent()) {
            userService.deleteUserById(id);
            model.addAttribute("user", userService.findAllUsers());
        } else {
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
        return "redirect:/user/list";
    }
}