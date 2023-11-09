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
 * This controller provides CRUD operations on CurvePoint entity
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */

@Controller
public class CurvePointController {

    @Autowired
    private ICurvePointService curvePointService;

    /**
     * This method allows to display all the curvePoints
     *
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoint", curvePointService.findAllCurvePoint());
        return "curvePoint/list";
    }

    /**
     * This method allows access to the form for creating a new curvePoint
     *
     * @return A string path of the requested view
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    /**
     * This method check the @Valid object and saves it if there is no error
     *
     * @param curvePoint    Object that must be validated before being saved
     * @param bindingResult Contains the result of the @Valid object validation, we can check if errors have occurred
     * @param model         An object that contain the data for rendering into the view
     * @return A string path of the requested view
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            curvePointService.saveCurvePoint(curvePoint);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    /**
     * This method allows access to the form for update an existing curvePoint
     *
     * @param id    The identifier of the object to display
     * @param model An object that contain the data for rendering into the view
     * @return A string path of the requested view
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