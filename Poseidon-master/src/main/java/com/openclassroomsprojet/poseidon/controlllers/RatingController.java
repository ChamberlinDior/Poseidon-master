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
 * This controller provides CRUD operations on Rating entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */

@Controller
public class RatingController {

    @Autowired
    private IRatingService ratingService;

    /**
     * This method allows to display all the ratings
     *
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("rating", ratingService.findAllRating());
        return "rating/list";
    }

    /**
     * This method allows access to the form for creating a new rating
     *
     * @return A string path of the requested view
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    /**
     * This method check the @Valid object and saves it if there is no error
     *
     * @param rating        Object that must be validated before being saved
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
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
     * This method allows access to the form for update an existing rating
     *
     * @param id    The identifier of the object to display
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> rating = ratingService.findRatingById(id);
        model.addAttribute("rating", rating.orElseThrow(() -> new IllegalArgumentException("Invalid Rating Id:" + id)));
        return "rating/update";
    }

    /**
     * This method check the @Valid object and update it if there is no error
     *
     * @param id            The identifier of the object to check and update
     * @param rating        Object that must be checked before being updated
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
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
     * This method allows to delete an existing rating
     *
     * @param id    The identifier of the object to delete
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the view to which the user is redirected
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