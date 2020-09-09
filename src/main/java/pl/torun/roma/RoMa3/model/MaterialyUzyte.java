/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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

    private MaterialyUzyte() {

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

}
