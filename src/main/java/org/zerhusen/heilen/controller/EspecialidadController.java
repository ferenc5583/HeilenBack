/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.controller;

import java.util.Collection;
import java.util.Optional;
import javax.annotation.PostConstruct;
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
import org.zerhusen.heilen.model.Especialidad;
import org.zerhusen.heilen.repository.EspecialidadRepository;

/**
 *
 * @author Ferenc
 */
@RestController
public class EspecialidadController {
    
    @Autowired
    private EspecialidadRepository repository;
    
    @PostConstruct
    public void init() {
        if (repository.findAll().isEmpty() == true) {
            //guarda enfermero
            Especialidad enfermero = new Especialidad("Enfermero");
            repository.save(enfermero);
            //guarda kinesiologo
            Especialidad kinesiologo = new Especialidad("Kinesiólogo");
            repository.save(kinesiologo);
            //guarda nutricionista
            Especialidad nutricionista = new Especialidad("Nutricionista");
            repository.save(nutricionista);
        }
    }
    
     // Petición GET (Mostrar Todos)
    @CrossOrigin
    @RequestMapping(value = "/especialidad/", method = GET)
    public Collection<Especialidad> getEspecialidades() {
        //JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request);         
        return repository.findAll();  
    }
    
    //Buscar una espcialidad
    @CrossOrigin
    @RequestMapping(value = "/espcialidad/{id}", method = GET)
    public Optional<Especialidad> getEspecialidad(@PathVariable long id) {
        return repository.findById(id);
    }
    
    // Petición POST Agregar
    @CrossOrigin
    @RequestMapping(value = "/especialidad/", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Especialidad nuevaEspecialidad(@Valid @RequestBody Especialidad especialidad) {
        repository.save(especialidad);
        return especialidad;
    }
    
    // Petición PUT Editar 
    @CrossOrigin
    @RequestMapping(value = "/especialidad/{id}", method = PUT)
    public ResponseEntity<Optional<Especialidad>> actualizarEspecialidad(@Valid @PathVariable long id, @RequestBody Especialidad actualizarEspecialidad) {
        Optional<Especialidad> especialidad = repository.findById(id);
        if (especialidad != null) {

            actualizarEspecialidad.setId(id);
            repository.save(actualizarEspecialidad);
            return new ResponseEntity<>(especialidad, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Petición DELETE(Eliminar)
    @CrossOrigin
    @RequestMapping(value = "/especialidad/{id}", method = DELETE)
    public ResponseEntity<Optional<Especialidad>> eliminarEspecialidad(@PathVariable long id) {
        Optional<Especialidad> especialidad = repository.findById(id);
        repository.deleteById(id);
        if (especialidad != null) {
            return new ResponseEntity<>(especialidad, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
