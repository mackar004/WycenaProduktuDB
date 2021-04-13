/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.repository;

import pl.pracaInzynierska.model.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author m
 */
@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long>{
    Uzytkownik findByUsername(String username);
    Uzytkownik getUzytkownikByUsername(String username);
    
}
