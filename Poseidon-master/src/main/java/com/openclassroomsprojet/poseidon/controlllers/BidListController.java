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
 * This controller provides CRUD operations on BidList entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Controller
public class BidListController {

    @Autowired
    private IBidListService bidListService;

    /**
     * This method allows to display all the bids
     *
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidList", bidListService.findAllBidList());
        return "bidList/list";
    }

    /**
     * This method allows access to the form for creating a new bid
     *
     * @return A string path of the requested view
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList) {
        return "bidList/add";
    }

    /**
     * This method check the @Valid object and saves it if there is no error
     *
     * @param bidList       Object that must be validated before being saved
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
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
     * This method allows access to the form for update an existing bid
     *
     * @param id    The identifier of the object to display
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
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
     * This method check the @Valid object and update it if there is no error
     *
     * @param id            The identifier of the object to check and update
     * @param bidList       Object that must be checked before being updated
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            bidListService.saveBidList(bidList);
            return "redirect:/bidList/list";
        }
        bidList.setBidListId(id);
        return "bidList/update";
    }

    /**
     * This method allows to delete an existing bid
     *
     * @param id    The identifier of the object to delete
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the view to which the user is redirected
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