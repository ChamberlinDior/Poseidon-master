package com.openclassroomsprojet.poseidon.controlllers;

import com.openclassroomsprojet.poseidon.domain.Rating;
import com.openclassroomsprojet.poseidon.service.IRatingService;
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
 * Ce contrôleur fournit les opérations CRUD sur l'entité Rating
 * @author chamberlin dior
 * @version 1.0
 */

@Controller
public class RatingController {

    @Autowired
    private IRatingService ratingService;


    /**
     * Cette méthode permet d'afficher tous les ratings
     * @param model Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
       // Ajouter la liste des ratings au modèle
        model.addAttribute("rating", ratingService.findAllRating());
        return "rating/list";// Renvoyer le nom de la vue "rating/list"
    }

    /**
     * Cette méthode permet d'accéder au formulaire pour créer un nouveau rating
     * (This method allows access to the form for creating a new rating)
     *
     * @return Un chemin de chaîne de la vue demandée
     * (A string path of the requested view)
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le sauvegarde s'il n'y a pas d'erreur
     * @param rating        Objet qui doit être validé avant d'être sauvegardé
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier si des erreurs sont survenues
     * @param model         Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            ratingService.saveRating(rating);
            return "redirect:/rating/list";
        }
        return "rating/add";
    }

    /**
     * Cette méthode permet d'accéder au formulaire pour modifier un rating existant
     * @param id    L'identifiant de l'objet à afficher
     * @param model Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> rating = ratingService.findRatingById(id);
        model.addAttribute("rating", rating.orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id)));
        return "rating/update";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le met à jour s'il n'y a pas d'erreur
     * @param id            L'identifiant de l'objet à vérifier et à mettre à jour
     * @param rating        Objet qui doit être vérifié avant d'être mis à jour
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier si des erreurs sont survenues
     * @param model         Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            ratingService.saveRating(rating);
            return "redirect:/rating/list";
        }
        rating.setId(id);
        return "rating/update";
    }


    /**
     * Cette méthode permet de supprimer un rating existant
     * @param id    L'identifiant de l'objet à supprimer
     * @param model Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> rating = ratingService.findRatingById(id);
        if (rating.isPresent()) {
            ratingService.deleteRatingById(id);
        } else {
            throw new IllegalArgumentException("Rating id not found");
        }
        return "redirect:/rating/list";
    }
}