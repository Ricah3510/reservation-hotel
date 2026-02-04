package com.touroperator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_reservation")
public class Reservation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Integer idReservation;
    
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;
    
    @Column(name = "nb_passager", nullable = false)
    private Integer nbPassager;
    
    @Column(name = "dateheure_arrivee", nullable = false)
    private LocalDateTime dateHeureArrivee;
    
    @Column(name = "assigner", nullable = false)
    private Boolean assigner = false;
    
    @ManyToOne
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;
    
    public Reservation() {
    }
    
    public Reservation(String nom, String prenom, Integer nbPassager, LocalDateTime dateHeureArrivee, Hotel hotel) {
        this.nom = nom;
        this.prenom = prenom;
        this.nbPassager = nbPassager;
        this.dateHeureArrivee = dateHeureArrivee;
        this.hotel = hotel;
        this.assigner = false;
    }
    
    public Integer getIdReservation() {
        return idReservation;
    }
    
    public void setIdReservation(Integer idReservation) {
        this.idReservation = idReservation;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public Integer getNbPassager() {
        return nbPassager;
    }
    
    public void setNbPassager(Integer nbPassager) {
        this.nbPassager = nbPassager;
    }
    
    public LocalDateTime getDateHeureArrivee() {
        return dateHeureArrivee;
    }
    
    public void setDateHeureArrivee(LocalDateTime dateHeureArrivee) {
        this.dateHeureArrivee = dateHeureArrivee;
    }
    
    public Boolean getAssigner() {
        return assigner;
    }
    
    public void setAssigner(Boolean assigner) {
        this.assigner = assigner;
    }
    
    public Hotel getHotel() {
        return hotel;
    }
    
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    
    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nbPassager=" + nbPassager +
                ", dateHeureArrivee=" + dateHeureArrivee +
                ", assigner=" + assigner +
                ", hotel=" + (hotel != null ? hotel.getNom() : "null") +
                '}';
    }
}