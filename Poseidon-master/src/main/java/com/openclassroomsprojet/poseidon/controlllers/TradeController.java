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
 * This controller provides CRUD operations on Trade entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */

@Controller
public class TradeController {

    @Autowired
    private ITradeService tradeService;

    /**
     * This method allows to display all the trades
     *
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trade", tradeService.findAllTrade());
        return "trade/list";
    }

    /**
     * This method allows access to the form for creating a new trade
     *
     * @return A string path of the requested view
     */
    @GetMapping("/trade/add")
    public String addUser(Trade trade) {
        return "trade/add";
    }

    /**
     * This method check the @Valid object and saves it if there is no error
     *
     * @param trade         Object that must be validated before being saved
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
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
     * This method allows access to the form for update an existing trade
     *
     * @param id    The identifier of the object to display
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> trade = tradeService.findTradeById(id);
        model.addAttribute("trade", trade.orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id)));
        return "trade/update";
    }

    /**
     * This method check the @Valid object and update it if there is no error
     *
     * @param id            The identifier of the object to check and update
     * @param trade         Object that must be checked before being updated
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
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
     * This method allows to delete an existing trade
     *
     * @param id    The identifier of the object to delete
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the view to which the user is redirected
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