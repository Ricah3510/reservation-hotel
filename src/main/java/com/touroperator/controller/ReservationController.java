package com.touroperator.controller;

import com.touroperator.model.Hotel;
import com.touroperator.model.Reservation;
import com.touroperator.repository.HotelRepository;
import com.touroperator.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReservationController {
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private HotelRepository hotelRepository;
    
    @GetMapping("/liste")
    public ModelAndView liste() {
        ModelAndView mav = new ModelAndView();
        
        List<Reservation> reservations = reservationService.getAllReservations();
        
        mav.addObject("reservations", reservations);
        mav.addObject("titre", "Liste des Réservations");
        mav.setViewName("reservation/liste");
        
        return mav;
    }
    
    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Integer id) {
        ModelAndView mav = new ModelAndView();
        
        Reservation reservation = reservationService.getReservationById(id);
        
        if (reservation == null) {
            mav.setViewName("redirect:/reservation/liste");
            return mav;
        }
        
        mav.addObject("reservation", reservation);
        mav.addObject("titre", "Détails de la Réservation #" + id);
        mav.setViewName("reservation/detail");
        
        return mav;
    }
    
    @GetMapping("/ajout")
    public ModelAndView ajout() {
        ModelAndView mav = new ModelAndView();
        
        List<Hotel> hotels = hotelRepository.findAll();
        
        mav.addObject("hotels", hotels);
        mav.addObject("titre", "Nouvelle Réservation");
        mav.setViewName("reservation/ajout");
        
        return mav;
    }
    
    @PostMapping("/save")
    public ModelAndView save(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("nbPassager") Integer nbPassager,
            @RequestParam("dateHeureArrivee") String dateHeureArrivee,
            @RequestParam("idHotel") Integer idHotel
    ) {
        ModelAndView mav = new ModelAndView();
        
        try {
            reservationService.createReservation(nom, prenom, nbPassager, dateHeureArrivee, idHotel);
            
            mav.setViewName("redirect:/reservation/liste");
            
        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("redirect:/reservation/ajout");
        }
        
        return mav;
    }
}