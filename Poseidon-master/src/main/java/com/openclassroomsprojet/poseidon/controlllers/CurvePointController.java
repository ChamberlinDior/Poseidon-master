package com.openclassroomsprojet.poseidon.controlllers;

import com.openclassroomsprojet.poseidon.domain.CurvePoint;
import com.openclassroomsprojet.poseidon.service.ICurvePointService;
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
 * Ce contrôleur fournit des opérations CRUD sur l'entité CurvePoint
 *
 * @author chamberlin dior
 * @version 1.0
 */

@Controller
public class CurvePointController {

    @Autowired
    private ICurvePointService curvePointService;
    /**
     * Cette méthode permet d'afficher tous les points de courbe
     *
     * @param model Un objet qui contient les données à rendre dans la vue
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        // On ajoute à l'objet model la liste de tous les points de courbe obtenus par le service
        model.addAttribute("curvePoint", curvePointService.findAllCurvePoint());
        // On retourne le nom de la vue qui affiche la liste des points de courbe
        return "curvePoint/list";
    }

    /**
     * Cette méthode permet d'accéder au formulaire pour créer un nouveau point de courbe
     *
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        // On retourne le nom de la vue qui affiche le formulaire d'ajout
        // d'un point de courbe
        return "curvePoint/add";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le sauvegarde s'il n'y a pas d'erreur
     *
     * @param curvePoint    Objet qui doit être validé avant d'être sauvegardé
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier s'il y a des erreurs
     * @param model         Un objet qui contient les données à rendre dans la vue
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult bindingResult, Model model) {
        // Si il n'y a pas d'erreurs de validation
        if (!bindingResult.hasErrors()) {
            // On sauvegarde le point de courbe par le service
            curvePointService.saveCurvePoint(curvePoint);
            // On redirige vers la liste des points de courbe
            return "redirect:/curvePoint/list";
        }
        // Sinon, on retourne le formulaire d'ajout avec les erreurs
        return "curvePoint/add";
    }

    /**
     * Cette méthode permet d'accéder au formulaire pour modifier un point de courbe existant
     *
     * @param id    L'identifiant de l'objet à afficher
     * @param model Un objet qui contient les données à rendre dans la vue
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> curvePoint = curvePointService.findCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint.orElseThrow(() -> new IllegalArgumentException("Invalid CursePoint Id:" + id)));
        return "curvePoint/update";
    }

    /**
     * This method check the @Valid object and update it if there is no error
     *
     * @param id            The identifier of the object to check and update
     * @param curvePoint    Object that must be checked before being updated
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            return "redirect:/curvePoint/list";
        }
        curvePoint.setId(id);
        return "curvePoint/update";
    }

    /**
     * This method allows to delete an existing curvePoint
     *
     * @param id    The identifier of the object to delete
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the view to which the user is redirected
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> curvePoint = curvePointService.findCurvePointById(id);
        if (curvePoint.isPresent()) {
            curvePointService.deleteCurvePointById(id);
        } else {
            throw new IllegalArgumentException("CurvePoint id not found");
        }
        return "redirect:/curvePoint/list";
    }
}