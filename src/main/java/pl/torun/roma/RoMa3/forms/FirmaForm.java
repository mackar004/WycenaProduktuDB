/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.forms;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import pl.torun.roma.RoMa3.model.Firma;
import pl.torun.roma.RoMa3.repository.FirmaRepository;

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class FirmaForm extends VerticalLayout implements KeyNotifier {

    private final FirmaRepository firmaRepo;

    private Firma firma;

    final Grid firmaGrid;

    private final TextField nazwaFirmy = new TextField("Nazwa");
    private final TextField miasto = new TextField("Miasto");
    private final TextField kraj = new TextField("Kraj");

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final Button edit = new Button("Edytuj");
    private final HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);
    private final HorizontalLayout formularz = new HorizontalLayout(nazwaFirmy, miasto, kraj);

    Binder<Firma> binderFirma = new Binder<>(Firma.class);

    private ChangeHandler changeHandler;

    @Autowired
    public FirmaForm(FirmaRepository firmaRepo) {
        this.firmaRepo = firmaRepo;

        delete.setEnabled(false);

        this.firmaGrid = new Grid<>(Firma.class);

        add(buttonBar, formularz);

        binderFirma.bind(nazwaFirmy, Firma::getNazwaFirmy, Firma::setNazwaFirmy);
        binderFirma.bind(miasto, Firma::getMiasto, Firma::setMiasto);
        binderFirma.bind(kraj, Firma::getKraj, Firma::setKraj);

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
        firmaRepo.save(firma);
        firmaGrid.select(null);
        changeHandler.onChange();
    }

    void delete() {
        firmaGrid.select(null);
        firmaRepo.delete(firma);
        changeHandler.onChange();
    }

    void cancel() {
        firmaGrid.select(null);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editFirma(Firma f) {
        if (f == null) {
            setVisible(false);
            return;
        }
        final boolean firmaIstnieje = f.getId() != null;
        if (firmaIstnieje) {
            firma = firmaRepo.findById(f.getId()).get();
            delete.setEnabled(true);
        } else {
            firma = f;
            nazwaFirmy.focus();
        }

        binderFirma.setBean(firma);
        setVisible(true);

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
