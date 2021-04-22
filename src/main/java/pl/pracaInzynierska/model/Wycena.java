package pl.pracaInzynierska.model;

import java.sql.Time;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import pl.pracaInzynierska.dane.TypPrzekrycia;

/**
 *
 * @author m
 */
@Entity
public class Wycena {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private TypPrzekrycia typPrzekrycia;
    private Integer srednica;
    private Integer dlugosc;
    private Integer szerokosc;

    private Integer iloscSandwich;
    private Integer iloscLaminat;

    private Double cenaKoncowa;
    private Double marza;

    private Time dataWyceny;
    private Boolean zaakceptowano;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wycena", fetch = FetchType.LAZY)
    private List<MaterialyUzyte> materialyUzyte;

    @ManyToOne
    @JoinColumn(name = "inwestycja_id")
    private Inwestycja inwestycja;

//    @ManyToOne
//    @JoinColumn(name = "pracownik_id")
//    private Pracownik pracownik;

    private Wycena() {
    }

    public Wycena(Integer dlugosc, Integer szerokosc, Integer srednica,
            Integer iloscSandwich, Integer iloscLaminat, Double cenaKoncowa,
            Double marza, Inwestycja inwestycja) {
        this.dlugosc = dlugosc;
        this.szerokosc = szerokosc;
        this.srednica = srednica;
        this.iloscSandwich = iloscSandwich;
        this.iloscLaminat = iloscLaminat;
        this.cenaKoncowa = cenaKoncowa;
        this.marza = marza;
        this.inwestycja = inwestycja;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypPrzekrycia getTypPrzekrycia() {
        return typPrzekrycia;
    }

    public void setTypPrzekrycia(TypPrzekrycia typPrzekrycia) {
        this.typPrzekrycia = typPrzekrycia;
    }

    public Integer getSrednica() {
        return srednica;
    }

    public void setSrednica(Integer srednica) {
        this.srednica = srednica;
    }

    public Integer getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(Integer dlugosc) {
        this.dlugosc = dlugosc;
    }

    public Integer getSzerokosc() {
        return szerokosc;
    }

    public void setSzerokosc(Integer szerokosc) {
        this.szerokosc = szerokosc;
    }

    public Integer getIloscSandwich() {
        return iloscSandwich;
    }

    public void setIloscSandwich(Integer iloscSandwich) {
        this.iloscSandwich = iloscSandwich;
    }

    public Integer getIloscLaminat() {
        return iloscLaminat;
    }

    public void setIloscLaminat(Integer iloscLaminat) {
        this.iloscLaminat = iloscLaminat;
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

//    public Pracownik getPracownik() {
//        return pracownik;
//    }
//
//    public void setPracownik(Pracownik pracownik) {
//        this.pracownik = pracownik;
//    }
}
