/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import pl.torun.roma.RoMa3.forms.WycenaForm;
import pl.torun.roma.RoMa3.model.Inwestycja;
import pl.torun.roma.RoMa3.model.Wycena;
import pl.torun.roma.RoMa3.repository.InwestycjaRepository;
import pl.torun.roma.RoMa3.repository.WycenaRepository;

/**
 *
 * @author m
 */
@Route("user/firmy/inwestycje/wyceny")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("RoMa - Wyceny")
public class WycenyView extends VerticalLayout{ // implements HasUrlParameter<String> {

    private final WycenaRepository wycenaRepository;
    private final InwestycjaRepository inwestycjaRepository;

    private Wycena wycena;

    private Inwestycja inwestycja = null;
    private final WycenaForm wycenaForm;

    private final Grid wycenaGrid;

    private final Button nowaWycena;
    private final Button wyswietlWycene;

    final Dialog dialogWycena;

    Binder<Wycena> binderWycena = new Binder<>(Wycena.class);

    private WycenaForm.ChangeHandler changeHandler;

    private WycenyView(WycenaRepository wycenaRepository, InwestycjaRepository inwestycjaRepository) {

        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy/inwestycje"));
        }));

        this.wycenaRepository = wycenaRepository;
        this.inwestycjaRepository = inwestycjaRepository;

        this.wycenaForm = new WycenaForm(wycenaRepository, inwestycjaRepository);
        wycenaGrid = new Grid<>(Wycena.class);

        this.dialogWycena = new Dialog();

        nowaWycena = new Button("Nowa", VaadinIcon.PLUS.create());
        //TYMCZASOWA FUNKCJA - Wyświetlanie zapisanych wycen testowych
        wyswietlWycene = new Button("Wyświetl");

        wyswietlWycene.setEnabled(true);

        dialogWycena.add(this.wycenaForm);
        dialogWycena.setWidth("600px");
        dialogWycena.setHeight("400px");
        dialogWycena.setCloseOnEsc(false);
        dialogWycena.setCloseOnOutsideClick(false);

        wycenaGrid.setColumns("id", "typPrzekrycia", "cenaKoncowa");
        wycenaGrid.getColumnByKey("id").setWidth("250px").setFlexGrow(0).setSortProperty("id");

        HorizontalLayout menuBar = new HorizontalLayout(nowaWycena, wyswietlWycene);

        // Stworzenie i edytowanie nowej wyceny po kliknięciu przycisku Nowa
        nowaWycena.addClickListener(e -> {
            dialogWycena.open();
            // this.wycenaForm.editWycena(new Wycena("", 0, 0.0, 0.0), this.inwestycja);
      //Tymczasowa wycena przez przekazywania TypuPrzekrycia do kostruktora
            //this.wycenaForm.editWycena(new Wycena(0, 0, 0.0, 0.0), this.inwestycja);
            /*
            TEST WYCENA BEZ PRZYPISANEJ INWESTYCJI
            */
            this.wycenaForm.editWycena(new Wycena(0, 0, 0.0, 0.0));
        });

        wyswietlWycene.addClickListener(e -> {
            System.out.println(wycenaRepository.findAll());
        });

        wycenaGrid.asSingleSelect().addValueChangeListener(e -> {
            //sprawdzenie czy w tabeli jest wybrany jakiś klucz i odpowiednie ustawienie dostępności przycisków
            if (wycenaGrid.getSelectedItems().isEmpty()) {
                nowaWycena.setEnabled(true);
                wyswietlWycene.setEnabled(false);
                this.wycena = null;
            } else {
                nowaWycena.setEnabled(false);
                wyswietlWycene.setEnabled(true);
                this.wycena = (Wycena) e.getValue();
                //         this.wycenaForm.editWycena((Wycena) e.getValue());
            }
        });

        add(menuBar, wycenaGrid);

    }

//    @Override
//    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
//        if (parameter == null || parameter.isEmpty() || parameter.equals("")) {
//
//            System.out.println('1' + parameter);
//            this.inwestycja = null;
////  Nie działą poniższe przekierowanie gdy nie jest przekazywany żaden parametr
//            //getUI().ifPresent(ui -> ui.navigate("main"));
//            //getUI().get().navigate("main");
//            //        anulujFirme.setEnabled(false);
//        } else {
//            this.inwestycja = (Inwestycja) inwestycjaRepository.findById(Long.parseLong(parameter));
////            anulujFirme.setEnabled(true);
////            aktualnaFirma.setReadOnly(false);
////            aktualnaFirma.setValue(this.firma.toString());
////            aktualnaFirma.setReadOnly(true);
//        }
//    }
}
