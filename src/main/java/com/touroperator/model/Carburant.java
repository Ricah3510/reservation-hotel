package com.touroperator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_carburant")
public class Carburant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carburant")
    private Integer idCarburant;

    @Column(name = "carburant", length = 20, nullable = false)
    private String carburant;

    @OneToMany(mappedBy = "carburant")
    private List<Vehicule> vehicules = new ArrayList<>();

    public Carburant() {
    }

    public Carburant(String carburant) {
        this.carburant = carburant;
    }

    public Integer getIdCarburant() {
        return idCarburant;
    }

    public void setIdCarburant(Integer idCarburant) {
        this.idCarburant = idCarburant;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    @Override
    public String toString() {
        return "Carburant{" +
                "idCarburant=" + idCarburant +
                ", carburant='" + carburant + '\'' +
                '}';
    }
}
