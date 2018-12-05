/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.model;

import java.sql.Time;
import java.util.Date;
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
@Table(name = "contrato")
public class Contrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private long id_paciente;
    private long id_profesional;
    private String detalle;
    private long monto;
    private boolean enabled;
    private boolean aceptada;
    private boolean terminada;
    private boolean calificada;
    private String fecha;
    private String hora;

    public Contrato() {
    }

    public Contrato(long id_paciente, long id_profesional, String detalle, long monto, boolean enabled, boolean aceptada, boolean terminada, boolean calificada, String fecha, String hora) {
        this.id_paciente = id_paciente;
        this.id_profesional = id_profesional;
        this.detalle = detalle;
        this.monto = monto;
        this.enabled = enabled;
        this.aceptada = aceptada;
        this.terminada = terminada;
        this.calificada = calificada;
        this.fecha = fecha;
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Contrato{" + "id=" + id + ", id_paciente=" + id_paciente + ", id_profesional=" + id_profesional + ", detalle=" + detalle + ", monto=" + monto + ", enabled=" + enabled + ", aceptada=" + aceptada + ", terminada=" + terminada + ", calificada=" + calificada + ", fecha=" + fecha + ", hora=" + hora + '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(long id_paciente) {
        this.id_paciente = id_paciente;
    }

    public long getId_profesional() {
        return id_profesional;
    }

    public void setId_profesional(long id_profesional) {
        this.id_profesional = id_profesional;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public boolean isAceptada() {
        return aceptada;
    }

    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }

    public boolean isCalificada() {
        return calificada;
    }

    public void setCalificada(boolean calificada) {
        this.calificada = calificada;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
}
