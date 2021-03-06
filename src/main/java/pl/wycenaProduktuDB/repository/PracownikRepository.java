package pl.wycenaProduktuDB.repository;

import java.util.List;
import pl.wycenaProduktuDB.model.Pracownik;
import pl.wycenaProduktuDB.model.Grupa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author maciek
 */
public interface PracownikRepository  extends JpaRepository<Pracownik, Long> {
    List <Pracownik> findById(int id);
    List <Pracownik> findByNazwiskoContainsOrImieContains(String lastname, String firstname);
    List <Pracownik> findByGrupa(Grupa grupa);
}
