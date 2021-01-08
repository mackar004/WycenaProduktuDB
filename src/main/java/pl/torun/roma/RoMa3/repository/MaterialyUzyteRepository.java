/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.repository;

import java.util.List;
import pl.torun.roma.RoMa3.model.MaterialyUzyte;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.torun.roma.RoMa3.model.Wycena;

/**
 *
 * @author m
 */
public interface MaterialyUzyteRepository extends JpaRepository<MaterialyUzyte, Long>{
    List<MaterialyUzyte> findByWycena(Wycena wycena);
    List<MaterialyUzyte> findByWycenaAndIsNew(Wycena wycena, Boolean isNew);
    List<MaterialyUzyte> findByWycenaOrWycena(Wycena wycena, Wycena wycena2);
}
