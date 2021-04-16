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

