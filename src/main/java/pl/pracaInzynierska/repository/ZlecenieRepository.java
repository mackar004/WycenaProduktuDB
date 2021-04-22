package pl.pracaInzynierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pracaInzynierska.model.Zlecenie;

/**
 *
 * @author m
 */
public interface ZlecenieRepository extends JpaRepository<Zlecenie, Long> {
    
}
