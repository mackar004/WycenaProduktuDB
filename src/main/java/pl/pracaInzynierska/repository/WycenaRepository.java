/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.repository;

import java.util.List;
import pl.pracaInzynierska.model.Wycena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pracaInzynierska.model.Inwestycja;

/**
 *
 * @author m
 */
@Repository
public interface WycenaRepository extends JpaRepository<Wycena, Long>{
    List<Wycena> findByInwestycja(Inwestycja inwestycja);
}
