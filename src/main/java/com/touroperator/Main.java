package com.touroperator;

import com.touroperator.model.Assignation;
import com.touroperator.model.Hotel;
import com.touroperator.model.Reservation;
import com.touroperator.service.ReservationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        System.out.println(" Démarrage de l'application \n");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        ReservationService reservationService = context.getBean(ReservationService.class);
        
        String dateTest = "2026-01-15";
        
        try {
            LocalDate date = LocalDate.parse(dateTest, DateTimeFormatter.ISO_LOCAL_DATE);
            
            System.out.println("ASSIGNATION POUR LE " + date);
            
            List<Assignation> assignations = reservationService.assigner(date);
            
            afficherResultats(assignations);
            
        } catch (Exception e) {
            System.out.println("ERREUR : " + e.getMessage());
            e.printStackTrace();
        } finally {
            ((ClassPathXmlApplicationContext) context).close();
        }
    }
    
    private static void afficherResultats(List<Assignation> assignations) {
        System.out.println("RÉSULTAT DE L'ASSIGNATION");
        
        System.out.println("Nombre d'assignations créées : " + assignations.size() + "\n");
        
        if (assignations.isEmpty()) {
            System.out.println("Aucune assignation créée.\n");
            return;
        }
        
        for (int i = 0; i < assignations.size(); i++) {
            Assignation a = assignations.get(i);
            
            System.out.println("│ ASSIGNATION #" + (i + 1) + "│");
            System.out.println("  Véhicule       : " + a.getVehicule().getNumero() +
                            " (" + a.getVehicule().getNbPlace() + " places, " +
                            (a.getVehicule().getCarburant() == 1 ? "Essence" : "Gasoil") + ")");
            System.out.println("  Heure départ   : " + a.getHeureDepart());
            System.out.println("  Heure retour   : " + a.getHeureRetour());
            System.out.println("  Nb réservations: " + a.getReservations().size());
            
            System.out.println("\n  Réservations :");
            for (Reservation r : a.getReservations()) {
                System.out.println("    • [ID " + r.getIdReservation() + "] " + 
                    r.getNom() + " " + r.getPrenom() + 
                    " (" + r.getNbPassager() + " pass.) → " + r.getHotel().getNom());
            }
            
            System.out.println("\n  Itinéraire (hôtels) :");
            for (Hotel h : a.getHotels()) {
                System.out.println("    → " + h.getNom());
            }
            
            System.out.println("-------------------------------------------------------------------");

        }
        
        System.out.println("-----------------------------fin");
    }
}