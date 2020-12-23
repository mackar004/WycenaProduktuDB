/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.repository;

import pl.torun.roma.RoMa3.model.Materialy;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author m
 */
public interface MaterialyRepository extends JpaRepository<Materialy, Long>{
    List<Materialy> findById(long id);
    List<Materialy> findByNazwa(String name);
    List<Materialy> findByNazwaContainsIgnoreCase(String name);
    List<Materialy> findByTypMaterialu(Enum name);
    List<Materialy> findByTypMaterialuOrTypMaterialu(Enum name, Enum name2);
}
