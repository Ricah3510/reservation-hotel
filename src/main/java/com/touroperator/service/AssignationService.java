package com.touroperator.service;

import com.touroperator.model.Assignation;
import com.touroperator.model.Hotel;
import com.touroperator.model.Reservation;
import com.touroperator.model.Vehicule;
import com.touroperator.repository.AssignationRepository;
import com.touroperator.repository.DistanceRepository;
import com.touroperator.repository.ParametreRepository;
import com.touroperator.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AssignationService {
    
    @Autowired
    private AssignationRepository assignationRepository;
    
    @Autowired
    private DistanceRepository distanceRepository;
    
    @Autowired
    private ParametreRepository parametreRepository;
    
    public Assignation saveAssignation(Assignation assignation) {
        return assignationRepository.save(assignation);
    }
    
    public Assignation creerAssignation(List<Reservation> reservations, Vehicule vehicule, Hotel aeroport) {
        if (reservations == null || reservations.isEmpty() || vehicule == null) {
            return null;
        }
        
        Assignation assignation = new Assignation();
        assignation.setVehicule(vehicule);
        
        for (Reservation r : reservations) {
            assignation.addReservation(r);
        }
        
        LocalDateTime heureDepart = calculerHeureDepartMax(reservations);
        assignation.setHeureDepart(heureDepart);
        
        List<Hotel> hotelsUniques = DistanceUtil.extractUniqueHotels(reservations);
        List<Hotel> hotelsOrdonnes = DistanceUtil.orderHotelsByProximity(aeroport, hotelsUniques, distanceRepository);
        
        for (Hotel hotel : hotelsOrdonnes) {
            assignation.addHotel(hotel);
        }
        
        LocalDateTime heureRetour = calculerHeureRetour(heureDepart, aeroport, hotelsOrdonnes);
        assignation.setHeureRetour(heureRetour);
        
        return assignation;
    }
    
    private LocalDateTime calculerHeureDepartMax(List<Reservation> reservations) {
        LocalDateTime maxHeureArrivee = null;
        
        for (Reservation r : reservations) {
            if (maxHeureArrivee == null || r.getDateHeureArrivee().isAfter(maxHeureArrivee)) {
                maxHeureArrivee = r.getDateHeureArrivee();
            }
        }
        
        return maxHeureArrivee;
    }
    
    private LocalDateTime calculerHeureRetour(LocalDateTime heureDepart, Hotel aeroport, List<Hotel> hotelsOrdonnes) {
        if (heureDepart == null || hotelsOrdonnes == null || hotelsOrdonnes.isEmpty()) {
            return heureDepart;
        }
        
        BigDecimal vitesse = parametreRepository.getValeurByNom("vitesse");
        if (vitesse == null) {
            vitesse = new BigDecimal("20");
        }
        
        BigDecimal distanceTotale = BigDecimal.ZERO;
        
        Hotel premierHotel = hotelsOrdonnes.get(0);
        BigDecimal distanceAeroportPremier = distanceRepository.findDistanceBetweenByIds(
            aeroport.getIdHotel(), 
            premierHotel.getIdHotel()
        );
        if (distanceAeroportPremier != null) {
            distanceTotale = distanceTotale.add(distanceAeroportPremier);
        }
        
        for (int i = 0; i < hotelsOrdonnes.size() - 1; i++) {
            BigDecimal distance = distanceRepository.findDistanceBetweenByIds(
                hotelsOrdonnes.get(i).getIdHotel(),
                hotelsOrdonnes.get(i + 1).getIdHotel()
            );
            if (distance != null) {
                distanceTotale = distanceTotale.add(distance);
            }
        }
        
        Hotel dernierHotel = hotelsOrdonnes.get(hotelsOrdonnes.size() - 1);
        BigDecimal distanceDernierAeroport = distanceRepository.findDistanceBetweenByIds(
            dernierHotel.getIdHotel(),
            aeroport.getIdHotel()
        );
        if (distanceDernierAeroport != null) {
            distanceTotale = distanceTotale.add(distanceDernierAeroport);
        }
        

        BigDecimal tempsTrajetDecimal = distanceTotale
            .divide(vitesse, 2, RoundingMode.HALF_UP)
            .multiply(new BigDecimal("60"));
        long tempsTrajetMinutes = tempsTrajetDecimal.longValue();
        
        // long tempsArretMinutes = hotelsOrdonnes.size() * 10L;
        
        long tempsTotalMinutes = tempsTrajetMinutes;
        
        return heureDepart.plusMinutes(tempsTotalMinutes);
    }
    
    public void marquerReservationsAssignees(List<Reservation> reservations) {
        for (Reservation r : reservations) {
            r.setAssigner(true);
        }
    }
    
    public List<Assignation> getAllAssignations() {
        return assignationRepository.findAll();
    }
    
    public Assignation getAssignationById(Integer id) {
        return assignationRepository.findById(id);
    }
}