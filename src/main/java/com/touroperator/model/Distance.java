package com.touroperator.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "t_distance")
public class Distance implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distance")
    private Integer idDistance;
    
    @ManyToOne
    @JoinColumn(name = "distance_from", nullable = false)
    private Hotel distanceFrom;
    
    @ManyToOne
    @JoinColumn(name = "distance_to", nullable = false)
    private Hotel distanceTo;
    
    @Column(name = "distance", nullable = false, precision = 5, scale = 2)
    private BigDecimal distance;
    
    public Distance() {
    }
    
    public Distance(Hotel distanceFrom, Hotel distanceTo, BigDecimal distance) {
        this.distanceFrom = distanceFrom;
        this.distanceTo = distanceTo;
        this.distance = distance;
    }
    
    public Integer getIdDistance() {
        return idDistance;
    }
    
    public void setIdDistance(Integer idDistance) {
        this.idDistance = idDistance;
    }
    
    public Hotel getDistanceFrom() {
        return distanceFrom;
    }
    
    public void setDistanceFrom(Hotel distanceFrom) {
        this.distanceFrom = distanceFrom;
    }
    
    public Hotel getDistanceTo() {
        return distanceTo;
    }
    
    public void setDistanceTo(Hotel distanceTo) {
        this.distanceTo = distanceTo;
    }
    
    public BigDecimal getDistance() {
        return distance;
    }
    
    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }
    
    @Override
    public String toString() {
        return "Distance{" +
                "idDistance=" + idDistance +
                ", distanceFrom=" + (distanceFrom != null ? distanceFrom.getNom() : "null") +
                ", distanceTo=" + (distanceTo != null ? distanceTo.getNom() : "null") +
                ", distance=" + distance +
                '}';
    }
}