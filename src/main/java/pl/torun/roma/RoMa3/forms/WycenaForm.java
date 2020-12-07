/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.forms;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import pl.torun.roma.RoMa3.model.Inwestycja;
import pl.torun.roma.RoMa3.model.Wycena;
import pl.torun.roma.RoMa3.model.dane.TypPrzekrycia;
import pl.torun.roma.RoMa3.repository.InwestycjaRepository;
import pl.torun.roma.RoMa3.repository.WycenaRepository;

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class WycenaForm extends VerticalLayout implements KeyNotifier {

    private final WycenaRepository wycenaRepository;
    private final InwestycjaRepository inwestycjaRepository;

    private Inwestycja inwestycja;
    private Wycena wycena;

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final Button edit = new Button("Edytuj");

    ComboBox<TypPrzekrycia> typPrzekrycia = new ComboBox<>("Typ przekrycia", TypPrzekrycia.values());
    private final TextField nazwaInwestycji = new TextField("Inwestycja");
    private final TextField dlugosc = new TextField("Długość");
    private final TextField szerokosc = new TextField("Szerokość");
    private final TextField srednica = new TextField("Średnica");

    private final TextField laminat = new TextField("Laminat");
    private final TextField sandwich = new TextField("Sandwich");
    private final TextField laminatSztuki = new TextField("Elementy laminat");
    private final TextField sandwichSztuki = new TextField("Elementy sandwich");

    private final HorizontalLayout typWymiary = new HorizontalLayout(typPrzekrycia, srednica, dlugosc, szerokosc);
    private final HorizontalLayout laminatIlosc = new HorizontalLayout(laminat, laminatSztuki);
    private final HorizontalLayout sandwichIlosc = new HorizontalLayout(sandwich, sandwichSztuki);

    Binder<Wycena> binderWycena = new Binder<>(Wycena.class);
    //private final Grid materialy;
    private WycenaForm.ChangeHandler changeHandler;

    @Autowired
    public WycenaForm(WycenaRepository wycenaRepository, InwestycjaRepository inwestycjaRepository) {
        this.wycenaRepository = wycenaRepository;
        this.inwestycjaRepository = inwestycjaRepository;

        add(nazwaInwestycji, typWymiary, laminatSztuki, sandwichSztuki);//, materialyGrid);

        
        binderWycena.bind(typPrzekrycia, Wycena::getTypPrzekrycia, Wycena::setTypPrzekrycia);
        binderWycena.bind(dlugosc, Wycena::getDlugosc, Wycena::setDlugosc);
        binderWycena.forField(szerokosc)
                .withConverter(new StringToDoubleConverter("Potrzebna liczba!"))
                .bind(Wycena::getSzerokosc, Wycena::setSzerokosc);
        binderWycena.forField(srednica)
                .withConverter(new StringToDoubleConverter("Potrzebna liczba!"))
                .bind(Wycena::getSrednica, Wycena::setSrednica);
        
        
        //Czy to potrzebne?
        //https://vaadin.com/forum/thread/15385912/15640053
        //binderWycena.bindInstanceFields(this);
    }

    void save() {
        wycenaRepository.save(wycena);
        //       inwestycjeGrid.select(null);
        changeHandler.onChange();
    }

    void delete() {
        //       inwestycjeGrid.select(null);
        wycenaRepository.delete(wycena);
        changeHandler.onChange();
    }

    void cancel() {
        //      inwestycjeGrid.select(null);
        changeHandler.onChange();

    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editWycena(Wycena w, Inwestycja inwestycja) {
        if (w == null) {
            setVisible(false);
            return;
        }
        this.inwestycja = inwestycja;
        //nazwaFirmy.setValue(this.firma.toString().replace("[", "").replace("]", ""));
        final boolean wycenaIstnieje = w.getId() != null;
        if (wycenaIstnieje) {
            wycena = wycenaRepository.findById(w.getId()).get();
            delete.setEnabled(true);
        } else {
            wycena = w;
        }

        binderWycena.setBean(wycena);
        setVisible(true);

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }
}