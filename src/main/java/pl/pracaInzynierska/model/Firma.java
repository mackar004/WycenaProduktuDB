package pl.pracaInzynierska.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author m
 */
@Entity
@Table(name = "Firma")
public class Firma {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nazwaFirmy;
    private String kraj;
    private String ulica;
    private String miasto;
    private String kodPocztowy;
    private Integer NIP;
    private Integer telefon;
    private String email;
    private String osobaKontaktowa;
    private Integer osobaTelefon;
    private String osobaEmail;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firma", fetch = FetchType.EAGER)
    private List<Inwestycja> inwestycja;

    public Firma() {
    }

    public Firma(String nazwaFirmy, String miasto, String kraj) {
        this.nazwaFirmy = nazwaFirmy;
        this.miasto = miasto;
        this.kraj = kraj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public void setNazwaFirmy(String nazwaFirmy) {
        this.nazwaFirmy = nazwaFirmy;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public Integer getNIP() {
        return NIP;
    }

    public void setNIP(Integer NIP) {
        this.NIP = NIP;
    }

    public Integer getTelefon() {
        return telefon;
    }

    public void setTelefon(Integer telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOsobaKontaktowa() {
        return osobaKontaktowa;
    }

    public void setOsobaKontaktowa(String osobaKontaktowa) {
        this.osobaKontaktowa = osobaKontaktowa;
    }

    public Integer getOsobaTelefon() {
        return osobaTelefon;
    }

    public void setOsobaTelefon(Integer osobaTelefon) {
        this.osobaTelefon = osobaTelefon;
    }

    public String getOsobaEmail() {
        return osobaEmail;
    }

    public void setOsobaEmail(String osobaEmail) {
        this.osobaEmail = osobaEmail;
    }

    public List<Inwestycja> getInwestycja() {
        return inwestycja;
    }

    public void setInwestycja(List<Inwestycja> inwestycja) {
        this.inwestycja = inwestycja;
    }

    @Override
    public String toString() {
        return this.nazwaFirmy;
    }

}
