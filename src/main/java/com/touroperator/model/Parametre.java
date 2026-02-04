package com.touroperator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "t_parametre")
public class Parametre implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parametre")
    private Integer idParametre;
    
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;
    
    @Column(name = "valeur", nullable = false, precision = 10, scale = 2)
    private BigDecimal valeur;
    
    public Parametre() {
    }
    
    public Parametre(String nom, BigDecimal valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }
    
    public Integer getIdParametre() {
        return idParametre;
    }
    
    public void setIdParametre(Integer idParametre) {
        this.idParametre = idParametre;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public BigDecimal getValeur() {
        return valeur;
    }
    
    public void setValeur(BigDecimal valeur) {
        this.valeur = valeur;
    }
    
    @Override
    public String toString() {
        return "Parametre{" +
                "idParametre=" + idParametre +
                ", nom='" + nom + '\'' +
                ", valeur=" + valeur +
                '}';
    }
}