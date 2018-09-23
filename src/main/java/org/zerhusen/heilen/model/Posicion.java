/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.zerhusen.model.security.User;

/**
 *
 * @author Ferenc
 */
@Entity
@Table(name = "posicion")
public class Posicion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private double lat;
    
    private double lng;
    
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private User id_usuario;
    
    public Posicion() {
    }

    public Posicion(double lat, double lng, User id_usuario) {
        super();
        this.lat = lat;
        this.lng = lng;
        this.id_usuario = id_usuario;
    }


    @Override
    public String toString() {
        return "Posicion{" + "id=" + id + ", lat=" + lat + ", lng=" + lng + ", id_usuario=" + id_usuario + '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public User getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(User id_usuario) {
        this.id_usuario = id_usuario;
    }
    
}
