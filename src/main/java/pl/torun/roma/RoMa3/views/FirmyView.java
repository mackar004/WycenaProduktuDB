/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.views;

import pl.torun.roma.RoMa3.model.Firma;
import pl.torun.roma.RoMa3.repository.FirmaRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import pl.torun.roma.RoMa3.forms.FirmaForm;

/**
 *
 * @author m
 */
@Route("user/firmy")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("RoMa - Firmy")
public class FirmyView extends VerticalLayout {

    private final FirmaRepository firmaRepository;

    private Firma firma;
    final FirmaForm firmaForm;

    private TextField nazwaFirmy = new TextField("Nazwa");
    private TextField miasto = new TextField("Miasto");
    private TextField kraj = new TextField("Kraj");
    final Grid firmaGrid;

    private final Button nowaFirma;
    final TextField filtrFirma;
    final Dialog dialog;

    private FirmyView(FirmaRepository repo, FirmaForm firmaForm) {

        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("main"));
        }));

        this.firmaRepository = repo;
        this.firmaForm = new FirmaForm(repo);
        firmaGrid = new Grid<>(Firma.class);

        this.filtrFirma = new TextField();
        this.dialog = new Dialog();

        this.nowaFirma = new Button("Nowa", VaadinIcon.PLUS.create());

        HorizontalLayout filterBar = new HorizontalLayout(filtrFirma, nowaFirma);

        filtrFirma.setPlaceholder("Szukaj w bazie");
        filtrFirma.setValueChangeMode(ValueChangeMode.EAGER);
        filtrFirma.addValueChangeListener(e -> listFirmy(e.getValue()));

        dialog.add(this.firmaForm);
        dialog.setWidth("600px");
        dialog.setHeight("400px");
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        firmaGrid.setColumns("nazwaFirmy", "miasto", "kraj");
        firmaGrid.getColumnByKey("nazwaFirmy").setWidth("250px").setFlexGrow(0).setSortProperty("nazwaFirmy");

        firmaGrid.asSingleSelect().addValueChangeListener(e -> {
            nowaFirma.setEnabled(false);
            dialog.open();
            this.firmaForm.editFirma((Firma) e.getValue());
        });

        nowaFirma.addClickListener(e -> {
            nazwaFirmy.setEnabled(true);
            miasto.setEnabled(true);
            kraj.setEnabled(true);
            nowaFirma.setEnabled(false);
            nazwaFirmy.focus();
        });

        // Stworzenie i edytowanie nowego pracownika po kliknięciu przycisku Nowy
        nowaFirma.addClickListener(e -> {
            dialog.open();
            this.firmaForm.editFirma(new Firma("", "", ""));
        });

        // Zamykanie okna po kliknięciu na przycisk i odświeżenie danych
        this.firmaForm.setChangeHandler(() -> {
            this.firmaForm.setVisible(false);
            listFirmy(filtrFirma.getValue());
            nowaFirma.setEnabled(true);
            dialog.close();
        });

        add(filterBar, firmaGrid);

        listFirmy(filtrFirma.getValue());

    }

    private void listFirmy(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            firmaGrid.setItems(firmaRepository.findAll());
        } else {
            firmaGrid.setItems(firmaRepository.findByNazwaFirmyContainsIgnoreCase(filterText));

        }
    }

}
