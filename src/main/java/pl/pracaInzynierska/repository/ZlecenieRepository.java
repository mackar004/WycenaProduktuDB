/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pracaInzynierska.model.Zlecenie;

/**
 *
 * @author m
 */
public interface ZlecenieRepository extends JpaRepository<Zlecenie, Long> {
    
}
