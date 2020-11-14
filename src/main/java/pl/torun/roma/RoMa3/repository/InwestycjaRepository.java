/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.repository;

import pl.torun.roma.RoMa3.model.Firma;
import pl.torun.roma.RoMa3.model.Inwestycja;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author m
 */

public interface InwestycjaRepository extends JpaRepository<Inwestycja, Long> {
    
    List<Inwestycja> findById(long id);
    List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCase(String nazwaInwestycji);
    List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCaseOrInwestycjaMiastoContainsIgnoreCase(String nazwaInwestycji, String miastoInwestycji);
    //List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCaseOrFirma(String nazwaInwestycji, Firma firma);
    List<Inwestycja> findByFirma(Firma firma);
}
