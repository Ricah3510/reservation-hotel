package com.touroperator.repository;

import com.touroperator.model.Hotel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HotelRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Hotel save(Hotel hotel) {
        if (hotel.getIdHotel() == null) {
            entityManager.persist(hotel);
            return hotel;
        } else {
            return entityManager.merge(hotel);
        }
    }
    
    public Hotel findById(Integer id) {
        return entityManager.find(Hotel.class, id);
    }
    
    public List<Hotel> findAll() {
        return entityManager.createQuery("SELECT h FROM Hotel h", Hotel.class)
                .getResultList();
    }
    
    public Hotel findByNom(String nom) {
        List<Hotel> hotels = entityManager
                .createQuery("SELECT h FROM Hotel h WHERE h.nom = :nom", Hotel.class)
                .setParameter("nom", nom)
                .getResultList();
        return hotels.isEmpty() ? null : hotels.get(0);
    }
    
    public void delete(Hotel hotel) {
        if (entityManager.contains(hotel)) {
            entityManager.remove(hotel);
        } else {
            entityManager.remove(entityManager.merge(hotel));
        }
    }
    
    public void deleteById(Integer id) {
        Hotel hotel = findById(id);
        if (hotel != null) {
            delete(hotel);
        }
    }
}