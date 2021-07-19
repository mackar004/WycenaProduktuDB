package pl.wycenaProduktuDB.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author maciek
 */

@Entity
public class Notatka {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tresc;
    
    @ManyToOne
    @JoinColumn(name="pracownik_id")
    private Pracownik pracownik;
    
    protected Notatka() {}
    
    public Notatka(String tresc){
        this.tresc = tresc;
    }
    
    public Notatka(String tresc, Pracownik pracownik){
        this.tresc = tresc;
        this.pracownik = pracownik;
    }
    
    public Notatka(String tresc, boolean positive){
        this.tresc = tresc;
        //this.positive = positive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }
    

}
