package com.openclassroomsprojet.poseidon.controlllers;

import com.openclassroomsprojet.poseidon.domain.BidList;
import com.openclassroomsprojet.poseidon.service.IBidListService;
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
 * Ce contrôleur fournit des opérations CRUD sur l'entité BidList
 *
 * @author chamberlin dior ebaley
 * @version 1.0
 */
@Controller
public class BidListController {

    @Autowired
    private IBidListService bidListService;


    /**
     * Cette méthode permet d'afficher tous les bids
     *
     * @param model Un objet qui contient les données à rendre dans la vue
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // On ajoute à l'objet model la liste de tous les bids obtenus par le service
        model.addAttribute("bidList", bidListService.findAllBidList());
        return "bidList/list";
    }


    /**
     * Cette méthode permet d'accéder au formulaire pour créer un nouveau bid
     *
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        return "bidList/add";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le sauvegarde s'il n'y a pas d'erreur
     *
     * @param bidList       Objet qui doit être validé avant d'être sauvegardé
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier s'il y a des erreurs
     * @param model         Un objet qui contient les données à rendre dans la vue
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            bidListService.saveBidList(bidList);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    /**
     * Cette méthode permet d'accéder au formulaire pour modifier un bid existant
     *
     * @param id    L'identifiant de l'objet à afficher
     * @param model Un objet qui contient les données à rendre dans la vue
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> bidList = bidListService.findBidListById(id);
        if (bidList.isPresent()) {
            BidList test = bidList.get();
            model.addAttribute("bidList", test);
        } else {
            throw new IllegalArgumentException("BidList id not found(id=" + id + ")");
        }
        return "bidList/update";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le met à jour s'il n'y a pas d'erreur
     *
     * @param id            L'identifiant de l'objet à vérifier et à mettre à jour
     * @param bidList       Objet qui doit être vérifié avant d'être mis à jour
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier s'il y a des erreurs
     * @param model         Un objet qui contient les données à rendre dans la vue
     * @return Une chaîne de caractères correspondant au chemin de la vue demandée
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            bidListService.saveBidList(bidList);
            return "redirect:/bidList/list";
        }
        bidList.setBidListId(id);
        // On retourne le nom de la vue qui affiche le formulaire de modification d'un bid
        return "bidList/update";
    }

    /**
     * Cette méthode permet de supprimer un bid existant
     *
     * @param id    L'identifiant de l'objet à supprimer
     * @param model Un objet qui contient les données à rendre dans la vue
     * @return Une chaîne de caractères correspondant au chemin de la vue vers laquelle l'utilisateur est redirigé
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        Optional<BidList> bidList = bidListService.findBidListById(id);

        if (bidList.isPresent()) {

            bidListService.deleteBidListById(id);
        } else {
            throw new IllegalArgumentException("BidList id not found(id=" + id + ")");
        }

        return "redirect:/bidList/list";
    }
}