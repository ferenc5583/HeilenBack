/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ferenc
 */
@Entity
@Table(name = "calificacion")
public class Calificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private double num_votos;
    private double voto;
    private double total_acum;
    private double pun_final;

    public Calificacion() {
    }

    public Calificacion(double num_votos, double voto, double total_acum, double pun_final) {
        super();
        this.num_votos = num_votos;
        this.voto = voto;
        this.total_acum = total_acum;
        this.pun_final = pun_final;
    }

    @Override
    public String toString() {
        return "Calificacion{" + "id=" + id + ", num_votos=" + num_votos + ", voto=" + voto + ", total_acum=" + total_acum + ", pun_final=" + pun_final + '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getNum_votos() {
        return num_votos;
    }

    public void setNum_votos(double num_votos) {
        this.num_votos = num_votos;
    }

    public double getVoto() {
        return voto;
    }

    public void setVoto(double voto) {
        this.voto = voto;
    }

    public double getTotal_acum() {
        return total_acum;
    }

    public void setTotal_acum(double total_acum) {
        this.total_acum = total_acum;
    }

    public double getPun_final() {
        return pun_final;
    }

    public void setPun_final(double pun_final) {
        this.pun_final = pun_final;
    }
    
    
}
