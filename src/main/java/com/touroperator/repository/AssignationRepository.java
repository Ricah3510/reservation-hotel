package com.touroperator.repository;

import com.touroperator.model.Assignation;
import com.touroperator.model.Vehicule;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AssignationRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Assignation save(Assignation assignation) {
        if (assignation.getIdAssignation() == null) {
            entityManager.persist(assignation);
            return assignation;
        } else {
            return entityManager.merge(assignation);
        }
    }
    
    public Assignation findById(Integer id) {
        return entityManager.find(Assignation.class, id);
    }
    
    public List<Assignation> findAll() {
        return entityManager.createQuery("SELECT a FROM Assignation a", Assignation.class).getResultList();
    }
    
    public List<Assignation> findByVehicule(Vehicule vehicule) {
        return entityManager.createQuery(
                "SELECT a FROM Assignation a WHERE a.vehicule = :vehicule", 
                Assignation.class)
                .setParameter("vehicule", vehicule)
                .getResultList();
    }
    
    public List<Assignation> findByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        
        return entityManager.createQuery(
                "SELECT a FROM Assignation a " +
                "WHERE a.heureDepart >= :start " +
                "AND a.heureDepart < :end", 
                Assignation.class)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getResultList();
    }
    
    public List<Assignation> findByVehiculeAndDate(Vehicule vehicule, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        
        return entityManager.createQuery(
                "SELECT a FROM Assignation a " +
                "WHERE a.vehicule = :vehicule " +
                "AND a.heureDepart >= :start " +
                "AND a.heureDepart < :end", 
                Assignation.class)
                .setParameter("vehicule", vehicule)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getResultList();
    }
    
    public Long countByVehiculeAndDate(Vehicule vehicule, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        
        return entityManager.createQuery(
                "SELECT COUNT(a) FROM Assignation a " +
                "WHERE a.vehicule = :vehicule " +
                "AND a.heureDepart >= :start " +
                "AND a.heureDepart < :end", 
                Long.class)
                .setParameter("vehicule", vehicule)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getSingleResult();
    }
    
    public List<Assignation> findEnCours() {
        return entityManager.createQuery(
                "SELECT a FROM Assignation a WHERE a.heureRetour IS NULL", 
                Assignation.class)
                .getResultList();
    }
    
    public void delete(Assignation assignation) {
        if (entityManager.contains(assignation)) {
            entityManager.remove(assignation);
        } else {
            entityManager.remove(entityManager.merge(assignation));
        }
    }
    
    public void deleteById(Integer id) {
        Assignation assignation = findById(id);
        if (assignation != null) {
            delete(assignation);
        }
    }
}