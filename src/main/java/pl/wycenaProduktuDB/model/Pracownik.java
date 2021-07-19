package pl.wycenaProduktuDB.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import pl.wycenaProduktuDB.dane.Stanowisko;

/**
 *
 * @author maciek
 */
@Entity
public class Pracownik {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String imie;
    private String nazwisko;
    @Column(nullable = true)
    private Stanowisko obecneStanowisko;
    private int premia;
    private String pesel;

    @ManyToOne
    @JoinColumn(name = "grupa_id")
    private Grupa grupa;

    private boolean zatrudniony;

    @OneToMany(mappedBy = "pracownik", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Notatka> notatka;

//    @OneToMany(mappedBy = "pracownik", fetch = FetchType.EAGER)
//    private Wycena wycena;

    protected Pracownik() {
    }

    public Pracownik(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public Pracownik(String imie, String nazwisko, Grupa grupa) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.grupa = grupa;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Stanowisko getObecneStanowisko() {
        return obecneStanowisko;
    }

    public void setObecneStanowisko(Stanowisko obecneStanowisko) {
        this.obecneStanowisko = obecneStanowisko;
    }

    public int getPremia() {
        return premia;
    }

    public void setPremia(int premia) {
        this.premia = premia;
    }

    public Grupa getGrupa() {
        return grupa;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public boolean isZatrudniony() {
        return zatrudniony;
    }

    public void setZatrudniony(boolean zatrudniony) {
        this.zatrudniony = zatrudniony;
    }

    public List<Notatka> getNotatka() {
        return notatka;
    }

    public void setNotatka(List<Notatka> notatka) {
        this.notatka = notatka;
    }

    @Override
    public String toString() {
        return nazwisko + " " + imie.charAt(0) + ".";
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

//    public Wycena getWycena() {
//        return wycena;
//    }
//
//    public void setWycena(Wycena wycena) {
//        this.wycena = wycena;
//    }

}
