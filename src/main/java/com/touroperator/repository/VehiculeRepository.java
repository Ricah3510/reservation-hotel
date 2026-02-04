package com.touroperator.repository;

import com.touroperator.model.Carburant;
import com.touroperator.model.Vehicule;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VehiculeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Vehicule save(Vehicule vehicule) {
        if (vehicule.getIdVehicule() == null) {
            entityManager.persist(vehicule);
            return vehicule;
        } else {
            return entityManager.merge(vehicule);
        }
    }

    public Vehicule findById(Integer id) {
        return entityManager.find(Vehicule.class, id);
    }

    public List<Vehicule> findAll() {
        return entityManager.createQuery("SELECT v FROM Vehicule v", Vehicule.class)
                .getResultList();
    }

    public Vehicule findByNumero(String numero) {
        List<Vehicule> vehicules = entityManager
                .createQuery("SELECT v FROM Vehicule v WHERE v.numero = :numero", Vehicule.class)
                .setParameter("numero", numero)
                .getResultList();
        return vehicules.isEmpty() ? null : vehicules.get(0);
    }

    public List<Vehicule> findByCarburant(Carburant carburant) {
        return entityManager.createQuery(
                        "SELECT v FROM Vehicule v WHERE v.carburant = :carburant", Vehicule.class)
                .setParameter("carburant", carburant)
                .getResultList();
    }

    public List<Vehicule> findByNbPlaceGreaterThanOrEqual(Integer nbPlace) {
        return entityManager.createQuery(
                        "SELECT v FROM Vehicule v WHERE v.nbPlace >= :nbPlace", Vehicule.class)
                .setParameter("nbPlace", nbPlace)
                .getResultList();
    }

    public List<Vehicule> findVehiculesDisponibles(LocalDateTime dateHeure) {
        return entityManager.createQuery(
                        "SELECT DISTINCT v FROM Vehicule v " +
                                "LEFT JOIN v.assignations a " +
                                "WHERE a IS NULL " +
                                "OR a.heureRetour IS NULL " +
                                "OR a.heureRetour <= :dateHeure",
                        Vehicule.class)
                .setParameter("dateHeure", dateHeure)
                .getResultList();
    }

    public void delete(Vehicule vehicule) {
        if (entityManager.contains(vehicule)) {
            entityManager.remove(vehicule);
        } else {
            entityManager.remove(entityManager.merge(vehicule));
        }
    }

    public void deleteById(Integer id) {
        Vehicule vehicule = findById(id);
        if (vehicule != null) {
            delete(vehicule);
        }
    }
}
