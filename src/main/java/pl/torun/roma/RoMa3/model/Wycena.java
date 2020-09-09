/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.model;

import java.sql.Time;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author m
 */
@Entity
@Table(name = "Wycena")
public class Wycena {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //ksztalt
    private Double srednica;
    private Double dlugosc;
    private Double szerokosc;
    private Integer iloscElementow;

    private Double cenaKoncowa;
    private Double marza;

    private Time dataWyceny;
    private Boolean zaakceptowano;

    @OneToMany(mappedBy = "wycena", fetch = FetchType.LAZY)
    private List<MaterialyUzyte> materialyUzyte;

    @ManyToOne
    @JoinColumn(name = "inwestycja_id")
    private Inwestycja inwestycja;

    private Wycena() {
    }

    private Wycena(Long id, Double srednica, Integer iloscElementow,
            Double cenaKoncowa, Double marza, Time dataWyceny,
            Boolean zaakceptowano, List<MaterialyUzyte> materialyUzyte) {
        this.id = id;
        this.srednica = srednica;
        this.iloscElementow = iloscElementow;
        this.cenaKoncowa = cenaKoncowa;
        this.marza = marza;
        this.dataWyceny = dataWyceny;
        this.zaakceptowano = zaakceptowano;
        this.materialyUzyte = materialyUzyte;
    }

    public Wycena(Long id, Double dlugosc, Double szerokosc,
            Integer iloscElementow, Double cenaKoncowa, Double marza,
            Time dataWyceny, Boolean zaakceptowano,
            List<MaterialyUzyte> materialyUzyte) {
        this.id = id;
        this.dlugosc = dlugosc;
        this.szerokosc = szerokosc;
        this.iloscElementow = iloscElementow;
        this.cenaKoncowa = cenaKoncowa;
        this.marza = marza;
        this.dataWyceny = dataWyceny;
        this.zaakceptowano = zaakceptowano;
        this.materialyUzyte = materialyUzyte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSrednica() {
        return srednica;
    }

    public void setSrednica(Double srednica) {
        this.srednica = srednica;
    }

    public Double getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(Double dlugosc) {
        this.dlugosc = dlugosc;
    }

    public Double getSzerokosc() {
        return szerokosc;
    }

    public void setSzerokosc(Double szerokosc) {
        this.szerokosc = szerokosc;
    }

    public Integer getIloscElementow() {
        return iloscElementow;
    }

    public void setIloscElementow(Integer iloscElementow) {
        this.iloscElementow = iloscElementow;
    }

    public Double getCenaKoncowa() {
        return cenaKoncowa;
    }

    public void setCenaKoncowa(Double cenaKoncowa) {
        this.cenaKoncowa = cenaKoncowa;
    }

    public Double getMarza() {
        return marza;
    }

    public void setMarza(Double marza) {
        this.marza = marza;
    }

    public Time getDataWyceny() {
        return dataWyceny;
    }

    public void setDataWyceny(Time dataWyceny) {
        this.dataWyceny = dataWyceny;
    }

    public Boolean getZaakceptowano() {
        return zaakceptowano;
    }

    public void setZaakceptowano(Boolean zaakceptowano) {
        this.zaakceptowano = zaakceptowano;
    }

    public List<MaterialyUzyte> getMaterialyUzyte() {
        return materialyUzyte;
    }

    public void setMaterialyUzyte(List<MaterialyUzyte> materialyUzyte) {
        this.materialyUzyte = materialyUzyte;
    }

    public Inwestycja getInwestycja() {
        return inwestycja;
    }

    public void setInwestycja(Inwestycja inwestycja) {
        this.inwestycja = inwestycja;
    }

}
