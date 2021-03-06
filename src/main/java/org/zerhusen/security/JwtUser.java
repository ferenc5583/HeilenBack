package org.zerhusen.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.zerhusen.heilen.model.Calificacion;
import org.zerhusen.heilen.model.Especialidad;

/**
 * Created by stephan on 20.03.16.
 */
public class JwtUser implements UserDetails {

    private final Long id;
    private final String username;
    private final String rut;
    private final String firstname;
    private final String lastname;
    private final String password;
//    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final boolean online;
    private final Calificacion id_calificacion;
    private final Especialidad id_especialidad;
    private final Date lastPasswordResetDate;

    public JwtUser(
          Long id,
          String username,
          String rut,
          String firstname,
          String lastname,
//          String email,
          String password, Collection<? extends GrantedAuthority> authorities,
          boolean enabled,
          boolean online,
          Calificacion id_calificacion,
          Especialidad id_especialidad,
          Date lastPasswordResetDate
    ) {
        this.id = id;
        this.username = username;
        this.rut = rut;
        this.firstname = firstname;
        this.lastname = lastname;
//        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.online = online;
        this.id_calificacion = id_calificacion;
        this.id_especialidad = id_especialidad;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getRut() {
        return rut;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

//    public String getEmail() {
//        return email;
//    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean isOnline() {
        return online;
    }

    public Calificacion getId_calificacion() {
        return id_calificacion;
    }

    public Especialidad getId_especialidad() {
        return id_especialidad;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
}
