package com.openclassroomsprojet.poseidon.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller provides the path to the home page
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Controller
public class HomeController {

    /**
     * this method provides the home page
     *
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @RequestMapping("/")
    public String home(Model model) {
        return "home";
    }
}