package pl.wycenaProduktuDB.views;

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
import pl.wycenaProduktuDB.forms.InwestycjaForm;
import pl.wycenaProduktuDB.model.Firma;
import pl.wycenaProduktuDB.model.Inwestycja;
import pl.wycenaProduktuDB.repository.FirmaRepository;
import pl.wycenaProduktuDB.repository.InwestycjaRepository;

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

    private TextField aktualnaFirma = new TextField();
    final Grid inwestycjaGrid;
    private final Button nowaInwestycja;
    private final Button anulujFirme;
    private final Button edytuj;
    private final Button wyswietlWyceny;

    final TextField filtrInwestycja;
    final Dialog dialogInwestycja;

    HorizontalLayout filterBar = new HorizontalLayout();

    private InwestycjeView(FirmaRepository firmaRepository, InwestycjaRepository inwestycjaRepository) {
        add(new Button("Powrót", event -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy"));
        }));

        this.firmaRepository = firmaRepository;
        this.inwestycjaRepository = inwestycjaRepository;
        this.inwestycjaForm = new InwestycjaForm(inwestycjaRepository);
        inwestycjaGrid = new Grid<>(Inwestycja.class);

        aktualnaFirma.setLabel("Aktualna firma:");
        aktualnaFirma.setReadOnly(true);

        this.filtrInwestycja = new TextField();
        this.dialogInwestycja = new Dialog();

        dialogInwestycja.add(this.inwestycjaForm);
        dialogInwestycja.setWidth("630px");
        dialogInwestycja.setHeight("400px");
        dialogInwestycja.setCloseOnEsc(false);
        dialogInwestycja.setCloseOnOutsideClick(false);

        //Przyciski
        nowaInwestycja = new Button("Nowa", VaadinIcon.PLUS.create());
        anulujFirme = new Button("Anuluj wybór", VaadinIcon.CLOSE_CIRCLE_O.create());
        anulujFirme.setHeight("68px");
        edytuj = new Button("Edytuj");
        wyswietlWyceny = new Button("WYCENY");

        nowaInwestycja.setEnabled(false);
        edytuj.setEnabled(false);
        wyswietlWyceny.setEnabled(false);
        anulujFirme.setEnabled(false);

        this.filterBar = new HorizontalLayout(filtrInwestycja);
        HorizontalLayout editBar = new HorizontalLayout(nowaInwestycja, edytuj, wyswietlWyceny);
        HorizontalLayout aktualnaFirmaBar = new HorizontalLayout(aktualnaFirma, anulujFirme);

        filterBar.setVisible(false);

        filtrInwestycja.setPlaceholder("Szukaj w bazie");
        filtrInwestycja.setValueChangeMode(ValueChangeMode.EAGER);
        filtrInwestycja.addValueChangeListener(e -> listInwestycje(e.getValue()));

        inwestycjaGrid.setColumns("inwestycja_id", "inwestycjaNazwa", "inwestycjaMiasto", "firma");
        inwestycjaGrid.getColumnByKey("inwestycja_id").setWidth("120px").setFlexGrow(0).setSortProperty("inwestycja_id");

        anulujFirme.addClickListener(e -> {
            this.firma = null;
            anulujFirme.setEnabled(false);
            filtrInwestycja.setPlaceholder("");
            aktualnaFirma.setVisible(false);
            anulujFirme.setVisible(false);
            filterBar.setVisible(true);
            inwestycjaGrid.setItems(inwestycjaRepository.findAll());
        });

        // Stworzenie i edytowanie nowej firmy po kliknięciu przycisku Nowa Inwestycja
        nowaInwestycja.addClickListener(e -> {
            dialogInwestycja.open();
            this.inwestycjaForm.editInwestycja(new Inwestycja("", "", this.firma), this.firma);
        });

        edytuj.addClickListener(e -> {
            dialogInwestycja.open();
            edytuj.setEnabled(false);
        });

        wyswietlWyceny.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate("user/firmy/inwestycje/wyceny/"
                    + this.inwestycja.getInwestycja_id().toString().replace("[", "").replace("]", "")));
        });

        inwestycjaGrid.asSingleSelect().addValueChangeListener(e -> {
            //sprawdzenie czy w tabeli jest wybrany jakiś klucz i odpowiednie ustawienie dostępności przycisków
            if (inwestycjaGrid.getSelectedItems().isEmpty()) {
                nowaInwestycja.setEnabled(true);
                edytuj.setEnabled(false);
                wyswietlWyceny.setEnabled(false);
                this.inwestycja = null;
            } else {
                nowaInwestycja.setEnabled(false);
                edytuj.setEnabled(true);
                wyswietlWyceny.setEnabled(true);
                this.inwestycja = (Inwestycja) e.getValue();
                if (this.inwestycja.getFirma() != null) {
                    this.firma = (Firma) firmaRepository.findByNazwaFirmy(this.inwestycja.getFirma().getNazwaFirmy());
                }
                this.inwestycjaForm.editInwestycja(this.inwestycja, this.firma);
            }
        });

        // Zamykanie okna po kliknięciu na przycisk i odświeżenie danych
        this.inwestycjaForm.setChangeHandler(() -> {
            this.inwestycjaForm.setVisible(false);
            listInwestycje(filtrInwestycja.getValue());
            nowaInwestycja.setEnabled(true);
            edytuj.setEnabled(false);
            wyswietlWyceny.setEnabled(false);
            dialogInwestycja.close();
        });

        add(filterBar, aktualnaFirmaBar, editBar, inwestycjaGrid);

        listInwestycje(filtrInwestycja.getValue());

    }

    private void listInwestycje(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            if (aktualnaFirma.isEmpty()) {
                inwestycjaGrid.setItems(inwestycjaRepository.findAll());
            } else {
                listInwestycje(this.firma);
            }
        } else {
            inwestycjaGrid.setItems(inwestycjaRepository.findInwestycjaByInwestycjaNazwaContainsIgnoreCaseOrInwestycjaMiastoContainsIgnoreCaseOrFirmaNazwaFirmyContainsIgnoreCase(filterText, filterText, filterText));
        }
    }

    private void listInwestycje(Firma firma) {
        if (StringUtils.isEmpty(firma)) {
            inwestycjaGrid.setItems(inwestycjaRepository.findAll());
        } else {
            inwestycjaGrid.setItems(inwestycjaRepository.findByFirma(firma));
        }
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter == null || parameter.isEmpty() || parameter.equals("")) {
            this.firma = null;
            anulujFirme.setEnabled(false);
            filtrInwestycja.setPlaceholder("");
            aktualnaFirma.setVisible(false);
            anulujFirme.setVisible(false);
            filterBar.setVisible(true);
            getUI().ifPresent(ui -> ui.navigate("main"));
        } else {
            this.firma = firmaRepository.findById(Long.parseLong(parameter));
            nowaInwestycja.setEnabled(true);
            anulujFirme.setEnabled(true);
            aktualnaFirma.setReadOnly(false);
            aktualnaFirma.setValue(this.firma.toString());
            aktualnaFirma.setReadOnly(true);
            listInwestycje(this.firma);
        }
    }
}
