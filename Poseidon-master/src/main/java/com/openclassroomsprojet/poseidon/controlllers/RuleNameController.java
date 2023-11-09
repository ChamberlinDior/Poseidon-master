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
 * This controller provides CRUD operations on RuleName entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */

@Controller
public class RuleNameController {

    @Autowired
    private IRuleNameService ruleNameService;

    /**
     * This method allows to display all the ruleNames
     *
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleName", ruleNameService.findAllRuleName());
        return "ruleName/list";
    }

    /**
     * This method allows access to the form for creating a new ruleName
     *
     * @return A string path of the requested view
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    /**
     * This method check the @Valid object and saves it if there is no error
     *
     * @param ruleName      Object that must be validated before being saved
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
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
     * This method allows access to the form for update an existing ruleName
     *
     * @param id    The identifier of the object to display
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> ruleName = ruleNameService.findRuleNameById(id);
        model.addAttribute("ruleName", ruleName.orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id)));
        return "ruleName/update";
    }

    /**
     * This method check the @Valid object and update it if there is no error
     *
     * @param id            The identifier of the object to check and update
     * @param ruleName      Object that must be checked before being updated
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
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
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        Optional<RuleName> ruleName = ruleNameService.findRuleNameById(id);
        if (ruleName.isPresent()) {
            ruleNameService.deleteRuleNameById(id);
        } else {
            throw new IllegalArgumentException("RuleName id not found");
        }
        return "redirect:/ruleName/list";
    }
}