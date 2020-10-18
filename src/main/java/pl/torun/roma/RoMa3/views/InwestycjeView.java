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
    private Firma firma;
    final InwestycjaForm inwestForm;

    private TextField nazwaInwestycji = new TextField("Nazwa");
    private TextField miasto = new TextField("Miasto");
    private TextField nazwaFirma = new TextField("Firma");
    final Grid inwestycjaGrid;

    private final Button nowaInwestycja;
    private final Button edytuj;
    final TextField filtrInwestycja;
    final Dialog dialogInwestycja;

    private InwestycjeView(FirmaRepository firmaRepository, InwestycjaRepository inwestycjaRepository) {

        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy"));
        }));

        this.firmaRepository = firmaRepository;
        this.inwestycjaRepository = inwestycjaRepository;
        this.inwestForm = new InwestycjaForm(inwestycjaRepository);
        inwestycjaGrid = new Grid<>(Inwestycja.class);

        nowaInwestycja = new Button("Nowa", VaadinIcon.PLUS.create());
        edytuj = new Button("Edytuj");

        this.filtrInwestycja = new TextField();
        this.dialogInwestycja = new Dialog();

        HorizontalLayout filterBar = new HorizontalLayout(filtrInwestycja, nowaInwestycja, edytuj);

        filtrInwestycja.setPlaceholder("Szukaj w bazie");
        filtrInwestycja.setValueChangeMode(ValueChangeMode.EAGER);
        filtrInwestycja.addValueChangeListener(e -> listInwestycje(e.getValue()));

        inwestycjaGrid.setColumns("inwestycjaNazwa", "inwestycjaMiasto", "firma");
        inwestycjaGrid.getColumnByKey("inwestycjaNazwa").setWidth("250px").setFlexGrow(0).setSortProperty("inwestycjaNazwa");

        inwestycjaGrid.asSingleSelect().addValueChangeListener(e -> {
            nowaInwestycja.setEnabled(false);
            //dialog.open();
            edytuj.setEnabled(true);
            this.inwestForm.editInwestycja((Inwestycja) e.getValue());
        });

        add(filterBar, inwestycjaGrid);

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
        //setText(String.format("Hello, %s!", parameter));
        if (parameter == null) {
    //    if (parameter.isEmpty()) {
            //this.firma = null;
        } else {
            this.firma = (Firma) firmaRepository.findByNazwaFirmy(parameter);
            filtrInwestycja.setPlaceholder(this.firma.toString());
        }
    }
}
