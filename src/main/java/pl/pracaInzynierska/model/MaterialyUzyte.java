package pl.pracaInzynierska.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author m
 */
@Entity
@Table(name = "materialyUzyte")
public class MaterialyUzyte {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "wycena_id")
    private Wycena wycena;

    @ManyToOne
    @JoinColumn(name = "materialy_id")
    private Materialy materialy;

    private Double iloscMaterialu;
    
    private Boolean isNew;

    private MaterialyUzyte() {

    }

    public MaterialyUzyte(Materialy materialy, Double iloscMaterialu) {
        this.materialy = materialy;
        this.iloscMaterialu = iloscMaterialu;
    }
    
    public MaterialyUzyte(Materialy materialy, Double iloscMaterialu, Boolean isNew) {
        this.materialy = materialy;
        this.iloscMaterialu = iloscMaterialu;
        this.isNew = isNew;
    }

    public MaterialyUzyte(Wycena wycena, Materialy materialy, Double iloscMaterialu) {
        this.wycena = wycena;
        this.materialy = materialy;
        this.iloscMaterialu = iloscMaterialu;
    }
    
    public MaterialyUzyte(Wycena wycena, Materialy materialy, Double iloscMaterialu, Boolean isNew) {
        this.wycena = wycena;
        this.materialy = materialy;
        this.iloscMaterialu = iloscMaterialu;
        this.isNew = isNew;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Wycena getWycena() {
        return wycena;
    }

    public void setWycena(Wycena wycena) {
        this.wycena = wycena;
    }

    public Materialy getMaterialy() {
        return materialy;
    }

    public void setMaterialy(Materialy materialy) {
        this.materialy = materialy;
    }

    public Double getIloscMaterialu() {
        return iloscMaterialu;
    }

    public void setIloscMaterialu(Double iloscMaterialu) {
        this.iloscMaterialu = iloscMaterialu;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

}
