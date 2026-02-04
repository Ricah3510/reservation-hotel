package com.touroperator.util;

import com.touroperator.model.Assignation;
import com.touroperator.model.Vehicule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VehiculeUtil {
    
    public static List<Vehicule> filtrerParPlace(List<Vehicule> vehicules, int nbPlaceTotal) {
        List<Vehicule> retour = new ArrayList<>();
        for (Vehicule vehicule : vehicules) {
            if (vehicule.getNbPlace() >= nbPlaceTotal) {
                retour.add(vehicule);
            }
        }
        return retour;
    }
    
    public static List<Vehicule> trierParPlace(List<Vehicule> vehicules) {
        List<Vehicule> resultat = new ArrayList<>(vehicules);
        for (int i = 0; i < resultat.size() - 1; i++) {
            for (int j = i + 1; j < resultat.size(); j++) {
                if (resultat.get(i).getNbPlace() > resultat.get(j).getNbPlace()) {
                    Vehicule tmp = resultat.get(i);
                    resultat.set(i, resultat.get(j));
                    resultat.set(j, tmp);
                }
            }
        }
        return resultat;
    }
    
    public static List<Vehicule> filtreByNbreTrajet(List<Vehicule> vehicules, LocalDate date) {
        List<Vehicule> resultat = new ArrayList<>();
        int minTrajet = Integer.MAX_VALUE;
        
        for (Vehicule v : vehicules) {
            int compteur = compterTrajets(v, date);
            
            if (compteur < minTrajet) {
                minTrajet = compteur;
                resultat.clear();
                resultat.add(v);
            } else if (compteur == minTrajet) {
                resultat.add(v);
            }
        }
        return resultat;
    }
    
    private static int compterTrajets(Vehicule vehicule, LocalDate date) {
        int compteur = 0;
        for (Assignation a : vehicule.getAssignations()) {
            if (a.getHeureDepart() != null &&
                a.getHeureDepart().toLocalDate().equals(date)) {
                compteur++;
            }
        }
        return compteur;
    }
    
    public static List<Vehicule> filtreByCarburant(List<Vehicule> vehicules) {
        List<Vehicule> resultat = new ArrayList<>();
        
        for (Vehicule v : vehicules) {
            if (v.getCarburant() != null && v.getCarburant().getIdCarburant() == 2) {
                resultat.add(v);
            }
        }
        return resultat.isEmpty() ? vehicules : resultat;
    }
    
    public static List<Vehicule> filtreByMinPlace(List<Vehicule> vehicules) {
        List<Vehicule> resultat = new ArrayList<>();
        int minPlace = Integer.MAX_VALUE;
        
        for (Vehicule v : vehicules) {
            if (v.getNbPlace() < minPlace) {
                minPlace = v.getNbPlace();
                resultat.clear();
                resultat.add(v);
            } else if (v.getNbPlace() == minPlace) {
                resultat.add(v);
            }
        }
        return resultat;
    }
    
    public static Vehicule getVehiculeFinal(List<Vehicule> vehicules, LocalDate date, int nbPlaceTotal) {
        if (vehicules == null || vehicules.isEmpty()) {
            return null;
        }
        
        List<Vehicule> retour;
        
        retour = trierParPlace(filtrerParPlace(vehicules, nbPlaceTotal));
        
        if (retour.size() < 1) {
            return null;
        }
        
        if (retour.size() > 1) {
            retour = filtreByNbreTrajet(retour, date);
        }
        
        // if (retour.size() > 1) {
        //     retour = filtreByCarburant(retour);
        // }
        
        if (retour.size() > 1) {
            retour = filtreByMinPlace(retour);
        }

        if (retour.size() > 1) {
            retour = filtreByCarburant(retour);
        }

        if (retour.size() > 1) {
            int index = new Random().nextInt(retour.size());
            return retour.get(index);
        }
        
        return retour.get(0);
    }
    
    public static boolean hasEnoughPlaces(Vehicule vehicule, int nbPassagers) {
        return vehicule != null && vehicule.getNbPlace() >= nbPassagers;
    }
}