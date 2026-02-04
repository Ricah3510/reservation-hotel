package com.touroperator.service;

import com.touroperator.model.Assignation;
import com.touroperator.model.Vehicule;
import com.touroperator.repository.VehiculeRepository;
import com.touroperator.util.VehiculeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VehiculeService {
    
    @Autowired
    private VehiculeRepository vehiculeRepository;
    
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }
    
    public Vehicule getVehiculeById(Integer id) {
        return vehiculeRepository.findById(id);
    }
    
    public Vehicule saveVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }
    
    public List<Vehicule> getVehiculesDisponibles(LocalDateTime dateHeure) {
        List<Vehicule> allVehicules = vehiculeRepository.findAll();
        List<Vehicule> disponibles = new ArrayList<>();
        
        for (Vehicule v : allVehicules) {
            if (isVehiculeDisponible(v, dateHeure)) {
                disponibles.add(v);
            }
        }
        
        return disponibles;
    }
    
    private boolean isVehiculeDisponible(Vehicule vehicule, LocalDateTime dateHeure) {
        List<Assignation> assignations = vehicule.getAssignations();
        
        if (assignations == null || assignations.isEmpty()) {
            return true;
        }
        
        for (Assignation a : assignations) {
            if (a.getHeureRetour() == null) {
                return false;
            }
            
            if (a.getHeureRetour().isAfter(dateHeure)) {
                return false;
            }
        }
        
        return true;
    }
    
    public List<Vehicule> getVehiculesDisponiblesDansDelai(LocalDateTime dateHeure, int delaiMinutes) {
        LocalDateTime dateLimite = dateHeure.plusMinutes(delaiMinutes);
        List<Vehicule> allVehicules = vehiculeRepository.findAll();
        List<Vehicule> disponibles = new ArrayList<>();
        
        for (Vehicule v : allVehicules) {
            if (isVehiculeDisponibleDansDelai(v, dateHeure, dateLimite)) {
                disponibles.add(v);
            }
        }
        
        return disponibles;
    }
    
    private boolean isVehiculeDisponibleDansDelai(Vehicule vehicule, LocalDateTime dateDebut, LocalDateTime dateLimite) {
        List<Assignation> assignations = vehicule.getAssignations();
        
        if (assignations == null || assignations.isEmpty()) {
            return true;
        }
        
        for (Assignation a : assignations) {
            if (a.getHeureRetour() != null) {
                if (a.getHeureRetour().isAfter(dateDebut) && 
                    a.getHeureRetour().isBefore(dateLimite)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public Vehicule getVehiculeApproprie(List<Vehicule> vehiculesDisponibles,int nbPassagers,LocalDate date) {
        return VehiculeUtil.getVehiculeFinal(vehiculesDisponibles, date, nbPassagers);
    }
    
    public int compterTrajetsVehicule(Vehicule vehicule, LocalDate date) {
        int compteur = 0;
        for (Assignation a : vehicule.getAssignations()) {
            if (a.getHeureDepart() != null &&
                a.getHeureDepart().toLocalDate().equals(date)) {
                compteur++;
            }
        }
        return compteur;
    }
}