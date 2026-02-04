package com.touroperator.repository;

import com.touroperator.model.Distance;
import com.touroperator.model.Hotel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class DistanceRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Distance save(Distance distance) {
        if (distance.getIdDistance() == null) {
            entityManager.persist(distance);
            return distance;
        } else {
            return entityManager.merge(distance);
        }
    }
    
    public Distance findById(Integer id) {
        return entityManager.find(Distance.class, id);
    }
    
    public List<Distance> findAll() {
        return entityManager.createQuery("SELECT d FROM Distance d", Distance.class)
                .getResultList();
    }
    
    public BigDecimal findDistanceBetween(Hotel hotelFrom, Hotel hotelTo) {
        List<Distance> distances = entityManager.createQuery(
                "SELECT d FROM Distance d " +
                "WHERE d.distanceFrom = :from " +
                "AND d.distanceTo = :to", 
                Distance.class)
                .setParameter("from", hotelFrom)
                .setParameter("to", hotelTo)
                .getResultList();
        
        return distances.isEmpty() ? null : distances.get(0).getDistance();
    }
    
    public BigDecimal findDistanceBetweenByIds(Integer idFrom, Integer idTo) {
        List<BigDecimal> distances = entityManager.createQuery(
                "SELECT d.distance FROM Distance d " +
                "WHERE d.distanceFrom.idHotel = :idFrom " +
                "AND d.distanceTo.idHotel = :idTo", 
                BigDecimal.class)
                .setParameter("idFrom", idFrom)
                .setParameter("idTo", idTo)
                .getResultList();
        
        return distances.isEmpty() ? null : distances.get(0);
    }
    
    public List<Distance> findDistancesFrom(Hotel hotel) {
        return entityManager.createQuery(
                "SELECT d FROM Distance d WHERE d.distanceFrom = :hotel", 
                Distance.class)
                .setParameter("hotel", hotel)
                .getResultList();
    }
    
    public List<Distance> findDistancesTo(Hotel hotel) {
        return entityManager.createQuery(
                "SELECT d FROM Distance d WHERE d.distanceTo = :hotel",
                Distance.class)
                .setParameter("hotel", hotel)
                .getResultList();
    }
    
    public void delete(Distance distance) {
        if (entityManager.contains(distance)) {
            entityManager.remove(distance);
        } else {
            entityManager.remove(entityManager.merge(distance));
        }
    }
    
    public void deleteById(Integer id) {
        Distance distance = findById(id);
        if (distance != null) {
            delete(distance);
        }
    }
}