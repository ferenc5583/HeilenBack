/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.heilen.model.Especialidad;

/**
 *
 * @author Ferenc
 */
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long>{
 
    
}
