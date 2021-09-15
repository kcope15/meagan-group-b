package org.launchcode.Liftoff.Project.Restaurant.App.controllers;

import org.launchcode.Liftoff.Project.Restaurant.App.data.CuisineRepository;
import org.launchcode.Liftoff.Project.Restaurant.App.data.RestaurantRepository;
import org.launchcode.Liftoff.Project.Restaurant.App.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

//    @RequestMapping("")
//    public String index(Model model) {
//        model.addAttribute("title", "restaurant");
//        model.addAttribute("restaurant", restaurantRepository.findAll());
//        return "index";
//    }

    @GetMapping("")
    public String displayAddRestaurantForm(Model model) {
        model.addAttribute("title", "Add Restaurant");
        model.addAttribute(new Restaurant());
        model.addAttribute("cuisines", cuisineRepository.findAll());
        return "add-restaurant";
    }

    @PostMapping("")
    public String processAddRestaurantForm(@ModelAttribute @Valid Restaurant newRestaurant,
                                           Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Restaurant");
            return "add-restaurant";
        }

        restaurantRepository.save(newRestaurant);

        return "redirect:";
    }

    @GetMapping("view/{restaurantId}")
    public String displayViewRestaurant(Model model, @PathVariable int restaurantId) {

        Optional optRestaurant = restaurantRepository.findById(restaurantId);
        if (optRestaurant.isPresent()) {
            Restaurant restaurant = (Restaurant) optRestaurant.get();
            model.addAttribute("restaurant", restaurant);
            return "list-restaurants-view";
        } else {
            return "redirect:../";
        }
    }

}
