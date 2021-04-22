package pl.pracaInzynierska.repository;

import java.util.List;
import javax.transaction.Transactional;
import pl.pracaInzynierska.model.MaterialyUzyte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import pl.pracaInzynierska.model.Wycena;

/**
 *
 * @author m
 */
@Repository
public interface MaterialyUzyteRepository extends JpaRepository<MaterialyUzyte, Long>{
    List<MaterialyUzyte> findByWycena(Wycena wycena);
    List<MaterialyUzyte> findByWycenaAndIsNew(Wycena wycena, Boolean isNew);
    List<MaterialyUzyte> findByWycenaOrWycena(Wycena wycena, Wycena wycena2);
    @Modifying
    @Transactional
    void deleteByWycenaAndIsNew(Wycena wycena, Boolean isNew);
}
