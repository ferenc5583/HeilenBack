/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.heilen.model.Contrato;

/**
 *
 * @author Ferenc
 */
public interface ContratoRepository extends JpaRepository<Contrato, Long>{
 
    @Query(value = "select * from contrato where id_paciente =?1 or id_profesional =?1 order by id desc", nativeQuery = true)
    Collection<Contrato> ContratosXUser(long id);
}
