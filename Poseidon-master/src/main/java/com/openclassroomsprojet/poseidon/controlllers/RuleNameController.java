package com.openclassroomsprojet.poseidon.controlllers;

import com.openclassroomsprojet.poseidon.domain.RuleName;
import com.openclassroomsprojet.poseidon.service.IRuleNameService;
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
 * Ce contrôleur fournit les opérations CRUD sur l'entité RuleName
 * @author chamberlin dior
 * @version 1.0
 */

@Controller
public class RuleNameController {

    @Autowired  // Injecter les dépendances automatiquement
    private IRuleNameService ruleNameService;// Déclarer le service de RuleName

    /**
     * Cette méthode permet d'afficher tous les RuleNames
     * @param model Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        // Ajouter la liste des RuleNames au modèle
        model.addAttribute("ruleName", ruleNameService.findAllRuleName());
        return "ruleName/list";
    }

    /**
     * Cette méthode permet d'accéder au formulaire pour créer un nouveau RuleName
     * @return Un chemin de chaîne de la vue demandée
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }


    /**
     * Cette méthode vérifie l'objet @Valid et le sauvegarde s'il n'y a pas d'erreur
     * @param ruleName      Objet qui doit être validé avant d'être sauvegardé
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier si des erreurs sont survenues
     * @param model         Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée

     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            return "redirect:/ruleName/list";
        }
        return "ruleName/add";
    }

    /**
     * Cette méthode permet d'accéder au formulaire pour modifier un RuleName existant
     * (This method allows access to the form for update an existing RuleName)
     *
     * @param id    L'identifiant de l'objet à afficher
     * @param model Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Chercher le RuleName par son identifiant
        Optional<RuleName> ruleName = ruleNameService.findRuleNameById(id);
        // Ajouter le RuleName au modèle ou lancer une exception si il n'existe pas
        model.addAttribute("ruleName", ruleName.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id)));
        return "ruleName/update";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le met à jour s'il n'y a pas d'erreur
     * @param id            L'identifiant de l'objet à vérifier et à mettre à jour
     * @param ruleName      Objet qui doit être vérifié avant d'être mis à jour
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier si des erreurs sont survenues
     * @param model         Un objet qui contient les données pour le rendu dans la vue
     * @return Un chemin de chaîne de la vue demandée
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            ruleNameService.saveRuleName(ruleName);
            return "redirect:/ruleName/list";
        }
        ruleName.setId(id);
        return "ruleName/update";
    }

    /**
     * This method allows to delete an existing ruleName
     *
     * @param id    The identifier of the object to delete
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the view to which the user is redirected
     */
    @GetMapping("/ruleName/delete/{id}")// Indiquer que cette méthode gère les requêtes GET à l'URL "/ruleName/delete/{id}"
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> ruleName = ruleNameService.findRuleNameById(id);
        if (ruleName.isPresent()) {
            ruleNameService.deleteRuleNameById(id);
        } else {
            throw new IllegalArgumentException("RuleName id not found");
        }
        return "redirect:/ruleName/list";// Rediriger vers la liste des RuleNames
    }
}