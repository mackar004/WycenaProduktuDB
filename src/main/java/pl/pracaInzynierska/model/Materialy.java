package pl.pracaInzynierska.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import pl.pracaInzynierska.dane.TypMaterialu;

/**
 *
 * @author m
 */
@Entity
public class Materialy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nazwa;
    private String cena;
    private LocalDate dataWprowadzenia;

    private TypMaterialu typMaterialu;

    @OneToMany(mappedBy = "materialy", fetch = FetchType.LAZY)
    private List<MaterialyUzyte> materialyUzyte;

    public Materialy() {
    }

    public Materialy(String nazwa, String cena) {
        this.nazwa = nazwa;
        this.cena = cena;
        this.dataWprowadzenia = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public List<MaterialyUzyte> getMaterialyUzyte() {
        return materialyUzyte;
    }

    public void setMaterialyUzyte(List<MaterialyUzyte> materialyUzyte) {
        this.materialyUzyte = materialyUzyte;
    }

    public LocalDate getDataWprowadzenia() {
        return dataWprowadzenia;
    }

    public void setDataWprowadzenia(LocalDate dataWprowadzenia) {
        this.dataWprowadzenia = dataWprowadzenia;
    }

    public TypMaterialu getTypMaterialu() {
        return typMaterialu;
    }

    public void setTypMaterialu(TypMaterialu typMaterialu) {
        this.typMaterialu = typMaterialu;
    }

    @Override
    public String toString() {
        return nazwa;
    }

}
