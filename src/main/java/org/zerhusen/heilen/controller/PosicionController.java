/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.zerhusen.security.JwtUser;

/**
 *
 * @author Ferenc
 */
@RestController
public class PosicionController {
    
    @Autowired
    private PosicionRepository repository;
    
    // Petición GET (Mostrar Todos)
    @CrossOrigin
    @RequestMapping(value = "/posicion/", method = GET)
    public Collection<Posicion> getPosiciones() {
        //JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request); 
        return repository.findAll();  
    }
    //endpoint que devuelve solo lat y lng
    @CrossOrigin
    @RequestMapping(value = "/posicion/posiciones/", method = GET)
    public List<Posicion> getPosicionesLATyLNG() {
        //JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request); 
        return repository.listaLATyLNG();  
    }
    
    //Buscar a un
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
        System.out.println(posicion);
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
    
    // Petición DELETE(Eliminar Bodega)
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
}
