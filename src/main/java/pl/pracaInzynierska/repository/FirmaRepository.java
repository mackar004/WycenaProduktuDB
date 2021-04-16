package pl.pracaInzynierska.repository;

import pl.pracaInzynierska.model.Firma;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author m
 */
@Repository
public interface FirmaRepository extends JpaRepository<Firma, Long>{
    List<Firma> findById(long id);
    List<Firma> findByNazwaFirmyContainsIgnoreCase(String nazwaFirmy);
    Firma findByNazwaFirmy(String nazwaFirmy);
}
