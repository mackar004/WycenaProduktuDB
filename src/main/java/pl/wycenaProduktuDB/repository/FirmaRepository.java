package pl.wycenaProduktuDB.repository;

import pl.wycenaProduktuDB.model.Firma;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author m
 */
@Repository
public interface FirmaRepository extends JpaRepository<Firma, Long>{
    List<Firma> findByNazwaFirmyContainsIgnoreCase(String nazwaFirmy);
    Firma findByNazwaFirmy(String nazwaFirmy);
    Firma findById(long id);
}
