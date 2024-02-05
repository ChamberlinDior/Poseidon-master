package com.openclassroomsprojet.poseidon.controlllers;

import com.openclassroomsprojet.poseidon.domain.Trade;
import com.openclassroomsprojet.poseidon.service.ITradeService;
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
 * Ce contrôleur fournit les opérations CRUD sur l'entité Trade
 * This controller provides CRUD operations on the Trade entity
 *
 * @author chamberlin dior
 * @version 1.0
 */

@Controller
public class TradeController {

    @Autowired
    private ITradeService tradeService;

    /**
     * Cette méthode permet d'afficher tous les trades
     * This method allows displaying all the trades
     *
     * @param model Un objet contenant les données à rendre dans la vue
     *              An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trade", tradeService.findAllTrade());
        return "trade/list";
    }

    /**
     * Cette méthode permet d'accéder au formulaire de création d'un nouveau trade
     * This method allows access to the form for creating a new trade
     *
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le sauvegarde s'il n'y a pas d'erreur
     * This method checks the @Valid object and saves it if there is no error
     *
     * @param trade         Objet qui doit être validé avant d'être enregistré
     *                      Object that must be validated before being saved
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier s'il y a des erreurs
     *                      Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         Un objet contenant les données à rendre dans la vue
     *                      An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            tradeService.saveTrade(trade);
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    /**
     * Cette méthode permet d'accéder au formulaire de mise à jour d'un trade existant
     * This method allows access to the form for updating an existing trade
     *
     * @param id    L'identifiant de l'objet à afficher
     *              The identifier of the object to display
     * @param model Un objet contenant les données à rendre dans la vue
     *              An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> trade = tradeService.findTradeById(id);
        model.addAttribute("trade", trade.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id)));
        return "trade/update";
    }

    /**
     * Cette méthode vérifie l'objet @Valid et le met à jour s'il n'y a pas d'erreur
     * This method checks the @Valid object and updates it if there is no error
     *
     * @param id            L'identifiant de l'objet à vérifier et à mettre à jour
     *                      The identifier of the object to check and update
     * @param trade         Objet qui doit être vérifié avant d'être mis à jour
     *                      Object that must be checked before being updated
     * @param bindingResult Contient le résultat de la validation de l'objet @Valid, on peut vérifier s'il y a des erreurs
     *                      Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         Un objet contenant les données à rendre dans la vue
     *                      An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue demandée
     *         A string path of the requested view
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            tradeService.saveTrade(trade);
            return "redirect:/trade/list";
        }
        trade.setTradeId(id);
        return "trade/update";
    }

    /**
     * Cette méthode permet de supprimer un trade existant
     * This method allows deleting an existing trade
     *
     * @param id    L'identifiant de l'objet à supprimer
     *              The identifier of the object to delete
     * @param model Un objet contenant les données à rendre dans la vue
     *              An object that contains the data for rendering into the view
     * @return Un chemin de chaîne de la vue vers laquelle l'utilisateur est redirigé
     *         A string path of the view to which the user is redirected
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> trade = tradeService.findTradeById(id);
        if (trade.isPresent()) {
            tradeService.deleteTradeById(id);
        } else {
            throw new IllegalArgumentException("Trade id not found");
        }
        return "redirect:/trade/list";
    }
}
