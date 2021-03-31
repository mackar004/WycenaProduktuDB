/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.dane;

/**
 *
 * @author m
 */
public enum TypPrzekrycia {
    Korytkowo_Zbieżne, Kopuła_samonośna, Korytkowo_proste, Sandwich, Inne;
    
    @Override
    public String toString(){
        return name();
    }
}

