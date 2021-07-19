package pl.wycenaProduktuDB.repository;

import pl.wycenaProduktuDB.model.Firma;
import pl.wycenaProduktuDB.model.Inwestycja;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author m
 */
@Repository
public interface InwestycjaRepository extends JpaRepository<Inwestycja, Long> {

    Inwestycja findById(long id);
    List<Inwestycja> findByFirma(Firma firma);
    List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCase(String nazwaInwestycji);
    List<Inwestycja> findByInwestycjaNazwaContainsIgnoreCaseOrInwestycjaMiastoContainsIgnoreCase(String nazwaInwestycji, String miastoInwestycji);
    List<Inwestycja> findInwestycjaByInwestycjaNazwaContainsIgnoreCaseOrInwestycjaMiastoContainsIgnoreCaseOrFirmaNazwaFirmyContainsIgnoreCase(String filtr,String filtr2,String filtr3);
}
