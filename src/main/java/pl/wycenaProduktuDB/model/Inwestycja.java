package pl.wycenaProduktuDB.model;

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

/**
 *
 * @author m
 */
@Entity
@Table(name = "Inwestycja")
public class Inwestycja {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inwestycja_id;
    private String inwestycjaMiasto;
    private String inwestycjaUlica;
    private String inwestycjaKod;
    private String inwestycjaNazwa;
    private String inwestycjaOpis;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inwestycja", fetch = FetchType.EAGER)
    private List<Wycena> wycena;
    
    @ManyToOne
    @JoinColumn(name = "firma_id")//, nullable = false)
    private Firma firma;
    
    
    protected Inwestycja() {
    }

    public Inwestycja(String inwestycjaNazwa, String inwestycjaMiasto, Firma firma) {
        this.inwestycjaNazwa = inwestycjaNazwa;
        this.inwestycjaMiasto = inwestycjaMiasto;
        this.firma = firma;
    }

    public Long getInwestycja_id() {
        return inwestycja_id;
    }

    public void setInwestycja_id(Long inwestycja_id) {
        this.inwestycja_id = inwestycja_id;
    }

    public String getInwestycjaMiasto() {
        return inwestycjaMiasto;
    }

    public void setInwestycjaMiasto(String inwestycjaMiasto) {
        this.inwestycjaMiasto = inwestycjaMiasto;
    }

    public String getInwestycjaUlica() {
        return inwestycjaUlica;
    }

    public void setInwestycjaUlica(String inwestycjaUlica) {
        this.inwestycjaUlica = inwestycjaUlica;
    }

    public String getInwestycjaKod() {
        return inwestycjaKod;
    }

    public void setInwestycjaKod(String inwestycjaKod) {
        this.inwestycjaKod = inwestycjaKod;
    }

    public String getInwestycjaNazwa() {
        return inwestycjaNazwa;
    }

    public void setInwestycjaNazwa(String inwestycjaNazwa) {
        this.inwestycjaNazwa = inwestycjaNazwa;
    }

    public String getInwestycjaOpis() {
        return inwestycjaOpis;
    }

    public void setInwestycjaOpis(String inwestycjaOpis) {
        this.inwestycjaOpis = inwestycjaOpis;
    }

    public List<Wycena> getWycena() {
        return wycena;
    }

    public void setWycena(List<Wycena> wycena) {
        this.wycena = wycena;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

        @Override
    public String toString() {
        return this.inwestycjaNazwa;
    }
    
}
