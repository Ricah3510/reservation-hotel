package com.touroperator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_vehicule")
public class Vehicule implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehicule")
    private Integer idVehicule;
    
    @Column(name = "numero", nullable = false, length = 50)
    private String numero;
    
    @Column(name = "nb_place", nullable = false)
    private Integer nbPlace;
    
    // @Column(name = "carburant")
    // private Integer carburant; // 1 = essence, 2 = gasoil
    
    @OneToMany(mappedBy = "vehicule")
    private List<Assignation> assignations = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "carburant", referencedColumnName = "id_carburant", nullable = false)
    private Carburant carburant;

    
    public Vehicule() {
    }
    
    // public Vehicule(String numero, Integer nbPlace, Integer carburant) {
    //     this.numero = numero;
    //     this.nbPlace = nbPlace;
    //     this.carburant = carburant;
    // }
    
    
    public Integer getIdVehicule() {
        return idVehicule;
    }
    
    public Vehicule(String numero, Integer nbPlace, Carburant carburant) {
        this.numero = numero;
        this.nbPlace = nbPlace;
        this.carburant = carburant;
    }

    public void setIdVehicule(Integer idVehicule) {
        this.idVehicule = idVehicule;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public Integer getNbPlace() {
        return nbPlace;
    }
    
    public void setNbPlace(Integer nbPlace) {
        this.nbPlace = nbPlace;
    }
    
    // public Integer getCarburant() {
    //     return carburant;
    // }
    
    // public void setCarburant(Integer carburant) {
    //     this.carburant = carburant;
    // }
    
    public List<Assignation> getAssignations() {
        return assignations;
    }
    
    public void setAssignations(List<Assignation> assignations) {
        this.assignations = assignations;
    }
    
    public void addAssignation(Assignation assignation) {
        this.assignations.add(assignation);
        assignation.setVehicule(this);
    }
    
    @Override
    public String toString() {
        return "Vehicule{" +
                "idVehicule=" + idVehicule +
                ", numero='" + numero + '\'' +
                ", nbPlace=" + nbPlace +
                ", carburant=" + carburant +
                '}';
    }

    public Carburant getCarburant() {
        return carburant;
    }

    public void setCarburant(Carburant carburant) {
        this.carburant = carburant;
    }
}