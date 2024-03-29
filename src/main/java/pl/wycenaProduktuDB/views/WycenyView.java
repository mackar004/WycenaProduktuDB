package pl.wycenaProduktuDB.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import pl.wycenaProduktuDB.forms.WycenaForm;
import pl.wycenaProduktuDB.model.Inwestycja;
import pl.wycenaProduktuDB.model.Wycena;
import pl.wycenaProduktuDB.repository.InwestycjaRepository;
import pl.wycenaProduktuDB.repository.MaterialyRepository;
import pl.wycenaProduktuDB.repository.MaterialyUzyteRepository;
import pl.wycenaProduktuDB.repository.WycenaRepository;

/**
 *
 * @author m
 */
@Route("user/firmy/inwestycje/wyceny")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("RoMa - Wyceny")
public class WycenyView extends VerticalLayout implements HasUrlParameter<String> {

    private final WycenaRepository wycenaRepository;
    private final InwestycjaRepository inwestycjaRepository;
    private final MaterialyRepository materialyRepository;
    private final MaterialyUzyteRepository materialyUzyteRepository;

    private Wycena wycena;

    private Inwestycja inwestycja;
    final WycenaForm wycenaForm;

    private final Grid wycenaGrid;

    private final Button nowaWycena;
    private final Button wyswietlWycene;

    final Dialog dialogWycena;

    Binder<Wycena> binderWycena = new Binder<>(Wycena.class);

    private WycenaForm.ChangeHandler changeHandler;

    private WycenyView(WycenaRepository wycenaRepository, InwestycjaRepository inwestycjaRepository,
            MaterialyRepository materialyRepository, MaterialyUzyteRepository materialyUzyteRepository) {

        add(new Button("Powrót", event -> {
            //Powrót z parametrem dla automatycznego wczytania tej samej firmy
            if (this.inwestycja.getFirma() == null) {
                getUI().ifPresent(ui -> ui.navigate("user/firmy/inwestycje/"));
            } else {
                getUI().ifPresent(ui -> ui.navigate("user/firmy/inwestycje/" + this.inwestycja.getFirma().getId()));

            }
        }));

        this.wycenaRepository = wycenaRepository;
        this.inwestycjaRepository = inwestycjaRepository;
        this.materialyRepository = materialyRepository;
        this.materialyUzyteRepository = materialyUzyteRepository;

        this.wycenaForm = new WycenaForm(wycenaRepository, inwestycjaRepository,
                materialyRepository, materialyUzyteRepository);
        wycenaGrid = new Grid<>(Wycena.class);

        this.dialogWycena = new Dialog();

        nowaWycena = new Button("Nowa", VaadinIcon.PLUS.create());
        wyswietlWycene = new Button("Wyświetl");

        wyswietlWycene.setEnabled(false);

        dialogWycena.add(this.wycenaForm);
        dialogWycena.setWidth("600px");
        dialogWycena.setHeight("400px");
        dialogWycena.setCloseOnEsc(false);
        dialogWycena.setCloseOnOutsideClick(false);

        wycenaGrid.setColumns("id", "typPrzekrycia", "cenaKoncowa");
        wycenaGrid.getColumnByKey("id").setWidth("120px").setFlexGrow(0).setSortProperty("id");

        HorizontalLayout menuBar = new HorizontalLayout(nowaWycena, wyswietlWycene);

        // Stworzenie i edytowanie nowej wyceny po kliknięciu przycisku Nowa
        nowaWycena.addClickListener(e -> {
            dialogWycena.open();
            this.wycenaForm.editWycena(new Wycena(0, 0, 0, 0, 0, 0.0, 0.0, this.inwestycja), this.inwestycja, true);
        });

        wyswietlWycene.addClickListener(e -> {
            dialogWycena.open();
            wycenaForm.listMaterialy();
            wyswietlWycene.setEnabled(false);
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
                this.wycenaForm.editWycena((Wycena) e.getValue(), this.inwestycja, false);
            }
        });

        // Zamykanie okna po kliknięciu na przycisk i odświeżenie danych
        this.wycenaForm.setChangeHandler(() -> {
            this.wycenaForm.setVisible(false);
            listWyceny();
            wyswietlWycene.setEnabled(false);
            dialogWycena.close();
        });

        add(menuBar, wycenaGrid);

        listWyceny();
    }

    private void listWyceny() {
        wycenaGrid.setItems(wycenaRepository.findByInwestycja(this.inwestycja));
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter == null || parameter.isEmpty() || parameter.equals("")) {
            this.inwestycja = null;
            getUI().ifPresent(ui -> ui.navigate("main"));
        } else {
            this.inwestycja = (Inwestycja) inwestycjaRepository.findById(Long.parseLong(parameter));
            listWyceny();
        }
    }
}
