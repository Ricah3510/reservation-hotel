package com.touroperator.repository;

import com.touroperator.model.Reservation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Reservation save(Reservation reservation) {
        if (reservation.getIdReservation() == null) {
            entityManager.persist(reservation);
            return reservation;
        } else {
            return entityManager.merge(reservation);
        }
    }
    
    public Reservation findById(Integer id) {
        return entityManager.find(Reservation.class, id);
    }
    
    public List<Reservation> findAll() {
        return entityManager.createQuery("SELECT r FROM Reservation r", Reservation.class)
                .getResultList();
    }
    
    public List<Reservation> findByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        
        return entityManager.createQuery(
                "SELECT r FROM Reservation r " +
                "WHERE r.dateHeureArrivee >= :start " +
                "AND r.dateHeureArrivee < :end " +
                "ORDER BY r.dateHeureArrivee ASC", 
                Reservation.class)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getResultList();
    }
    
    public List<Reservation> findNonAssigneesByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        
        return entityManager.createQuery(
                "SELECT r FROM Reservation r " +
                "WHERE r.dateHeureArrivee >= :start " +
                "AND r.dateHeureArrivee < :end " +
                "AND r.assigner = false " +
                "ORDER BY r.dateHeureArrivee ASC", 
                Reservation.class)
                .setParameter("start", startOfDay)
                .setParameter("end", endOfDay)
                .getResultList();
    }
    
    public List<Reservation> findAssignees() {
        return entityManager.createQuery(
                "SELECT r FROM Reservation r WHERE r.assigner = true", 
                Reservation.class)
                .getResultList();
    }
    
    public List<Reservation> findNonAssignees() {
        return entityManager.createQuery(
                "SELECT r FROM Reservation r WHERE r.assigner = false", 
                Reservation.class)
                .getResultList();
    }
    
    public void delete(Reservation reservation) {
        if (entityManager.contains(reservation)) {
            entityManager.remove(reservation);
        } else {
            entityManager.remove(entityManager.merge(reservation));
        }
    }
    
    public void deleteById(Integer id) {
        Reservation reservation = findById(id);
        if (reservation != null) {
            delete(reservation);
        }
    }
}