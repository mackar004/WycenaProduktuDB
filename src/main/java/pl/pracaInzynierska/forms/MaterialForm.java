/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pracaInzynierska.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import pl.pracaInzynierska.model.Materialy;
import pl.pracaInzynierska.dane.TypMaterialu;
import pl.pracaInzynierska.repository.MaterialyRepository;

/**
 *
 * @author m
 */
@SpringComponent
@UIScope
public class MaterialForm extends VerticalLayout {

    private final MaterialyRepository materialyRepo;

    private Materialy material;

    final Grid materialyGrid;

    private final TextField nazwaMaterialu = new TextField("Nazwa");
    private final TextField cena = new TextField("Cena");
    
    ComboBox<TypMaterialu> typMaterialu = new ComboBox<>("Typ Materialu", TypMaterialu.values());

    private final Button save = new Button("Zapisz", VaadinIcon.CHECK.create());
    private final Button cancel = new Button("Anuluj");
    private final Button delete = new Button("Usuń", VaadinIcon.TRASH.create());
    private final HorizontalLayout buttonBar = new HorizontalLayout(save, cancel, delete);
    private final VerticalLayout formularz = new VerticalLayout(nazwaMaterialu, cena, typMaterialu);

    Binder<Materialy> binderMaterialy = new Binder<>(Materialy.class);

    private MaterialForm.ChangeHandler changeHandler;

    @Autowired
    public MaterialForm(MaterialyRepository materialyRepo) {
        this.materialyRepo = materialyRepo;
        delete.setEnabled(false);

        this.materialyGrid = new Grid<>(Materialy.class);

        add(buttonBar, formularz);

        binderMaterialy.bind(nazwaMaterialu, Materialy::getNazwa, Materialy::setNazwa);
        binderMaterialy.bind(cena, Materialy::getCena, Materialy::setCena);
        binderMaterialy.bind(typMaterialu, Materialy::getTypMaterialu, Materialy::setTypMaterialu);

        setSpacing(false);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> cancel());
        setVisible(false);
    }

    void save() {
        materialyRepo.save(material);
        materialyGrid.select(null);
        changeHandler.onChange();
    }

    void delete() {
        materialyGrid.select(null);
        materialyRepo.delete(material);
        changeHandler.onChange();
    }

    void cancel() {
        materialyGrid.select(null);
        changeHandler.onChange();
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editMaterial(Materialy m) {
        if (m == null) {
            setVisible(false);
            return;
        }
        final boolean materialIstnieje = m.getId() != null;
        if (materialIstnieje) {
            material = materialyRepo.findById(m.getId()).get();
            delete.setEnabled(true);
        } else {
            material = m;
            nazwaMaterialu.focus();
        }

        binderMaterialy.setBean(material);
        setVisible(true);

    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

}
