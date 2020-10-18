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

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class InwestycjaForm extends VerticalLayout implements KeyNotifier {

    private final InwestycjaRepository inwestycjaRepo;

    private Inwestycja inwestycja;
    
    //private final String firmaNazwa;

    private final TextField nazwaInwestycji = new TextField("Nazwa");
    private final TextField miasto = new TextField("Miasto");
    private final TextField firma = new TextField("Firma");

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final Button edit = new Button("Edytuj");
    private final HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);
    private final HorizontalLayout formularz = new HorizontalLayout(nazwaInwestycji, miasto, firma);

    Binder<Inwestycja> binderInwestycja = new Binder<>(Inwestycja.class);

    private InwestycjaForm.ChangeHandler changeHandler;

    final Grid inwestycjeGrid;
    
    @Autowired
    public InwestycjaForm(InwestycjaRepository inwestycjaRepo) {
        //dopisać przekazywanie do konstruktora inwestycji Stringa z nazwą firmy.
        this.inwestycjaRepo = inwestycjaRepo;
        //this.firmaNazwa = firmaNazwa;
        
        delete.setEnabled(false);
        firma.setEnabled(false);

        this.inwestycjeGrid = new Grid<>(Inwestycja.class);
        inwestycjeGrid.setColumns("inwestycjaNazwa","inwestycjaMiasto");
        inwestycjeGrid.getColumnByKey("inwestycjaNazwa").setHeader("Nazwa");
        inwestycjeGrid.getColumnByKey("inwestycjaMiasto").setHeader("Miasto");
        
        add(buttonBar, formularz, inwestycjeGrid);

        binderInwestycja.bind(nazwaInwestycji, Inwestycja::getInwestycjaNazwa, Inwestycja::setInwestycjaNazwa);
        binderInwestycja.bind(miasto, Inwestycja::getInwestycjaMiasto, Inwestycja::setInwestycjaMiasto);

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
        inwestycjeGrid.select(null);
        changeHandler.onChange();
    }

    void delete() {
        inwestycjeGrid.select(null);
        inwestycjaRepo.delete(inwestycja);
        changeHandler.onChange();
    }

    void cancel() {
        inwestycjeGrid.select(null);
        changeHandler.onChange();
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editInwestycja(Inwestycja i) {
        if (i == null) {
            setVisible(false);
            return;
        }
        final boolean inwestycjaIstnieje = i.getInwestycja_id() != null;
        if (inwestycjaIstnieje) {
            inwestycja = inwestycjaRepo.findById(i.getInwestycja_id()).get();
            delete.setEnabled(true);
        } else {
            inwestycja = i;
            nazwaInwestycji.focus();
        }

        binderInwestycja.setBean(inwestycja);
        setVisible(true);

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
