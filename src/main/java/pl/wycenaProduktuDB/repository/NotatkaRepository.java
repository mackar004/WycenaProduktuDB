package pl.wycenaProduktuDB.repository;

import java.util.List;
import pl.wycenaProduktuDB.model.Notatka;
import pl.wycenaProduktuDB.model.Pracownik;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author maciek
 */
public interface NotatkaRepository extends JpaRepository<Notatka, Long> {
    List <Notatka> findById(int id);
    List <Notatka> findByTrescContainsIgnoreCase(String tresc);
    List <Notatka> findByPracownik(Pracownik pracownik);
}
