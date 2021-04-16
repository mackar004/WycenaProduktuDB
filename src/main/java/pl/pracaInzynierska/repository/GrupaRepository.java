package pl.pracaInzynierska.repository;

import java.util.List;
import pl.pracaInzynierska.model.Grupa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author maciek
 */
public interface GrupaRepository extends JpaRepository<Grupa, Long>{
    List<Grupa> findByNazwaContains(String nazwa);
}
