/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.torun.roma.RoMa3.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.util.StringUtils;
import pl.torun.roma.RoMa3.forms.InwestycjaForm;
import pl.torun.roma.RoMa3.model.Firma;
import pl.torun.roma.RoMa3.model.Inwestycja;
import pl.torun.roma.RoMa3.repository.FirmaRepository;
import pl.torun.roma.RoMa3.repository.InwestycjaRepository;

/**
 *
 * @author m
 */
@Route("user/firmy/inwestycje")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("RoMa - Inwestycje")
public class InwestycjeView extends VerticalLayout implements HasUrlParameter<String> {

    private final InwestycjaRepository inwestycjaRepository;
    private final FirmaRepository firmaRepository;

    private Inwestycja inwestycja;
    private Firma firma = null;
    final InwestycjaForm inwestycjaForm;

    private TextField nazwaInwestycji = new TextField("Nazwa");
    private TextField miasto = new TextField("Miasto");
    private TextField nazwaFirma = new TextField("Firma");
    private TextField aktualnaFirma = new TextField();
    final Grid inwestycjaGrid;
    private final Button nowaInwestycja;
    private final Button anulujFirme;
    private final Button edytuj;

    final TextField filtrInwestycja;
    final Dialog dialogInwestycja;

    private InwestycjeView(FirmaRepository firmaRepository, InwestycjaRepository inwestycjaRepository) {

        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy"));
        }));

        this.firmaRepository = firmaRepository;
        this.inwestycjaRepository = inwestycjaRepository;
        this.inwestycjaForm = new InwestycjaForm(inwestycjaRepository);//, firma);
        inwestycjaGrid = new Grid<>(Inwestycja.class);

        aktualnaFirma.setLabel("Aktualna firma:");
        aktualnaFirma.setReadOnly(true);

        this.filtrInwestycja = new TextField();
        this.dialogInwestycja = new Dialog();
        
        dialogInwestycja.add(this.inwestycjaForm);
        dialogInwestycja.setWidth("600px");
        dialogInwestycja.setHeight("400px");
        dialogInwestycja.setCloseOnEsc(false);
        dialogInwestycja.setCloseOnOutsideClick(false);

        //Przyciski
        nowaInwestycja = new Button("Nowa", VaadinIcon.PLUS.create());
        anulujFirme = new Button("Anuluj wybór", VaadinIcon.CLOSE_CIRCLE_O.create());
        anulujFirme.setHeight("68px");
        edytuj = new Button("Edytuj");
        
        anulujFirme.setEnabled(false);

        HorizontalLayout filterBar = new HorizontalLayout(filtrInwestycja, nowaInwestycja, edytuj);
        HorizontalLayout aktualnaFirmaBar = new HorizontalLayout(aktualnaFirma, anulujFirme);

        filtrInwestycja.setPlaceholder("Szukaj w bazie");
        filtrInwestycja.setValueChangeMode(ValueChangeMode.EAGER);
        filtrInwestycja.addValueChangeListener(e -> listInwestycje(e.getValue()));

        inwestycjaGrid.setColumns("inwestycjaNazwa", "inwestycjaMiasto", "firma");
        inwestycjaGrid.getColumnByKey("inwestycjaNazwa").setWidth("250px").setFlexGrow(0).setSortProperty("inwestycjaNazwa");

        //Listenery
        anulujFirme.addClickListener(e -> {
            this.firma = null;
            anulujFirme.setEnabled(false);
            filtrInwestycja.setPlaceholder("");
            aktualnaFirma.setReadOnly(false);
            aktualnaFirma.setValue("---");
            aktualnaFirma.setReadOnly(true);
            inwestycjaGrid.setItems(inwestycjaRepository.findAll());
        });
        
        // Stworzenie i edytowanie nowej firmy po kliknięciu przycisku Nowa Inwestycja
        nowaInwestycja.addClickListener(e -> {
            dialogInwestycja.open();
            this.inwestycjaForm.editInwestycja(new Inwestycja("", "", this.firma),this.firma);
        });

        inwestycjaGrid.asSingleSelect().addValueChangeListener(e -> {
            nowaInwestycja.setEnabled(false);
            edytuj.setEnabled(true);
            //this.inwestycjaForm.editInwestycja((Inwestycja) e.getValue());
        });
        
        // Zamykanie okna po kliknięciu na przycisk i odświeżenie danych
        this.inwestycjaForm.setChangeHandler(() -> {
            this.inwestycjaForm.setVisible(false);
            listInwestycje(filtrInwestycja.getValue());
            nowaInwestycja.setEnabled(true);
            edytuj.setEnabled(false);
            dialogInwestycja.close();
        });

        add(filterBar, aktualnaFirmaBar, inwestycjaGrid);

        listInwestycje(filtrInwestycja.getValue());

    }

    private void listInwestycje(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            inwestycjaGrid.setItems(inwestycjaRepository.findAll());
        } else {
            inwestycjaGrid.setItems(inwestycjaRepository.findByInwestycjaNazwaContainsIgnoreCase(filterText));

        }
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter == null) {
            this.firma = null;
            aktualnaFirma.setPlaceholder("---");
            anulujFirme.setEnabled(false);
        } else {
            this.firma = (Firma) firmaRepository.findByNazwaFirmy(parameter);
            filtrInwestycja.setPlaceholder(this.firma.toString());
            anulujFirme.setEnabled(true);
            aktualnaFirma.setReadOnly(false);
            aktualnaFirma.setValue(this.firma.toString());
            aktualnaFirma.setReadOnly(true);
        }
    }
}
