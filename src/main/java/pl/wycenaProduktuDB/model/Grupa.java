package pl.wycenaProduktuDB.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author maciek
 */

@Entity
@Table(name = "grupa")
public class Grupa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazwa;

    @OneToMany(mappedBy = "grupa", fetch = FetchType.EAGER)   
    @Column(name = "Pracownicy")
    private List<Pracownik> pracownik;

    protected Grupa() {
    }

    public Grupa(String nazwa) {
        this.nazwa = nazwa;
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

    public List<Pracownik> getPracownik() {
        return pracownik;
    }

    public void setPracownik(List<Pracownik> pracownik) {
        this.pracownik = pracownik;
    }

}
