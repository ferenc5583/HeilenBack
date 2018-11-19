/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.controller;

import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.heilen.model.Posicion;
import org.zerhusen.heilen.repository.PosicionRepository;
import org.zerhusen.model.security.UserData;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;

/**
 *
 * @author Ferenc
 */
@RestController
public class PosicionController {
    
    @Autowired
    private PosicionRepository repository;
    
    UserData user = new UserData();  
    
    @Value("${jwt.header}")
    public String tokenHeader;

    @Autowired
    public JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    public UserDetailsService userDetailsService;
    
    @Autowired
    public HttpServletRequest request;
    
    // Petición GET (Mostrar Todos)
    @CrossOrigin
    @RequestMapping(value = "/posicion/", method = GET)
    public Collection<Posicion> getPosiciones() {
        //JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request);         
        return repository.findAll();  
    }
    //endpoint que lista a todos los profesionales
    @CrossOrigin
    @RequestMapping(value = "/posicionProf/", method = GET)
    public Collection<Posicion> getPosicionesProf() {
        JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request);         
        return repository.ListaProf(eluse.getId());
    }
    
    //Buscar una posicion
    @CrossOrigin
    @RequestMapping(value = "/posicion/{id}", method = GET)
    public Optional<Posicion> getPosicion(@PathVariable long id) {
        return repository.findById(id);
    }
    
    // Petición POST Agregar
    @CrossOrigin
    @RequestMapping(value = "/posicion/", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Posicion nuevaPosicion(@Valid @RequestBody Posicion posicion) {
        repository.save(posicion);
        return posicion;
    }
    
    // Petición PUT Editar 
    @CrossOrigin
    @RequestMapping(value = "/posicion/{id}", method = PUT)
    public ResponseEntity<Optional<Posicion>> actualizarPosicion(@Valid @PathVariable long id, @RequestBody Posicion actualizarPosicion) {
        Optional<Posicion> posicion = repository.findById(id);
        if (posicion != null) {

            actualizarPosicion.setId(id);
            repository.save(actualizarPosicion);
            return new ResponseEntity<>(posicion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Petición DELETE(Eliminar)
    @CrossOrigin
    @RequestMapping(value = "/posicion/{id}", method = DELETE)
    public ResponseEntity<Optional<Posicion>> eliminarPosicion(@PathVariable long id) {
        Optional<Posicion> posicion = repository.findById(id);
        repository.deleteById(id);
        if (posicion != null) {
            return new ResponseEntity<>(posicion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Petición PUT Edita una popsicion de un usuario 
    @CrossOrigin
    @RequestMapping(value = "/posicion/editUser/{lat},{lng}", method = PUT)
    public void actualizarPosicionUser(@Valid @PathVariable double lat, @PathVariable double lng) {
        JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request);
        repository.UserEditPosition(lat, lng, eluse.getId());
    }
    
    //Buscar una posicion x id_user
    @CrossOrigin
    @RequestMapping(value = "/posicionUserId/{id_user}", method = GET)
    public Optional<Posicion> getPosicionUser(@PathVariable long id_user) {
        return repository.PosicionUserId(id_user);
    }  
}
