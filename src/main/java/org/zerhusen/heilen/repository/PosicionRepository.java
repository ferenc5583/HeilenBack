/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.heilen.model.Posicion;

/**
 *
 * @author Ferenc
 */
public interface PosicionRepository extends JpaRepository<Posicion, Long>{
 
}
