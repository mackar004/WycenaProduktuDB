package pl.wycenaProduktuDB.repository;

import java.util.List;
import pl.wycenaProduktuDB.model.Wycena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wycenaProduktuDB.model.Inwestycja;

/**
 *
 * @author m
 */
@Repository
public interface WycenaRepository extends JpaRepository<Wycena, Long>{
    List<Wycena> findByInwestycja(Inwestycja inwestycja);
}
