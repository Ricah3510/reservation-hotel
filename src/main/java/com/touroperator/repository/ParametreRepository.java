package com.touroperator.repository;

import com.touroperator.model.Parametre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class ParametreRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Parametre save(Parametre parametre) {
        if (parametre.getIdParametre() == null) {
            entityManager.persist(parametre);
            return parametre;
        } else {
            return entityManager.merge(parametre);
        }
    }
    
    public Parametre findById(Integer id) {
        return entityManager.find(Parametre.class, id);
    }
    
    public List<Parametre> findAll() {
        return entityManager.createQuery("SELECT p FROM Parametre p", Parametre.class)
                .getResultList();
    }
    
    public Parametre findByNom(String nom) {
        List<Parametre> parametres = entityManager.createQuery(
                "SELECT p FROM Parametre p WHERE p.nom = :nom", 
                Parametre.class)
                .setParameter("nom", nom)
                .getResultList();
        
        return parametres.isEmpty() ? null : parametres.get(0);
    }
    
    public BigDecimal getValeurByNom(String nom) {
        Parametre parametre = findByNom(nom);
        return parametre != null ? parametre.getValeur() : null;
    }
    
    public void updateValeur(String nom, BigDecimal nouvelleValeur) {
        Parametre parametre = findByNom(nom);
        if (parametre != null) {
            parametre.setValeur(nouvelleValeur);
            save(parametre);
        }
    }
    
    public void delete(Parametre parametre) {
        if (entityManager.contains(parametre)) {
            entityManager.remove(parametre);
        } else {
            entityManager.remove(entityManager.merge(parametre));
        }
    }
    
    public void deleteById(Integer id) {
        Parametre parametre = findById(id);
        if (parametre != null) {
            delete(parametre);
        }
    }
}