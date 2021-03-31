/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.repository;

import pl.pracaInzynierska.model.Firma;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author m
 */
public interface FirmaRepository extends JpaRepository<Firma, Long>{
    List<Firma> findById(long id);
    List<Firma> findByNazwaFirmyContainsIgnoreCase(String nazwaFirmy);
    Firma findByNazwaFirmy(String nazwaFirmy);
}
