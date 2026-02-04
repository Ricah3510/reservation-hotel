package com.touroperator.repository;

import com.touroperator.model.Carburant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CarburantRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Enregistrer ou mettre à jour un carburant
    public Carburant save(Carburant carburant) {
        if (carburant.getIdCarburant() == null) {
            entityManager.persist(carburant);
            return carburant;
        } else {
            return entityManager.merge(carburant);
        }
    }

    // Trouver par ID
    public Carburant findById(Integer id) {
        return entityManager.find(Carburant.class, id);
    }

    // Récupérer tous les carburants
    public List<Carburant> findAll() {
        return entityManager.createQuery("SELECT c FROM Carburant c", Carburant.class)
                .getResultList();
    }

    // Trouver par nom de carburant
    public Carburant findByName(String name) {
        List<Carburant> list = entityManager.createQuery(
                        "SELECT c FROM Carburant c WHERE c.carburant = :name", Carburant.class)
                .setParameter("name", name)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    // Supprimer un carburant
    public void delete(Carburant carburant) {
        if (entityManager.contains(carburant)) {
            entityManager.remove(carburant);
        } else {
            entityManager.remove(entityManager.merge(carburant));
        }
    }

    // Supprimer par ID
    public void deleteById(Integer id) {
        Carburant carburant = findById(id);
        if (carburant != null) {
            delete(carburant);
        }
    }
}
