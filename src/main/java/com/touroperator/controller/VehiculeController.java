package com.touroperator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.touroperator.model.Vehicule;
import com.touroperator.service.VehiculeService;

@Controller
@RequestMapping("/vehicule")
public class VehiculeController {
    
    @Autowired
    private VehiculeService vehiculeService;
    
    @GetMapping("/liste")
    public ModelAndView liste() {
        ModelAndView mav = new ModelAndView();
        
        List<Vehicule> vehicules = vehiculeService.getAllVehicules();
        
        mav.addObject("vehicules", vehicules);
        mav.addObject("titre", "Liste des Véhicules");
        
        mav.setViewName("vehicule/liste");
        
        return mav;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();

        Vehicule vehicule = vehiculeService.getVehiculeById(id);

        mav.addObject("vehicule", vehicule);
        mav.addObject("titre", "Détails du véhicule");

        mav.setViewName("vehicule/details");
        return mav;
    }

    @GetMapping("/ajout")
    public String ajoutForm() {
        return "vehicule/ajout";
    }

    @PostMapping("/ajouter")
    public String ajouter(String numero,int nbPlace,int carburant) {
        Vehicule v = new Vehicule();
        v.setNumero(numero);
        v.setNbPlace(nbPlace);
        v.setCarburant(carburant);

        vehiculeService.saveVehicule(v);

        return "redirect:/vehicule/liste";
    }

}