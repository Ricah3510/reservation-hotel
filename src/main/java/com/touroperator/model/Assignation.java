package com.touroperator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_assignation")
public class Assignation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assignation")
    private Integer idAssignation;
    
    @Column(name = "heure_depart")
    private LocalDateTime heureDepart;
    
    @Column(name = "heure_retour")
    private LocalDateTime heureRetour;
    
    @ManyToOne
    @JoinColumn(name = "id_vehicule", nullable = false)
    private Vehicule vehicule;
    
    @ManyToMany
    @JoinTable(
        name = "t_assignation_reservation",
        joinColumns = @JoinColumn(name = "id_assignation"),
        inverseJoinColumns = @JoinColumn(name = "id_reservation")
    )
    private List<Reservation> reservations = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "t_assignation_hotel",
        joinColumns = @JoinColumn(name = "id_assignation"),
        inverseJoinColumns = @JoinColumn(name = "id_hotel")
    )
    @OrderBy("ordre ASC")
    private List<Hotel> hotels = new ArrayList<>();
    
    public Assignation() {
    }
    
    public Assignation(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
    
    public Assignation(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    public Integer getIdAssignation() {
        return idAssignation;
    }
    
    public void setIdAssignation(Integer idAssignation) {
        this.idAssignation = idAssignation;
    }
    
    public LocalDateTime getHeureDepart() {
        return heureDepart;
    }
    
    public void setHeureDepart(LocalDateTime heureDepart) {
        this.heureDepart = heureDepart;
    }
    
    public LocalDateTime getHeureRetour() {
        return heureRetour;
    }
    
    public void setHeureRetour(LocalDateTime heureRetour) {
        this.heureRetour = heureRetour;
    }
    
    public Vehicule getVehicule() {
        return vehicule;
    }
    
    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
    
    public List<Reservation> getReservations() {
        return reservations;
    }
    
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
    
    public List<Hotel> getHotels() {
        return hotels;
    }
    
    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
    
    public void addHotel(Hotel hotel) {
        this.hotels.add(hotel);
    }
    
    @Override
    public String toString() {
        return "Assignation{" +
                "idAssignation=" + idAssignation +
                ", heureDepart=" + heureDepart +
                ", heureRetour=" + heureRetour +
                ", vehicule=" + (vehicule != null ? vehicule.getNumero() : "null") +
                ", nbReservations=" + reservations.size() +
                ", nbHotels=" + hotels.size() +
                '}';
    }
}