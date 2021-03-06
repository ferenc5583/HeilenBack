/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zerhusen.heilen.repository;

import java.util.Collection;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.heilen.model.Contrato;

/**
 *
 * @author Ferenc
 */
public interface ContratoRepository extends JpaRepository<Contrato, Long>{
 
    @Query(value = "select * from contrato where aceptada=1 and (id_paciente =?1 or id_profesional =?1) order by id desc", nativeQuery = true)
    Collection<Contrato> ContratosXUser(long id);
    
    @Query(value = "select * from contrato where id_profesional =?1 and enabled = 1", nativeQuery = true)
    Optional<Contrato> ProNotifier(long id);
    
    @Transactional
    @Modifying
    @Query(value = "update contrato set enabled =?1, aceptada=?2, terminada=?3 , calificada=?4, cancelada=?5 where id =?6", nativeQuery = true)
    void DesContrato(int enabled, int aceptada, int terminada, int calificada, int cancelada, long id);
    
    @Transactional
    @Query(value = "SELECT SUM(monto) FROM contrato WHERE (fecha BETWEEN '17-12-2018' and '23-12-2018' and id_profesional =?1 and terminada =1 or cancelada=1)", nativeQuery = true)
    double SumaCont(long id);
    
    @Transactional
    @Modifying
    @Query(value = "update contrato set monto =?1 where id =?2", nativeQuery = true)
    void addComision(double monto, long id);
    
    @Transactional
    @Modifying
    @Query(value = "update contrato set terminada=1 where id =?1", nativeQuery = true)
    void TerContrato(long id);
}
