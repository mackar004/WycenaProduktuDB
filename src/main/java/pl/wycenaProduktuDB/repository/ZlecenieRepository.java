package pl.wycenaProduktuDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wycenaProduktuDB.model.Zlecenie;

/**
 *
 * @author m
 */
public interface ZlecenieRepository extends JpaRepository<Zlecenie, Long> {
    
}
