/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.repository;

import java.util.List;
import pl.torun.roma.RoMa3.model.Wycena;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.torun.roma.RoMa3.model.Inwestycja;

/**
 *
 * @author m
 */
public interface WycenaRepository extends JpaRepository<Wycena, Long>{
    List<Wycena> findByInwestycja(Inwestycja inwestycja);
}
