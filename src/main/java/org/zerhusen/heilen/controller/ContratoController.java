/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.controller;

import com.google.gson.JsonObject;
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
import org.zerhusen.heilen.model.Contrato;
import org.zerhusen.heilen.repository.ContratoRepository;
import org.zerhusen.model.security.UserData;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;

/**
 *
 * @author Ferenc
 */
@RestController
public class ContratoController {

    @Autowired
    private ContratoRepository repository;

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
    @RequestMapping(value = "/contrato/", method = GET)
    public Collection<Contrato> getContratos() {
        //JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request);         
        return repository.findAll();
    }

    //Buscar una contrato
    @CrossOrigin
    @RequestMapping(value = "/contrato/{id}", method = GET)
    public Optional<Contrato> getContrato(@PathVariable long id) {
        return repository.findById(id);
    }

    // Petición POST Agregar
    @CrossOrigin
    @RequestMapping(value = "/contrato/", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Contrato nuevoContrato(@Valid @RequestBody Contrato contrato) {

        JwtUser eluse = user.getAuthenticatedUser(tokenHeader, jwtTokenUtil, userDetailsService, request);
        Contrato elcontrato = new Contrato();

        elcontrato.setAceptada(false);
        elcontrato.setCalificada(false);
        elcontrato.setDetalle("primer detalle por back");
        elcontrato.setId_paciente(eluse.getId());
        elcontrato.setId_profesional(contrato.getId_profesional());
        elcontrato.setMonto(contrato.getMonto());
        elcontrato.setFecha(contrato.getFecha());
        elcontrato.setHora(contrato.getHora());
        elcontrato.setTerminada(false);
        elcontrato.setEnabled(true);
        repository.save(elcontrato);
        return elcontrato;
    }

    // Petición PUT Editar 
    @CrossOrigin
    @RequestMapping(value = "/contrato/{id}", method = PUT)
    public ResponseEntity<Optional<Contrato>> actualizarContrato(@Valid @PathVariable long id, @RequestBody Contrato actualizarContrato) {
        Optional<Contrato> contrato = repository.findById(id);
        if (contrato != null) {

            actualizarContrato.setId(id);
            repository.save(actualizarContrato);
            return new ResponseEntity<>(contrato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Petición DELETE(Eliminar)
    @CrossOrigin
    @RequestMapping(value = "/contrato/{id}", method = DELETE)
    public ResponseEntity<Optional<Contrato>> eliminarContrato(@PathVariable long id) {
        Optional<Contrato> contrato = repository.findById(id);
        repository.deleteById(id);
        if (contrato != null) {
            return new ResponseEntity<>(contrato, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Petición GET todos los contratos de un usuario paciente
    @CrossOrigin
    @RequestMapping(value = "/contrato/miscontratos/", method = GET)
    public Collection<Contrato> getContratosXUser() {
        JwtUser eluse = user.getAuthenticatedUser(tokenHeader, jwtTokenUtil, userDetailsService, request);
        return repository.ContratosXUser(eluse.getId());
    }

    // Petición GET a un profesional notificando una solicitud
    @CrossOrigin
    @RequestMapping(value = "/contrato/notifier/", method = GET)
    public Optional<Contrato> getNotifier() {
        JwtUser eluse = user.getAuthenticatedUser(tokenHeader, jwtTokenUtil, userDetailsService, request);
        return repository.ProNotifier(eluse.getId());
    }

    // Petición PUT para cambiar estados del contrato
    @CrossOrigin
    @RequestMapping(value = "/contrato/estados/{aceptada},{terminada},{calificada},{cancelada},{id}", method = PUT, produces = "application/json")
    public String desContrato(@PathVariable int aceptada, @PathVariable int terminada, @PathVariable int calificada, @PathVariable int cancelada, @PathVariable long id) {
        JsonObject res = new JsonObject();

        repository.DesContrato(0, aceptada, terminada, calificada, cancelada, id);

        res.addProperty("peticion_editada", true);
        return res.toString();
    }

    //suma de contratos de la semana a un profesional
    @CrossOrigin
    @RequestMapping(value = "/contrato/suma/", method = GET, produces = "application/json")
    public String SumaCont() {
        JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request);
        JsonObject res = new JsonObject();

        try {
            double sum = repository.SumaCont(eluse.getId());
            double comi = sum * 0.10;
            
            res.addProperty("suma_semana", sum);
            res.addProperty("comision", comi);

            return res.toString();

        } catch (Exception e) {
            
            res.addProperty("suma_semana", 0);
            res.addProperty("comision", 0);

            return res.toString();
        }

    }
    
    // Petición PUT para cambiar monto al contrato
    @CrossOrigin
    @RequestMapping(value = "/contrato/cMonto/{monto},{id}", method = PUT, produces = "application/json")
    public String addComision(@PathVariable int monto, @PathVariable long id) {
        JsonObject res = new JsonObject();

        double comision = monto * 0.10;
        
        repository.addComision(comision, id);

        res.addProperty("peticion_editada", true);
        return res.toString();
    }
    
    // Petición PUT para cambiar estados terminado del contrato
    @CrossOrigin
    @RequestMapping(value = "/contrato/estadoTer/{id}", method = PUT, produces = "application/json")
    public String TerContrato(@PathVariable long id) {
        JsonObject res = new JsonObject();

        repository.TerContrato(id);

        res.addProperty("peticion_editada", true);
        return res.toString();
    }
    
}
