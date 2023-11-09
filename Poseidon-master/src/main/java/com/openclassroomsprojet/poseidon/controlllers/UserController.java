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
 * This controller provides CRUD operations on user entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * This method allows to display all the users
     *
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "user/list";
    }

    /**
     * This method allows access to the form for creating a new user
     *
     * @return A string path of the requested view
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        return "user/add";
    }

    /**
     * This method check the @Valid object and saves it if there is no error
     *
     * @param user          Object that must be validated before being saved
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            userService.saveUser(user);
            model.addAttribute("users", userService.findAllUsers());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * This method allows access to the form for update an existing user
     *
     * @param id    The identifier of the object to display
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> optionalUser = userService.findUserById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword("");
            model.addAttribute("user", user);
        } else {
            throw new IllegalArgumentException("Invalid user Id:" + id);
        }
        return "user/update";
    }

    /**
     * This method check the @Valid object and update it if there is no error
     *
     * @param id            The identifier of the object to check and update
     * @param user          Object that must be checked before being updated
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/update";
        }
        user.setId(id);
        userService.saveUser(user);
        model.addAttribute("users", userService.findAllUsers());
        return "redirect:/user/list";
    }

    /**
     * This method allows to delete an existing user
     *
     * @param id    The identifier of the object to delete
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the view to which the user is redirected
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