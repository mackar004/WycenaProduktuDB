/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

Wykonać odpowiednik
firmaForm.editFirma
dla utworzenia nowej inwestycji. Przekazać firmę (lub wyszukać potem w repo odpowiednią)
do konstruktora inwestycji, i wyświetlić jego toString w polu z Firmą.




 */
package pl.torun.roma.RoMa3.forms;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import pl.torun.roma.RoMa3.model.Inwestycja;
import pl.torun.roma.RoMa3.repository.InwestycjaRepository;
import com.vaadin.flow.data.binder.Binder;
import pl.torun.roma.RoMa3.model.Firma;
import pl.torun.roma.RoMa3.repository.WycenaRepository;

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class InwestycjaForm extends VerticalLayout implements KeyNotifier {

    private final InwestycjaRepository inwestycjaRepo;
//    private final WycenaRepository wycenaRepository;

    private Firma firma;
    private Inwestycja inwestycja;

    //private final String firmaNazwa;
    private final TextField inwestycjaNazwa = new TextField("Nazwa");
    private final TextField inwestycjaMiasto = new TextField("Miasto");
    private final TextField nazwaFirmy = new TextField("Firma");

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final Button edit = new Button("Edytuj");
    private final HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);
    private final HorizontalLayout formularz = new HorizontalLayout(inwestycjaNazwa, inwestycjaMiasto, nazwaFirmy);

    Binder<Inwestycja> binderInwestycja = new Binder<>(Inwestycja.class);

    private InwestycjaForm.ChangeHandler changeHandler;

//    final Grid inwestycjeGrid;
    @Autowired
    public InwestycjaForm(InwestycjaRepository inwestycjaRepo){
//    public InwestycjaForm(InwestycjaRepository inwestycjaRepo, WycenaRepository wycenaRepository) {
        
//        this.wycenaRepository = wycenaRepository;
        this.inwestycjaRepo = inwestycjaRepo;

        delete.setEnabled(false);
        nazwaFirmy.setEnabled(false);

//        this.inwestycjeGrid = new Grid<>(Inwestycja.class);
//        inwestycjeGrid.setColumns("inwestycjaNazwa", "inwestycjaMiasto");
//        inwestycjeGrid.getColumnByKey("inwestycjaNazwa").setHeader("Nazwa");
//        inwestycjeGrid.getColumnByKey("inwestycjaMiasto").setHeader("Miasto");
        add(buttonBar, formularz); //, inwestycjeGrid);

        //binderInwestycja.bindInstanceFields(this);
        binderInwestycja.bind(inwestycjaNazwa, Inwestycja::getInwestycjaNazwa, Inwestycja::setInwestycjaNazwa);
        binderInwestycja.bind(inwestycjaMiasto, Inwestycja::getInwestycjaMiasto, Inwestycja::setInwestycjaMiasto);
        //binderInwestycja.bind(nazwaFirmy, Inwestycja::getFirma, Inwestycja::setFirma);

        setSpacing(false);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        //Zapisywanie na klawisz Enter
        //addKeyPressListener(Key.ENTER, e -> save());
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void save() {
        inwestycjaRepo.save(inwestycja);
//        inwestycjeGrid.select(null);
        changeHandler.onChange();
    }

    void delete() {
        inwestycja.setFirma(null);
        inwestycjaRepo.save(inwestycja);
        inwestycjaRepo.delete(inwestycja);
        inwestycjaRepo.flush();
        changeHandler.onChange();
        /*
                wycenaRepository.save(wycena);
        wycena.setInwestycja(null);
        wycenaRepository.delete(wycena);
        System.out.println(wycena.getInwestycja());
        System.out.println(wycenaRepository.findAll());
        //clearFields();
        changeHandler.onChange();
        */
    }

    void cancel() {
//        inwestycjeGrid.select(null);
        changeHandler.onChange();
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editInwestycja(Inwestycja i, Firma firma) {
        if (i == null) {
            setVisible(false);
            return;
        }
        this.firma = firma;
        //if (firma != null) nazwaFirmy.setValue(this.firma.toString().replace("[", "").replace("]", ""));
        if (firma != null) nazwaFirmy.setValue(this.firma.getNazwaFirmy());
        final boolean inwestycjaIstnieje = i.getInwestycja_id() != null;
        if (inwestycjaIstnieje) {
            inwestycja = inwestycjaRepo.findById(i.getInwestycja_id()).get();
            delete.setEnabled(true);
        } else {
            inwestycja = i;
            inwestycjaNazwa.focus();
        }
        binderInwestycja.setBean(inwestycja);
        setVisible(true);

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
