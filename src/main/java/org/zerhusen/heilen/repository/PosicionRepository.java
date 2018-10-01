/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.repository;

import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.heilen.model.Posicion;

/**
 *
 * @author Ferenc
 */
public interface PosicionRepository extends JpaRepository<Posicion, Long>{
 
    @Query(value = "select * from posicion where id_usuario = any "
            + "(select id from user WHERE id = any "
            + "(select user_id from user_authority WHERE authority_id = 3))", nativeQuery = true)
    Collection<Posicion> ListaProf();
    
    @Transactional
    @Modifying
    @Query(value = "update posicion set lat = ?1, lng = ?2 where id_usuario = ?3", nativeQuery = true)
    void UserEditPosition(double lat, double lng, long id);
}
