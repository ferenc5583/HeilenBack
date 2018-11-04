/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.controller;

import java.util.Collection;
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
import org.zerhusen.heilen.model.Calificacion;
import org.zerhusen.heilen.repository.CalificacionRepository;

/**
 *
 * @author Ferenc
 */
@RestController
public class CalificacionController {
    
    @Autowired
    private CalificacionRepository repository;
    
     // Petición GET (Mostrar Todos)
    @CrossOrigin
    @RequestMapping(value = "/calificacion/", method = GET)
    public Collection<Calificacion> getCalificaciones() {
        //JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request);         
        return repository.findAll();  
    }
    
    //Buscar una calificacion
    @CrossOrigin
    @RequestMapping(value = "/calificacion/{id}", method = GET)
    public Optional<Calificacion> getCalificacion(@PathVariable long id) {
        return repository.findById(id);
    }
    
    // Petición POST Agregar
    @CrossOrigin
    @RequestMapping(value = "/calificacion/", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Calificacion nuevaCalificacion(@Valid @RequestBody Calificacion calificacion) {
        repository.save(calificacion);
        return calificacion;
    }
    
    // Petición PUT Editar 
    @CrossOrigin
    @RequestMapping(value = "/calificacion/{id}", method = PUT)
    public ResponseEntity<Optional<Calificacion>> actualizarCalificacion(@Valid @PathVariable long id, @RequestBody Calificacion actualizarCalificacion) {
        Optional<Calificacion> calificacion = repository.findById(id);
        if (calificacion != null) {

            actualizarCalificacion.setId(id);
            repository.save(actualizarCalificacion);
            return new ResponseEntity<>(calificacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Petición DELETE(Eliminar)
    @CrossOrigin
    @RequestMapping(value = "/calificacion/{id}", method = DELETE)
    public ResponseEntity<Optional<Calificacion>> eliminarCalificacion(@PathVariable long id) {
        Optional<Calificacion> calificacion = repository.findById(id);
        repository.deleteById(id);
        if (calificacion != null) {
            return new ResponseEntity<>(calificacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Petición PUT Editar calificacion de un usuario
    @CrossOrigin
    @RequestMapping(value = "/calificacion/user/{id},{voto}", method = PUT)
    public ResponseEntity<Optional<Calificacion>> actualizarCalificacionXUser(@Valid @PathVariable long id, @PathVariable double voto, @RequestBody Calificacion actualizarCalificacionXUser) {
        double promedio;
        double newNum_votos;
        double newTotal_acum;
        
        Optional<Calificacion> calificacion = repository.findById(id);
        
        if (calificacion != null) {
            
            promedio = ((calificacion.get().getTotal_acum()+voto)/calificacion.get().getNum_votos());
            newNum_votos = (calificacion.get().getNum_votos()+1);
            newTotal_acum = (calificacion.get().getTotal_acum()+voto);
            
            actualizarCalificacionXUser.setId(id);
            actualizarCalificacionXUser.setNum_votos(newNum_votos);
            actualizarCalificacionXUser.setTotal_acum(newTotal_acum);
            actualizarCalificacionXUser.setPun_final(promedio);
            actualizarCalificacionXUser.setVoto(voto);
            repository.save(actualizarCalificacionXUser);
            return new ResponseEntity<>(calificacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
