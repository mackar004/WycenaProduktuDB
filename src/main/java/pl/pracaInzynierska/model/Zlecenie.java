/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author m
 */
@Entity
public class Zlecenie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dataZakonczenia;

    private Zlecenie() {
    }

    public Zlecenie(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataZakonczenia() {
        return dataZakonczenia;
    }

    public void setDataZakonczenia(LocalDate dataZakonczenia) {
        this.dataZakonczenia = dataZakonczenia;
    }

}
