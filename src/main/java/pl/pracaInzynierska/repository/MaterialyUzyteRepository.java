/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.repository;

import java.util.List;
import pl.pracaInzynierska.model.MaterialyUzyte;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
