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
import pl.torun.roma.RoMa3.forms.InwestycjaForm;
import pl.torun.roma.RoMa3.model.Inwestycja;
import pl.torun.roma.RoMa3.repository.InwestycjaRepository;

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
    private final Button edytuj;
    private final Button pokazInwestycje;
    private final Button dodajInwestycje;

    final TextField filtrFirma;

    final Dialog dialogFirma;
    final Dialog dialogInwestycja;

    private FirmyView(FirmaRepository firmaRepo, FirmaForm firmaForm,
            InwestycjaRepository inwestRepo) {

        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("main"));
        }));

        this.firmaRepository = firmaRepo;
        this.firmaForm = new FirmaForm(firmaRepo, inwestRepo);
        firmaGrid = new Grid<>(Firma.class);

        this.filtrFirma = new TextField();
        this.dialogFirma = new Dialog();
        this.dialogInwestycja = new Dialog();

        nowaFirma = new Button("Nowa", VaadinIcon.PLUS.create());
        edytuj = new Button("Edytuj");
        pokazInwestycje = new Button("Wyświetl inwestycje");
        dodajInwestycje = new Button("Dodaj inwestycję");

        edytuj.setEnabled(false);
        pokazInwestycje.setEnabled(false);

        HorizontalLayout filterBar = new HorizontalLayout(filtrFirma, nowaFirma, edytuj, pokazInwestycje, dodajInwestycje);

        filtrFirma.setPlaceholder("Szukaj w bazie");
        filtrFirma.setValueChangeMode(ValueChangeMode.EAGER);
        filtrFirma.addValueChangeListener(e -> listFirmy(e.getValue()));

        dialogFirma.add(this.firmaForm);
        dialogFirma.setWidth("600px");
        dialogFirma.setHeight("400px");
        dialogFirma.setCloseOnEsc(false);
        dialogFirma.setCloseOnOutsideClick(false);

        firmaGrid.setColumns("nazwaFirmy", "miasto", "kraj");
        firmaGrid.getColumnByKey("nazwaFirmy").setWidth("250px").setFlexGrow(0).setSortProperty("nazwaFirmy");

        firmaGrid.asSingleSelect().addValueChangeListener(e -> {
            //sprawdzenie czy w tabeli jest wybrany jakiś klucz i odpowiednie ustawienie dostępności przycisków
            if (firmaGrid.getSelectedItems().isEmpty()) {
                nowaFirma.setEnabled(true);
                edytuj.setEnabled(false);
                pokazInwestycje.setEnabled(false);
                dodajInwestycje.setEnabled(false);
                this.firma = null;
            } else {
                nowaFirma.setEnabled(false);
                edytuj.setEnabled(true);
                pokazInwestycje.setEnabled(true);
                dodajInwestycje.setEnabled(true);
                this.firma = (Firma) e.getValue();
                this.firmaForm.editFirma((Firma) e.getValue(), false);
            }
        });

        nowaFirma.addClickListener(e -> {
            nazwaFirmy.setEnabled(true);
            miasto.setEnabled(true);
            kraj.setEnabled(true);
            nowaFirma.setEnabled(false);
            nazwaFirmy.focus();
        });

        edytuj.addClickListener(e -> {
            dialogFirma.open();
            edytuj.setEnabled(false);
            pokazInwestycje.setEnabled(false);
        });

        pokazInwestycje.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy/inwestycje/"
                    + firmaGrid.getSelectedItems().toString().replace("[", "").replace("]", "")));

            edytuj.setEnabled(false);
            pokazInwestycje.setEnabled(false);
        });

        // Stworzenie i edytowanie nowej firmy po kliknięciu przycisku Nowy
        nowaFirma.addClickListener(e -> {
            dialogFirma.open();
            this.firmaForm.editFirma(new Firma("", "", ""), false);
        });

        dodajInwestycje.addClickListener(e -> {
            dialogFirma.open();
            edytuj.setEnabled(false);
            pokazInwestycje.setEnabled(false);
            this.firmaForm.editFirma(this.firma, true);
        });

        // Zamykanie okna po kliknięciu na przycisk i odświeżenie danych
        this.firmaForm.setChangeHandler(() -> {
            this.firmaForm.setVisible(false);
            listFirmy(filtrFirma.getValue());
            nowaFirma.setEnabled(true);
            edytuj.setEnabled(false);
            pokazInwestycje.setEnabled(false);
            dialogFirma.close();
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
