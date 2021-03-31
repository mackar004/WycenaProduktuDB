/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.repository;

import pl.pracaInzynierska.model.Firma;
import pl.pracaInzynierska.model.Inwestycja;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author m
 */

public interface InwestycjaRepository extends JpaRepository<Inwestycja, Long> {
    
    Inwestycja findById(long id);
    List<Inwestycja> findByFirma(Firma firma);
    List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCase(String nazwaInwestycji);
    List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCaseOrInwestycjaMiastoContainsIgnoreCase(String nazwaInwestycji, String miastoInwestycji);
//    List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCaseOrInwestycjaMiastoContainsIgnoreCaseOrFirmaContainsIgnoreCase(String nazwaInwestycji, String miastoInwestycji, String firma);
    //List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCaseOrFirma(String nazwaInwestycji, Firma firma);
}
