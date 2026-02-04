package com.touroperator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_hotel")
public class Hotel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hotel")
    private Integer idHotel;
    
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    
    @OneToMany(mappedBy = "hotel")
    private List<Reservation> reservations = new ArrayList<>();
    
    public Hotel() {
    }
    
    public Hotel(String nom) {
        this.nom = nom;
    }
    
    public Integer getIdHotel() {
        return idHotel;
    }
    
    public void setIdHotel(Integer idHotel) {
        this.idHotel = idHotel;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public List<Reservation> getReservations() {
        return reservations;
    }
    
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    
    @Override
    public String toString() {
        return "Hotel{" +
                "idHotel=" + idHotel +
                ", nom='" + nom + '\'' +
                '}';
    }
}