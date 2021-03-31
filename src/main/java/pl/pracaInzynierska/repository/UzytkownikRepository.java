/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.repository;

import pl.pracaInzynierska.model.Uzytkownik;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author m
 */
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Long>{
    
}
