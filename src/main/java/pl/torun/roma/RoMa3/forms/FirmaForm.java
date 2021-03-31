/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.forms;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import pl.torun.roma.RoMa3.model.Firma;
import pl.torun.roma.RoMa3.model.Inwestycja;
import pl.torun.roma.RoMa3.repository.FirmaRepository;
import pl.torun.roma.RoMa3.repository.InwestycjaRepository;

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class FirmaForm extends VerticalLayout implements KeyNotifier {

    private final FirmaRepository firmaRepo;
    private final InwestycjaRepository inwestRepo;

    private Firma firma;

    private final TextField nazwaFirmy = new TextField("Nazwa");
    private final TextField miasto = new TextField("Miasto");
    private final TextField kraj = new TextField("Kraj");

    private final TextField nazwaInwestycji = new TextField("Nazwa inwestycji");
    private final TextField miastoInwestycji = new TextField("Miasto inwestycji");

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button cancelInw = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final Button edit = new Button("Edytuj");

    private final Button saveInwest = new Button("Zapisz");

    private final HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);
    private final HorizontalLayout buttonBarInw = new HorizontalLayout(saveInwest, cancelInw);
    private final HorizontalLayout polaFirmy = new HorizontalLayout(nazwaFirmy, miasto, kraj);
    private final HorizontalLayout polaInwestycji = new HorizontalLayout(nazwaInwestycji, miastoInwestycji);

    Binder<Firma> binderFirma = new Binder<>(Firma.class);
    Binder<Inwestycja> binderInwestycja = new Binder<>(Inwestycja.class);

    private FirmaForm.ChangeHandler changeHandler;

    @Autowired
    public FirmaForm(FirmaRepository firmaRepo, InwestycjaRepository inwestRepo) {
        this.firmaRepo = firmaRepo;
        this.inwestRepo = inwestRepo;

        delete.setEnabled(false);

        add(buttonBar, buttonBarInw, polaFirmy, polaInwestycji);

        binderFirma.bind(nazwaFirmy, Firma::getNazwaFirmy, Firma::setNazwaFirmy);
        binderFirma.bind(miasto, Firma::getMiasto, Firma::setMiasto);
        binderFirma.bind(kraj, Firma::getKraj, Firma::setKraj);

        setSpacing(false);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        save.addClickListener(e -> save());
        saveInwest.addClickListener(e -> saveI());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        cancelInw.addClickListener(e -> cancel());
        setVisible(false);
    }

    void save() {
        firmaRepo.save(firma);
        changeHandler.onChange();
    }

    void saveI() {
        inwestRepo.save(new Inwestycja(nazwaInwestycji.getValue(), miastoInwestycji.getValue(), firma));
        nazwaInwestycji.setValue("");
        miastoInwestycji.setValue("");
        changeHandler.onChange();
    }

    void delete() {
        firmaRepo.delete(firma);
        firmaRepo.flush();
        changeHandler.onChange();
    }

    void cancel() {
        nazwaInwestycji.setValue("");
        miastoInwestycji.setValue("");
        changeHandler.onChange();
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editFirma(Firma f, boolean nowInw) {
        if (f == null) {
            setVisible(false);
            return;
        }

        final boolean firmaIstnieje = f.getId() != null;

        polaFirmy.setVisible(!firmaIstnieje && !nowInw);
        buttonBar.setVisible(!firmaIstnieje && !nowInw);

        polaInwestycji.setVisible(firmaIstnieje && nowInw);
        buttonBarInw.setVisible(firmaIstnieje && nowInw);

        if (firmaIstnieje && !nowInw) {
            polaFirmy.setVisible(true);
            buttonBar.setVisible(true);
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
