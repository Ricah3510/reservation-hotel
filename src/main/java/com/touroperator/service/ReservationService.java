package com.touroperator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.touroperator.model.Assignation;
import com.touroperator.model.Hotel;
import com.touroperator.model.Reservation;
import com.touroperator.model.Vehicule;
import com.touroperator.repository.HotelRepository;
import com.touroperator.repository.ParametreRepository;
import com.touroperator.repository.ReservationRepository;

@Service
@Transactional
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private ParametreRepository parametreRepository;
    
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private VehiculeService vehiculeService;
    
    @Autowired
    private AssignationService assignationService;
    
    public List<Assignation> assigner(LocalDate date) {
        List<Assignation> assignations = new ArrayList<>();
        
        List<Reservation> reservations = getReservationsForDay(date);
        
        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation pour la date : " + date);
            return assignations;
        }
        
        int tempsAttente = getTempsAttente();
        
        Hotel aeroport = hotelRepository.findById(1);
        
        for (Reservation reservation : reservations) {
            if (reservation.getAssigner()) {
                continue;
            }
            
            List<Vehicule> vehiculesDisponibles = vehiculeService.getVehiculesDisponibles(reservation.getDateHeureArrivee());
            
            List<Reservation> reservationsTA = grouperReservationsTA(
                reservation,
                reservations,
                tempsAttente
            );
            
            int nbPassagersTotal = calculerNombrePassagerTotal(reservationsTA);
            
            Vehicule vehicule = vehiculeService.getVehiculeApproprie(
                vehiculesDisponibles, 
                nbPassagersTotal, 
                date
            );
            
            if (vehicule != null) {
                Assignation assignation = creerEtSauvegarderAssignation(
                    reservationsTA, 
                    vehicule, 
                    aeroport
                );
                
                if (assignation != null) {
                    assignations.add(assignation);
                }
            } 
            else {
                gererAbsenceVehicule(
                    reservation, 
                    reservationsTA, 
                    vehiculesDisponibles, 
                    date, 
                    tempsAttente, 
                    aeroport, 
                    assignations
                );
            }
        }
        
        return assignations;
    }
    
    private List<Reservation> getReservationsForDay(LocalDate date) {
        return reservationRepository.findByDate(date);
    }
    
    private List<Reservation> grouperReservationsTA(Reservation reservation, 
                                                    List<Reservation> reservations, 
                                                    int tempsAttente) {
        List<Reservation> retour = new ArrayList<>();
        retour.add(reservation);
        
        LocalDateTime tempsLimite = reservation.getDateHeureArrivee().plusMinutes(tempsAttente);
        
        for (Reservation r : reservations) {
            if (!r.getIdReservation().equals(reservation.getIdReservation()) &&
                !r.getAssigner()) {
                
                if (tempsLimite.isAfter(r.getDateHeureArrivee())) {
                    retour.add(r);
                } else {
                    return retour;
                }
            }
        }
        
        return retour;
    }
    
    private int calculerNombrePassagerTotal(List<Reservation> reservations) {
        int total = 0;
        for (Reservation r : reservations) {
            total += r.getNbPassager();
        }
        return total;
    }
    
    private Assignation creerEtSauvegarderAssignation(List<Reservation> reservations,
                                                        Vehicule vehicule,
                                                        Hotel aeroport) {
        Assignation assignation = assignationService.creerAssignation(
            reservations,
            vehicule,
            aeroport
        );
        
        if (assignation == null) {
            return null;
        }
        
        assignationService.marquerReservationsAssignees(reservations);
        
        for (Reservation r : reservations) {
            reservationRepository.save(r);
        }
        
        vehicule.addAssignation(assignation);
        
        return assignationService.saveAssignation(assignation);
    }
    
    private void gererAbsenceVehicule(Reservation reservationInitiale,
                                        List<Reservation> reservationsTA,
                                        List<Vehicule> vehiculesDisponibles,
                                        LocalDate date,
                                        int tempsAttente,
                                        Hotel aeroport,
                                        List<Assignation> assignations) {
        
        if (vehiculesDisponibles != null && !vehiculesDisponibles.isEmpty()) {
            tentativeReductionGroupe(
                reservationsTA, 
                vehiculesDisponibles, 
                date, 
                aeroport, 
                assignations
            );
        } 
        else {
            tentativeAttenteVehicule(
                reservationInitiale,
                reservationsTA,
                date,
                tempsAttente,
                aeroport,
                assignations
            );
        }
    }
    
    private void tentativeReductionGroupe(List<Reservation> reservationsTA,
                                            List<Vehicule> vehiculesDisponibles,
                                            LocalDate date,
                                            Hotel aeroport,
                                            List<Assignation> assignations) {
        
        while (reservationsTA.size() > 1) {
            reservationsTA.remove(reservationsTA.size() - 1);
            
            int nbPassagers = calculerNombrePassagerTotal(reservationsTA);
            Vehicule vehicule = vehiculeService.getVehiculeApproprie(
                vehiculesDisponibles, 
                nbPassagers, 
                date
            );
            
            if (vehicule != null) {
                Assignation assignation = creerEtSauvegarderAssignation(
                    reservationsTA, 
                    vehicule, 
                    aeroport
                );
                
                if (assignation != null) {
                    assignations.add(assignation);
                }
                return;
            }
        }
        
        if (reservationsTA.size() == 1) {
            System.out.println("Aucun véhicule disponible pour la réservation : " +
                reservationsTA.get(0).getIdReservation());
        }
    }
    
    private void tentativeAttenteVehicule(Reservation reservationInitiale,
                                            List<Reservation> reservationsTA,
                                            LocalDate date,
                                            int tempsAttente,
                                            Hotel aeroport,
                                            List<Assignation> assignations) {
        
        List<Vehicule> vehiculesFuturs = vehiculeService.getVehiculesDisponiblesDansDelai(
            reservationInitiale.getDateHeureArrivee(), 
            tempsAttente
        );
        
        if (vehiculesFuturs != null && !vehiculesFuturs.isEmpty()) {
            int nbPassagers = calculerNombrePassagerTotal(reservationsTA);
            Vehicule vehicule = vehiculeService.getVehiculeApproprie(
                vehiculesFuturs, 
                nbPassagers, 
                date
            );
            
            if (vehicule != null) {
                Assignation assignation = creerEtSauvegarderAssignation(
                    reservationsTA, 
                    vehicule, 
                    aeroport
                );
                
                if (assignation != null) {
                    assignations.add(assignation);
                }
                return;
            }
        }
        
        System.out.println("Réservation non assignée : " + reservationInitiale.getIdReservation());
    }
    
    private int getTempsAttente() {
        BigDecimal valeur = parametreRepository.getValeurByNom("temps_attente");
        return valeur != null ? valeur.intValue() : 30;
    }
    
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
    
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id);
    }
}